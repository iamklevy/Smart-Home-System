package com.example.smarthomesystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;

public class items extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int itemID = getIntent().getIntExtra("id", 0);
        final TextView tmp_value = findViewById(R.id.tmpvalue);

        //temperature activity
        if (itemID == 0) {
            startActivity(new Intent(this, Temperature.class));
            finish();
        }
        //implement password activity
        else if (itemID == 1) {
            startActivity(new Intent(this, PasswordActivity.class));
            finish();
        }
        //implement Light activity
        else if (itemID == 2) {
            startActivity(new Intent(this, Light.class));
            finish();

        }
        //implement fan activity
        else if (itemID == 3) {
            startActivity(new Intent(this, Fan.class));
            finish();

        } else if (itemID == 4) {
            startActivity(new Intent(this, entryAttack.class));
            finish();
        } else if (itemID == 5) {
            startActivity(new Intent(this, Message_Activity.class));
            finish();

        }
    }
}