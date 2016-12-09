package com.dev.abeneto.odontofact.pojo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Alberto on 13/09/2016.
 */
@DatabaseTable(tableName = "paciente")
public class Pacient implements Serializable {

    private static final long serialVersionUID = 7809606597975405705L;
    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String nom;

    @DatabaseField
    private String cognom1;

    @DatabaseField
    private String cognom2;

    @DatabaseField
    private String numeroHistoria;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private ArrayList<Tractament> tractaments;


    public Pacient() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }

    public String getNumeroHistoria() {
        return numeroHistoria;
    }

    public void setNumeroHistoria(String numeroHistoria) {
        this.numeroHistoria = numeroHistoria;
    }

    public ArrayList<Tractament> getTractaments() {
        return tractaments;
    }

    public void setTractaments(ArrayList<Tractament> tractaments) {
        this.tractaments = tractaments;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(this.nom+" "+this.cognom1);
        if(this.cognom2 != null) {
            sb.append(" ").append(this.cognom2);
        }

        return sb.toString();
    }
}
