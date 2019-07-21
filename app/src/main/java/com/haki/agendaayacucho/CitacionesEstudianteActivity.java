package com.haki.agendaayacucho;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haki.agendaayacucho.Adaptadores.FaltasEstudianteAdapter;
import com.haki.agendaayacucho.LuisMiguel.Maya;
import com.haki.agendaayacucho.Modelos.Estudiante;
import com.haki.agendaayacucho.Modelos.FaltaHijo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CitacionesEstudianteActivity extends AppCompatActivity {

    private Maya maya;
    private RecyclerView _rvFaltas;
    //private Curso itemCurso;
    private Estudiante itemEstudiante;
    private EditText _etBuscar;
    private FaltasEstudianteAdapter adapterFalta;
    private ArrayList<FaltaHijo> listFalta;
    private ArrayList<FaltaHijo> listFaltaAux;
    private LinearLayoutManager linearLayoutManager;
    private ProgressDialog progress;

    private int sw = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citaciones_estudiante);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        maya = new Maya(this);

        sw = getIntent().getExtras().getInt("sw");
        //itemCurso = (Curso) getIntent().getSerializableExtra("itemCurso");

        itemEstudiante = (Estudiante) getIntent().getSerializableExtra("itemEstudiante");

        if(sw == 1){
            getSupportActionBar().setTitle(maya.buscarNombreLogeado());
        }else{
            getSupportActionBar().setTitle(itemEstudiante.getNombre()+" "+itemEstudiante.getPaterno());
        }




        _rvFaltas = findViewById(R.id.fe_rvFaltas);
        _etBuscar = findViewById(R.id.fe_etbuscar);

        _etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscardor(""+s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        consultarFaltas();
        _rvFaltas.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        _rvFaltas.setLayoutManager(linearLayoutManager);
    }

    private void consultarFaltas() {
        progress=new ProgressDialog(this);
        progress.setMessage("Espere un momento...");
        progress.show();
        progress.setCancelable(false);

        String url=maya.buscarUrlServidor()+"/app/servicesREST/ws_faltas_hijo.php";
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
                        JSONArray json = jsonUsuario.optJSONArray("faltashijo");
                        listFalta    = new ArrayList<>();
                        listFaltaAux = new ArrayList<>();

                        for (int i = 0; i < json.length(); i++) {
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            listFalta.add(new FaltaHijo(Integer.parseInt(jsonObject.optString("gestion")),jsonObject.optString("nombre_asignatura"), jsonObject.optString("tipoFalta"),jsonObject.optString("descripcion"),jsonObject.optString("obseracion"),jsonObject.optString("fecha")));
                            listFaltaAux.add(new FaltaHijo(Integer.parseInt(jsonObject.optString("gestion")),jsonObject.optString("nombre_asignatura"), jsonObject.optString("tipoFalta"),jsonObject.optString("descripcion"),jsonObject.optString("obseracion"),jsonObject.optString("fecha")));
                        }
                        progress.hide();
                        progress.dismiss();
                        adapterFalta = new FaltasEstudianteAdapter(listFalta, getApplicationContext());
                        _rvFaltas.setAdapter(adapterFalta);
                        adapterFalta.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //maya.toastInfo(listFalta.get(_rvFaltas.getChildAdapterPosition(v)).getDescripcion());
                            }
                        });
                    }else{
                        progress.hide();
                        maya.Toast("No existen Faltas a mostrar...");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                if(sw == 1){
                    params.put("id_rude",String.valueOf(maya.buscarId_Objeto()));
                }else{
                    params.put("id_rude",String.valueOf(itemEstudiante.getId_rude()));
                }

                params.put("fecha","");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void buscardor(String filtro) {
        listFalta.clear();

        for (int i = 0; i < listFaltaAux.size(); i++){
            if (listFaltaAux.get(i).getMateria().toLowerCase().contains(filtro.toLowerCase())){
                listFalta.add(listFaltaAux.get(i));
            }
        }
        adapterFalta.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed (){
        finish();
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
