package com.dev.abeneto.charanifact.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Alberto on 16/09/2016.
 */
@DatabaseTable(tableName = "usuari")
public class Usuari implements Serializable {


    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String login;

    @DatabaseField
    private String password;

    @DatabaseField
    private Boolean logado;

    @DatabaseField
    private String nombre;

    @DatabaseField
    private String apellido1;

    public Usuari() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLogado() {
        return logado;
    }

    public void setLogado(Boolean logado) {
        this.logado = logado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }
}
