package com.dev.abeneto.charanifact.enums;

/**
 * Created by Alberto on 13/09/2016.
 */
public enum TipoTractamentEnum {

    ORTODONCIA("ORTODONCIA",0.35,"Ortodoncia"),
    RECONSTRUCCIO("RECONSTRUCCIO", 0.25,"Reconstrucción"),
    CURETATGE("CURETATGE", 0.25,"Curetaje"),
    PROTESIS("PROTESIS", 0.25, "Prótesis"),
    ENDODONCIA("ENDODONCIA", 0.25, "Endodoncia");

    private String codi;
    private Double percentatge;
    private String label;

    TipoTractamentEnum(String codi, Double percentatge, String label) {
        this.codi = codi;
        this.percentatge = percentatge;
        this.label = label;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public Double getPercentatge() {
        return percentatge;
    }

    public void setPercentatge(Double percentatge) {
        this.percentatge = percentatge;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
