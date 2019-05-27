package com.omega.turnoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Modelo.Resultado;
import Modelo.Ruta;
import Servicios.RestListener;
import Servicios.RestRuta;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class DatosChoferActivity extends AppCompatActivity {

    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;
    ImageView imgFoto;
    String path;
    LocationManager locationManager;
    String nroCelular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_chofer);
        this.setTitle(R.string.title_datoschofer);

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null) {
            nroCelular = parametros.getString("celular");
        }

        imgFoto= findViewById(R.id.imgFoto);

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.user);
        RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        imgFoto.setImageDrawable(roundedBitmapDrawable);
        /*if(validaPermisos()){
            imgFoto.setEnabled(true);
        }else{
            imgFoto.setEnabled(false);
        }*/

        imgFoto.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(validaPermisos()){
                    cargarImagen();
                }else{
                    imgFoto.setEnabled(false);
                }

                //cargarImagen();
            }
        });


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
            ActualizarDatos();
        }

        return super.onOptionsItemSelected(item);
    }

    public void ActualizarDatos(){
        EditText txtNombre= findViewById(R.id.txtNombre);
        EditText txtApellidos= findViewById(R.id.txtApellidos);
        EditText txtAlias= findViewById(R.id.txtvAlias);

        if(txtNombre.getText().toString().trim().length()==0){
            Toast.makeText(DatosChoferActivity.this, "Ingrese nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        if(txtApellidos.getText().toString().trim().length()==0){
            Toast.makeText(DatosChoferActivity.this, "Ingrese apellidos", Toast.LENGTH_SHORT).show();
            return;
        }

        if(txtAlias.getText().toString().trim().length()==0){
            Toast.makeText(DatosChoferActivity.this, "Ingrese alias", Toast.LENGTH_SHORT).show();
            return;
        }

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            showDialog();
        }
        else {

            RestRuta.Listar(new RestListener<ArrayList<Resultado<Ruta>>>() {
                @Override
                public void onResult(ArrayList<Resultado<Ruta>> Object) {
                    boolean respuesta=  Object.get(0).isEstado();
                    String mensaje= Object.get(0).getMensaje();

                    List<Ruta> listRutas = Object.get(0).getLista();

                    if(respuesta){
                        Intent i =  new Intent(DatosChoferActivity.this , PanelTurnos.class);
                        startActivity(i);
                    }else{

                    }

                }
            });
            //descomentar
            /*Intent i = new Intent(DatosChoferActivity.this, PanelTurnos.class);

            startActivity(i);*/
        }
    }

    public void showDialog(){

        LayoutInflater inflater= LayoutInflater.from(this);
        View view= inflater.inflate(R.layout.activity_cuadro_dialogo_primary,null);

        final AlertDialog alertDialog= new AlertDialog.Builder(this)
                .setView(view)
                .create();

        TextView txtTituloConfirmacion= view.findViewById(R.id.txtTituloConfirmacion);
        TextView txtMensajeConfirmacion= view.findViewById(R.id.txtMensajeConfirmacion);
        Button btnActivar =(Button) view.findViewById(R.id.btnAceptarConfirmacion);

        String mensaje= String.format(getString(R.string.cuadrodialogodatoschofer_mensaje), getString( R.string.app_name));

        txtTituloConfirmacion.setText(R.string.cuadrodialogodatoschofer_titulo);
        txtMensajeConfirmacion.setText(mensaje);
        btnActivar.setText(R.string.cuadrodialogodatoschofer_btnaceptar);

        btnActivar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(DatosChoferActivity.this,"Enviado...", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                alertDialog.dismiss();
            }
        });

        Button btnCancelar =(Button) view.findViewById(R.id.btnCancelarConfirmacion);
        btnCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(DatosChoferActivity.this, "Cancelado...", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //alertDialog.getWindow().setLayout(900,650);


    }

    //foto
    private boolean validaPermisos() {

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]== PackageManager.PERMISSION_GRANTED){
                //botonCargar.setEnabled(true);
            }else{
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(DatosChoferActivity.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(DatosChoferActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
                }
            }
        });
        dialogo.show();
    }

    public void onclick(View view) {
        cargarImagen();
    }

    private void cargarImagen() {

        final CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(DatosChoferActivity.this);
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")){
                    tomarFotografia();
                }else{
                    if (opciones[i].equals("Cargar Imagen")){
                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicación"),COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }

    private void tomarFotografia() {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreImagen="";
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada==true){
            nombreImagen=(System.currentTimeMillis()/1000)+".jpg";
        }


        path=Environment.getExternalStorageDirectory()+
                File.separator+RUTA_IMAGEN+File.separator+nombreImagen;

        File imagen=new File(path);

        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ////
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
        {
            String authorities=getApplicationContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else
        {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);

        ////
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONA:
                    Uri miPath=data.getData();
                    imgFoto.setImageURI(miPath);
                    break;

                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Ruta de almacenamiento","Path: "+path);
                                }
                            });

                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    imgFoto.setImageBitmap(bitmap);

                    break;
            }


        }
    }

}
