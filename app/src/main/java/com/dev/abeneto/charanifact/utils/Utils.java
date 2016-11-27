package com.dev.abeneto.charanifact.utils;

import com.dev.abeneto.charanifact.enums.TipoTractamentEnum;
import com.dev.abeneto.charanifact.enums.TipusPagament;
import com.dev.abeneto.charanifact.pojo.LineaFactura;

import org.apache.poi.sl.usermodel.Line;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Alberto on 21/10/2016.
 */
public class Utils {


    public static BigDecimal calcularTotalFactura(ArrayList<LineaFactura> lineasFacturas){

        BigDecimal totalFactura = new BigDecimal(0);
        BigDecimal provisionalOrto = new BigDecimal(0);
        BigDecimal provisionalResta = new BigDecimal(0);

        for (LineaFactura lineaFactura:lineasFacturas) {

            BigDecimal importeProvisional = lineaFactura.getImporte();

            if(lineaFactura.getTipusPagament().getCodi().equals(TipusPagament.TARGETA.getCodi())) {

                importeProvisional = importeProvisional.multiply(new BigDecimal(0.991));

            }

            if(lineaFactura.getTractament().getTipoTractamentEnum().getCodi().equals(TipoTractamentEnum.ORTODONCIA.getCodi())) {
                provisionalOrto.add(importeProvisional);
            } else {
                provisionalResta.add(importeProvisional);
            }

        }


        return totalFactura;

    }


}
