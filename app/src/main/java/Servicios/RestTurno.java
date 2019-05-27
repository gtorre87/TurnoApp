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
import Modelo.TurnoLogic;

public class RestTurno {
    public static void Listar(final RestListener<ArrayList<Resultado<TurnoLogic>>> listener, String rutaId){
        RestManager.getPgLoading().show();
        String url= RestManager.getURL()+ "/api/Turno/Lista/"+rutaId;
        StringRequest stringRequest= new StringRequest(Request.Method.GET,url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Resultado<TurnoLogic>> listaResultado = new ArrayList<Resultado<TurnoLogic>>();
                        ArrayList<TurnoLogic> listaTurnos= new ArrayList<TurnoLogic>();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Resultado resultado = new Resultado();
                            resultado.setEstado(jsonObject.getBoolean("estado"));
                            //resultado.setTitulo(jsonObject.getString("titulo"));
                            resultado.setMensaje(jsonObject.getString("mensaje"));
                            resultado.setAyudaTexto(jsonObject.getString("ayudaTexto"));

                            JSONArray jArrayRutas= new JSONArray(jsonObject.getString("turnos"));
                            for (int i = 0; i < jArrayRutas.length(); i++) {
                                JSONObject jObjectTurno = jArrayRutas.getJSONObject(i);
                                TurnoLogic objTurno = new TurnoLogic();
                                objTurno.setTurnoId(jObjectTurno.getInt("turnoId"));
                                objTurno.setTipo(jObjectTurno.getInt("tipo"));
                                objTurno.setRutaId(jObjectTurno.getInt("rutaId"));
                                objTurno.setOrden(jObjectTurno.getInt("orden"));
                                objTurno.setOrden(jObjectTurno.getInt("estado"));
                                objTurno.setLatitud(jObjectTurno.getDouble("latitud"));
                                objTurno.setLongitud(jObjectTurno.getDouble("longitud"));
                                objTurno.setCantidadPasajeros(jObjectTurno.getInt("cantidadPasajeros"));
                                objTurno.setNombre(jObjectTurno.getString("nombre"));
                                objTurno.setApellidos(jObjectTurno.getString("apellido"));
                                objTurno.setAlias(jObjectTurno.getString("alias"));
                                objTurno.setDni(jObjectTurno.getString("numeroDocumento"));
                                objTurno.setCorreo(jObjectTurno.getString("correo"));
                                objTurno.setTelefono(jObjectTurno.getString("telefono"));
                                objTurno.setChoferId(jObjectTurno.getInt("choferId"));
                                objTurno.setVehiculoId(jObjectTurno.getInt("vehiculoId"));

                                listaTurnos.add(objTurno);
                            }
                            resultado.setLista(listaTurnos);
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
