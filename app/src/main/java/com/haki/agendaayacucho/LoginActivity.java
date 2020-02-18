package com.haki.agendaayacucho;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haki.agendaayacucho.Lai.HandlerBasedeDatos;
import com.haki.agendaayacucho.Lai.Variables;
import com.haki.agendaayacucho.LuisMiguel.Maya;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout _etNombreUsuario, _etcontrasenia;
    private Button _btnConectar;
    private Maya maya;
    private HandlerBasedeDatos manejaBD;
    private SQLiteDatabase nuestraBD;
    public  String token = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // -->Poner la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // -->Poner Pantalla Completa
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        maya = new Maya(this);
        cargarToken();
        _etcontrasenia   = findViewById(R.id.etContrasenia);
        _etNombreUsuario = findViewById(R.id.etUsuario);
        _btnConectar     = findViewById(R.id.btnConectar);
        _btnConectar.setOnClickListener(this);
        permisosGeolocalizacion();
    }


    @Override
    public void onClick(View v) {

        if (maya.accesoInternet()==1){
            if(validarUsuario() && validarContraseña()){
                LoginUsuario();
            }
        }else{
            maya.toastAdvertencia("Conectese a una Red 4G o WIFI");
        }


    }

    private boolean validarUsuario() {
        String usuario     = _etNombreUsuario.getEditText().getText().toString().trim();

        if(usuario.isEmpty()){
            _etNombreUsuario.setError("Introduca el nombre de usuario");
            return false;
        }else{
            _etNombreUsuario.setError(null);
            return true;
        }
    }


    private boolean validarContraseña() {

        String contrasenia = _etcontrasenia.getEditText().getText().toString().trim();

        if (contrasenia.isEmpty()) {
            _etcontrasenia.setError("Introduca una contraseña");
            return false;
        } else{
            _etcontrasenia.setError(null);
            return true;
        }
    }

    private void LoginUsuario(){
        //String url="http://prueba.hakiari.com/wsLoginJson.php";
        //String url="http://190.104.29.14/inventarios/?/sitio/app-autenticar";
        String url=maya.buscarUrlServidor()+"/app/servicesREST/ws_login.php";
        Log.d("URL Sesion ", url);
        Log.d("SESION ", _etNombreUsuario.getEditText().getText().toString().trim()+" / "+_etcontrasenia.getEditText().getText().toString().trim());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("JSON Usuario",response);
                    JSONObject jsonUsuario = new JSONObject(response);
                    //maya.Toast("tam "+jsonUsuario.length());
                    if(jsonUsuario.length() > 0){
                        //Preguntamos que usuario es el logeado
                        switch (Integer.parseInt(jsonUsuario.optString("id_rol"))){
                            case 2:
                                manejaBD = new HandlerBasedeDatos(getApplicationContext());
                                manejaBD.addUsuario(Integer.parseInt(jsonUsuario.optString("id_user")),jsonUsuario.optString("id_rol"),jsonUsuario.optString("rol"),jsonUsuario.optString("id_objeto"),jsonUsuario.optString("nombre")+" "+jsonUsuario.optString("ap_paterno")+" "+jsonUsuario.optString("ap_materno"),"https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_960_720.png",_etNombreUsuario.getEditText().getText().toString().trim(),_etcontrasenia.getEditText().getText().toString().trim());
                                Intent ma = new Intent(getApplicationContext(),MenuPrincipalActivity.class);
                                startActivity(ma);
                                break;
                            case 3:
                                manejaBD = new HandlerBasedeDatos(getApplicationContext());
                                manejaBD.addUsuario(Integer.parseInt(jsonUsuario.optString("id_user")),jsonUsuario.optString("id_rol"),jsonUsuario.optString("rol"),jsonUsuario.optString("id_objeto"),jsonUsuario.optString("nombre")+" "+jsonUsuario.optString("ap_paterno")+" "+jsonUsuario.optString("ap_materno"),"https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_960_720.png",_etNombreUsuario.getEditText().getText().toString().trim(),_etcontrasenia.getEditText().getText().toString().trim());
                                Intent mat = new Intent(getApplicationContext(),MenuTutorActivity.class);
                                startActivity(mat);
                                break;
                            case 4:
                                manejaBD = new HandlerBasedeDatos(getApplicationContext());
                                manejaBD.addUsuario(Integer.parseInt(jsonUsuario.optString("id_user")),jsonUsuario.optString("id_rol"),jsonUsuario.optString("rol"),jsonUsuario.optString("id_objeto"),jsonUsuario.optString("nombre")+" "+jsonUsuario.optString("ap_paterno")+" "+jsonUsuario.optString("ap_materno"),"https://cdn.pixabay.com/photo/2016/08/08/09/17/avatar-1577909_960_720.png",_etNombreUsuario.getEditText().getText().toString().trim(),_etcontrasenia.getEditText().getText().toString().trim());
                                Intent me = new Intent(getApplicationContext(),MenuEstudianteActivity.class);
                                startActivity(me);
                                break;
                        }
                        enviarTokenUsuario(jsonUsuario.optString("id_user"),jsonUsuario.optString("id_rol"));
                        finish();
                    }else{
                        maya.Toast("Comuniquese con su administrador :(");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    maya.toastError("No existe respuesta correcta a la peticion");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("URLLogin ", error+"");
                //maya.Toast(error+"");
                maya.toastInfo("No existe respuesta para continuar");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("user",_etNombreUsuario.getEditText().getText().toString().trim());
                params.put("password",_etcontrasenia.getEditText().getText().toString().trim());
                params.put("token", Variables.TOKEN);
                //params.put("token", "LUIS MIGUEL");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void enviarTokenUsuario(final String id_usuario, final String id_rol){
        //String url="http://prueba.hakiari.com/wsLoginJson.php";
        //String url="http://190.104.29.14/inventarios/?/sitio/app-autenticar";
        String url=maya.buscarUrlServidor()+"/app/servicesREST/ws_cargar_token.php";
        Log.d("URL Sesion ", url);
        Log.d("SESION ", _etNombreUsuario.getEditText().getText().toString().trim()+" / "+_etcontrasenia.getEditText().getText().toString().trim());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("JSON Token",response);
                    JSONObject jsonUsuario = new JSONObject(response);
                    //maya.Toast("tam "+jsonUsuario.length());
                    if(jsonUsuario.length() > 0){
                        if(jsonUsuario.optString("estado").equals("s")){
                            maya.toastInfo("Registrado exitosamente");
                        }else{
                            maya.toastError("Registrado exitosamente");
                        }

                    }else{
                        maya.Toast("Comuniquese con su administrador :(");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    maya.toastError("No existe respuesta correcta a la peticion");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("URLLogin ", error+"");
                //maya.Toast(error+"");
                maya.toastInfo("No existe respuesta para continuar");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("usuario",_etNombreUsuario.getEditText().getText().toString().trim());
                params.put("contrasenia",_etcontrasenia.getEditText().getText().toString().trim());
                params.put("token", token);
                params.put("id_usuario", id_usuario);
                params.put("id_rol", id_rol );
                Log.d("params",String.valueOf(params));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void cargarToken(){
        SharedPreferences sharedPref = getSharedPreferences("token", Context.MODE_PRIVATE);
        token = sharedPref.getString("token","000000000");
    }

    public void permisosGeolocalizacion() {


        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.
                finish();
                Log.e("LOGTAG", "Permiso denegado");
                finish();
            }
        }
    }
}

