package com.haki.agendaayacucho;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EnviarCitacionEstudianteActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {

    private Estudiante itemEstudiante;
    private Curso itemCurso;
    private Button _btnRc, _btnEc;
    private TextView _tvUno,_tvDos,_tvTres,_tvCuatro;
    private int dia,dia_x,mes,mes_x,anio,anio_x,hora,hora_x,minuto,minuto_x;
    private Maya maya;
    private String [] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_citacion_estudiante);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        itemCurso = (Curso) getIntent().getSerializableExtra("itemCurso");
        itemEstudiante = (Estudiante) getIntent().getSerializableExtra("itemEstudiante");
        getSupportActionBar().setTitle(itemEstudiante.getNombre()+" "+itemEstudiante.getPaterno());
        maya = new Maya(this);
        _btnRc    = findViewById(R.id.btn_rc);
        _btnEc    = findViewById(R.id.btn_ec);
        _tvUno    = findViewById(R.id.tv_uno);
        _tvDos    = findViewById(R.id.tv_dos);
        _tvTres   = findViewById(R.id.tv_tres);
        _tvCuatro = findViewById(R.id.tv_cuatro);
        _btnRc.setOnClickListener(this);
        _btnEc.setOnClickListener(this);
        _tvUno.setVisibility(View.INVISIBLE);
        _tvDos.setVisibility(View.INVISIBLE);
        _tvTres.setVisibility(View.INVISIBLE);
        _tvCuatro.setVisibility(View.INVISIBLE);
        _btnEc.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        anio_x = i;
        mes_x = i1+1;
        dia_x = i2;
        Calendar c = Calendar.getInstance();
        hora   = c.get(Calendar.HOUR);
        minuto = c.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(EnviarCitacionEstudianteActivity.this,EnviarCitacionEstudianteActivity.this,hora,minuto,true);
        tpd.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hora_x   = i;
        minuto_x = i1;
        _tvDos.setText("Señor padre de familia. el/la maestro/a "+maya.buscarNombreLogeado()+"\n del paralelo :  "+itemCurso.getGrado()+" "+itemCurso.getParalelo()+" \n Materia de : "+itemCurso.getMateria()+"\n del estudiante : "+itemEstudiante.getNombre()+" "+itemEstudiante.getPaterno()+" "+itemEstudiante.getMaterno()+" ");
        _tvCuatro.setText("se le ruega asistir en la fecha de "+dia_x+"/"+meses[mes_x]+"/"+anio_x+"\n a horas : "+hora_x+" : "+minuto_x + "Para poder conversar sobre su hijo");
        _tvUno.setVisibility(View.VISIBLE);
        _tvDos.setVisibility(View.VISIBLE);
        _tvTres.setVisibility(View.VISIBLE);
        _tvCuatro.setVisibility(View.VISIBLE);
        _btnEc.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_rc:
                Calendar c = Calendar.getInstance();
                anio = c.get(Calendar.YEAR);
                mes  = c.get(Calendar.MONTH);
                dia  = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(EnviarCitacionEstudianteActivity.this,EnviarCitacionEstudianteActivity.this,anio,mes,dia );
                dpd.show();
                break;
            case R.id.btn_ec:
                registarCitacion(_tvUno.getText().toString()+_tvDos.getText().toString()+_tvTres.getText().toString()+_tvCuatro.getText().toString(),anio_x+"-"+(mes_x+1)+"-"+dia_x+" "+hora_x+":"+minuto_x);
                break;
            default:
                break;
        }

    }

    private void registarCitacion(final String citacion, final String fecha) {
            String url=maya.buscarUrlServidor()+"/app/servicesREST/ws_registrar_citacion.php";
            Log.e("UrlCitacion",url);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //maya.Toast(response);
                    Log.e("UrlCitacion",response);
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
                    params.put("citacion",citacion);
                    params.put("fechayhora",fecha);
                    params.put("id_kardex",String.valueOf(itemEstudiante.getId_kardex()));
                    params.put("id_user",String.valueOf(maya.id_user()));
                    Log.d("PARAM", String.valueOf(params));
                    return params;
                }
            };
            requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed (){
        this.finish();
    }
}
