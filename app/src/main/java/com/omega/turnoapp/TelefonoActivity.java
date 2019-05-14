package com.omega.turnoapp;



import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Modelo.Resultado;
import Servicios.RestChofer;
import Servicios.RestListener;
import Servicios.RestManager;

public class TelefonoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telefono);
        this.setTitle("Tu Teléfono");

       /* Button mShowDialog=(Button) findViewById(R.id.btnAceptar);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               showDialog();
                                           }

                                       });            */


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_aceptar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.aceptar) {
            RestManager.getInstance(this);

            RestChofer.getChofer(new RestListener<ArrayList<Resultado>>() {
                @Override
                public void onResult(ArrayList<Resultado> Object) {
                    boolean respuesta=  Object.get(0).isEstado();
                    String mensaje= Object.get(0).getMensaje();
                    if(respuesta){
                        showDialog(mensaje);
                        Toast.makeText(TelefonoActivity.this,mensaje, Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(TelefonoActivity.this,mensaje, Toast.LENGTH_LONG).show();
                    }
                }
            });


        }

        return super.onOptionsItemSelected(item);
    }

    TextView txtTituloConfirmacion;
    TextView txtMensajeConfirmacion;
    public void showDialog(String mensaje){

        LayoutInflater inflater= LayoutInflater.from(this);
        View view= inflater.inflate(R.layout.activity_cuadro_dialogo,null);

        final AlertDialog alertDialog= new AlertDialog.Builder(this)
                .setView(view)
                .create();

        txtTituloConfirmacion= view.findViewById(R.id.txtTituloConfirmacion);
        txtMensajeConfirmacion= view.findViewById(R.id.txtMensajeConfirmacion);
        Button btnEnviar =(Button) view.findViewById(R.id.btnAceptarConfirmacion);

        //txtTituloConfirmacion.setText(R.string.cuadrodialogotelefono_titulo);
        txtMensajeConfirmacion.setText(R.string.cuadrodialogotelefono_mensaje);
        txtTituloConfirmacion.setText(mensaje);
        btnEnviar.setText(R.string.cuadrodialogotelefono_btnaceptar);

        btnEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(TelefonoActivity.this,"Enviado...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(view.getContext(), VerificacionActivity.class );

                startActivity(i);
            }
        });

        Button btnCancelar =(Button) view.findViewById(R.id.btnCancelarConfirmacion);
        btnCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(TelefonoActivity.this, "Cancelado...", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




        //alertDialog.getWindow().setLayout( 800,650);
        //alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        //alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);



    }

    public void cargarWebService(){

    }
}
