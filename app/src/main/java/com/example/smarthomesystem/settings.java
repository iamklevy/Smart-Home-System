package com.example.smarthomesystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class settings extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private ImageView profilePicture;
    TextView username;
    TextView userphone;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        auth = FirebaseAuth.getInstance();

       profilePicture = findViewById(R.id.profile_picture);
        username = findViewById(R.id.user_name);
        userphone = findViewById(R.id.user_phone);
       ImageView backBtn = findViewById(R.id.backBtn);
       Button EditProfile = findViewById(R.id.edit_profile);
       RelativeLayout logout = findViewById(R.id.logout_layout);

        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            profilePicture.setImageURI(user.getPhotoUrl());
            username.setText(user.getDisplayName());
            userphone.setText(user.getPhoneNumber());
        }

        profilePicture.setOnClickListener(v -> {
            Intent imagePickerIntent = new Intent(Intent.ACTION_PICK);
            imagePickerIntent.setType("image/*");
            startActivityForResult(imagePickerIntent, PICK_IMAGE_REQUEST_CODE);
        });

        backBtn.setOnClickListener(v -> {
            startActivity(new Intent(settings.this, MainActivity.class));
        });

        EditProfile.setOnClickListener(v -> {
            startActivity(new Intent(settings.this, profile.class));
        });

        logout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(settings.this, LoginActivity.class));
            Toast.makeText(this, "logged out successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            Uri selectedImageUri = data.getData();
            profilePicture.setImageURI(selectedImageUri);
        }
    }
}
