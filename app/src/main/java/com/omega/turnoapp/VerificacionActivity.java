package com.omega.turnoapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class VerificacionActivity extends AppCompatActivity {
    ProgressBar pgbLoad;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);
        this.setTitle(R.string.title_verificacion);

        pgbLoad= findViewById(R.id.pgbLoad);
        ImageButton btnVerificar= findViewById(R.id.btnValidarVerif);

        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            pgbLoad.setProgress(progressStatus);
                            //textView.setText(progressStatus+"/"+progressBar.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                //View view = View.inflate(VerificacionActivity.this, R.layout.activity_verificacion, null);
                Intent i = new Intent(VerificacionActivity.this, ValidarVerificacionActivity.class );
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
