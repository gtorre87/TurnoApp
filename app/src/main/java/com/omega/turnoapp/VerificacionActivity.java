package com.omega.turnoapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class VerificacionActivity extends AppCompatActivity {
    ProgressBar pgbLoad;
    TextView txtConteoProgreso;
    TextView txtMensaje;
    private int progressStatus = 0;
    String CELULAR;
    int PROGRESS_TEMP=0;
    int TOPE=30;
    int MILISEGUNDOS=1000;

    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);
        this.setTitle(R.string.title_verificacion);

        txtMensaje = findViewById(R.id.txtMensajeVerificacion);
        txtConteoProgreso = findViewById(R.id.txtConteoProgreso);

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            String datos = parametros.getString("correo");
            CELULAR = parametros.getString("celular");
            String mensaje= String.format(getString(R.string.verificacion_mensaje1), datos);
            txtMensaje.setText(mensaje);
        }

        pgbLoad= findViewById(R.id.pgbLoad);
        ImageButton btnVerificar= findViewById(R.id.btnValidarVerif);

        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < TOPE) {
                    progressStatus += 1;
                    PROGRESS_TEMP = TOPE - progressStatus;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            pgbLoad.setProgress(progressStatus);
                            String mensaje= getString(R.string.verificacion_mensaje3);
                            String tiempo= PROGRESS_TEMP>9? String.valueOf( PROGRESS_TEMP): "0"+ String.valueOf( PROGRESS_TEMP);
                            String mensajeTmp= String.format( mensaje,String.valueOf( tiempo));
                            txtConteoProgreso.setText(mensajeTmp);
                            //textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(MILISEGUNDOS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //View view = View.inflate(VerificacionActivity.this, R.layout.activity_verificacion, null);
                Intent i = new Intent(VerificacionActivity.this, ValidarVerificacionActivity.class );
                i.putExtra("celular",CELULAR);
                startActivity(i);
                //Toast.makeText(VerificacionActivity.this,"Verificando...", Toast.LENGTH_SHORT).show();

            }
        }).start();

        /*btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ValidarVerificacionActivity.class );
                startActivity(i);
                Toast.makeText(VerificacionActivity.this,"Verificando...", Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}
