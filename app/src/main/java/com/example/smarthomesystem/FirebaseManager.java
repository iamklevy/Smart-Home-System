package com.example.smarthomesystem;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseManager {
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    public FirebaseManager() {
        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public DatabaseReference getUsersRef() {
        return usersRef;
    }

    public void setUsersRef(DatabaseReference usersRef) {
        this.usersRef = usersRef;
    }


    public void registerUser(Users user, Activity activity) {

        getmAuth().createUserWithEmailAndPassword(user.getMail(), user.getPass()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser fbUser = mAuth.getCurrentUser();

                if (fbUser != null) {
                    // String userId = user.getUid();
                    DatabaseReference currentUserRef = usersRef.child(user.getUser());
                    DatabaseReference userInfoRef = currentUserRef.child("user info");
                    userInfoRef.child("username").setValue(user.getUser());
                    userInfoRef.child("email").setValue(user.getMail());
                    userInfoRef.child("phone").setValue(user.getPhone());
                    userInfoRef.child("country").setValue(user.getCountry());
                    userInfoRef.child("city").setValue(user.getCity());
                    userInfoRef.child("birthdate").setValue(user.getBirthdate());

                    Toast.makeText(activity, "Registration success ", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(activity, "User is null", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(activity, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}