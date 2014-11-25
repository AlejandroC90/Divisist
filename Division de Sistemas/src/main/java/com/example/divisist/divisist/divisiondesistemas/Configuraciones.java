package com.example.divisist.divisist.divisiondesistemas;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by alejandro on 8/10/14.
 */
public class Configuraciones extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new FragmentConfiguraciones())
                .commit();

       // setTitle(R.string.Conf);

     //   PreferenceManager.setDefaultValues(this, R.xml.configuracion, false);
    }


    }

