package com.dev.abeneto.odontofact.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Alberto on 21/10/2016.
 */
@DatabaseTable(tableName = "gastos_laboratori")
public class GastosLaboratori implements Serializable{

    private static final long serialVersionUID = -5676646785826796137L;

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private BigDecimal labOrtodoncia;

    @DatabaseField
    private BigDecimal labResitecnic;

    @DatabaseField
    private BigDecimal labSystem;

    @DatabaseField
    private int mes;

    @DatabaseField
    private int any;

    public GastosLaboratori() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLabOrtodoncia() {
        return labOrtodoncia;
    }

    public void setLabOrtodoncia(BigDecimal labOrtodoncia) {
        this.labOrtodoncia = labOrtodoncia;
    }

    public BigDecimal getLabResitecnic() {
        return labResitecnic;
    }

    public void setLabResitecnic(BigDecimal labResitecnic) {
        this.labResitecnic = labResitecnic;
    }

    public BigDecimal getLabSystem() {
        return labSystem;
    }

    public void setLabSystem(BigDecimal labSystem) {
        this.labSystem = labSystem;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAny() {
        return any;
    }

    public void setAny(int any) {
        this.any = any;
    }
}
