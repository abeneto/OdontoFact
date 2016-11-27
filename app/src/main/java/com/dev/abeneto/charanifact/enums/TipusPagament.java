package com.dev.abeneto.charanifact.enums;

/**
 * Created by Alberto on 13/09/2016.
 */
public enum TipusPagament {

    EFECTIU("E","Efectivo"),
    TARGETA("T","Tarjeta"),
    TRANSFERENCIA("TF","Transferencia"),
    FINANSAMENT("FF","Financiado");

    private String codi;
    private String label;

    TipusPagament(String codi, String label) {
        this.codi = codi;
        this.label = label;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
