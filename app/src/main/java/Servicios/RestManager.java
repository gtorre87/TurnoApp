package Servicios;


import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RestManager {
    private static RestManager instance=null;
    private static RequestQueue requestQueue;
    private static String URL;
    private static Context context;
    private static ProgressDialog pgLoading;

    private RestManager(Context context){
        this.context= context;
        pgLoading= new ProgressDialog(context);
        pgLoading.setMessage("\tCargando...");
        pgLoading.setCancelable(false);
        URL="https://servicerestturno.azurewebsites.net";
        requestQueue= Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized RestManager getInstance(Context context){
        instance= new RestManager(context);
        return instance;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static String getURL() {
        return URL;
    }

    public static Context getContext() {
        return context;
    }

    public static ProgressDialog getPgLoading() {
        return pgLoading;
    }
}
