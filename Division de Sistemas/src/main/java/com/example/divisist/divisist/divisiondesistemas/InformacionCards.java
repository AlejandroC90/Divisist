package com.example.divisist.divisist.divisiondesistemas;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;




/**
 * Created by alejandro on 31/03/15.
 */
public class InformacionCards extends ActionBarActivity implements SharedPreferences.OnSharedPreferenceChangeListener{


    private String tablaNotas;


    private String tablaHorario;

    private String nombreEs;
    private String codigoEs;
    private Toolbar toolbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informacion_cards);

        ListView list = (ListView)findViewById(R.id.list_view);

        list.addHeaderView(new View(this));
        list.addFooterView(new View(this));





        Bundle bundle = getIntent().getExtras();
        this.tablaNotas = bundle.getString("pagina");
        this.tablaHorario = bundle.getString("horario");
        this.nombreEs = bundle.getString("nombre");
        this.codigoEs = bundle.getString("codigo");

        setTitle(nombreEs + " - " +codigoEs  + " - NOTAS");

        Document doc = Jsoup.parse(tablaNotas);


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

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }


}
