package com.example.smarthomesystem;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Temperature extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private static final String TAG = "TemperatureActivity";
    private final Handler handler = new Handler();
    private static final long REFRESH_INTERVAL = 1000;
    private TextView tmp_txt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        tmp_txt = findViewById(R.id.tmp);

        // Initialize Firebase references
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("temperature");
        Connection connection = new Connection(this);

        if (connection.isConnectionAvailable()) {
            // Start periodic update
            handler.postDelayed(updateTemperatureRunnable, REFRESH_INTERVAL);
        } else {
            Toast.makeText(this, "You are offline", Toast.LENGTH_SHORT).show();
        }

    }

    private final Runnable updateTemperatureRunnable = new Runnable() {
        @Override
        public void run() {
            // Get the temperature from Firebase
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Float stored_temp = dataSnapshot.getValue(Float.class);
                    if (stored_temp != null ) {
                        tmp_txt.setText((String.valueOf(stored_temp)));
                    } else {
                        Toast.makeText(Temperature.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Log error for debugging
                    Log.e(TAG, "Database error: ", error.toException());
                    tmp_txt.setText("Error: " + error.getMessage());

                }
            });
        }

    };
}
