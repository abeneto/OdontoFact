package com.dev.abeneto.charanifact.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dev.abeneto.charanifact.R;
import com.dev.abeneto.charanifact.enums.TipoTractamentEnum;
import com.dev.abeneto.charanifact.enums.TipusPagament;
import com.dev.abeneto.charanifact.pojo.GastosLaboratori;
import com.dev.abeneto.charanifact.pojo.LineaFactura;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Alberto on 21/10/2016.
 */
public class Utils extends Activity {


    //TODO: Completar funcionalidad.

    /**
     * Calculamos el total de la factura con sus porcentajes asociados.
     *
     * @param lineasFacturas
     * @return
     */
    public static BigDecimal calcularTotalFactura(ArrayList<LineaFactura> lineasFacturas, GastosLaboratori gastosLaboratori, BigDecimal porcentajeOrtodoncia, BigDecimal porcentajeOtros, BigDecimal descuentoTarjeta) {

        BigDecimal totalFactura = BigDecimal.ZERO;
        BigDecimal provisionalOrto = BigDecimal.ZERO;
        BigDecimal provisionalResta = BigDecimal.ZERO;

        for (LineaFactura lineaFactura : lineasFacturas) {

            BigDecimal importeProvisional = lineaFactura.getImporte();

            if (lineaFactura.getTipusPagament().getCodi().equals(TipusPagament.TARGETA.getCodi())) {

                importeProvisional = importeProvisional.multiply(new BigDecimal(0.991));

            }

            if (lineaFactura.getTractament().getTipoTractamentEnum().getCodi().equals(TipoTractamentEnum.ORTODONCIA.getCodi())) {
                provisionalOrto = provisionalOrto.add(importeProvisional);
            } else {
                provisionalResta = provisionalResta.add(importeProvisional);
            }

        }

        provisionalOrto = provisionalOrto.subtract(gastosLaboratori.getLabOrtodoncia());
        provisionalOrto = provisionalOrto.multiply(BigDecimal.valueOf(0.35));

        provisionalResta = provisionalResta.subtract(gastosLaboratori.getLabSystem());
        provisionalResta = provisionalResta.subtract(gastosLaboratori.getLabResitecnic());

        provisionalResta = provisionalResta.multiply(BigDecimal.valueOf(0.25));

        totalFactura = provisionalOrto.add(provisionalResta);

        return totalFactura;

    }

    /**
     * Obtenemos el mes en formato String a partir del numero de este.
     *
     * @param num
     * @return
     */
    public static String getMonthForInt(int num) {
        String month = null;
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    /**
     * Obtener el numero del mes seleccionado. Enero = 1 ; Diciembre = 12
     *
     * @param mContext
     * @param mesSelected
     * @return
     */
    public static Integer getMesSelected(Context mContext, String mesSelected) {

        Integer mesInteger = 0;

        if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.enero))) {
            mesInteger = 1;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.febrero))) {
            mesInteger = 2;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.marzo))) {
            mesInteger = 3;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.abril))) {
            mesInteger = 4;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.mayo))) {
            mesInteger = 5;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.junio))) {
            mesInteger = 6;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.julio))) {
            mesInteger = 7;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.agosto))) {
            mesInteger = 8;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.septiembre))) {
            mesInteger = 9;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.octubre))) {
            mesInteger = 10;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.noviembre))) {
            mesInteger = 11;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.diciembre))) {
            mesInteger = 12;
        }

        return mesInteger;

    }


    public static Integer getMesSelectedJavaFormat(Context mContext, String mesSelected) {

        Integer mesInteger = 0;

        if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.enero))) {
            mesInteger = 0;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.febrero))) {
            mesInteger = 1;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.marzo))) {
            mesInteger = 2;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.abril))) {
            mesInteger = 3;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.mayo))) {
            mesInteger = 4;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.junio))) {
            mesInteger = 5;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.julio))) {
            mesInteger = 6;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.agosto))) {
            mesInteger = 7;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.septiembre))) {
            mesInteger = 8;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.octubre))) {
            mesInteger = 9;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.noviembre))) {
            mesInteger = 10;
        } else if (mesSelected.equalsIgnoreCase(mContext.getString(R.string.diciembre))) {
            mesInteger = 11;
        }

        return mesInteger;

    }


    /**
     * Cargar un listado con los string de todos los meses del anyo.
     *
     * @param mContext
     * @return
     */
    public static ArrayList<String> cargarMeses(Context mContext) {

        ArrayList<String> meses = new ArrayList<>();

        meses.add(mContext.getResources().getString(R.string.enero));
        meses.add(mContext.getResources().getString(R.string.febrero));
        meses.add(mContext.getResources().getString(R.string.marzo));
        meses.add(mContext.getResources().getString(R.string.abril));
        meses.add(mContext.getResources().getString(R.string.mayo));
        meses.add(mContext.getResources().getString(R.string.junio));
        meses.add(mContext.getResources().getString(R.string.julio));
        meses.add(mContext.getResources().getString(R.string.agosto));
        meses.add(mContext.getResources().getString(R.string.septiembre));
        meses.add(mContext.getResources().getString(R.string.octubre));
        meses.add(mContext.getResources().getString(R.string.noviembre));
        meses.add(mContext.getResources().getString(R.string.diciembre));

        return meses;

    }

    /**
     * Cargar un listado con el anyo actual, el anterior y el posterior.
     *
     * @param mContext
     * @return
     */
    public static ArrayList<Integer> cargarAnyos(Context mContext) {

        ArrayList<Integer> anyos = new ArrayList<>();

        Calendar calendar = GregorianCalendar.getInstance();

        int anyoActual = calendar.get(Calendar.YEAR);

        anyos.add(anyoActual - 1);
        anyos.add(anyoActual);
        anyos.add(anyoActual + 1);

        return anyos;

    }

    /**
     * Formatear un BigDecimal con 2 digitos y devolver valor como String.
     *
     * @param numero
     * @return
     */
    public static String formatBigDecimal(BigDecimal numero) {
        DecimalFormat df = new DecimalFormat();

        df.setMaximumFractionDigits(2);

        df.setMinimumFractionDigits(0);

        df.setGroupingUsed(false);

        String result = df.format(numero);

        return result;
    }


}
