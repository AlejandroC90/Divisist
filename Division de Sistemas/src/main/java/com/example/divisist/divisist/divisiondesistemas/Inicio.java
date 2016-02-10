package com.example.divisist.divisist.divisiondesistemas;


/**Los imports necesarios para ejecutar la aplicación */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

import static com.example.divisist.divisist.divisiondesistemas.R.string.espere;

/**
 * VERSIÓN FINAL CON EL CÓDIGO FUNCIONANDO, TIENE CÓDIGO QUE NO USARÉ MÁS Y SERÁ BORRADO
 * TENDRÁ EL NOMBRE DE VERSIÓN 2.0
 *
 */

public  class Inicio extends Activity {

    /**Declaro todos los componentes de la interfaz, Botones, Cuadros de Texto, también las variables que uso
     * para almacenar la información.
     */

    /** el botón de iniciar sesión */
    private  Button iniciarSesion;

    /**los cuadros de texto donde se escriben los datos*/
    private EditText textoCodigo;
    private EditText textoContraseña;

    /**los datos del estudiante que son necesarios para iniciar sesión*/
    private String codigo = "codigo";
    private String valorcodigo;
    private String contraseña="clave";
    private String valorcontraseña;


    /**Strings que uso para almacenar la información del estudiante para luego ser enviada a la clase Información*/
    private String nombreEstudiante;
    private String codigoEstudiante;
    private String TablaMaterias;
    public String tablaHorario;
    public String paginaWebdeNotas;
    public String paginaWebdeHorario;


    /**Dialogo que muestra el "Iniciando Sesión" */
    private ProgressDialog cargando;
    private Context context;


    /**las URLs a la que se conecta la app: Divisist, Materias, Horarios*/
    String urlDivisist ="http://divisist.ufps.edu.co/index.php";
    String urlMaterias ="http://divisist.ufps.edu.co/informacionacademica/materias.php";
    String urlHorario  = "http://divisist.ufps.edu.co/informacionacademica/horarios.php";
    String espere = String.valueOf(R.string.espere);


    /**
     * Método propio de Android
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        /**Se le pone el layout que quiero mostrar en la pantalla de inicio, en este caso el de inicio de sesión */
        setContentView(R.layout.activity_inicio);

        /**Contexto de la aplicación, lo que se muestra ahora*/
        context = this;

        /**INICIALIZACIÓN DE LO QUE DECLARÉ MÁS ARRIBA, STRINGS, BOTOENES, CUADROS DE TEXTO*/

        /**inicializo los datos del estudiante*/
        this.paginaWebdeNotas ="";
        this.valorcodigo="";
        this.valorcontraseña="";


        /** Botón de iniciar sesión*/
        this.iniciarSesion = (Button) findViewById(R.id.peticionPOST);

        /**inicializo los cuadros de texto de codigo y contraseña*/
        this.textoCodigo=(EditText)findViewById(R.id.codigo);
        this.textoContraseña =(EditText)findViewById(R.id.contraseña);

        /**permite que el teclado no se ponga encima de los cuadros de contraseña y usuario cuando el usuario esté
         * digitando la información
         */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        /**En esta condición confirmo que no hayan datos guardados en la aplicación, si los hay se inicia sesión
         * automáticamente
         */
            if(hayDatos()){

            /**si se cumple la condición anterior entonces desactivo los botones*/
            iniciarSesion.setEnabled(false);
            iniciarSesion.setClickable(false);
            textoCodigo.setEnabled(false);
            textoContraseña.setEnabled(false);


            /**como hay datos guardados, simplemente los busco donde deberían estar guardados
             * y se los paso a las variables
             * */
            SharedPreferences settings = getSharedPreferences("datos", 0);
            valorcodigo = settings.getString("codigo","").toString();
            valorcontraseña = settings.getString("contraseña","").toString();

            /**por ultimo ejecuto el hilo de buscar las Notas, el hilo envía o inicia la siguiente
             * actividad
             * */
            BuscarNotas irPorLasNotas = new BuscarNotas(Inicio.this);
            irPorLasNotas.execute();
        }


        /**Método que escucha al botón de iniciar, espera el click
         *
         */


       this.iniciarSesion.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               /**Se desactivan los cuadros de texto y/o botones*/
               iniciarSesion.setEnabled(false);
               textoContraseña.setEnabled(false);
               textoCodigo.setEnabled(false);

               /**Capturo la información ingresada por el usuario */
               valorcodigo = textoCodigo.getText().toString();
               valorcontraseña = textoContraseña.getText().toString();


              /**Inicializo la página web completamente vacía */
               paginaWebdeNotas = "";

