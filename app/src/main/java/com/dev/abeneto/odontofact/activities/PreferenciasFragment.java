package com.dev.abeneto.odontofact.activities;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.dev.abeneto.odontofact.R;

/**
 * Created by Alberto on 07/10/2016.
 */
public class PreferenciasFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }

}

