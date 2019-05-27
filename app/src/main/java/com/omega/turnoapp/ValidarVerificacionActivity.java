package com.omega.turnoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import Modelo.Resultado;
import Servicios.RestChofer;
import Servicios.RestListener;

public class ValidarVerificacionActivity extends AppCompatActivity {
    EditText txtCodigoVerificacion;
    Intent i;
    String CELULAR;
    boolean RESPUESTA;
    String MENSAJE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_verificacion);
        this.setTitle(R.string.title_verificacion);

        txtCodigoVerificacion= findViewById(R.id.txtCodigoVerificacion);
        CELULAR= getIntent().getExtras().getString("celular");
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aceptar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        i= new Intent(this, DatosChoferActivity.class );
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.aceptar) {
            String codigoVerificacion = txtCodigoVerificacion.getText().toString();
            codigoVerificacion = codigoVerificacion.length()==0? "0":codigoVerificacion;
            RestChofer.ValidacionConfirmacionCorreo(new RestListener<ArrayList<Resultado>>() {
                @Override
                public void onResult(ArrayList<Resultado> Object) {
                    RESPUESTA=  Object.get(0).isEstado();
                    MENSAJE = Object.get(0).getMensaje();

                    if(RESPUESTA){
                        Toast.makeText(ValidarVerificacionActivity.this, MENSAJE, Toast.LENGTH_LONG);
                        i.putExtra("celular",CELULAR);
                        startActivity(i);
                    }else{
                        Toast.makeText(ValidarVerificacionActivity.this, MENSAJE, Toast.LENGTH_LONG);
                    }
                }
            },CELULAR,codigoVerificacion);


            //if(codigoVerificacion.length()==6){

            /*}else{
                Toast.makeText(ValidarVerificacionActivity.this, "El código de verificación ", Toast.LENGTH_LONG);
            }*/
        };

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //Si llamas super.onBackPressed(), esto internamente ejecuta finish().
        //super.onBackPressed();
    }
}
