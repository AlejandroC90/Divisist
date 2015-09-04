package com.example.divisist.divisist.divisiondesistemas;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by alejandro on 4/7/15.
 *
 * CLASE QUE MUESTRA LAS NOTAS Y EL HORARIO EN DOS PESTAÑAS DIFERENTES, HACE DE USO DE FRAGMENTS
 *
 */
public class InformacionenPestanas extends FragmentActivity implements ActionBar.TabListener {

    /**Inicializo los String necesarios */
    public static String tablaN;
    private static String tablaHorario;
    private String nombreEs;
    private String codigoEs;

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will display the three primary sections of the app, one at a
     * time.
     */
    ViewPager mViewPager;
    private Toolbar toolbar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_tabs);

        Bundle bundle = getIntent().getExtras();
        this.tablaN = bundle.getString("pagina");
        this.tablaHorario = bundle.getString("horario");
        this.nombreEs = bundle.getString("nombre");
        this.codigoEs = bundle.getString("codigo");

        setTitle(nombreEs + " - " + codigoEs);

        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // listener for when this tab is selected.
        actionBar.addTab(
                actionBar.newTab()
                        .setText(R.string.notas)
                        .setTabListener(this));
        // listener for when this tab is selected.
        actionBar.addTab(
                actionBar.newTab()
                        .setText(R.string.horario)
                        .setTabListener(this));

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pestanas, menu);
        return true;
    }

    /**
     * Metódo propio de Android
     */
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        boolean guardarD = sharedPreferences
                .getBoolean("guardar_datos", true);

        if(guardarD==true){
            this.almacenarDatos();
        }

    }

    /**
     * Método que hace acciones dependiendo de lo que se seleccione en Opciones
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.About:
                Intent i = new Intent(this,acerca_de.class);
                startActivity(i);
                return true;
            case R.id.action_search:
                Intent j = new Intent(this,Configuraciones.class);
                startActivity(j);
                return true;
            case R.id.cerrar:
                Toast.makeText(getApplicationContext(), R.string.SesionCerrada, Toast.LENGTH_SHORT).show();
                this.finish();
                return true;
            case R.id.Cal:
                Intent k = new Intent(this,calculadora_notas.class);
                startActivity(k);
                return true;
        }
        return false;
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public  class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            switch (i) {
                case 0:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.
                    return new FragmentNotas();

                case 1:
                    return new FragmentHorario();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Section " + (position + 1);
        }
    }

    public   static class FragmentNotas extends android.support.v4.app.Fragment {


        public FragmentNotas(){

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.informacion_cards, container, false);



            ListView list = (ListView) rootView.findViewById(R.id.list_view);


          //  list.addHeaderView(new View(this));
           // list.addFooterView(new View(this));


            Document doc = Jsoup.parse(tablaN);

            Elements rows = doc.select("tr");
            int tam = rows.size();

            //rows.get(3);

            BaseInflaterAdapter<CardItemData> adapter = new BaseInflaterAdapter<CardItemData>(new CardInflater());

            for(int i = 2;i < tam ;i++){
                Elements data = rows.get(i).getElementsByTag("td");


                CardItemData lineas = new CardItemData(data.get(1).text(),data.get(5).text(),data.get(6).text(),data.get(7).text(),data.get(8).text(),data.get(9).text(),data.get(11).text());
                adapter.addItem(lineas, false);

            }

            list.setAdapter(adapter);



            return rootView;






        }





    }

    public  static class FragmentHorario extends android.support.v4.app.Fragment {

        public FragmentHorario(){

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.activity_informacion, container, false);


            WebView navegador = (WebView) rootView.findViewById(R.id.navegador);
            navegador.loadData(tablaHorario,"text/html",null);
            return rootView;
        }
    }


    public void almacenarDatos(){


        Bundle bundle = getIntent().getExtras();


        SharedPreferences preferencias=getSharedPreferences("datos", 0);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("codigo", bundle.getString("codigo"));
        editor.putString("contraseña", bundle.getString("contraseña"));
        editor.commit();


        //  Toast.makeText(getApplicationContext(), "Datos almacenados", 3000).show();


    }


}
