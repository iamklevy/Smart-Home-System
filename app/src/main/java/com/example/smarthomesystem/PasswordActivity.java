package com.example.smarthomesystem;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class PasswordActivity extends AppCompatActivity {
    String password;
    private DatabaseReference passrec;
    private DatabaseReference PR;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        passrec = databaseReference.child("logs");

        Connection conn = new Connection(this);

        PR = databaseReference.child("password");
        EditText passwordInput = findViewById(R.id.password_input);
        EditText reEnterPasswordInput = findViewById(R.id.re_enter_password_input);
        Button confirmButton = findViewById(R.id.confirm_button);

        if (!conn.isConnectionAvailable()) {
            passwordInput.setEnabled(false);
            reEnterPasswordInput.setEnabled(false);
            confirmButton.setEnabled(false);
            Toast.makeText(this, "You are offline >:(", Toast.LENGTH_SHORT).show();

        } else {

            passwordInput.setEnabled(true);
            reEnterPasswordInput.setEnabled(true);
            confirmButton.setEnabled(true);
            reEnterPasswordInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    password = passwordInput.getText().toString();
                }


                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            confirmButton.setOnClickListener(v -> {
                if (passwordInput.getText().toString().isEmpty() || reEnterPasswordInput.getText().toString().isEmpty()) {
                    Toast.makeText(PasswordActivity.this, "Invalid Password!", Toast.LENGTH_SHORT).show();

                } else {
                    if (passwordInput.getText().toString().equals(reEnterPasswordInput.getText().toString())) {
                        sendPasswordToFB(reEnterPasswordInput.getText().toString());
                    } else {
                        Toast.makeText(PasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();

                    }
                }
            });
            HashMap<String, Object> record = new HashMap<>();
            record.put("description", "Changing password");
            record.put("id", 3);
            record.put("img", 10111);
            record.put("type", "Feature Password");
            record.put("timestamp", get_timestamp());
            passrec.child("PasswordRec").setValue(record);
            LogsManager logsManager = LogsManager.getInstance();
            logsManager.addLog(new Log_recycler_list(3, get_timestamp(), "Changing password", "Feature Password", R.drawable.password));
        }
    }

    private boolean isMatching(String value) {
        return password.equals(value);
    }

    private String get_timestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void sendPasswordToFB(String msg) {
        PR.setValue(msg, (error, ref) -> {
            if (error != null) {
                Toast.makeText(PasswordActivity.this, "Failed to send the Password to Firebase", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PasswordActivity.this, "Password sent to Firebase Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
