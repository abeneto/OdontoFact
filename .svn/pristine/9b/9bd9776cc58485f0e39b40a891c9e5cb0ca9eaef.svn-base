package com.dev.abeneto.charanifact.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dev.abeneto.charanifact.R;
import com.dev.abeneto.charanifact.db.DatabaseHelper;
import com.dev.abeneto.charanifact.enums.ClinicaEnum;
import com.dev.abeneto.charanifact.enums.TipoTractamentEnum;
import com.dev.abeneto.charanifact.enums.TipusPagament;
import com.dev.abeneto.charanifact.pojo.LineaFactura;
import com.dev.abeneto.charanifact.pojo.Pacient;
import com.dev.abeneto.charanifact.pojo.Tractament;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Alberto on 11/09/2016.
 */
public class Fragment2 extends Fragment {

    View inflated = null;
    DatabaseHelper dbHelper = null;

    Button botonAnyadir = null;
    Button botonResetForm = null;
    Spinner desplegablePacients = null;
    Spinner desplegableCliniques = null;
    Spinner desplegableTipoPago = null;
    Spinner desplegableTratamiento = null;
    EditText campoFecha = null;

    List<Pacient> pacients = null;
    List<String> nomsPacients = null;
    ArrayAdapter<Pacient> adapterPacients = null;

    List<String> llistaCliniques = null;
    ArrayAdapter<String> adapterCliniques = null;

    List<String> llistaTipoPago = null;
    ArrayAdapter<String> adapterTipoPago = null;

    List<String> llistaTratamientos = null;
    ArrayAdapter<String> adapterTratamientos = null;


    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflated = inflater.inflate(R.layout.fragment_fragment2, container, false);
        dbHelper = new DatabaseHelper(getActivity().getApplicationContext());

        botonAnyadir = (Button) inflated.findViewById(R.id.botonAnyadir);
        botonResetForm = (Button) inflated.findViewById(R.id.botonResetForm);

        this.setListenerBotonAnyadir();

        desplegablePacients = (Spinner) inflated.findViewById(R.id.desplegablePacients);
        desplegableCliniques = (Spinner) inflated.findViewById(R.id.desplegableCliniques);
        desplegableTipoPago = (Spinner) inflated.findViewById(R.id.desplegableTipoPago);
        desplegableTratamiento = (Spinner) inflated.findViewById(R.id.desplegableTratmiento);
        campoFecha = (EditText) inflated.findViewById(R.id.editTextFecha);

