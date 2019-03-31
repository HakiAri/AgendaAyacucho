package com.haki.agendaayacucho;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MiFirebaseInstance extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh(){
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token",token);
    }
}
