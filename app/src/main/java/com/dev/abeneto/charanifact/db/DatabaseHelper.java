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

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alberto on 14/09/2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "facturacio_db";
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

            this.crearPacientes();


            Log.d("Usuari creat: ", "Usuari creat: " + usuariAdmin.getLogin() + " id: " + usuariAdmin.getId());
            Log.d("Usuari creat: ", "Usuari creat: " + usuariAdmin.getLogin() + " id: " + usuariAdmin.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void crearPacientes() {
        try {

            if (this.pacientDao == null) {
                this.pacientDao = this.getPacientDao();
            }

            Pacient pacient = new Pacient();
            pacient.setNom("Guiliana");
            pacient.setCognom1("Vilela");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2892");
            this.pacientDao.create(pacient);

            pacient.setNom("Beatriz");
            pacient.setCognom1("Valverde");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2645");
            this.pacientDao.create(pacient);

            pacient.setNom("Boukan");
            pacient.setCognom1("Fuster");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("3141");
            this.pacientDao.create(pacient);

            pacient.setNom("Mª Carmen");
            pacient.setCognom1("Chavarría");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("1065");
            this.pacientDao.create(pacient);

            pacient.setNom("Tania");
            pacient.setCognom1("Ferrer");
            pacient.setCognom2("Torres");
            pacient.setNumeroHistoria("3178");
            this.pacientDao.create(pacient);

            pacient.setNom("Sheryl");
            pacient.setCognom1("Ramírez");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("3175");
            this.pacientDao.create(pacient);

            pacient.setNom("Miriam");
            pacient.setCognom1("Fernández");
            pacient.setCognom2("Canós");
            pacient.setNumeroHistoria("288");
            this.pacientDao.create(pacient);

            pacient.setNom("Mónica");
            pacient.setCognom1("Piles");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("201");
            this.pacientDao.create(pacient);

            pacient.setNom("Estela");
            pacient.setCognom1("Muñoz");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("3237");
            this.pacientDao.create(pacient);

            pacient.setNom("Clara");
            pacient.setCognom1("Muñoz");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("3295");
            this.pacientDao.create(pacient);

            pacient.setNom("Marcos");
            pacient.setCognom1("Ferrando");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2897");
            this.pacientDao.create(pacient);

            pacient.setNom("Claudia");
            pacient.setCognom1("Ghigoarta");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2710");
            this.pacientDao.create(pacient);

            pacient.setNom("Alejandro");
            pacient.setCognom1("Gil");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2734");
            this.pacientDao.create(pacient);

            pacient.setNom("Emilio");
            pacient.setCognom1("Sanz");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2704");
            this.pacientDao.create(pacient);

            pacient.setNom("Oscar");
            pacient.setCognom1("Aponte");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2619");
            this.pacientDao.create(pacient);

            pacient.setNom("Victor");
            pacient.setCognom1("Bonina");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("1048");
            this.pacientDao.create(pacient);

            pacient.setNom("Beatriz");
            pacient.setCognom1("Iordache");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2756");
            this.pacientDao.create(pacient);

            pacient.setNom("Ana");
            pacient.setCognom1("García");
            pacient.setCognom2("de Frutos");
            pacient.setNumeroHistoria("2612");
            this.pacientDao.create(pacient);

            pacient.setNom("Victoria");
            pacient.setCognom1("Benavent");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2686");
            this.pacientDao.create(pacient);

            pacient.setNom("Laura");
            pacient.setCognom1("García");
            pacient.setCognom2("Escandell");
            pacient.setNumeroHistoria("3346");
            this.pacientDao.create(pacient);

            pacient.setNom("Luca");
            pacient.setCognom1("Centonze");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2021");
            this.pacientDao.create(pacient);

            pacient.setNom("Charrok");
            pacient.setCognom1("Ouakti");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("3278");
            this.pacientDao.create(pacient);

            pacient.setNom("Rebeca");
            pacient.setCognom1("Soler");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("90");
            this.pacientDao.create(pacient);

            pacient.setNom("Anna");
            pacient.setCognom1("Melchor");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("3220");
            this.pacientDao.create(pacient);

            pacient.setNom("Iris");
            pacient.setCognom1("Cano");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("3056");
            this.pacientDao.create(pacient);

            pacient.setNom("Olga");
            pacient.setCognom1("Vidal");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("3123");
            this.pacientDao.create(pacient);

            pacient.setNom("Pedro");
            pacient.setCognom1("Cosín");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("89");
            this.pacientDao.create(pacient);

            pacient.setNom("Antonio");
            pacient.setCognom1("Serrano");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("3344");
            this.pacientDao.create(pacient);

            pacient.setNom("Toñi");
            pacient.setCognom1("Montaño");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2899");
            this.pacientDao.create(pacient);

            pacient.setNom("Mª Fernanda");
            pacient.setCognom1("Alves");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2647");
            this.pacientDao.create(pacient);

            pacient.setNom("Beatriz");
            pacient.setCognom1("Martinez");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("1715");
            this.pacientDao.create(pacient);

            pacient.setNom("Eva");
            pacient.setCognom1("Cuenca");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("1075");
            this.pacientDao.create(pacient);

            pacient.setNom("Victor");
            pacient.setCognom1("Bonina");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("1048");
            this.pacientDao.create(pacient);

            pacient.setNom("Sonia");
            pacient.setCognom1("Salvatierra");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("1063");
            this.pacientDao.create(pacient);

            pacient.setNom("Marcos");
            pacient.setCognom1("Ferrando");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2897");
            this.pacientDao.create(pacient);

            pacient.setNom("Mónica");
            pacient.setCognom1("Chavarria");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2682");
            this.pacientDao.create(pacient);

            pacient.setNom("Vanesa");
            pacient.setCognom1("Domingo");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2709");
            this.pacientDao.create(pacient);

            pacient.setNom("Javier");
            pacient.setCognom1("Esponera");
            pacient.setCognom2("");

            pacient.setNumeroHistoria("2649");
            this.pacientDao.create(pacient);

        } catch (SQLException e) {
            Log.e("ERROR", "ERROR creando usuarios", e);
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
        calIni.set(Calendar.DAY_OF_MONTH, 0);
        calIni.set(Calendar.MONTH, month);
        calIni.set(Calendar.YEAR, year);

        Calendar calFin = Calendar.getInstance();
        calFin.set(Calendar.MONTH, month);
        calFin.set(Calendar.YEAR, year);
        calFin.set(Calendar.DAY_OF_MONTH, calFin.getActualMaximum(Calendar.DAY_OF_MONTH));

        QueryBuilder<LineaFactura, Long> queryBuilder = this.getLineaFacturaDao().queryBuilder();
        // queryBuilder.where().le("fecha",calFin.getTime()).and().ge("fecha",calIni.getTime());
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

    public List<Pacient> buscarPacientesPorHistoriayApellido(String apellido, String numHistoria) throws SQLException {

        List<Pacient> pacientes = null;

        if (numHistoria != null) {
            Map<String, Object> fieldsPaciente = new HashMap<>();
            fieldsPaciente.put("numeroHistoria", numHistoria);
            pacientes = getPacientesByFields(fieldsPaciente);
        } else if (apellido != null) {
            QueryBuilder<Pacient, Long> queryBuilder = this.getPacientDao().queryBuilder();
            queryBuilder.where().like("cognom1", apellido).or().like("cognom2", apellido);
            PreparedQuery<Pacient> preparedQuery = queryBuilder.prepare();

            pacientes = this.getPacientDao().query(preparedQuery);
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

        if (gastos != null && gastos.size() == 1) {
            return gastos.get(0);
        }

        return null;

    }


}
