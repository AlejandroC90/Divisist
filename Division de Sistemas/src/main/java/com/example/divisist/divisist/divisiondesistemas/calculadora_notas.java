package com.example.divisist.divisist.divisiondesistemas;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by alejandro on 11/12/14.
 */
public class calculadora_notas extends Activity {

    double p1 = 0;
    double p2 = 0;
    double p3 = 0;
    double ex = 0;
    double de = 0;

    private EditText pre1,pre2,pre3,exa,def;
    private Button reini;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculadora_notas);

        this.pre1 = (EditText)findViewById(R.id.editText);
        this.pre2 = (EditText)findViewById(R.id.editText2);
        this.pre3 = (EditText)findViewById(R.id.editText3);
        this.exa = (EditText)findViewById(R.id.editText4);

        this.reini = (Button) findViewById(R.id.reiniciar);

        this.def = (EditText)findViewById(R.id.editText5);
        this.calcularFinal();

        this.reini.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

               pre1.setText("");
               pre2.setText("");
               pre3.setText("");
               exa.setText("");
            }



        });

        pre1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try{

                    if(pre1.getText().toString().equals("")){
                        p1=0.0;
                        calcularFinal();
                        return;
                    }

                    p1 = Double.valueOf(pre1.getText().toString());
                    if (p1>5){
                        p1=0.0;
                        pre1.setText("");
                        Toast.makeText(getApplicationContext(), "La nota no puede ser mayor que 5", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    calcularFinal();

                }catch (Exception e){
                    return;
                }

            }
        });

        pre2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try{
                    if(pre2.getText().toString().equals("")){
                        p2=0.0;
                        calcularFinal();
                        return;
                    }

                    p2 = Double.valueOf(pre2.getText().toString());
                    if (p2>5){
                        p2=0.0;
                        pre2.setText("");
                        Toast.makeText(getApplicationContext(), "La nota no puede ser mayor que 5", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    calcularFinal();

                }catch (Exception e){
                    return;
                }

            }
        });


        pre3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {

                    if(pre3.getText().toString().equals("")){
                        p3=0.0;
                        calcularFinal();
                        return;
                    }

                    p3 = Double.valueOf(pre3.getText().toString());
                    if (p3 > 5) {
                        p3 = 0.0;
                        pre3.setText("");
                        Toast.makeText(getApplicationContext(), "La nota no puede ser mayor que 5", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    calcularFinal();

                } catch (Exception e) {
                    return;
                }

            }
        });

        exa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {

                    if(exa.getText().toString().equals("")){
                        ex=0.0;
                        calcularFinal();
                        return;
                    }
                    ex = Double.valueOf(exa.getText().toString());
                    if (ex > 5.0) {
                        ex = 0.0;
                        exa.setText("");
                        Toast.makeText(getApplicationContext(), "La nota no puede ser mayor que 5", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    calcularFinal();

                } catch (Exception e) {
                    return;
                }

            }
        });



    }




public void calcularFinal(){

    try {
        p1 = Double.valueOf(pre1.getText().toString());
    }catch (Exception e){
         p1=0.0;
    }
    try {
        p2 = Double.valueOf(pre2.getText().toString());
    }catch (Exception e){
        p2=0.0;
    }
    try {
        p3 = Double.valueOf(pre3.getText().toString());
    }catch (Exception e){
        p3=0.0;
    }

    try {
        ex = Double.valueOf(exa.getText().toString());
    }catch (Exception e){
        ex=0.0;
    }

    double definitiva = (((this.p1+this.p2+this.p3)/3)*0.7+(this.ex*0.3));



    this.def.setText(Double.toString(Math.round((definitiva)*100.00)/100.00));

    }

    public void calcularExamen(){
        double defi= de - ((((this.p1+this.p2+this.p3)/3)*0.7)/0.3);
        this.exa.setText(Double.toString(Math.round((defi)*100.00)/100.00));

    }

    public void calcularTodo(){
        this.pre1.setText(Double.toString(Math.round((p1) * 100.00) / 100.00));
        this.pre2.setText(Double.toString(Math.round((p2) * 100.00) / 100.00));
        this.pre3.setText(Double.toString(Math.round((p3) * 100.00) / 100.00));
        this.exa.setText(Double.toString(Math.round((ex)*100.00)/100.00));
    }
}

