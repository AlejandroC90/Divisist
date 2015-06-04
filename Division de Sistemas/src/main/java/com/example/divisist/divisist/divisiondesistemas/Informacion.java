package com.example.divisist.divisist.divisiondesistemas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
//import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by alejandro on 14/03/14.
 */
public class Informacion extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private WebView navegador;
    private String tablaNotas;
    private String tablaHorario;

    private String nombreEs;
    private String codigoEs;

    public boolean seMuestra;

    //variables menu lateral

    private ListView navList;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);



        this.navegador = (WebView) findViewById(R.id.navegador);

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);



        Bundle bundle = getIntent().getExtras();
        this.tablaNotas = bundle.getString("pagina");
        this.tablaHorario = bundle.getString("horario");
        this.nombreEs = bundle.getString("nombre");
        this.codigoEs = bundle.getString("codigo");
        this.seMuestra=true;
       // setTitle(codigo());

  //     try {
            //se coloca el titulo de en la action bar
            setTitle(nombreEs + " - " +codigoEs  + " - NOTAS");

            navegador.loadData(tablaNotas, "text/html", null);


//*
         //Se crea la notificación
     //      NotificationCompat.Builder mBuilder =
       ///             new NotificationCompat.Builder(this)
             //               .setSmallIcon(R.drawable.ufpsinletras)
               //             .setContentTitle("Notas UFPS")
                 //           .setContentText("Bienvenido");

          //  int mNotificationId = 001;
            // Gets an instance of the NotificationManager service
           // NotificationManager mNotifyMgr =
             //       (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // Builds the notification and issues it.
           // mNotifyMgr.notify(mNotificationId, mBuilder.build());

/**

        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Se ha producido un error, Reintente", Toast.LENGTH_SHORT).show();
            this.finish();

        }

*/
       // navegador.loadData(tablaNotas,"text/tablaNotas",null);




        //inicializa el menú lateral




    }



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




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.informacion, menu);
        return true;
    }


    public String codigo (){

        return  tablaNotas.substring(1140,1147);
    }

    public String nombre () {
        int i = 1258;
        int j = 1258;
        boolean finalNombre=false;
        char a=' ';


        while(finalNombre!=true){

            a = tablaNotas.charAt(j);
            if(String.valueOf(a).equalsIgnoreCase("<")){
                finalNombre = true;
            }
            j++;
        }



          return invertirNombre(tablaNotas.substring(i, j - 1));

    }

    public String invertirNombre(String nombreinvertido){
        String nombre = nombreinvertido;
        String separador = "[ ]";
        String[] arreglonombre = nombre.split(separador);

        return arreglonombre[2] + " " + arreglonombre [0];


    }


    public String tabladeMaterias(){

        String matriculadas;
        int inicio = tablaNotas.indexOf("MATERIAS MATRICULADAS");
        int fin = tablaNotas.indexOf("400");
        this.cambiarTamañoTabla();


       return "<html> <head><link href=\"http://divisist.ufps.edu.co/hojas_estilo/estilosnuevos.css\" rel=\"stylesheet\" type=\"text/css\"></head>" +  tablaNotas.substring(inicio-228,fin-81) + "</html>";

     //   return this.tablaNotas;
//return tablaNotas.substring(inicio-238,fin-53);
// return tablaNotas.substring(inicio-228,fin-81);
    }

    public void cambiarTamañoTabla(){

        this.tablaNotas = tablaNotas.replaceAll("600","100%");

    }

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

            case R.id.cerrarsesion:
                Toast.makeText(getApplicationContext(), "Sesión Cerrada", Toast.LENGTH_SHORT).show();
                this.finish();
                return true;

         /**   case R.id.cambiar:
                if(seMuestra==true){
                    this.navegador.loadData(tablaHorario,"text/html",null);s
                    setTitle(nombreEs + " - " + codigoEs + " - HORARIO");
                   seMuestra=false;
                    return true;
                }
                if(seMuestra==false) {
                    this.navegador.loadData(tablaNotas, "text/html", null);
                    setTitle(nombreEs + " - " + codigoEs + " - NOTAS");
                    seMuestra = true;
                    return true;
                }
*/

            case R.id.Cal:
                Intent k = new Intent(this,calculadora_notas.class);
                startActivity(k);
                return true;
        }

        return false;
    }


    public boolean queSeMuestra(){

        return false;
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

    public void borrarDatos(){



    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {


    }


}
