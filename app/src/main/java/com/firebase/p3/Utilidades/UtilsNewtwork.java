package com.firebase.p3.Utilidades;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class UtilsNewtwork {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean isOnline(Context contex){
        ConnectivityManager conectivityMaganer=(ConnectivityManager)contex.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if(conectivityMaganer!=null){
            NetworkInfo info=conectivityMaganer.getActiveNetworkInfo();
            if(info!=null){
                return info.isConnected();
            }
        }
        return false;
    }
}
