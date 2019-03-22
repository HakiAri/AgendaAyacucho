package com.haki.agendaayacucho;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.haki.agendaayacucho.LuisMiguel.Maya;

public class MainActivity extends AppCompatActivity {


    private Maya maya;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // -->Poner la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // -->Poner Pantalla Completa
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        maya = new Maya(this);

        // Asignamos la animacion al logo
        //Animation MyAnim = AnimationUtils.loadAnimation(this,R.anim.transicion);

        //iniciamos el tiempo que le daremos a la animacion
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                }catch (InterruptedException er){
                    er.printStackTrace();
                }finally {
                        if(!maya.buscarNombreLogeado().isEmpty()){
                            switch (maya.buscarRol()){
                                case 2:
                                    Intent md = new Intent(getApplicationContext(), MenuPrincipalActivity.class);
                                    startActivity(md);
                                    break;
                                case 3:
                                    Intent mpf = new Intent(getApplicationContext(), MenuTutorActivity.class);
                                    startActivity(mpf);
                                    break;
                                case 4:
                                    Intent me = new Intent(getApplicationContext(), MenuTutorActivity.class);
                                    startActivity(me);
                                    break;
                            }
                        }else{
                            i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                        }
                    finish();

                }
            }
        };
        timer.start();
    }
}


