package com.dev.abeneto.charanifact.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dev.abeneto.charanifact.R;
import com.dev.abeneto.charanifact.pojo.GastosLaboratori;
import com.dev.abeneto.charanifact.pojo.LineaFactura;
import com.dev.abeneto.charanifact.pojo.Pacient;
import com.dev.abeneto.charanifact.pojo.Tractament;
import com.dev.abeneto.charanifact.pojo.Usuari;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.apache.poi.ss.usermodel.Table;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Alberto on 14/09/2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "facturacio_aabm";
    private static final int DATABASE_VERSION = 1;

    private Dao<Pacient, Long> pacientDao;
    private Dao<Tractament, Long> tractamentDao;
    private Dao<LineaFactura, Long> lineaFacturaDao;
    private Dao<Usuari, Long> usuariDao;
    private Dao<GastosLaboratori, Long> gastosLabDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Usuari.class);
            TableUtils.createTable(connectionSource, Pacient.class);
            TableUtils.createTable(connectionSource, Tractament.class);
            TableUtils.createTable(connectionSource, LineaFactura.class);
            TableUtils.createTable(connectionSource, GastosLaboratori.class);

            Usuari usuariAdmin = new Usuari();
            usuariAdmin.setLogin("admin");
            usuariAdmin.setPassword("04f2d70");
            usuariAdmin.setNombre("Alberto");
            usuariAdmin.setApellido1("Benetó");
            this.usuariDao.create(usuariAdmin);

            Usuari usuariEva = new Usuari();
            usuariEva.setLogin("ebp");
            usuariEva.setPassword("ebp");
            usuariEva.setNombre("Eva");
            usuariEva.setApellido1("Bellvís");
            this.usuariDao.create(usuariEva);

            Log.d("Usuari creat: ", "Usuari creat: " + usuariAdmin.getLogin() + " id: " + usuariAdmin.getId());
            Log.d("Usuari creat: ", "Usuari creat: " + usuariAdmin.getLogin() + " id: " + usuariAdmin.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Usuari.class);
            TableUtils.createTableIfNotExists(connectionSource, Pacient.class);
            TableUtils.createTableIfNotExists(connectionSource, Tractament.class);
            TableUtils.createTableIfNotExists(connectionSource, LineaFactura.class);
            TableUtils.createTableIfNotExists(connectionSource, GastosLaboratori.class);
            this.onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Pacient, Long> getPacientDao() throws SQLException {
        if (pacientDao == null) {
            pacientDao = getDao(Pacient.class);
        }
        return pacientDao;
    }

    public Dao<Tractament, Long> getTractamentDao() throws SQLException {
        if (tractamentDao == null) {
            tractamentDao = getDao(Tractament.class);
        }
        return tractamentDao;
    }

    public Dao<LineaFactura, Long> getLineaFacturaDao() throws SQLException {
        if (lineaFacturaDao == null) {
            lineaFacturaDao = getDao(LineaFactura.class);
        }
        return lineaFacturaDao;
    }

    public Dao<Usuari, Long> getUsuariDao() throws SQLException {
        if (usuariDao == null) {
            usuariDao = getDao(Usuari.class);
        }
        return usuariDao;
    }

    public Dao<GastosLaboratori, Long> getGastosLabDao() throws SQLException {
        if (gastosLabDao == null) {
            gastosLabDao = getDao(GastosLaboratori.class);
        }
        return gastosLabDao;
    }

    public List<LineaFactura> getLineasFacturaOfMonth(int month, int year) throws SQLException {

        List<LineaFactura> lineasFactura;

        Calendar calIni = Calendar.getInstance();
        calIni.set(Calendar.DAY_OF_MONTH, 1);
        calIni.set(Calendar.MONTH, month);
        calIni.set(Calendar.YEAR, year);

        Calendar calFin = Calendar.getInstance();
        calFin.set(Calendar.MONTH, month);
        calFin.set(Calendar.YEAR, year);
        calFin.set(Calendar.DAY_OF_MONTH, calIni.getActualMaximum(Calendar.DAY_OF_MONTH));

        QueryBuilder<LineaFactura, Long> queryBuilder = this.getLineaFacturaDao().queryBuilder();
        queryBuilder.where().between("fecha", calIni.getTime(), calFin.getTime());

        PreparedQuery<LineaFactura> preparedQuery = queryBuilder.prepare();

        lineasFactura = getLineaFacturaDao().query(preparedQuery);

        return lineasFactura;

    }

    public List<Pacient> getPacientesByFields(Map<String, Object> fieldsPaciente) throws SQLException {

        List<Pacient> pacientes;

        if (fieldsPaciente != null && !fieldsPaciente.isEmpty()) {
            pacientes = this.getPacientDao().queryForFieldValues(fieldsPaciente);
        } else {
            pacientes = this.getPacientDao().queryForAll();
        }

        return pacientes;

    }

    public GastosLaboratori getGastosByFields(Map<String, Object> fieldsGastos) throws SQLException {

        List<GastosLaboratori> gastos;

        if (fieldsGastos != null && !fieldsGastos.isEmpty()) {
            gastos = this.getGastosLabDao().queryForFieldValues(fieldsGastos);
        } else {
            gastos = this.getGastosLabDao().queryForAll();
        }

        if(gastos!= null && gastos.size() == 1){
            return gastos.get(0);
        }

        return null;

    }


}
