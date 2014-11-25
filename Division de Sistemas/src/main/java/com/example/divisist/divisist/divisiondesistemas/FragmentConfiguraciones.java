package com.example.divisist.divisist.divisiondesistemas;

/**
 * Created by alejandro on 1/10/14.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;
import android.content.Context;


public class FragmentConfiguraciones extends PreferenceFragment  {




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.configuracion);


    }




}
