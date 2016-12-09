package com.dev.abeneto.odontofact.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Alberto on 07/10/2016.
 */
public class Preferencias extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferenciasFragment())
                .commit();
    }
/*
    SharedPreferences.OnSharedPreferenceChangeListener spChanged = new
            SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                                      String key) {
                    String idioma = sharedPreferences.getString("language_property", "0");
                }
            };*/

}
