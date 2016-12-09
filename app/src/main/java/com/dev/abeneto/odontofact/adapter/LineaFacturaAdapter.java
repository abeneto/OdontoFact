package com.dev.abeneto.odontofact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dev.abeneto.odontofact.R;
import com.dev.abeneto.odontofact.pojo.LineaFactura;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class LineaFacturaAdapter extends ArrayAdapter<LineaFactura> {

    private final Context context;
    private final ArrayList<LineaFactura> values;

    public LineaFacturaAdapter(Context context, ArrayList<LineaFactura> users) {
        super(context, R.layout.factura_item, users);
        this.context = context;
        this.values = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        LineaFactura lineaFactura = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.factura_item, parent, false);
        }
        // Lookup view for data population
        TextView textoFecha = (TextView) convertView.findViewById(R.id.textoFechaLinea);
        TextView textoClinica = (TextView) convertView.findViewById(R.id.textoClinicaLinia);
        TextView textoPaciente = (TextView) convertView.findViewById(R.id.textoPacienteLinia);

        TextView textoTratamiento = (TextView) convertView.findViewById(R.id.textoTratamientoLinia);
        TextView textoImporte = (TextView) convertView.findViewById(R.id.textoImporteLinia);
        TextView textoPago = (TextView) convertView.findViewById(R.id.tipoPagoLinia);

        // Populate the data into the template view using the data object
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        textoFecha.setText(sdf.format(lineaFactura.getFecha()));
        textoClinica.setText(lineaFactura.getClinica().getNumero().toString());
        textoPaciente.setText(lineaFactura.getPacient().getNom() + " "+ lineaFactura.getPacient().getCognom1());

        textoTratamiento.setText(lineaFactura.getTractament().getTipoTractamentEnum().getLabel());
        textoImporte.setText(lineaFactura.getImporte().toString() + " €");

        if(lineaFactura.getTipusPagament() != null) {
            textoPago.setText(lineaFactura.getTipusPagament().getCodi());
        }

        return convertView;
    }

}