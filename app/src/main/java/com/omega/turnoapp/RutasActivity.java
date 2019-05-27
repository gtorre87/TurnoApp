package com.omega.turnoapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adaptador.RecyclerViewAdaptadorRuta;
import Modelo.Resultado;
import Modelo.Ruta;
import Modelo.TurnoLogic;
import Servicios.RestListener;
import Servicios.RestRuta;

public class RutasActivity extends AppCompatActivity {
    private RecyclerView recyclerViewRuta;
    private RecyclerViewAdaptadorRuta adaptadorRuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //setContentView(R.layout.lista);

        /*ListView lista;
        ArrayAdapter<String> adaptador;

        lista = (ListView)findViewById(R.id.lvwRutas);

        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1);

        lista.setAdapter(adaptador);*/

        recyclerViewRuta=(RecyclerView) findViewById(R.id.recyclerViewRuta);
        recyclerViewRuta.setLayoutManager(new LinearLayoutManager(this));
        ObtenerRutas();
    }

    public void ObtenerRutas(){
        RestRuta.Listar(new RestListener<ArrayList<Resultado<Ruta>>>() {
            @Override
            public void onResult(ArrayList<Resultado<Ruta>> Object) {
                List<Ruta> listaRutas= Object.get(0).getLista();
                adaptadorRuta= new RecyclerViewAdaptadorRuta(listaRutas);
                recyclerViewRuta.setAdapter(adaptadorRuta);
            }
        });
    }

}
