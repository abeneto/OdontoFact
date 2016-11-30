package com.dev.abeneto.charanifact.utils;

import com.dev.abeneto.charanifact.enums.TipoTractamentEnum;
import com.dev.abeneto.charanifact.enums.TipusPagament;
import com.dev.abeneto.charanifact.pojo.GastosLaboratori;
import com.dev.abeneto.charanifact.pojo.LineaFactura;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.ArrayList;

/**
 * Created by Alberto on 21/10/2016.
 */
public class Utils {


    //TODO: Completar funcionalidad.

    /**
     * Calculamos el total de la factura con sus porcentajes asociados.
     *
     * @param lineasFacturas
     * @return
     */
    public static BigDecimal calcularTotalFactura(ArrayList<LineaFactura> lineasFacturas, GastosLaboratori gastosLaboratori) {

        BigDecimal totalFactura = new BigDecimal(0);
        BigDecimal provisionalOrto = new BigDecimal(0);
        BigDecimal provisionalResta = new BigDecimal(0);

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
        provisionalOrto = provisionalOrto.multiply(BigDecimal.valueOf(0.65));

        provisionalResta = provisionalResta.subtract(gastosLaboratori.getLabSystem());
        provisionalResta = provisionalResta.subtract(gastosLaboratori.getLabResitecnic());

        provisionalResta = provisionalResta.multiply(BigDecimal.valueOf(0.75));

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


}
