package Servicios;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Modelo.Resultado;
import Modelo.Ruta;

public class RestRuta {
    public static void Listar(final RestListener<ArrayList<Resultado<Ruta>>> listener){
        RestManager.getPgLoading().show();
        String url= RestManager.getURL()+ "/api/Ruta/Lista";
        StringRequest stringRequest= new StringRequest(Request.Method.GET,url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Resultado<Ruta>> listaResultado = new ArrayList<Resultado<Ruta>>();
                        ArrayList<Ruta> listaRutas = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Resultado resultado = new Resultado();
                            resultado.setEstado(jsonObject.getBoolean("estado"));
                            //resultado.setTitulo(jsonObject.getString("titulo"));
                            resultado.setMensaje(jsonObject.getString("mensaje"));
                            resultado.setAyudaTexto(jsonObject.getString("ayudaTexto"));

                            JSONArray jArrayRutas= new JSONArray(jsonObject.getString("rutas"));
                            for (int i = 0; i < jArrayRutas.length(); i++) {
                                JSONObject jObjectRuta = jArrayRutas.getJSONObject(i);
                                Ruta objRuta = new Ruta();
                                objRuta.setRutaId(jObjectRuta.getInt("rutaId"));
                                objRuta.setDescripcion(jObjectRuta.getString("descripcion"));
                                objRuta.setDescripcionCorta(jObjectRuta.getString("descripcionCorta"));
                                listaRutas.add(objRuta);
                            }
                            resultado.setLista(listaRutas);
                            listaResultado.add(resultado);
                            listener.onResult(listaResultado);
                            RestManager.getPgLoading().dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RestManager.getContext(),e.getMessage() , Toast.LENGTH_LONG).show();
                            RestManager.getPgLoading().dismiss();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RestManager.getContext(),error.toString(), Toast.LENGTH_LONG).show();
                Log.d("Error WS",error.toString());
                RestManager.getPgLoading().dismiss();
            }
        });
        RestManager.getRequestQueue().add(stringRequest);
    }
}
