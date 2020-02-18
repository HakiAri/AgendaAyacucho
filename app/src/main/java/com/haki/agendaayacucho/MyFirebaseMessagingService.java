package com.haki.agendaayacucho;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.haki.agendaayacucho.LuisMiguel.Maya;

import java.util.Hashtable;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private Maya maya = new Maya(this);
    private SharedPreferences sharedPref;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData() != null) {
            enviarNotificacion(remoteMessage);
        }
        if(remoteMessage.getNotification() != null) {
            Log.d(TAG, "Body notification: "+remoteMessage.getNotification().getBody());
            enviarNotificacion(remoteMessage);
        }
    }

    private void enviarNotificacion(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String body = data.get("body");

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "Juanita";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Solo para android Oreo o superior
            @SuppressLint("WrongConstant")
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Mi notificacion",
                    NotificationManager.IMPORTANCE_MAX
            );

            // Configuracion del canal de notificacion
            channel.setDescription("hakiari");
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            channel.enableVibration(true);

            manager.createNotificationChannel(channel);

        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                intent,
                0
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_faltas_cometidas)
                .setTicker("Colegio Ayacucho")
                .setContentTitle(title)
                .setContentText(body)
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setContentIntent(pendingIntent)
                .setContentInfo("info");

        manager.notify(1, builder.build());
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed Token"+token);
        FirebaseMessaging.getInstance().subscribeToTopic("dispositivos");
        guardarTokenServer(token);
        //enviarTokenToServer(token);

    }

    private void guardarTokenServer(String token){
        sharedPref = getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPref.edit();
        editor.putString("token",token);
        editor.commit();
    }

    private void enviarTokenToServer(final String token) {
        //String URL = maya.buscarUrlServidor()+"";
        //String URL = "http://192.168.0.18/notificaciones/registrarToken.php";
        //String URL = "http://172.16.100.121/notificaciones/registrarToken.php";
        String URL = "http://172.16.100.6/notificaciones/registrarToken.php";

        Log.d("URL", URL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Se registro exitosamente", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR EN LA CONEXION", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("Token", token);
                Log.d("params",String.valueOf(parametros));
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

