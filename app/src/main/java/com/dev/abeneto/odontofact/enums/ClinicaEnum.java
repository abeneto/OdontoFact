package com.dev.abeneto.odontofact.enums;

/**
 * Created by Alberto on 22/09/2016.
 */
public enum ClinicaEnum {

    CLINICA_UNO(1L,"Clinica 1 - Calle Colón"),
    CLINICA_DOS(2L,"Clinica 2 - Avda Puerto"),
    CLINICA_TRES(3L, "Clinica 3 - Avda Aragón");

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
