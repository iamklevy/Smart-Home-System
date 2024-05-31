package com.example.smarthomesystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profile extends AppCompatActivity {


    //handle sync info by regular username & pass
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private ImageView profilePicture;
    private ImageView backBtn;
    TextView username;
    TextView useremail;
    TextView userphone;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        auth = FirebaseAuth.getInstance();

        Button logoutBtn = findViewById(R.id.profile_logoutBtn);
        username = findViewById(R.id.user_name);
        useremail = findViewById(R.id.user_email);
        userphone = findViewById(R.id.user_phone);
        profilePicture = findViewById(R.id.profile_picture);
        backBtn = findViewById(R.id.arrow_back);

        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            profilePicture.setImageURI(user.getPhotoUrl());
            username.setText(user.getDisplayName());
            useremail.setText(user.getEmail());
            userphone.setText(user.getPhoneNumber());
        }

        profilePicture.setOnClickListener(v -> {
            Intent imagePickerIntent = new Intent(Intent.ACTION_PICK);
            imagePickerIntent.setType("image/*");
            startActivityForResult(imagePickerIntent, PICK_IMAGE_REQUEST_CODE);
        });

        backBtn.setOnClickListener(v -> {
            startActivity(new Intent(profile.this, MainActivity.class));
        });


        logoutBtn.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(profile.this, LoginActivity.class));
            Toast.makeText(this, "logged out successfully", Toast.LENGTH_SHORT).show();
            finish();
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                Uri selectedImageUri = data.getData();
                profilePicture.setImageURI(selectedImageUri);
                databaseReference.child("users").child(user.getUid()).child("photoUrl").setValue(selectedImageUri.toString());
            } else {
                Uri selectedImageUri = data.getData();
                profilePicture.setImageURI(selectedImageUri);
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
