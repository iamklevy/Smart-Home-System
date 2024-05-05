package com.example.smarthomesystem;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Message_Activity extends AppCompatActivity {
    private DatabaseReference MR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference messageRecord = databaseReference.child("logs");
        MR = databaseReference.child("message");
        EditText messageInput = findViewById(R.id.message_input);
        Button displayButton = findViewById(R.id.display_button);
        Connection connection = new Connection(this);

        if (connection.isConnectionAvailable()) {
            displayButton.setOnClickListener(v -> {
                String msg = messageInput.getText().toString();
                sendMsgToFB(msg);
            });
            HashMap<String, Object> record = new HashMap<>();
            record.put("description", "Display Messages");
            record.put("id", 2);
            record.put("img", 10101);
            record.put("type", "Feature message");
            record.put("timestamp", get_timestamp());
            messageRecord.child("MsgRec").setValue(record);
            LogsManager logsManager = LogsManager.getInstance();
            logsManager.addLog(new Log_recycler_list(2, get_timestamp(), "Display Messages", "Feature message", R.drawable.message));
            messageInput.setEnabled(true);
            displayButton.setEnabled(true);
        } else {
            Toast.makeText(this, "You are Offline >:( OUTTTT", Toast.LENGTH_SHORT).show();
            messageInput.setEnabled(false);
            displayButton.setEnabled(false);
        }
    }

    private String get_timestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void sendMsgToFB(String msg) {
        MR.setValue(msg, (error, ref) -> {
            if (error != null) {
                Toast.makeText(Message_Activity.this, "Failed to send the message to Firebase", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Message_Activity.this, "Message sent to Firebase Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}