package com.dev.abeneto.charanifact.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.abeneto.charanifact.R;
import com.dev.abeneto.charanifact.db.DatabaseHelper;
import com.dev.abeneto.charanifact.fragments.AltaPacienteFragment;
import com.dev.abeneto.charanifact.fragments.Fragment2;
import com.dev.abeneto.charanifact.fragments.Fragment3;
import com.dev.abeneto.charanifact.fragments.FragmentAddGastosLaboratori;
import com.dev.abeneto.charanifact.fragments.FragmentConsultarPacientes;
import com.dev.abeneto.charanifact.pojo.Usuari;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private Usuari usuariLogat;
    private DatabaseHelper dbHelper;
    private static Menu miMenu;
    private ImageView ivFondoGlobal;
    private List<Fragment> listaFragmentsVisitados = new ArrayList<>();
    private List<MenuItem> menuItemsFragmentsVisitados = new ArrayList<>();
    private static Boolean dobleClickSalir = false;
    private Toast toastSalir;


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

                                Bundle args = new Bundle();
                                args.putBoolean("showAsDialog", Boolean.FALSE);
                                fragment.setArguments(args);

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

                        }

                        if (fragmentTransaction) {


                            Fragment fragmentActual = null;
                            if (getSupportFragmentManager().getFragments() != null && !getSupportFragmentManager().getFragments().isEmpty()) {
                                fragmentActual = getSupportFragmentManager().getFragments().get(0);
                                listaFragmentsVisitados.add(fragmentActual);
                                menuItemsFragmentsVisitados.add(menuItem);
                            }

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


    @Override
    protected void onStart() {
        super.onStart();
/**
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new AltaPacienteFragment())
                .commit();*/
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
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (layout.isDrawerOpen(GravityCompat.START)) {
            layout.closeDrawer(GravityCompat.START);
        } else {
            if (listaFragmentsVisitados == null || listaFragmentsVisitados.isEmpty()) {

                if (!dobleClickSalir) {
                    toastSalir = Toast.makeText(this, "Vuelve a pulsar el botón atrás para salir", Toast.LENGTH_SHORT);
                    toastSalir.setGravity(Gravity.CENTER, 0, 30);
                    toastSalir.show();
                    dobleClickSalir = true;
                } else {
                    dobleClickSalir = false;
                    super.onBackPressed();
                }
            } else {

                Fragment fragmentAVolver = listaFragmentsVisitados.get(listaFragmentsVisitados.size() - 1);
                MenuItem menuItem = menuItemsFragmentsVisitados.get(menuItemsFragmentsVisitados.size() - 1);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragmentAVolver)
                        .commit();

                menuItem.setChecked(true);
                getSupportActionBar().setTitle(menuItem.getTitle());

            }


        }
    }

    @Override
    protected void onDestroy() {
        if (toastSalir != null) {
            toastSalir.cancel();
        }
        super.onDestroy();
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

        } catch (SQLException e) {
            Log.e("ERROR EN LOGOUT: ", "LOGOUT: ", e);
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
