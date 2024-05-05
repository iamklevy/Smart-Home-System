package com.example.smarthomesystem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Fan extends AppCompatActivity {
    private static final String TAG = "FanActivity";
    Connection conn;
    private DatabaseReference databaseReference;
    private DatabaseReference fanrec;
    private DatabaseReference FR;
    private boolean Fan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);

        conn = new Connection(this);
        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        fanrec = databaseReference.child("logs");
        FR = databaseReference.child("Fan");
        SwitchCompat switchCompat = findViewById(R.id.switch_track);

        if (conn.isConnectionAvailable()) {


            switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Fan = buttonView.isChecked();
                sendFanToFirebase(Fan);
            });
            HashMap<String, Object> record = new HashMap<>();
            record.put("description", "Fan Room");
            record.put("id", 0);
            record.put("img", 10001);
            record.put("type", "Feature Fan");
            record.put("timestamp", get_timestamp());
            fanrec.child("FanRec").setValue(record);
            LogsManager logsManager = LogsManager.getInstance();
            logsManager.addLog(new Log_recycler_list(0, get_timestamp(), "Fan Room", "Fan Action", R.drawable.fan));
            switchCompat.setAlpha(1);

            switchCompat.setEnabled(true);

        } else {
            Toast.makeText(this, "You are offline >:(", Toast.LENGTH_SHORT).show();
            switchCompat.setAlpha(0.5F);
            switchCompat.setEnabled(false);



        }


    }

    private String get_timestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void sendFanToFirebase(boolean Fan) {
        FR.setValue(Fan, (error, ref) -> {
            if (error != null) {
                Log.e(TAG, "Failed to set value to Firebase: " + error.getMessage());
            } else {
                Log.d(TAG, "Value sent to Firebase successfully");
            }
        });
    }
}
