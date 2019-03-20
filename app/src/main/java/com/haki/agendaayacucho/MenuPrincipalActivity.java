package com.haki.agendaayacucho;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.haki.agendaayacucho.Fragments.CursosAsesoradosFragment;
import com.haki.agendaayacucho.Fragments.CursosAsignadosFragment;
import com.haki.agendaayacucho.Fragments.EstudiantesCursoAsesoradoFragment;
import com.haki.agendaayacucho.Fragments.EstudiantesCursoFragment;
import com.haki.agendaayacucho.Fragments.RegistroFaltasFragment;
import com.haki.agendaayacucho.Interface.IFragmentCursoAsesorado;
import com.haki.agendaayacucho.Interface.IFragmentCursoAsignado;
import com.haki.agendaayacucho.LuisMiguel.Maya;
import com.haki.agendaayacucho.Modelos.Curso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuPrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CursosAsignadosFragment.OnFragmentInteractionListener, CursosAsesoradosFragment.OnFragmentInteractionListener, IFragmentCursoAsesorado,IFragmentCursoAsignado,EstudiantesCursoFragment.OnFragmentInteractionListener ,EstudiantesCursoAsesoradoFragment.OnFragmentInteractionListener, RegistroFaltasFragment.OnFragmentInteractionListener{

    DrawerLayout drawer;
    Maya maya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        maya   = new Maya(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        Glide.with(this)
                .load(maya.buscarAvatarLogueado())
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.5f)
                .into((CircleImageView) header.findViewById(R.id.nh_iv_Avatar));
        ((TextView) header.findViewById(R.id.nh_tv_Usuario)).setText(maya.buscarNombreLogeado());
        ((TextView) header.findViewById(R.id.nh_tv_Rol)).setText(maya.buscarUsuarioRol());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment miFragment = null;

        boolean fragmentSeleccionado = false;

        if (id == R.id.nav_camera) {
            miFragment = new CursosAsignadosFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_gallery) {
            miFragment = new CursosAsesoradosFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_slideshow) {
            miFragment = new RegistroFaltasFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_share) {
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

        if (fragmentSeleccionado == true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,miFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void enviarCurso(Curso curso) {
        EstudiantesCursoFragment ecf = new EstudiantesCursoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("itemCurso",curso);
        ecf.setArguments(bundle);
        //Cargar el fragment en el activity
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,ecf).addToBackStack(null).commit();
    }

    @Override
    public void enviarCursoAsesorado(Curso curso) {
        EstudiantesCursoAsesoradoFragment ecaf = new EstudiantesCursoAsesoradoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("itemCurso",curso);
        ecaf.setArguments(bundle);
        //Cargar el fragment en el activity
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,ecaf).addToBackStack(null).commit();
    }
}
