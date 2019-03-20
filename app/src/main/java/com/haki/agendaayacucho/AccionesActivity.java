package com.haki.agendaayacucho;

import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.haki.agendaayacucho.Adaptadores.ViewPageAdapter;
import com.haki.agendaayacucho.Fragments.CitacionesFragment;
import com.haki.agendaayacucho.Fragments.FaltasCometidasFragment;
import com.haki.agendaayacucho.Fragments.FelicitacionesFragment;
import com.haki.agendaayacucho.LuisMiguel.Maya;
import com.haki.agendaayacucho.Modelos.Estudiante;

public class AccionesActivity extends AppCompatActivity implements FaltasCometidasFragment.OnFragmentInteractionListener,CitacionesFragment.OnFragmentInteractionListener,FelicitacionesFragment.OnFragmentInteractionListener{

    private ImageView imagenView;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private ViewPageAdapter viewAdapter;
    private Estudiante itemEstudiante;
    private Maya maya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        maya = new Maya(this);
        itemEstudiante = (Estudiante) getIntent().getSerializableExtra("itemEstudiante");
        getSupportActionBar().setTitle(itemEstudiante.getNombre()+" "+itemEstudiante.getPaterno());
        //getSupportActionBar().setTitle("Hola Luis Miguel");
        imagenView = findViewById(R.id.ap_ivImagen);
        tabLayout = findViewById(R.id.tabLayout);
        appBarLayout = findViewById(R.id.appBarLayout);
        viewPager = findViewById(R.id.viewPager);

        viewAdapter = new ViewPageAdapter(getSupportFragmentManager());

        viewPagerAdapter();
    }

    private void viewPagerAdapter() {
        viewAdapter.addFragment(new FaltasCometidasFragment(),"Faltas Cometidas");
        viewAdapter.addFragment(new CitacionesFragment(),"Citaciones");
        viewAdapter.addFragment(new FelicitacionesFragment(),"Felicitaciones");
        viewPager.setAdapter(viewAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        imagenView.setImageResource(R.drawable.ic_faltas_cometidas);
                        break;
                    case 1:
                        imagenView.setImageResource(R.drawable.ic_citaciones);
                        break;
                    case 2:
                        imagenView.setImageResource(R.drawable.ic_felicitaciones);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
