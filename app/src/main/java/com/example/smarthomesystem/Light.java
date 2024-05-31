package com.example.smarthomesystem;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Light extends AppCompatActivity {
    private static final String TAG = "LightActivity";
    private DatabaseReference databaseReference;
    private boolean light;
    private DatabaseReference lightrec;
    private DatabaseReference LR;

    Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

conn=new Connection(this);
        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        lightrec=databaseReference.child("logs");
        LR=databaseReference.child("light");
        SwitchCompat switchCompat = findViewById(R.id.switch_track);

        if (conn.isConnectionAvailable()) {

            switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            light = buttonView.isChecked();
            sendLightToFirebase(light);
        });
            HashMap<String, Object> record = new HashMap<>();
            record.put("description", "Light Room");
            record.put("id", 1);
            record.put("img",  10101);
            record.put("type", "Feature Light");
            record.put("timestamp",get_timestamp() );
            lightrec.child("LightRec").setValue(record);
            LogsManager logsManager = LogsManager.getInstance();
            logsManager.addLog(new Log_recycler_list(1, get_timestamp(), "Light Room", "Feature Light", R.drawable.light));


        } else {
            Toast.makeText(this, "You are offline :(", Toast.LENGTH_SHORT).show();
            switchCompat.setAlpha(0.5F);
            switchCompat.setEnabled(false);
        }


    }
    private String get_timestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
    private void sendLightToFirebase(boolean light) {
        LR.setValue(light, (error, ref) -> {
            if (error != null) {
                Log.e(TAG, "Failed to set value to Firebase: " + error.getMessage());
            } else {
                Log.d(TAG, "Value sent to Firebase successfully");
            }
        });
    }

}
