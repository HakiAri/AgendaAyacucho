package com.haki.agendaayacucho;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.haki.agendaayacucho.Adaptadores.FaltasEstudianteAdapter;
import com.haki.agendaayacucho.LuisMiguel.Maya;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuEstudianteActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView _ivAvatar;
    private TextView _tvNombreEstudiante,_tvFaltas, _tvFelicitaciones, _tvTutor, _tvCurso, _tvAsesora;
    private Button _btnKardex, _btnSalir;
    private Maya maya;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_estudiante);
        maya = new Maya(this);
        Glide.with(this)
                .load(maya.buscarAvatarLogueado())
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.5f)
                .into((CircleImageView) findViewById(R.id.e_iv_Avatar));

        _tvNombreEstudiante = findViewById(R.id.e_tvNombreEstudiante);
        _tvFaltas = findViewById(R.id.e_tvFaltas);
        _tvFelicitaciones = findViewById(R.id.e_tvFelicitaciones);
        _tvTutor = findViewById(R.id.e_tvTutorEstudiante);
        _tvCurso = findViewById(R.id.e_tvCursoEstudiante);
        _tvAsesora = findViewById(R.id.e_AsesorCursoEstudiante);
        _btnKardex = findViewById(R.id.e_btnKardexEstudiante);
        _btnKardex.setOnClickListener(this);
        _btnSalir = findViewById(R.id.e_btnSalirEstudiante);
        _btnSalir.setOnClickListener(this);

        _tvNombreEstudiante.setText(maya.buscarNombreLogeado());
        consultarPerfilEstudiante();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.e_btnKardexEstudiante){
            Intent fe = new Intent(this, FaltasEstudianteActivity.class);
            fe.putExtra("sw",1);
            startActivity(fe);
            //finish();
        }

        if (v.getId()==R.id.e_btnSalirEstudiante){
            //maya.toastAdvertencia("Debe estar Conectado a una Red 4G, 3G o WIFI");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Esta seguro de cerrar sesion...")
                    .setTitle("Advertencia...")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            maya.cerrarSesionUsuario();
                            Intent la = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(la);
                            finish();
                        }
                    });
            AlertDialog dialogIcon = builder.create();
            dialogIcon.show();
        }

    }

    private void consultarPerfilEstudiante() {
        progress=new ProgressDialog(this);
        progress.setMessage("Espere un momento...");
        progress.show();
        progress.setCancelable(false);

        String url=maya.buscarUrlServidor()+"/app/servicesREST/ws_perfil_estudiante.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //maya.Toast(response);
                Log.e("json",response);
                try {
                    //JSONObject jsonUsuario = new JSONObject(response+"}]}");
                    JSONObject jsonUsuario = new JSONObject(response);
                    //maya.Toast("tam "+jsonUsuario.length());

                    if(jsonUsuario.length()==2){
                        JSONArray json = jsonUsuario.optJSONArray("perfil");


                        for (int i = 0; i < json.length(); i++) {
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            _tvFaltas.setText(jsonObject.optString("nro"));
                            _tvFelicitaciones.setText("0");
                            _tvTutor.setText(jsonObject.optString("tutor").toUpperCase());
                            _tvCurso.setText(jsonObject.optString("curso"));
                            _tvAsesora.setText(jsonObject.optString("asesor").toUpperCase());

                        }
                        progress.hide();
                        progress.dismiss();
                    }else{
                        progress.hide();
                        maya.Toast("No existen datos en su perfil...");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progress.hide();
                    _tvFaltas.setText("-1");
                    _tvFelicitaciones.setText("0");
                    _tvTutor.setText("-1");
                    _tvCurso.setText("-1");
                    _tvAsesora.setText("-1");
                    maya.Toast("No existen relacion con tuto en su perfil...");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("URLFaltas ", error+"");
                //maya.toastAdvertencia("Servidor no encontrado");
                maya.toastInfo("No existe respuesta para continuar");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                /*params.put("usuario",maya.buscarUsuarioLogeado());
                params.put("contrasenia",maya.buscarSecretLogeado());*/
                params.put("id_rude",String.valueOf(maya.buscarId_Objeto()));
                params.put("fecha","");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