               /**Reviso que la información ingresada no sea nula
                *Aún no se revisa si la información es correcta, tamaño del codigo y contraseña sean correctos
                * igualmente no se podrá inicial sesión si la información no es correcta
                *
                */
               if (valorcontraseña.isEmpty() || valorcodigo.isEmpty()) {

                   Toast.makeText(getApplicationContext(), R.string.espacio_vacio, Toast.LENGTH_SHORT).show();

                   /**Como ha encontrado espacios vacíos, se procede a regresar al usuario*/
                   iniciarSesion.setEnabled(true);
                   textoContraseña.setEnabled(true);
                   textoCodigo.setEnabled(true);

                   return;
               }


               /**Si se ha llegado hasta aquí, quiere decir la información es relativamente correcta
                * se procede a iniciar sesión normalmente, se pone dentro de una excepción en caso de que ocurran
                * errores
                */

               try {
                   BuscarNotas irPorLasNotas = new BuscarNotas(Inicio.this);
                   irPorLasNotas.execute();

               } catch (Exception e) {
                   /** En el caso de que se produzca un error, es captura por la excepción, y se hace retornar al usuario
                    *
                    */
                   iniciarSesion.setEnabled(true);
                   textoContraseña.setEnabled(true);
                   textoCodigo.setEnabled(true);
                   /**Mostramos el error */
                   Toast.makeText(getApplicationContext(), R.string.error_app, Toast.LENGTH_SHORT).show();

                   return;

               }


           }
       });






    }


    /**
     * El onpause se llama cuando el usuario pausa la actividad al ver notificaciones o recibir una llamada, etc...
     */
    public void onPause() {
      super.onPause();
    }

    /**
     * Metodo que se llama en el oncreate
     */

    public void onResume(){
        super.onResume();
    }

    /**
     * El onstop se llama cuando la actividad ya no se encuentra visible para el usuario
     */
    public void onStop(){
        super.onStop();
    }

    /**
     * El onstart se llama cuando el usuario llama la actividad del onStop y la vuelve a mostrar
     */
    public void onRestart(){
        super.onRestart();
        iniciarSesion.setEnabled(true);
        textoCodigo.setEnabled(true);
        textoContraseña.setEnabled(true);
        textoCodigo.setText("");
        textoContraseña.setText("");

    }


    /**Método que pone el menú en el layout
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }


    /**Método que controla el comportamiento del menú del layout inicializado arriba
     *
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

            case R.id.Cal:
                Intent j = new Intent(this,calculadora_notas.class);
                startActivity(j);
                return true;
        }
        return false;
    }




    /**Al ser una consulta de uso web, se ejecuta en otro hilo para prevenir el congelamiento de la interaz
     * Creación de un hilo de ejecución mediante Asynctask de android
     */
    private class BuscarNotas extends AsyncTask<Void, Void, Void> {

        public BuscarNotas(Activity activity) {
        }

        /**metodo de asynctask que se ejecuta antes de empezar el trabajo del hilo, en este caso
         * muestro la ventana de Iniciando Sesión
         */

        protected void onPreExecute() {
          //  setProgressBarIndeterminateVisibility(true);
            cargando = new ProgressDialog(context);
            cargando.setTitle(R.string.iniciando_sesion);
            cargando.setMessage(getResources().getString(R.string.espere));
            cargando.setCancelable(false);
            cargando.setIndeterminate(true);
            cargando.show();
            //Toast.makeText(getApplicationContext(), R.string.iniciando_sesion, Toast.LENGTH_SHORT).show();
        }

        /**
        *la actividad principal de hilo, en este caso, enviar las peticiones y tomar la tabla de materias
         */
        @Override
        protected Void doInBackground(Void... voids) {
            //se llama el metodo de peticion post y get
           peticionPostyGet();
           return null;
        }

        /**metodo que se llama despues que se ejecuta el hilo
         *
         * @param a
         */
        protected void onPostExecute(Void a) {
          //  setProgressBarIndeterminateVisibility(false);
            /**se toma la pagina web y se sacan las notas, en el caso de que se produzcan errores, se muestra
             * el toast y se regresa al usuario
             *
             */
            try {
                 sacarNombreNotas();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), R.string.error_app, Toast.LENGTH_SHORT).show();
                iniciarSesion.setEnabled(true);
                textoCodigo.setEnabled(true);
                textoContraseña.setEnabled(true);
                cargando.dismiss();
                return;
            }

            if(nombreEstudiante==null||nombreEstudiante.equals("")){
                Toast.makeText(getApplicationContext(), R.string.error_app, Toast.LENGTH_SHORT).show();
                iniciarSesion.setEnabled(true);
                textoCodigo.setEnabled(true);
                textoContraseña.setEnabled(true);
                valorcodigo="";
                valorcontraseña="";
                cargando.dismiss();
                return;
            }

            /**
             * En el caso de que no se produzca ningún problema tomando la información: tomando notas
             * y horario, se procede a iniciar la siguiente ventana, en la cual se muestra, el dialogo se borra
             * de la pantalla.
             * También se le envía la información a la otra interfaz
             */

            Intent notas = new Intent();
            notas.setClass(getApplicationContext(), InformacionenPestanas.class);
            notas.putExtra("pagina", TablaMaterias);
            notas.putExtra("codigo", codigoEstudiante);
            notas.putExtra("nombre", nombreEstudiante);
            notas.putExtra("contraseña",valorcontraseña);
            notas.putExtra("horario", tablaHorario);
            startActivity(notas);
            cargando.dismiss();

        }
    }

    /**El corazón de la aplicación:
     * Peticion Post Y Get que permite iniciar sesión para tomar la tabla de materias y horario
     * los resultados se le ponen a las variables
     * */
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

    /**
     * Método usado por las peticiones Post y Get, recibe el contenido de la página para convertirlo a string
     * @param br
     * @return
     */
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


    /**Metodo que tomael Nombre y las Notas de las Materias del Estudiante
     *
     */
    public void sacarNombreNotas(){


        nombreEstudiante = nombre();
        this.codigoEstudiante = valorcodigo;
        TablaMaterias = tabladeMaterias();
        tablaHorario  = tabladeHorarios();
     }

    /**
     * Método que toma la tabla de Horario del html puro
     *
     * @return regresa una página web con solo una tabla, la tabla del horario
     */
    public String tabladeHorarios(){
        int inicio = paginaWebdeHorario.indexOf("LUNES");
        int fin = paginaWebdeHorario.indexOf("22");
        this.cambiarTamañoTabla();
        return "<html> <head> <LINK REL=\"StyleSheet\" HREF=\"http://divisist.ufps.edu.co/hojas_estilo/hoja_estilo.css\" TYPE=\"text/css\"> <link href=\"http://divisist.ufps.edu.co/hojas_estilo/estilosnuevos.css\" rel=\"stylesheet\" type=\"text/css\"></head>" +  paginaWebdeHorario.substring(inicio-277,fin+341) + "</html>";
    }

    /**Método que toma precisamente la tabla que contiene las materias matriculadas por el estudiante
     * usa métodos de la clase String para saber de donde a dónde empezar
     * @return una página web con la tabla de las Materias Matriculadas
     */
    public String tabladeMaterias(){
        int inicio = paginaWebdeNotas.indexOf("MATERIAS MATRICULADAS");
        int fin = paginaWebdeNotas.indexOf("400");
       // this.cambiarTamañoTabla();
        try {
            return "<html> <head><link href=\"http://divisist.ufps.edu.co/hojas_estilo/estilosnuevos.css\" rel=\"stylesheet\" type=\"text/css\"></head>" + paginaWebdeNotas.substring(inicio - 228, fin - 81) + "</html>";
        }catch (Exception e
                ){
            return "<html> <head><link href=\"http://divisist.ufps.edu.co/hojas_estilo/estilosnuevos.css\" rel=\"stylesheet\" type=\"text/css\"></head>" + paginaWebdeNotas.substring(inicio - 228, paginaWebdeNotas.length()) + "</html>";

        }

        //return "<html> <head><link href=\"http://divisist.ufps.edu.co/hojas_estilo/estilosnuevos.css\" rel=\"stylesheet\" type=\"text/css\"></head></html>";

        //   return this.html;
        //return html.substring(inicio-238,fin-53);
        // return html.substring(inicio-228,fin-81);
    }

    /**Método que cambia el tamaño de la tabla de las materias matriculadas, lo cual permite que ocupe todo el espacio
     * del teléfono
     */
    public void cambiarTamañoTabla(){
        this.paginaWebdeNotas = paginaWebdeNotas.replaceAll("600","100%");


        this.paginaWebdeHorario =paginaWebdeHorario.replaceAll("700","100%");
    }

    /**Método que toma el nombre del estudiante
     *
      * @return un String con el primer nombre y primer apellido del estudiante
     * el nombre aparece al revés en divisist, el método lo invierte automáticamente.
     */
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

    /**Invierte un nombre de cuatro posiciones */
    public String invertirNombre(String nombreinvertido){
        String nombre = nombreinvertido;
        String separador = "[ ]";
        String[] arreglonombre = nombre.split(separador);

        return arreglonombre[2] + " " + arreglonombre [0];


    }

    /**Método que permite comprobar que haya información o no almancenada del estudiante, si la hay
     * será usada para iniciar sesión automáticamente
     * @return el valor de si hay o no notas
     */
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
