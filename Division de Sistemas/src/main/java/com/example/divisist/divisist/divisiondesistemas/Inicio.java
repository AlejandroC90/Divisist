package com.example.divisist.divisist.divisiondesistemas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Inicio extends Activity {

    //el botón de iniciar sesión
    private  Button peticionPOST;

    //los cuadros de texto donde se escriben los datos
    private EditText textoCodigo;
    private EditText textoContraseña;

    //los datos del estudiante que son necesarios para iniciar sesión
    private String codigo = "codigo";
    private String valorcodigo;
    private String contraseña="clave";
    private String valorcontraseña;


    //Strings que uso para almacenar la información del estudiante para luego ser enviada a Información
    private String nombreEstudiante;
    private String codigoEstudiante;
    private String TablaMaterias;
    public String tablaHorario;
    public  String paginaWebdeNotas;
    public String paginaWebdeHorario;


    //la URL a la que se conecta la app
    String urlDivisist ="http://divisist.ufps.edu.co/index.php";
    String urlMaterias ="http://divisist.ufps.edu.co/informacionacademica/materias.php";
    String urlHorario  = "http://divisist.ufps.edu.co/informacionacademica/horarios.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //inicializo los datos del estudiante
        this.paginaWebdeNotas ="";
        this.valorcodigo="";
        this.valorcontraseña="";

        //inicializo el botón de iniciar sesión
        this.peticionPOST = (Button) findViewById(R.id.peticionPOST);

        //inicializo los cuadros de texto de codigo y contraseña
        this.textoCodigo=(EditText)findViewById(R.id.codigo);
        this.textoContraseña =(EditText)findViewById(R.id.contraseña);

        //permite que el teclado no se ponga encima de los cuadros de contraseña y usuario
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



        //condición que permite saber si el usuario tiene guardados sus datos en la aplicación
        if(hayDatos()){

            //si se cumple la condición anterior entonces desactivo los botones
            peticionPOST.setEnabled(false);
            peticionPOST.setClickable(false);
            textoCodigo.setEnabled(false);
            textoContraseña.setEnabled(false);


            //como hay datos guardados, simplemente los busco donde deberían estar guardados
            SharedPreferences settings = getSharedPreferences("datos", 0);
            valorcodigo = settings.getString("codigo","").toString();
            valorcontraseña = settings.getString("contraseña","").toString();

            //por ultimo ejecuto el hilo de buscar las Notas
            BuscarNotas notasaqui2 = new BuscarNotas(Inicio.this);
            notasaqui2.execute();



           //luego se inicia la actividad con la Información, normalmente

        }



        //el método que se ejecuta cuando el ususario presiona el boton de iniciar sesión
       this.peticionPOST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //nuevamente desactivo los botones mientras se buscan las notas
                peticionPOST.setEnabled(false);
                textoContraseña.setEnabled(false);
                textoCodigo.setEnabled(false);

                //tomo los datos digitados por el usuario
                valorcodigo = textoCodigo.getText().toString();
                valorcontraseña = textoContraseña.getText().toString();


                //pongo en blanco pagina web
                paginaWebdeNotas ="";

                //reviso que en los cuadros de texto no haya ningún valor en blanco
                //falta revisar que el codigo tenga un tamaño fijo y la contraseña también
                if(valorcontraseña.isEmpty()||valorcodigo.isEmpty()){

                    Toast.makeText(getApplicationContext(), R.string.espacio_vacio, Toast.LENGTH_SHORT).show();

                    //como hay un espacio vacio o el codigo no esta completo vuelvo a activar los botones y me regreso
                    peticionPOST.setEnabled(true);
                    textoContraseña.setEnabled(true);
                    textoCodigo.setEnabled(true);

                    return;
                }

                //se crea un objeto de la clase que consulta las notas y se ejecuta

                try {
                    BuscarNotas notasaqui = new BuscarNotas(Inicio.this);
                    notasaqui.execute();
                    //notasaqui.get(20000, TimeUnit.MILLISECONDS);

                }catch (Exception e){
                    // si se produce un error en la busqueda de las notas, se vuelven a activar los botones para que el usuario pueda reintentar
                    peticionPOST.setEnabled(true);
                    textoContraseña.setEnabled(true);
                    textoCodigo.setEnabled(true);
                    Toast.makeText(getApplicationContext(), R.string.error_app, Toast.LENGTH_SHORT).show();

                    return;

                }

     //Este es el delay que usaba para predecir el tiempo en el que se demoraria en conultar las notas
        /**   new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i;
                        i = new Intent(Inicio.this,Informacion.class);
                        i.putExtra("pagina", paginaWebdeNotas);
                        i.putExtra("codigo", valorcodigo);
                        i.putExtra("contraseña", valorcontraseña);

                        startActivity(i);

                    }
                }, 20000);



*/

            }
        });






    }


    //el onpause se llama cuando el usuario pausa la actividad al ver notificaciones o recibir una llamada, etc...
    public void onPause() {
      super.onPause();
    }


    //metodo que se llama en el oncreate
    public void onResume(){
        super.onResume();


    }

    //el onstop se llama cuando la actividad ya no se encuentra visible para el usuario
    public void onStop(){

        super.onStop();




    }

    //el onstart se llama cuando el usuario llama la actividad del onStop y la vuelve a mostrar
    public void onRestart(){
        super.onRestart();
        peticionPOST.setEnabled(true);
        textoCodigo.setEnabled(true);
        textoContraseña.setEnabled(true);
        textoCodigo.setText("");
        textoContraseña.setText("");

    }


    //metodo que infla el menu, en este caso, es el menu inicio
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }



    //método que le dice qué hacer en caso de que se presione x item en el menu
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.About:
                Intent i = new Intent(this,acerca_de.class);
                startActivity(i);
                return true;

            case R.id.Cal:
                Intent j = new Intent(this,calculadora_notas.class);
                startActivity(j);
                return true;
        }
        return false;
    }


    //Creación de un hilo de ejecución mediante Asynctask de android
    private class BuscarNotas extends AsyncTask<Void, Void, Void> {

        public BuscarNotas(Activity activity) {



        }

        //metodo de asynctask que se ejecuta antes de empezar el trabajo del hilo, en este caso, muestro la notificacion
        protected void onPreExecute() {


            Toast.makeText(getApplicationContext(), R.string.iniciando_sesion, Toast.LENGTH_SHORT).show();


        }

        //la actividad principal de hilo, en este caso, enviar las peticiones y tomar la tabla de materias
        @Override
        protected Void doInBackground(Void... voids) {

            //se llama el metodo de peticion post y get
           peticionPostyGet();

           return null;
        }

        //metodo que se llama despues que se ejecuta el hilo
        protected void onPostExecute(Void a) {

            //se toma la pagina web y se sacan las notas

            try {
                 sacarNombreNotas();
            }catch (Exception e){

                Toast.makeText(getApplicationContext(), R.string.error_app, Toast.LENGTH_SHORT).show();

                peticionPOST.setEnabled(true);
                textoCodigo.setEnabled(true);
                textoContraseña.setEnabled(true);

                return;
            }


            if(nombreEstudiante==null||nombreEstudiante.equals("")){
                Toast.makeText(getApplicationContext(), R.string.error_app, Toast.LENGTH_SHORT).show();
                peticionPOST.setEnabled(true);
                textoCodigo.setEnabled(true);
                textoContraseña.setEnabled(true);
                valorcodigo="";
                valorcontraseña="";

                return;
            }

            Intent notas = new Intent();
            notas.setClass(getApplicationContext(),InformacionenPestanas.class);
            notas.putExtra("pagina", TablaMaterias);
            notas.putExtra("codigo", codigoEstudiante);
            notas.putExtra("nombre", nombreEstudiante);
            notas.putExtra("contraseña",valorcontraseña);
            notas.putExtra("horario", tablaHorario);
            startActivity(notas);


        }
    }



    //peticion Post Y Get que permite iniciar sesión para tomar la tabla de materias y horario
    public void peticionPostyGet(){

        HttpClient httpclient = new DefaultHttpClient();

        String html;

        try{
            HttpPost httpost = new HttpPost(urlDivisist);

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair(codigo, valorcodigo));
            nvps.add(new BasicNameValuePair(contraseña,valorcontraseña));

            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            HttpResponse response = httpclient.execute(httpost);
            HttpEntity entity = response.getEntity();
            html = readFromBuffer(
                    new BufferedReader(
                            new InputStreamReader(entity.getContent(),"UTF-8")));
        } catch(Exception e){
            e.printStackTrace();
            // Tratar excepción!!!
        }

        //aqui empieza a tomar la tabla de materias matriculadas
        String html2="";

        try{
            HttpGet httpget = new HttpGet(urlMaterias);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            html2 = readFromBuffer(
                    new BufferedReader(
                            new InputStreamReader(entity.getContent(),"UTF-8")));

       } catch(Exception e){
            e.printStackTrace();


            // Tratar excepción!!!
        }
        paginaWebdeNotas=html2;



        //aqui empieza a tomar la tabla del horario
        String html3="";

        try{
            HttpGet httpget = new HttpGet(urlHorario);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            html3 = readFromBuffer(
                    new BufferedReader(
                            new InputStreamReader(entity.getContent(),"UTF-8")));

        } catch(Exception e){
            e.printStackTrace();


            // Tratar excepción!!!
        }
        paginaWebdeHorario =html3;


    }

    private String readFromBuffer(BufferedReader br){
        StringBuilder text = new StringBuilder();
        try{
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // tratar excepción!!!
        }
        return text.toString();
    }


    //metodo que saca el Nombre y las Notas de las Materias del Estudiante
    public void sacarNombreNotas(){


        nombreEstudiante = nombre();
        this.codigoEstudiante = valorcodigo;
        TablaMaterias = tabladeMaterias();
        tablaHorario  = tabladeHorarios();
     }

    public String tabladeHorarios(){

        int inicio = paginaWebdeHorario.indexOf("LUNES");
        int fin = paginaWebdeHorario.indexOf("22");
        this.cambiarTamañoTabla();


        return "<html> <head> <LINK REL=\"StyleSheet\" HREF=\"http://divisist.ufps.edu.co/hojas_estilo/hoja_estilo.css\" TYPE=\"text/css\"> <link href=\"http://divisist.ufps.edu.co/hojas_estilo/estilosnuevos.css\" rel=\"stylesheet\" type=\"text/css\"></head>" +  paginaWebdeHorario.substring(inicio-277,fin+341) + "</html>";
    }

    //metodo que toma precisamente la tabla que contiene las materias matriculadas por el estudiante
    public String tabladeMaterias(){


        int inicio = paginaWebdeNotas.indexOf("MATERIAS MATRICULADAS");
        int fin = paginaWebdeNotas.indexOf("400");

        this.cambiarTamañoTabla();


        return "<html> <head><link href=\"http://divisist.ufps.edu.co/hojas_estilo/estilosnuevos.css\" rel=\"stylesheet\" type=\"text/css\"></head>" +  paginaWebdeNotas.substring(inicio-228,fin-81) + "</html>";

        //   return this.html;
//return html.substring(inicio-238,fin-53);
// return html.substring(inicio-228,fin-81);
    }

    public void cambiarTamañoTabla(){

        this.paginaWebdeNotas = paginaWebdeNotas.replaceAll("600","100%");
        this.paginaWebdeHorario =paginaWebdeHorario.replaceAll("700","100%");

    }

    public String nombre () {
        int i = 1799;
        int j = 1799;
        boolean finalNombre=false;
        char a=' ';
       // return paginaWebdeNotas.substring(i,j);


     while(finalNombre!=true){

            a = paginaWebdeNotas.charAt(j);
            if(String.valueOf(a).equalsIgnoreCase("<")){
                finalNombre = true;
            }
            j++;
        }



        return invertirNombre(paginaWebdeNotas.substring(i, j - 1));

    }

    public String invertirNombre(String nombreinvertido){
        String nombre = nombreinvertido;
        String separador = "[ ]";
        String[] arreglonombre = nombre.split(separador);

        return arreglonombre[2] + " " + arreglonombre [0];


    }
    public boolean hayDatos(){

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        boolean guardarD = sharedPreferences
                .getBoolean("guardar_datos", false);

        if(guardarD) {
            return true;
        }

        return false;
    }






}
