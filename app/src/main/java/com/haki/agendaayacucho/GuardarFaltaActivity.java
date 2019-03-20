package com.haki.agendaayacucho;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haki.agendaayacucho.LuisMiguel.Maya;
import com.haki.agendaayacucho.Modelos.Curso;
import com.haki.agendaayacucho.Modelos.Estudiante;
import com.haki.agendaayacucho.Modelos.Falta;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GuardarFaltaActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView _tvTipoFalta;
    private EditText _etObservaciones;
    private Maya maya;
    private Button _btnCancelar, _btnAceptar;
    private Curso itemCurso;
    private Falta itemFalta;
    private Estudiante itemEstudiante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // -->Poner la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_guardar_falta);

        maya = new Maya(this);
        _tvTipoFalta = findViewById(R.id.gf_tvFalta);
        _etObservaciones = findViewById(R.id.gf_etObs);
        _btnAceptar = findViewById(R.id.rc_btnOk);
        _btnCancelar = findViewById(R.id.rc_btnCancelar);
        _btnAceptar.setOnClickListener(this);
        _btnCancelar.setOnClickListener(this);
        itemCurso = (Curso) getIntent().getSerializableExtra("itemCurso");
        itemEstudiante = (Estudiante) getIntent().getSerializableExtra("itemEstudiante");
        itemFalta = (Falta) getIntent().getSerializableExtra("itemFalta");
        getSupportActionBar().setTitle(itemEstudiante.getNombre()+" "+itemEstudiante.getPaterno());

        _tvTipoFalta.setText(itemFalta.getDescripcion());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rc_btnOk){
            if (maya.accesoInternet() == 1){
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setIcon(R.mipmap.ic_launcher);
                    progressDialog.setMessage("Espero un momento...");
                    progressDialog.show();
                   guardarFalta(_etObservaciones.getText().toString());
            }else{
                maya.toastAdvertencia("Debe estar Conectado a una Red 4G, 3G o WIFI");
                finish();
            }
        }

        if (v.getId() == R.id.rc_btnCancelar){
            finish();
        }
    }

    private void guardarFalta(final String observacion) {

        String url=maya.buscarUrlServidor()+"/app/servicesREST/ws_guardarfalta.php";
        Log.e("UrlFalta",url);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //maya.Toast(response);
                Log.e("UrlFalta",response);
                try {
                    JSONObject jsonUsuario = new JSONObject(response);
                    //maya.Toast("tam "+jsonUsuario.length());
                   if(jsonUsuario.length()>0){
                        if (jsonUsuario.optString("estado").equals("s")){
                            maya.toastOk("Guardado exitosamente :)");
                            finish();
                        }else if (jsonUsuario.optString("estado").equals("n")){
                            maya.toastOk("No se pudo Guardar  :( intentelo mas tarde");
                            finish();
                        }
                    }else{
                        maya.Toast("Comuniquese con su administrador :(");
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    maya.toastError("Comuniquese con su administrador no se encuentra respuesta :(");
                    finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("URLGuardar ", error+"");
                //maya.toastError("Comuniquese con su administrador");
                maya.toastInfo("No existe respuesta para continuar");
                finish();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();

                /*params.put("usuario",maya.buscarUsuarioLogeado());
                params.put("contrasenia",maya.buscarSecretLogeado());*/
                params.put("fecha",maya.fechaActual(3));
                params.put("id_kardex",String.valueOf(itemEstudiante.getId_kardex()));
                params.put("id_user",maya.id_user());
                params.put("obs",observacion);
                /*params.put("latitud",latitud);
                params.put("longitud",""+longitud);*/
                params.put("id_falta",String.valueOf(itemFalta.getId_falta()));
                params.put("id_asignatura",String.valueOf(itemCurso.getId_asignatura()));
                Log.d("FALTA", String.valueOf(params));
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed (){
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
