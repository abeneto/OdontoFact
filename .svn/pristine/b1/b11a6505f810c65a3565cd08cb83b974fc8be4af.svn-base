package com.dev.abeneto.charanifact.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dev.abeneto.charanifact.R;
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

    View inflated = null;
    DatabaseHelper dbHelper = null;
    private EditText editTextApellido;
    private EditText editTextHistoria;
    private List<Pacient> listadoPacientes = new ArrayList<>();
    ListView listView;
    ArrayAdapter<Pacient> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflated = inflater.inflate(R.layout.fragment_consultar_pacientes, container, false);

        dbHelper = new DatabaseHelper(getActivity().getApplicationContext());

        Button botonBuscarPaciente = (Button) inflated.findViewById(R.id.botonBuscarPaciente);
        editTextApellido = (EditText) inflated.findViewById(R.id.editTextApellido);
        editTextHistoria = (EditText) inflated.findViewById(R.id.editTextHistoria);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listadoPacientes);
        // Attach the adapter to a ListView
        listView = (ListView) inflated.findViewById(R.id.listViewPacientes);
        listView.setAdapter(adapter);

        try {
            listadoPacientes = dbHelper.getPacientesByFields(new HashMap<String, Object>());
            adapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        botonBuscarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, Object> fields = new HashMap<String, Object>();

                if (editTextApellido.getText() != null && editTextApellido.getText().length() != 0  ) {
                    fields.put("cognom1", editTextApellido.getText());
                }

                if (editTextHistoria.getText() != null && editTextHistoria.getText().length() != 0) {
                    fields.put("numeroHistoria", editTextHistoria.getText());
                }

                try {
                    listadoPacientes = dbHelper.getPacientesByFields(fields);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (listadoPacientes == null || listadoPacientes.isEmpty()) {
                    Toast.makeText(getActivity(), "No hay resultados", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        });

        return inflated;
    }


}
