package com.haki.agendaayacucho;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MiFirebaseServicioMensaje extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("NEW_TOKEN",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remote){
        super.onMessageReceived(remote);
        if (remote.getNotification()!=null){
            Log.d("Notificacion","Notificacion :"+remote.getNotification().getBody()+ " Titulo :"+remote.getNotification().getTitle());
        }
    }
}
