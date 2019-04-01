package com.haki.agendaayacucho;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.InstanceIdResult;
import com.haki.agendaayacucho.Lai.Variables;

public class MiFirebaseInstance extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh(){
        super.onTokenRefresh();
        Variables.TOKEN = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token",Variables.TOKEN);
    }


}
