package com.dev.abeneto.charanifact.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dev.abeneto.charanifact.R;
import com.dev.abeneto.charanifact.activities.MainActivity;
import com.dev.abeneto.charanifact.constants.FacturacioConstants;
import com.dev.abeneto.charanifact.db.DatabaseHelper;
import com.dev.abeneto.charanifact.pojo.Pacient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aabm on 11/10/2016.
 */
public class FragmentConsultarPacientes extends Fragment {

    private View inflated = null;
    private DatabaseHelper dbHelper = null;
    private EditText editTextApellido;
    private EditText editTextHistoria;
    private List<Pacient> listadoPacientes = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter<Pacient> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflated = inflater.inflate(R.layout.fragment_consultar_pacientes, container, false);

        this.dbHelper = new DatabaseHelper(getActivity().getApplicationContext());

        Button botonBuscarPaciente = (Button) this.inflated.findViewById(R.id.botonBuscarPaciente);
        this.editTextApellido = (EditText) this.inflated.findViewById(R.id.editTextApellido);
        this.editTextHistoria = (EditText) this.inflated.findViewById(R.id.editTextHistoria);

        this.adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listadoPacientes);
        // Attach the adapter to a ListView
        this.listView = (ListView) this.inflated.findViewById(R.id.listViewPacientes);
        this.listView.setAdapter(this.adapter);

        try {
            this.listadoPacientes = this.dbHelper.getPacientesByFields(new HashMap<String, Object>());
            this.adapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        botonBuscarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listadoPacientes = buscarPacientes();

                if (listadoPacientes == null || listadoPacientes.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.toast_message_no_hay_resultados), Toast.LENGTH_SHORT).show();
                } else {
                    adapter.clear();
                    adapter.addAll(listadoPacientes);

                    listView = (ListView) inflated.findViewById(R.id.listViewPacientes);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        MainActivity activity = (MainActivity) getActivity();
        activity.getIvFondoGlobal().setVisibility(View.INVISIBLE);

        return inflated;
    }

    /**
     * Metodo para buscar pacientes.
     *
     * @return
     */
    private List<Pacient> buscarPacientes() {

        List<Pacient> listaAuxPacientes = null;

        String apellido = null;
        String numHistoria = null;

        if (editTextApellido.getText() != null && editTextApellido.getText().length() != 0) {
            apellido = editTextApellido.getText().toString();
        }

        if (editTextHistoria.getText() != null && editTextHistoria.getText().length() != 0) {
            numHistoria = editTextHistoria.getText().toString();
        }

        try {
            listaAuxPacientes = dbHelper.buscarPacientesPorHistoriayApellido(apellido, numHistoria);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaAuxPacientes;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        MainActivity activity = (MainActivity) getActivity();
        activity.getIvFondoGlobal().setVisibility(View.INVISIBLE);

        super.onConfigurationChanged(newConfig);
    }


}
