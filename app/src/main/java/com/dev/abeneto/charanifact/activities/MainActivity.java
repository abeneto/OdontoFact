package com.dev.abeneto.charanifact.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.abeneto.charanifact.R;
import com.dev.abeneto.charanifact.db.DatabaseHelper;
import com.dev.abeneto.charanifact.fragments.AltaPacienteFragment;
import com.dev.abeneto.charanifact.fragments.Fragment2;
import com.dev.abeneto.charanifact.fragments.Fragment3;
import com.dev.abeneto.charanifact.fragments.FragmentAddGastosLaboratori;
import com.dev.abeneto.charanifact.fragments.FragmentConsultarPacientes;
import com.dev.abeneto.charanifact.pojo.Usuari;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private Usuari usuariLogat;
    private DatabaseHelper dbHelper;
    private static Menu miMenu;
    private ImageView ivFondoGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.usuariLogat = (Usuari) getIntent().getExtras().getSerializable("usuari");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navview);

        this.ivFondoGlobal = (ImageView) findViewById(R.id.imageBackGlobal);
        this.ivFondoGlobal.setImageResource(R.drawable.inicio_odontofac);

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        ivFondoGlobal.setVisibility(View.INVISIBLE);

                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                fragment = new AltaPacienteFragment();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                fragment = new Fragment2();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_alta_gastos_lab:
                                fragment = new FragmentAddGastosLaboratori();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_3:
                                fragment = new Fragment3();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_item_consultar_pacientes:
                                fragment = new FragmentConsultarPacientes();
                                fragmentTransaction = true;
                                break;

                            case R.id.menu_opcion_2:
                                Log.i("NavigationView", "Pulsada opción 2");
                                break;
                        }

                        if (fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });

        this.setElementosPantallaUsuario();
    }


    private void setElementosPantallaUsuario() {
        TextView text = (TextView) findViewById(R.id.labelNombreUsuario);
        if (this.usuariLogat != null && this.usuariLogat.getNombre() != null && this.usuariLogat.getApellido1() != null) {
            String nombreMostrar = usuariLogat.getNombre() + " " + usuariLogat.getApellido1();
            text.setText(nombreMostrar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.miMenu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);

        miMenu.findItem(R.id.delete).setVisible(false);


        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Listener para los botones del ActionBar
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                Intent i = new Intent(this, Preferencias.class);
                startActivity(i);
                break;
            case R.id.action_logout:
                deslogarUsuarioActual();
                Intent intent_login = new Intent(this, LoginActivity.class);
                startActivity(intent_login);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuarioActual() {

        Usuari usuari = null;

        try {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            List<Usuari> usuaris = db.getUsuariDao().queryForAll();

            for (Usuari usuariAux : usuaris) {
                if (usuariAux.getLogado() == Boolean.TRUE) {
                    usuari = usuariAux;
                    break;
                }
            }

            usuari.setLogado(Boolean.FALSE);

            db.getUsuariDao().update(usuari);

        }catch (SQLException e) {
            Log.e("ERROR EN LOGOUT: ","LOGOUT: ", e);
        }
    }

    public Menu getMiMenu() {
        return miMenu;
    }

    public ImageView getIvFondoGlobal() {
        return ivFondoGlobal;
    }

    public void setIvFondoGlobal(ImageView ivFondoGlobal) {
        this.ivFondoGlobal = ivFondoGlobal;
    }
}
