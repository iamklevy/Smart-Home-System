
package com.example.smarthomesystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ForgetPassword extends AppCompatActivity {

    //handle updating the pass related to the username related to the typed-in email

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth;
    ProgressBar progressBar;
    Button BtnReset;
    String strEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);


        Button Btnback = findViewById(R.id.btnForgotPasswordBack);
        BtnReset = findViewById(R.id.btnReset);
        EditText editEmail = findViewById(R.id.ForgotPasswordEmail);


        auth = FirebaseAuth.getInstance();

        BtnReset.setOnClickListener(v -> {
            strEmail = editEmail.getText().toString().trim();
            if(!TextUtils.isEmpty(strEmail)){
                ResetPassword();
            }
            else{
                editEmail.setError("Email Field can't be empty");
            }
        });

        Btnback.setOnClickListener(v -> {
            startActivity(new Intent(ForgetPassword.this, LoginActivity.class));
        });

    }

    private void ResetPassword() {
        auth.sendPasswordResetEmail(strEmail).addOnSuccessListener(unused -> {
            Toast.makeText(ForgetPassword.this, "Reset password Link has been sent to the registered email", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ForgetPassword.this, LoginActivity.class));
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(ForgetPassword.this, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }
}

