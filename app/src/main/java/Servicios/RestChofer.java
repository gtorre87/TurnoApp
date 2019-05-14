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

public class RestChofer {
    public static void getChofer(final RestListener<ArrayList<Resultado>> listener){
        RestManager.getPgLoading().show();
        String url= RestManager.getURL(); //+ "/api/chofer/987178895";
        StringRequest stringRequest= new StringRequest(Request.Method.GET,url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Resultado> listaResultado = new ArrayList<Resultado>();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            /*for (int i = 0; i < jArray.length(); i++) {

                                    JSONObject jsonObject = jArray.getJSONObject(i);
                                    Resultado resultado = new Resultado();
                                    resultado.setEstado(jsonObject.getBoolean("Estado"));
                                    resultado.setMensaje(jsonObject.getString("Mensaje"));
                                    resultado.setAyudaTexto(jsonObject.getString("AyudaTexto"));
                                    listaResultado.add(resultado);

                            }*/
                            //JSONObject jsonObject = jArray.getJSONObject(i);
                            Resultado resultado = new Resultado();
                            resultado.setEstado(jsonObject.getBoolean("estado"));
                            resultado.setMensaje(jsonObject.getString("mensaje"));
                            resultado.setAyudaTexto(jsonObject.getString("ayudaTexto"));
                            listaResultado.add(resultado);
                            //listaResultado.add(resultado);
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
