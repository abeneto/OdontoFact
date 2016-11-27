package com.dev.abeneto.charanifact.fragments;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.dev.abeneto.charanifact.R;
import com.dev.abeneto.charanifact.activities.MainActivity;
import com.dev.abeneto.charanifact.adapter.LineaFacturaAdapter;
import com.dev.abeneto.charanifact.custom_elements.CheckedLinearLayout;
import com.dev.abeneto.charanifact.db.DatabaseHelper;
import com.dev.abeneto.charanifact.pojo.GastosLaboratori;
import com.dev.abeneto.charanifact.pojo.LineaFactura;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alberto on 11/09/2016.
 */
public class Fragment3 extends Fragment {

    View inflated = null;
    DatabaseHelper dbHelper = null;
    ArrayList<LineaFactura> lineasDeFactura = null;
    Menu miMenu = null;


    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflated = inflater.inflate(R.layout.fragment_fragment3, container, false);
        dbHelper = new DatabaseHelper(getActivity().getApplicationContext());

        MainActivity activity = (MainActivity) getActivity();
        this.miMenu = activity.getMiMenu();

        miMenu.findItem(R.id.delete).setVisible(true);

        lineasDeFactura = null;
        try {
            //lineasDeFactura = (ArrayList<LineaFactura>) dbHelper.getLineaFacturaDao().queryForAll();
            Calendar calIni = Calendar.getInstance();
            lineasDeFactura = (ArrayList<LineaFactura>) dbHelper.getLineasFacturaOfMonth(calIni.get(Calendar.MONTH), calIni.get(Calendar.YEAR));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (lineasDeFactura == null) {
            lineasDeFactura = new ArrayList<LineaFactura>();
        } else {
            Collections.sort(lineasDeFactura, new Comparator<LineaFactura>() {
                @Override
                public int compare(LineaFactura lf1, LineaFactura lf2) {
                    return lf1.getFecha().compareTo(lf2.getFecha());
                }
            });
        }
        // Create the adapter to convert the array to views
        LineaFacturaAdapter adapter = new LineaFacturaAdapter(inflated.getContext(), lineasDeFactura);
        // Attach the adapter to a ListView
        ListView listView = (ListView) inflated.findViewById(R.id.listaLineasFactura);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        Button botonExcel = (Button) inflated.findViewById(R.id.botonGenerarFacturaExcel);
        Button botonPdf = (Button) inflated.findViewById(R.id.botonGenerarFacturaPdf);

        botonExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    generarFactura();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        botonPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Esta funcionalidad aun no está implementada.", Toast.LENGTH_SHORT).show();
            }
        });

        return inflated;
    }


    private void generarFactura() throws IOException {
        InputStream is = getActivity().getApplicationContext().getResources().openRawResource(R.raw.templatefactura);

        Workbook wb = new HSSFWorkbook(is);

        Sheet sheet = wb.getSheetAt(0);

        Cell cell = null;

        Calendar gc = GregorianCalendar.getInstance();
        int mes = gc.get(Calendar.MONTH) + 1;
        int anyo = gc.get(Calendar.YEAR);

        Row filaTitulo = sheet.getRow(0);
        Cell celdaTitulo = filaTitulo.createCell(0);
        celdaTitulo.setCellValue("Facturación de " + getMonthForInt(mes - 1) + " de " + anyo + "");
        celdaTitulo.getCellStyle().setAlignment(HorizontalAlignment.CENTER);

        int rownum = 2;
        if (lineasDeFactura != null && !lineasDeFactura.isEmpty()) {
            for (LineaFactura lineaFact : lineasDeFactura) {
                Row row = sheet.createRow(rownum++);
                int cellnum = 0;
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                sdf.format(lineaFact.getFecha());
                cell = row.createCell(cellnum++);
                cell.setCellValue(sdf.format(lineaFact.getFecha()));

                cell = row.createCell(cellnum++);
                cell.setCellValue(lineaFact.getClinica().getNumero());

                cell = row.createCell(cellnum++);
                cell.setCellValue(lineaFact.getPacient().toString());

                cell = row.createCell(cellnum++);
                cell.setCellValue(lineaFact.getPacient().getNumeroHistoria());

                cell = row.createCell(cellnum++);
                cell.setCellValue(lineaFact.getTractament().getTipoTractamentEnum().getLabel() + " " + lineaFact.getObservaciones());

                cell = row.createCell(cellnum++);
                cell.setCellValue(this.formatBigDecimal(lineaFact.getImporte()));

                cell = row.createCell(cellnum++);
                cell.setCellValue(lineaFact.getTipusPagament().getCodi());
            }
        }

        Map<String, Object> fieldsGastos = new HashMap<>();
        fieldsGastos.put("mes", mes);
        fieldsGastos.put("any", anyo);

        GastosLaboratori gastosLaboratori;

        try {
            gastosLaboratori = dbHelper.getGastosByFields(fieldsGastos);

            if (gastosLaboratori != null) {

                rownum += 2;

                Row row = sheet.createRow(rownum++);
                int cellnum = 0;

                cell = row.createCell(cellnum++);
                cell.setCellValue("Gastos de Laboratorio");

                row = sheet.createRow(rownum++);
                cellnum = 0;

                cell = row.createCell(cellnum++);
                cell.setCellValue("Laboratorio Ortodoncia");

                cell = row.createCell(cellnum++);
                cell.setCellValue(this.formatBigDecimal(gastosLaboratori.getLabOrtodoncia()));


                row = sheet.createRow(rownum++);
                cellnum = 0;

                cell = row.createCell(cellnum++);
                cell.setCellValue("Laboratorio Resitecnic");

                cell = row.createCell(cellnum++);
                cell.setCellValue(this.formatBigDecimal(gastosLaboratori.getLabResitecnic()));

                row = sheet.createRow(rownum++);
                cellnum = 0;

                cell = row.createCell(cellnum++);
                cell.setCellValue("Laboratorio System");

                cell = row.createCell(cellnum++);
                cell.setCellValue(this.formatBigDecimal(gastosLaboratori.getLabSystem()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/saved_facturas");
            myDir.mkdirs();

            String fileName = "FacturaOctubre.xls";

            File file = new File(myDir, fileName);

            FileOutputStream out = new FileOutputStream(file);
            wb.write(out);
            out.flush();
            out.close();

            Toast.makeText(getActivity(), "Fichero excel generado correctamente", Toast.LENGTH_SHORT).show();

            this.viewExcel(Uri.fromFile(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    private void enviarEmail(Uri path) {
        //Instanciamos un Intent del tipo ACTION_SEND
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        //Definimos la tipologia de datos del contenido dle Email en este caso text/html
        emailIntent.setType("text/html");
        // Indicamos con un Array de tipo String las direcciones de correo a las cuales
        //queremos enviar el texto
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"abeneto23@gmail.com"});
        // Definimos un titulo para el Email
        emailIntent.putExtra(android.content.Intent.EXTRA_TITLE, "El Titulo");
        // Definimos un Asunto para el Email
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "El Asunto");
        // Obtenemos la referencia al texto y lo pasamos al Email Intent
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Factura to you");

        emailIntent.putExtra(Intent.EXTRA_STREAM, path);

        try {
            //Enviamos el Correo iniciando una nueva Activity con el emailIntent.
            startActivity(Intent.createChooser(emailIntent, "Enviar E-mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "No hay ningun cliente de correo instalado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewExcel(Uri file) {
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(file, "application/vnd.ms-excel");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("No Application Found");
            builder.setMessage("Download one from Android Market?");
            builder.setPositiveButton("Yes, Please",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                            marketIntent.setData(Uri.parse("market://details?id=com.adobe.reader"));
                            startActivity(marketIntent);
                        }
                    });
            builder.setNegativeButton("No, Thanks", null);
            builder.create().show();
        }
    }

    private String formatBigDecimal(BigDecimal numero) {
        DecimalFormat df = new DecimalFormat();

        df.setMaximumFractionDigits(2);

        df.setMinimumFractionDigits(0);

        df.setGroupingUsed(false);

        String result = df.format(numero);

        return result;
    }


    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

}
