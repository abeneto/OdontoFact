package com.dev.abeneto.charanifact.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dev.abeneto.charanifact.R;
import com.dev.abeneto.charanifact.db.DatabaseHelper;
import com.dev.abeneto.charanifact.pojo.GastosLaboratori;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alberto on 21/10/2016.
 */
public class FragmentAddGastosLaboratori extends Fragment {

    DatabaseHelper dbHelper = null;
    View inflated = null;

    private Spinner spinnerAnyo;
    private Spinner spinnerMes;

    private EditText editTextOrto;
    private EditText editTextSystem;
    private EditText editTextResi;

    private Button botonAddGastosLab;

    private ArrayList<String> meses;
    ArrayAdapter<String> adapterMeses = null;

    private ArrayList<Integer> anyos;
    ArrayAdapter<Integer> adapterAnyos = null;

    public FragmentAddGastosLaboratori() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflated = inflater.inflate(R.layout.add_gastos_laboratori, container, false);

        dbHelper = new DatabaseHelper(inflated.getContext().getApplicationContext());

        spinnerAnyo = (Spinner) inflated.findViewById(R.id.spinnerAnyo);
        spinnerMes = (Spinner) inflated.findViewById(R.id.spinnerMes);

        this.cargarMeses();
        this.cargarAnyos();

        adapterMeses = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.spinner_item, meses);
        adapterMeses.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerMes.setAdapter(adapterMeses);

        adapterAnyos = new ArrayAdapter<Integer>(getActivity().getApplicationContext(),
                R.layout.spinner_item, anyos);
        adapterAnyos.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerAnyo.setAdapter(adapterAnyos);

        spinnerAnyo.setSelection(1);

        Calendar cal = new GregorianCalendar();
        spinnerMes.setSelection(cal.get(Calendar.MONTH));


        editTextOrto = (EditText) inflated.findViewById(R.id.editTextOrto);
        editTextSystem = (EditText) inflated.findViewById(R.id.editTextSystem);
        editTextResi = (EditText) inflated.findViewById(R.id.editTextResi);

        botonAddGastosLab = (Button) inflated.findViewById(R.id.botonAddGastosLab);

        botonAddGastosLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (checkAltaGastos(view)) {
                        GastosLaboratori gastosLaboratori = new GastosLaboratori();
                        gastosLaboratori.setAny((Integer) spinnerAnyo.getSelectedItem());
                        gastosLaboratori.setLabOrtodoncia(new BigDecimal(Double.valueOf(editTextOrto.getText().toString())));
                        gastosLaboratori.setLabResitecnic(new BigDecimal(Double.valueOf(editTextResi.getText().toString())));
                        gastosLaboratori.setLabSystem(new BigDecimal(Double.valueOf(editTextSystem.getText().toString())));
                        gastosLaboratori.setMes(getMesSelected((String) spinnerMes.getSelectedItem()));

                        dbHelper.getGastosLabDao().create(gastosLaboratori);

                        Log.i("Gastos creados", "Gastos CREADO: " + gastosLaboratori.getId());
                        Toast toast = Toast.makeText(inflated.getContext(), "Gastos de laboratorio para el mes " + gastosLaboratori.getMes() + " y año " + gastosLaboratori.getAny() + " añadidos correctamente.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 30);
                        toast.show();

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

        return inflated;
    }

    private void cargarMeses() {

        meses = new ArrayList<>();
        meses.add(getResources().getString(R.string.enero));
        meses.add(getResources().getString(R.string.febrero));
        meses.add(getResources().getString(R.string.marzo));
        meses.add(getResources().getString(R.string.abril));
        meses.add(getResources().getString(R.string.mayo));
        meses.add(getResources().getString(R.string.junio));
        meses.add(getResources().getString(R.string.julio));
        meses.add(getResources().getString(R.string.agosto));
        meses.add(getResources().getString(R.string.septiembre));
        meses.add(getResources().getString(R.string.octubre));
        meses.add(getResources().getString(R.string.noviembre));
        meses.add(getResources().getString(R.string.diciembre));

    }

    private Integer getMesSelected(String mesSelected) {

        Integer mesInteger = 0;

        if (mesSelected.equalsIgnoreCase(getString(R.string.enero))) {
            mesInteger = 1;
        } else if (mesSelected.equalsIgnoreCase(getString(R.string.febrero))) {
            mesInteger = 2;
        } else if (mesSelected.equalsIgnoreCase(getString(R.string.marzo))) {
            mesInteger = 3;
        } else if (mesSelected.equalsIgnoreCase(getString(R.string.abril))) {
            mesInteger = 4;
        } else if (mesSelected.equalsIgnoreCase(getString(R.string.mayo))) {
            mesInteger = 5;
        } else if (mesSelected.equalsIgnoreCase(getString(R.string.junio))) {
            mesInteger = 6;
        } else if (mesSelected.equalsIgnoreCase(getString(R.string.julio))) {
            mesInteger = 7;
        } else if (mesSelected.equalsIgnoreCase(getString(R.string.agosto))) {
            mesInteger = 8;
        } else if (mesSelected.equalsIgnoreCase(getString(R.string.septiembre))) {
            mesInteger = 9;
        } else if (mesSelected.equalsIgnoreCase(getString(R.string.octubre))) {
            mesInteger = 10;
        } else if (mesSelected.equalsIgnoreCase(getString(R.string.noviembre))) {
            mesInteger = 11;
        } else if (mesSelected.equalsIgnoreCase(getString(R.string.diciembre))) {
            mesInteger = 12;
        }

        return mesInteger;

    }

    private void cargarAnyos() {

        anyos = new ArrayList<>();

        Calendar calendar = GregorianCalendar.getInstance();

        int anyoActual = calendar.get(Calendar.YEAR);

        anyos.add(anyoActual - 1);
        anyos.add(anyoActual);
        anyos.add(anyoActual + 1);

    }

    private Boolean checkAltaGastos(View view) throws SQLException {
        Boolean cancel = false;
        View focusView = null;

        if (editTextOrto == null || editTextOrto.getText().toString().equals("")) {
            editTextOrto.setError(getString(R.string.error_campo_obligatorio));
            cancel = true;
            focusView = editTextOrto;
        } else if (editTextSystem == null || editTextSystem.getText().toString().equals("")) {
            editTextSystem.setError(getString(R.string.error_campo_obligatorio));
            cancel = true;
            focusView = editTextSystem;
        } else if (editTextResi == null || editTextResi.getText().toString().equals("")) {
            editTextResi.setError(getString(R.string.error_campo_obligatorio));
            cancel = true;
            focusView = editTextResi;
        }


        Map<String, Object> fieldsGastos = new HashMap<>();
        fieldsGastos.put("mes", getMesSelected((String) spinnerMes.getSelectedItem()));
        fieldsGastos.put("any", spinnerAnyo.getSelectedItem());

        if (dbHelper.getGastosByFields(fieldsGastos) != null) {
            editTextResi.setError(getString(R.string.error_gastos_ya_existen));
            cancel = true;
            focusView = editTextResi;
        }

        //TODO: Comprobar que no exista el año y el mes

        if (cancel) {
            focusView.requestFocus();
            return false;
        }

        return true;
    }
}
