package com.example.smarthomesystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Objects;

public class RegDemo extends AppCompatActivity {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String TAG = "RegDemo";
    private final boolean isOfflineRegistration = false;
    EditText et_user, et_pass, et_repass, et_Email, et_phone;
    Button btn_register, btn_login;
    DBhelper db_helper;
    Spinner spinner1, spinner2;
    CheckBox CB;
    Connection conn;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    TextView DisplayDate;
    DatePickerDialog.OnDateSetListener mylistener;
    FirebaseManager fb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //definitions of ui
        setContentView(R.layout.activity_regesiter);


        fb = new FirebaseManager();

        DisplayDate = findViewById(R.id.tvdate);

        mylistener = (view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            DisplayDate.setText(selectedDate);
        };

        DisplayDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(RegDemo.this, android.R.style.Theme_Holo_Light_Dialog, mylistener, year, month, day);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        et_user = findViewById(R.id.et_User_name);
        et_pass = findViewById(R.id.et_Password);
        et_repass = findViewById(R.id.et_RePassword);
        et_Email = findViewById(R.id.et_Email);
        et_phone = findViewById(R.id.et_Phone);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        CB = findViewById(R.id.checkBoxAgree);

        btn_login = findViewById(R.id.Btn_login);
        btn_register = findViewById(R.id.Btn_register);
        db_helper = new DBhelper(this);

        conn = new Connection(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        Spinner spinnerCountry = spinner1;
        Spinner spinnerCity = spinner2;


        String[] countries = {"Select Country", "Egypt", "United States", "United Kingdom", "Canada", "Australia", "Germany", "France", "Japan", "India", "China"};
        String[] cities = {"Select City", "Cairo", "Alex", "New York", "London", "Toronto", "Sydney", "Berlin", "Paris", "Tokyo", "Mumbai", "Beijing"};


        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);

        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCountry.setAdapter(countryAdapter);
        spinnerCity.setAdapter(cityAdapter);


        btn_login.setOnClickListener(v -> {
            Intent intent = new Intent(RegDemo.this, LoginActivity.class);
            startActivity(intent);
        });


        btn_register.setOnClickListener(v -> {
            if (conn.isConnectionAvailable()) {
                Toast.makeText(RegDemo.this, "Sign up ONLINE ", Toast.LENGTH_SHORT).show();
                PerformAuth();
                storeDataInSQLite();
            } else {
                Toast.makeText(RegDemo.this, "Sign up OFFLINE", Toast.LENGTH_SHORT).show();
                storeDataInSQLite();
            }
        });
    }

    private void storeDataInSQLite() {
        String user, pass, repass, email, phone, spin1, spin2;
        user = et_user.getText().toString();
        pass = et_pass.getText().toString();
        repass = et_repass.getText().toString();
        email = et_Email.getText().toString();
        phone = et_phone.getText().toString();
        spin1 = spinner1.getSelectedItem().toString();
        spin2 = spinner2.getSelectedItem().toString();
        String selectedDate = DisplayDate.getText().toString();

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(spin1) || TextUtils.isEmpty(spin2)) {
            Toast.makeText(RegDemo.this, "Please fill all fields ", Toast.LENGTH_LONG).show();
            return;
        }
        if (!CB.isChecked()) {
            Toast.makeText(RegDemo.this, "Please Agree on Terms and Conditions  ", Toast.LENGTH_LONG).show();
            return;
        }
        if (pass.length() < 8) {
            et_pass.setError("Password must be at least 8 characters long");
            return;
        }
        if (phone.length() != 11) {
            et_phone.setError("Phone number must be 12 digits long");

            return;
        }

        boolean register_flag;
        register_flag = db_helper.insertData(user, pass, email, selectedDate, spin1, spin2, phone);
        if (register_flag) {
            Toast.makeText(RegDemo.this, "Registration successfully cached :) ", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(RegDemo.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(RegDemo.this, "Registration failed :( from SQL", Toast.LENGTH_LONG).show();
        }
    }

    // PerformAuth method remains unchanged (for Firebase authentication and data storage)
    private void PerformAuth() {
        // Your existing PerformAuth method implementation
        final String user, pass, repass, email, phone, spin1, spin2;
        user = et_user.getText().toString();
        pass = et_pass.getText().toString();
        repass = et_repass.getText().toString();
        email = et_Email.getText().toString();
        phone = et_phone.getText().toString();
        spin1 = spinner1.getSelectedItem().toString();
        spin2 = spinner2.getSelectedItem().toString();
        final String selectedDate = DisplayDate.getText().toString();


        if (!email.matches(EMAIL_PATTERN)) {
            et_Email.setError("Enter Valid Email");
            return;
        }

        //check  all fields are filled
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(spin1) || TextUtils.isEmpty(spin2)) {
            Toast.makeText(RegDemo.this, "Please fill all fields ", Toast.LENGTH_LONG).show();
            return;
        }

        // Check Terms and conditions
        if (!CB.isChecked()) {
            Toast.makeText(RegDemo.this, "Please Agree on Terms and Conditions  ", Toast.LENGTH_LONG).show();
            return;
        }

        // Check password length
        if (pass.length() < 8) {
            et_pass.setError("Password must be at least 8 characters long");
            return;
        }

        if (phone.length() != 11) {
            et_phone.setError("Phone number must be 12 digits long");
            return;
        }

        Users userid = new Users(user, pass, email, phone, selectedDate, spin1, spin2);
        fb.registerUser(userid, this);


        Toast.makeText(RegDemo.this, "Registration Successfully Completed", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(RegDemo.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}