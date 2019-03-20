package com.haki.agendaayacucho;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haki.agendaayacucho.Adaptadores.FaltaAdapter;
import com.haki.agendaayacucho.LuisMiguel.Maya;
import com.haki.agendaayacucho.Modelos.Curso;
import com.haki.agendaayacucho.Modelos.Estudiante;
import com.haki.agendaayacucho.Modelos.Falta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TiposFaltaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner _spFaltas;
    private List<String> listFaltas;
    private Maya maya;
    private ArrayAdapter<String> adapterSpinner;
    private RecyclerView _rvFaltas;
    private Curso itemCurso;
    private Estudiante itemEstudiante;
    private EditText _etBuscar;
    private FaltaAdapter adapterFalta;
    private ArrayList<Falta> listFalta;
    private ArrayList<Falta> listFaltaAux;
    private LinearLayoutManager linearLayoutManager;
    private ProgressDialog progress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipos_falta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        maya = new Maya(this);
        itemCurso = (Curso) getIntent().getSerializableExtra("itemCurso");
        itemEstudiante = (Estudiante) getIntent().getSerializableExtra("itemEstudiante");
        getSupportActionBar().setTitle(itemEstudiante.getNombre()+" "+itemEstudiante.getPaterno());
        _spFaltas = findViewById(R.id.tf_spFaltas);
        _rvFaltas = findViewById(R.id.tf_rvFaltas);
        _etBuscar = findViewById(R.id.tf_etbuscar);
        _spFaltas.setOnItemSelectedListener(this);
        listFaltas = new ArrayList<>();
        CargarFaltas();
        adapterSpinner = new ArrayAdapter<>(this,android.R.layout.simple_selectable_list_item,listFaltas);
        _spFaltas.setAdapter(adapterSpinner);
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
    }

    private void CargarFaltas() {
        listFaltas.add("Leves");
        listFaltas.add("Graves");
        listFaltas.add("Muy Graves");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.tf_spFaltas:
                String falta = listFaltas.get(position);
                //maya.toastInfo(falta);
                consultarFaltas(falta.toLowerCase());
                _rvFaltas.setHasFixedSize(true);
                linearLayoutManager = new LinearLayoutManager(this);
                _rvFaltas.setLayoutManager(linearLayoutManager);
                break;
        }
    }

    private void consultarFaltas(final String falta) {
        progress=new ProgressDialog(this);
        progress.setMessage("Espere un momento...");
        progress.show();
        progress.setCancelable(false);

        String url=maya.buscarUrlServidor()+"/app/servicesREST/ws_tiposfalta.php";
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
                        JSONArray json = jsonUsuario.optJSONArray("faltas");
                        listFalta    = new ArrayList<>();
                        listFaltaAux = new ArrayList<>();

                        for (int i = 0; i < json.length(); i++) {
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            listFalta.add(new Falta(Integer.parseInt(jsonObject.optString("id_falta")),Integer.parseInt(jsonObject.optString("estado")),jsonObject.optString("tipoFalta"), jsonObject.optString("descripcion")));
                            listFaltaAux.add(new Falta(Integer.parseInt(jsonObject.optString("id_falta")),Integer.parseInt(jsonObject.optString("estado")),jsonObject.optString("tipoFalta"), jsonObject.optString("descripcion")));
                        }
                        progress.hide();
                        progress.dismiss();
                        adapterFalta = new FaltaAdapter(listFalta, getApplicationContext());
                        _rvFaltas.setAdapter(adapterFalta);
                        adapterFalta.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //maya.toastInfo(listFalta.get(_rvFaltas.getChildAdapterPosition(v)).getDescripcion());
                                Intent gf = new Intent(TiposFaltaActivity.this, GuardarFaltaActivity.class);
                                gf.putExtra("itemFalta",listFalta.get(_rvFaltas.getChildAdapterPosition(v)));
                                gf.putExtra("itemEstudiante",itemEstudiante);
                                gf.putExtra("itemCurso",itemCurso);
                                startActivity(gf);
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
                params.put("falta",falta);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void buscardor(String filtro) {
        listFalta.clear();

        for (int i = 0; i < listFaltaAux.size(); i++){
            if (listFaltaAux.get(i).getDescripcion().toLowerCase().contains(filtro.toLowerCase())){
                listFalta.add(listFaltaAux.get(i));
            }
        }
        adapterFalta.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
