package com.haki.agendaayacucho;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MiFirebaseServicioMensaje extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remote){
        super.onMessageReceived(remote);
        if (remote.getNotification()!=null){
            Log.d("Notificacion","Notificacion :"+remote.getNotification().getBody()+ " Titulo :"+remote.getNotification().getTitle());
        }
    }
}
