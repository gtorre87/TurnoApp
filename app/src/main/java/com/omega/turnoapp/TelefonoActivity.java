package com.omega.turnoapp;



import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Modelo.Resultado;
import Servicios.RestChofer;
import Servicios.RestListener;
import Servicios.RestManager;

public class TelefonoActivity extends AppCompatActivity {
    String CELULAR="";
    String CORREO="";

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
            EditText txtNumero= (EditText) findViewById(R.id.etxtTelefono);
            CELULAR= txtNumero.getText().toString();
            RestChofer.ValidacionTelefono(new RestListener<ArrayList<Resultado>>() {
                @Override
                public void onResult(ArrayList<Resultado> Object) {
                    boolean respuesta=  Object.get(0).isEstado();
                    String mensaje= Object.get(0).getMensaje();
                    CORREO = Object.get(0).getAyudaTexto();

                    showDialog(respuesta, mensaje);
                    /*if(respuesta){
                        showDialog(respuesta,mensaje);
                        Toast.makeText(TelefonoActivity.this,mensaje, Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(TelefonoActivity.this,mensaje, Toast.LENGTH_LONG).show();
                    }*/
                }
            }, CELULAR);


        }

        return super.onOptionsItemSelected(item);
    }

    TextView txtTituloDialogo;
    TextView txtMensajeDialogo;
    Intent i;
    public void showDialog(final boolean rpta, String mensaje){
        LayoutInflater inflater= LayoutInflater.from(this);

        int layout= rpta? R.layout.activity_cuadro_dialogo_primary: R.layout.activity_cuadro_dialogo_error;
        int idTitulo= rpta? R.id.txtTituloConfirmacion: R.id.txtTituloError;
        int idMensaje= rpta? R.id.txtMensajeConfirmacion:R.id.txtMensajeError;
        int idBotonAceptar= rpta?R.id.btnAceptarConfirmacion:R.id.btnAceptarError;

        String textoBotonAceptar= rpta? getText(R.string.cuadrodialogotelefono_btnaceptar).toString() :"Aceptar";
        String textoTitulo= rpta? mensaje: "TurnoApp - Advertencia";
        String textoMensaje= rpta? getText(R.string.cuadrodialogotelefono_mensaje).toString(): mensaje;

        View view= inflater.inflate(layout,null);

        final AlertDialog alertDialog= new AlertDialog.Builder(this)
                .setView(view)
                .create();

        txtTituloDialogo= view.findViewById(idTitulo);
        txtMensajeDialogo= view.findViewById(idMensaje);
        Button btnEnviar =(Button) view.findViewById(idBotonAceptar);


        txtTituloDialogo.setText(textoTitulo);
        txtMensajeDialogo.setText(textoMensaje);
        btnEnviar.setText(textoBotonAceptar);

        btnEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if(rpta) {
                    i = new Intent(view.getContext(), VerificacionActivity.class);

                    RestChofer.GenerarConfirmacionCorreo(new RestListener<ArrayList<Resultado>>() {
                        @Override
                        public void onResult(ArrayList<Resultado> Object) {
                            boolean respuesta=  Object.get(0).isEstado();
                            String mensaje= Object.get(0).getMensaje();

                            if(respuesta){
                                Toast.makeText(TelefonoActivity.this, "Enviado..."+mensaje, Toast.LENGTH_SHORT).show();
                                i.putExtra("correo", CORREO);
                                i.putExtra("celular", CELULAR);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(TelefonoActivity.this, mensaje, Toast.LENGTH_LONG);
                            }
                            //showDialog(respuesta, mensaje);
                        }
                    },CELULAR);


                }
                else{
                    alertDialog.dismiss();
                }
            }
        });

        if(rpta) {
            Button btnCancelar = (Button) view.findViewById(R.id.btnCancelarConfirmacion);
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(TelefonoActivity.this, "Cancelado...", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            });
        }

        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //alertDialog.getWindow().setLayout( 800,650);
        //alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        //alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

}
