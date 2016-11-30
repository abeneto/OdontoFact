package com.dev.abeneto.charanifact.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.abeneto.charanifact.R;
import com.dev.abeneto.charanifact.db.DatabaseHelper;
import com.dev.abeneto.charanifact.pojo.Pacient;

import java.sql.SQLException;

/**
 * Created by Alberto on 11/09/2016.
 */
public class AltaPacienteFragment extends Fragment {

    DatabaseHelper dbHelper = null;
    View inflated = null;

    private EditText editTextNombre;
    private EditText editTextApellido1;
    private EditText editTextApellido2;
    private EditText editTextNumHistoria;

    public AltaPacienteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflated = inflater.inflate(R.layout.alta_paciente_fragment, container, false);

        editTextNombre = (EditText) inflated.findViewById(R.id.nomInput);
        editTextApellido1 = (EditText) inflated.findViewById(R.id.apellido1Input);
        editTextApellido2 = (EditText) inflated.findViewById(R.id.apellido2Input);
        editTextNumHistoria = (EditText) inflated.findViewById(R.id.numHistoriaInput);

        // Inflate the layout for this fragment
        inflated.findViewById(R.id.botonAltaPaciente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAltaPacienteParams(view)) {
                    dbHelper = new DatabaseHelper(getActivity().getApplicationContext());

                    Pacient pacient = new Pacient();
                    pacient.setNom(editTextNombre.getText().toString());
                    pacient.setCognom1(editTextApellido1.getText().toString());
                    pacient.setCognom2(editTextApellido2.getText().toString());
                    pacient.setNumeroHistoria(editTextNumHistoria.getText().toString());

                    try {
                        dbHelper.getPacientDao().create(pacient);
                        Log.i("Paciente creado", "Paciente CREADO: " + pacient.getNom());
                        Toast toast =  Toast.makeText(inflated.getContext(), "Paciente " + pacient.getNom() + " " + pacient.getCognom1()+" añadido correctamente.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,30);
                        toast.show();

                        limpiarCampos();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        return inflated;

    }

    private void limpiarCampos() {
        this.editTextNombre.getText().clear();
        this.editTextApellido1.getText().clear();
        this.editTextApellido2.getText().clear();
        this.editTextNumHistoria.getText().clear();
    }

    private Boolean checkAltaPacienteParams(View view) {
        Boolean cancel = false;
        View focusView = null;

        if (inflated.findViewById(R.id.nomInput) == null || ((EditText) inflated.findViewById(R.id.nomInput)).getText().toString().isEmpty()) {
            ((EditText) inflated.findViewById(R.id.nomInput)).setError(getString(R.string.error_campo_obligatorio));
            cancel = true;
            focusView = (EditText) inflated.findViewById(R.id.nomInput);
        } else if (inflated.findViewById(R.id.apellido1Input) == null || ((EditText) inflated.findViewById(R.id.apellido1Input)).getText().toString().isEmpty()) {
            ((EditText) inflated.findViewById(R.id.apellido1Input)).setError(getString(R.string.error_campo_obligatorio));
            cancel = true;
            focusView = (EditText) inflated.findViewById(R.id.apellido1Input);
        } else if (inflated.findViewById(R.id.numHistoriaInput) == null || ((EditText) inflated.findViewById(R.id.numHistoriaInput)).getText().toString().isEmpty()) {
            ((EditText) inflated.findViewById(R.id.numHistoriaInput)).setError(getString(R.string.error_campo_obligatorio));
            cancel = true;
            focusView = (EditText) inflated.findViewById(R.id.numHistoriaInput);
        }

        if (cancel) {
            focusView.requestFocus();
            return false;
        }

        return true;
    }

}
