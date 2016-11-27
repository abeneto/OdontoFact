package com.dev.abeneto.charanifact.enums;

/**
 * Created by Alberto on 22/09/2016.
 */
public enum ClinicaEnum {

    CLINICA_UNO(1L,"Charani 1 - Historiador Diago"),
    CLINICA_DOS(2L,"Charani 2 - Corts Valencianes"),
    CLINICA_TRES(3L, "Charani 3 - Fuencaliente");

    private Long numero;
    private String nom;

    ClinicaEnum(Long numero, String nom) {
        this.numero = numero;
        this.nom = nom;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
