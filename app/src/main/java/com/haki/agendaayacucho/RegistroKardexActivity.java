package com.haki.agendaayacucho;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haki.agendaayacucho.LuisMiguel.Maya;
import com.haki.agendaayacucho.Modelos.Curso;
import com.haki.agendaayacucho.Modelos.Estudiante;

import org.w3c.dom.Text;

public class RegistroKardexActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout _menuFaltas, _menuCitaciones, _menuFelicitaciones;
    private Maya maya;
    private TextView _tvNombreEstudiante;
    private Estudiante itemEstudiante;
    private Curso      itemCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // -->Poner la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_kardex);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        maya = new Maya(this);
        _tvNombreEstudiante = findViewById(R.id.rk_tvEstudiante);
        itemEstudiante = (Estudiante) getIntent().getSerializableExtra("itemEstudiante");
        itemCurso      = (Curso) getIntent().getSerializableExtra("itemCurso");
        //getSupportActionBar().setTitle(itemEstudiante.getNombre()+" "+itemEstudiante.getPaterno());
        getSupportActionBar().setTitle(itemCurso.getGrado()+" "+itemCurso.getParalelo()+" - "+itemCurso.getMateria());

        _tvNombreEstudiante.setText(itemEstudiante.getNombre()+" "+itemEstudiante.getPaterno());
        _menuFaltas = findViewById(R.id.menu_faltas);
        _menuFaltas.setOnClickListener(this);
        _menuCitaciones = findViewById(R.id.menu_citaciones);
        _menuCitaciones.setOnClickListener(this);
        _menuFelicitaciones = findViewById(R.id.menu_felicitaciones);
        _menuFelicitaciones.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (maya.accesoInternet() == 1){
            Intent i;
            if (v.getId()== R.id.menu_faltas){
                i =new Intent(this,TiposFaltaActivity.class);
                i.putExtra("itemEstudiante",itemEstudiante);
                i.putExtra("itemCurso",itemCurso);
                startActivity(i);
            }

            if (v.getId()== R.id.menu_citaciones){
                i =new Intent(this,EnviarCitacionEstudianteActivity.class);
                i.putExtra("itemEstudiante",itemEstudiante);
                i.putExtra("itemCurso",itemCurso);
                startActivity(i);
            }

            if (v.getId()== R.id.menu_felicitaciones){
                maya.toastInfo("Estamos trabajando aun...");
            }

        }else{
            maya.toastAdvertencia("Debe estar Conectado a una Red 4G, 3G o WIFI");
        }
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
