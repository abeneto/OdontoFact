package com.dev.abeneto.charanifact.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.dev.abeneto.charanifact.R;
import com.dev.abeneto.charanifact.db.DatabaseHelper;
import com.dev.abeneto.charanifact.pojo.Pacient;
import com.j256.ormlite.field.types.BooleanCharType;

import java.sql.SQLException;

public class PacienteBean extends FragmentActivity {

    DatabaseHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.botonAltaPaciente).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAltaPacienteParams()) {
                    dbHelper = new DatabaseHelper(getApplicationContext());

                    Pacient pacient = new Pacient();
                    pacient.setNom(((EditText) findViewById(R.id.nomInput)).getText().toString());
                    pacient.setCognom1(((EditText) findViewById(R.id.apellido1Input)).getText().toString());
                    pacient.setCognom2(((EditText) findViewById(R.id.apellido2Input)).getText().toString());
                    pacient.setNumeroHistoria(((EditText) findViewById(R.id.numHistoriaInput)).getText().toString());

                    try {
                        dbHelper.getPacientDao().create(pacient);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }


    private Boolean checkAltaPacienteParams() {
        Boolean cancel = false;
        View focusView = null;

        if (findViewById(R.id.nomInput) != null || ((EditText) findViewById(R.id.nomInput)).getText().toString().equals("")){
            ((EditText) findViewById(R.id.nomInput)).setError(getString(R.string.error_campo_obligatorio));
            cancel = true;
            focusView =(EditText) findViewById(R.id.nomInput);
        } else if(findViewById(R.id.apellido1Input) == null && ((EditText) findViewById(R.id.apellido1Input)).getText().toString().equals("")){
            ((EditText) findViewById(R.id.apellido1Input)).setError(getString(R.string.error_campo_obligatorio));
            cancel = true;
            focusView =(EditText) findViewById(R.id.apellido1Input);
        } else if(findViewById(R.id.numHistoriaInput) == null && ((EditText) findViewById(R.id.numHistoriaInput)).getText().toString().equals("")){
            ((EditText) findViewById(R.id.numHistoriaInput)).setError(getString(R.string.error_campo_obligatorio));
            cancel = true;
            focusView =(EditText) findViewById(R.id.numHistoriaInput);
        }

        if (cancel) {
            focusView.requestFocus();
            return false;
        }

        return true;
    }





}
