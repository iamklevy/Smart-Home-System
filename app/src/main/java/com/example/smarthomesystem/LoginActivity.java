package com.example.smarthomesystem;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText et_username, et_password;
    TextView tv_forgot_password; // Add TextView for "Forgot password" link
    Button btn_login, btn_register;
    DBhelper db_helper;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    GoogleSignInClient googleSignInClient;
    Connection conn;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_username = findViewById(R.id.et_User_name);
        et_password = findViewById(R.id.et_Password);
        btn_login = findViewById(R.id.Btn_login);
        btn_register = findViewById(R.id.btn_register_here);
        Button googleAuth = findViewById(R.id.googleBtn);
        tv_forgot_password = findViewById(R.id.tv_forgot_password); // Initialize TextView

        db_helper = new DBhelper(this);

        GoogleSignInOptions google_sign_in = new GoogleSignInOptions.Builder(GoogleSignInOptions
                .DEFAULT_SIGN_IN).requestIdToken(getString(R.string.google_oauth_client_id))
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this,google_sign_in);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        conn = new Connection(this);

        btn_login.setOnClickListener(v -> {
            if (conn.isConnectionAvailable()) {
                Toast.makeText(LoginActivity.this, "Login ONLINE ", Toast.LENGTH_SHORT).show();
                authenticateOnline();

            } else {
                Toast.makeText(LoginActivity.this, "Login OFFLINE", Toast.LENGTH_SHORT).show();

                authenticateOffline();
            }
        });
        btn_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegDemo.class);
            startActivity(intent);
        });

        googleAuth.setOnClickListener(view -> {
            googleSignIn();
        });

        tv_forgot_password.setOnClickListener(view -> {
            // Define the URL to open
            Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);


            startActivity(intent);
        });
    }

    private void googleSignIn() {
            Intent intent = googleSignInClient.getSignInIntent();
            signInLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        firebaseAuth(account.getIdToken());

                    } catch (ApiException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, "something went wrong: result code is:" + result.getResultCode(), Toast.LENGTH_SHORT).show();
                }
            });

    private void firebaseAuth(String idToken) {

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {

            if (task.isSuccessful()){
                FirebaseUser user = mAuth.getCurrentUser();
                HashMap<String,Object> map = new HashMap<>();
                assert user != null;
                map.put("id",user.getUid());
                map.put("full name", user.getDisplayName());
                map.put("profile picture", Objects.requireNonNull(user.getPhotoUrl()).toString());
                map.put("mobile num", user.getPhoneNumber());
                map.put("email address", user.getEmail());

                usersRef.child("users").child(user.getUid()).setValue(map);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Toast.makeText(this, "successfully logged in", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(LoginActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }

        });

    }


    private void updateFirebase(Users userData) {

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        String username = userData.user; // Get the username from the input field


        DatabaseReference currentUserRef = usersRef.child(username);

        DatabaseReference userInfo=currentUserRef.child("user info");



        userInfo.child("username").setValue(userData.getUser());
        userInfo.child("email").setValue(userData.getMail());
        userInfo.child("phone").setValue(userData.getPhone());
        userInfo.child("country").setValue(userData.getCountry());
        userInfo.child("city").setValue(userData.getCity());
        userInfo.child("birthdate").setValue(userData.getBirthdate());



    }


    private void authenticateOnline() {
        // Retrieve user credentials from UI
        String email = et_username.getText().toString();
        String password = et_password.getText().toString();

        // Check if the user exists in SQLite
        Users userData = db_helper.getUserData(email);


        if (userData != null) {
            // User exists in SQLite
            // Check if the user exists in Firebase
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Authentication successful, navigate to next screen
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            et_username.setText("");
                            et_password.setText("");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Sync User in firebase...", Toast.LENGTH_SHORT).show();

                            // User doesn't exist in Firebase, create a new user
                            mAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            // User creation successful, update Firebase with user information
                                            updateFirebase(userData);
                                            Toast.makeText(LoginActivity.this, "Sync is done !", Toast.LENGTH_SHORT).show();
                                            authenticateOnline();
                                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                                            et_username.setText("");
                                            et_password.setText("");
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            // User creation failed
                                            Toast.makeText(LoginActivity.this, "Failed to Sync user ", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
        }
    }

    private void authenticateOffline() {

        boolean login_flag = db_helper.checkUserName(et_username.getText().toString(), et_password.getText().toString());
        boolean Exist_flag = db_helper.checkUserNameExist(et_username.getText().toString());
        if (!Exist_flag) {
            Toast.makeText(LoginActivity.this, "False username  ", Toast.LENGTH_LONG).show();
            return;
        }


        if (login_flag) {
            Toast.makeText(LoginActivity.this, "Login success :) ", Toast.LENGTH_LONG).show();


            et_username.setText("");
            et_password.setText("");


            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

            finish(); // Close the current activity


        } else {
            Toast.makeText(LoginActivity.this, "Login Failed ,please ReEnter password ", Toast.LENGTH_LONG).show();
            //Toast.makeText(LoginActivity.this, "ReEnter password  ", Toast.LENGTH_LONG).show();


        }

    }
}