        Date hoy = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        campoFecha.setText(sdf.format(hoy));
        campoFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment date = new DatePickerFragment();
                /**
                 * Set Up Current Date Into dialog
                 */
                Calendar calender = Calendar.getInstance();
                Bundle args = new Bundle();
                args.putInt("year", calender.get(Calendar.YEAR));
                args.putInt("month", calender.get(Calendar.MONTH));
                args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
                date.setArguments(args);
                /**
                 * Set Call back to capture selected date
                 */
                date.setCallBack(ondate);
                date.show(getFragmentManager(), "Date Picker");
            }
        });

        this.loadPacients(desplegablePacients);
        this.loadCliniques(desplegableCliniques);
        this.loadTipoPago(desplegableTipoPago);
        this.loadTratamiento(desplegableTratamiento);

        return inflated;
    }

    private void setListenerBotonAnyadir() {
        this.botonAnyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LineaFactura lineaFactura = new LineaFactura();

                Pacient pacientSelected = (Pacient) desplegablePacients.getSelectedItem();
                String clinicaSelected = (String) desplegableCliniques.getSelectedItem();
                String tipoPagoSelected = (String) desplegableTipoPago.getSelectedItem();
                String tratamientoSelected = (String) desplegableTratamiento.getSelectedItem();
                String importeEditText = ((EditText) inflated.findViewById(R.id.importeEditText)).getText().toString();
                String observacionesInput = ((EditText) inflated.findViewById(R.id.observacionesInput)).getText().toString();
                String fecha = campoFecha.getText().toString();

                TipusPagament tipusPagament = null;

                //TODO: obtenir els tractaments del pacient de bbdd, si es el mateix obtenirlo i afegirli linea factura, sino crear uno nou
                Tractament tratamentPacient = obtenirTractamentPacient(pacientSelected, tratamientoSelected);
                try {
                    if (tratamentPacient != null) {
                        if (tratamentPacient.getLiniesFactura() == null) {
                            tratamentPacient.setLiniesFactura(new ArrayList<LineaFactura>());
                        }
                        tratamentPacient.getLiniesFactura().add(lineaFactura);
                        dbHelper.getTractamentDao().update(tratamentPacient);
                    } else {
                        tratamentPacient = new Tractament();
                        tratamentPacient.setTipoTractamentEnum(getTipoTractament(tratamientoSelected));
                        tratamentPacient.setLiniesFactura(new ArrayList<LineaFactura>());
                        tratamentPacient.getLiniesFactura().add(lineaFactura);

                        dbHelper.getTractamentDao().create(tratamentPacient);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(inflated.getContext(), "Se ha producido un error al insertar la facturación.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 30);
                    toast.show();
                }

                lineaFactura.setClinica(getClinica(clinicaSelected));

                DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                Date date = null;
                try {
                    date = format.parse(fecha);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                lineaFactura.setFecha(date);
                lineaFactura.setImporte(new BigDecimal(importeEditText));
                lineaFactura.setObservaciones(observacionesInput);
                lineaFactura.setPacient(pacientSelected);
                lineaFactura.setTipusPagament(getTipoPago(tipoPagoSelected));
                lineaFactura.setTractament(tratamentPacient);

                try {
                    dbHelper.getLineaFacturaDao().create(lineaFactura);
                } catch (SQLException e) {
                    e.printStackTrace();

                    Toast toast = Toast.makeText(inflated.getContext(), "Se ha producido un error al insertar la facturación.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 30);
                    toast.show();
                }

                Toast toast = Toast.makeText(inflated.getContext(), "Se ha insertado la linea de factura correctamente", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 30);
                toast.show();

            }
        });

        botonResetForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditText) inflated.findViewById(R.id.importeEditText)).setText(null);
                ((EditText) inflated.findViewById(R.id.observacionesInput)).setText(null);
            }
        });
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            campoFecha.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(year));
        }
    };

    private Tractament obtenirTractamentPacient(Pacient pac, String tractamentSelected) {

        Tractament tractamentRes = null;

        if (pac.getTractaments() != null && !pac.getTractaments().isEmpty()) {

            for (Tractament tratamentAux : pac.getTractaments()) {
                if (tratamentAux.getTipoTractamentEnum().getLabel() != null && tratamentAux.getTipoTractamentEnum().getLabel().equals(tractamentSelected)) {
                    tractamentRes = tratamentAux;
                }
            }
        }

        return tractamentRes;

    }


    private ClinicaEnum getClinica(String clinicaSelected) {
        ClinicaEnum clinicaEnum = null;

        if (clinicaSelected.equals(ClinicaEnum.CLINICA_UNO.getNom())) {
            clinicaEnum = ClinicaEnum.CLINICA_UNO;
        } else if (clinicaSelected.equals(ClinicaEnum.CLINICA_DOS.getNom())) {
            clinicaEnum = ClinicaEnum.CLINICA_DOS;
        } else if (clinicaSelected.equals(ClinicaEnum.CLINICA_TRES.getNom())) {
            clinicaEnum = ClinicaEnum.CLINICA_TRES;
        }

        return clinicaEnum;
    }


    private TipusPagament getTipoPago(String tipoPago) {
        TipusPagament tipusPagament = null;

        if (tipoPago.equals(TipusPagament.EFECTIU.getLabel())) {
            tipusPagament = TipusPagament.EFECTIU;
        } else if (tipoPago.equals(TipusPagament.FINANSAMENT.getLabel())) {
            tipusPagament = TipusPagament.FINANSAMENT;
        } else if (tipoPago.equals(TipusPagament.TARGETA.getLabel())) {
            tipusPagament = TipusPagament.TARGETA;
        }

        return tipusPagament;
    }

    private TipoTractamentEnum getTipoTractament(String tipoTractament) {
        TipoTractamentEnum tipoTractamentEnum = null;

        if (tipoTractament.equals(TipoTractamentEnum.CURETATGE.getLabel())) {
            tipoTractamentEnum = TipoTractamentEnum.CURETATGE;
        } else if (tipoTractament.equals(TipoTractamentEnum.PROTESIS.getLabel())) {
            tipoTractamentEnum = TipoTractamentEnum.PROTESIS;
        } else if (tipoTractament.equals(TipoTractamentEnum.RECONSTRUCCIO.getLabel())) {
            tipoTractamentEnum = TipoTractamentEnum.RECONSTRUCCIO;
        } else if (tipoTractament.equals(TipoTractamentEnum.ORTODONCIA.getLabel())) {
            tipoTractamentEnum = TipoTractamentEnum.ORTODONCIA;
        } else if (tipoTractament.equals(TipoTractamentEnum.ENDODONCIA.getLabel())) {
            tipoTractamentEnum = TipoTractamentEnum.ENDODONCIA;
        }

        return tipoTractamentEnum;
    }

    private void loadPacients(Spinner desplegablePacients) {
        try {
            pacients = dbHelper.getPacientDao().queryForAll();
            if (pacients != null) {
                nomsPacients = new ArrayList<String>();
                for (Pacient p : pacients) {
                    nomsPacients.add(p.getNom() + " " + p.getCognom1());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (pacients != null) {

            Collections.sort(pacients, new Comparator<Pacient>() {
                @Override
                public int compare(Pacient p1, Pacient p2) {
                    if(p1.getCognom1().equals(p2.getCognom1()))
                        return 0;
                    return p1.getCognom1().compareTo(p2.getCognom1());
                }
            });

            adapterPacients = new ArrayAdapter<Pacient>(getActivity().getApplicationContext(),
                    R.layout.spinner_item, pacients);
            adapterPacients.setDropDownViewResource(R.layout.spinner_dropdown_item);
            desplegablePacients.setAdapter(adapterPacients);

        }
    }

    private void loadCliniques(Spinner desplegableCliniques) {
        llistaCliniques = new ArrayList<>();
        llistaCliniques.add(ClinicaEnum.CLINICA_UNO.getNom());
        llistaCliniques.add(ClinicaEnum.CLINICA_DOS.getNom());
        llistaCliniques.add(ClinicaEnum.CLINICA_TRES.getNom());

        adapterCliniques = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.spinner_item, llistaCliniques);

        adapterCliniques.setDropDownViewResource(R.layout.spinner_dropdown_item);
        desplegableCliniques.setAdapter(adapterCliniques);

    }

    private void loadTipoPago(Spinner desplegableTipoPago) {
        llistaTipoPago = new ArrayList<>();
        llistaTipoPago.add(TipusPagament.EFECTIU.getLabel());
        llistaTipoPago.add(TipusPagament.TARGETA.getLabel());
        llistaTipoPago.add(TipusPagament.TRANSFERENCIA.getLabel());
        llistaTipoPago.add(TipusPagament.FINANSAMENT.getLabel());

        adapterTipoPago = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.spinner_item, llistaTipoPago);

        adapterTipoPago.setDropDownViewResource(R.layout.spinner_dropdown_item);
        desplegableTipoPago.setAdapter(adapterTipoPago);

    }

    private void loadTratamiento(Spinner desplegableTratamiento) {
        llistaTratamientos = new ArrayList<>();
        llistaTratamientos.add(TipoTractamentEnum.CURETATGE.getLabel());
        llistaTratamientos.add(TipoTractamentEnum.ENDODONCIA.getLabel());
        llistaTratamientos.add(TipoTractamentEnum.ORTODONCIA.getLabel());
        llistaTratamientos.add(TipoTractamentEnum.PROTESIS.getLabel());
        llistaTratamientos.add(TipoTractamentEnum.RECONSTRUCCIO.getLabel());

        adapterTratamientos = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.spinner_item, llistaTratamientos);

        adapterTratamientos.setDropDownViewResource(R.layout.spinner_dropdown_item);
        desplegableTratamiento.setAdapter(adapterTratamientos);
    }
}
