package com.example.smarthomesystem;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class Connection implements Interceptor {

    private final Context appContext;

    public Connection(Context context) {
        appContext = context.getApplicationContext();
    }

    boolean isConnectionAvailable() {
        boolean result = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    // Network transport types: WiFi, Cellular, Ethernet
                    result = true;
                }
            }
        }
        return result;
    }


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        return null;
    }
}
