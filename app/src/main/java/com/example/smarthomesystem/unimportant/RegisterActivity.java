/*
package com.example.myapplication;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    EditText et_user, et_pass, et_repass ,et_Email,et_phone;
    Button btn_register, btn_login;
    DBhelper db_helper;
    Spinner spinner1,spinner2;
    CheckBox CB;

    private static final String TAG = "RegisterActivity";
    /////Date/////
    TextView DisplayDate;
    DatePickerDialog.OnDateSetListener mylistener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //definitions
        setContentView(R.layout.activity_regesiter);

       */
/* DisplayDate = (TextView) findViewById(R.id.tvdate);
        DisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, android.R.style.Theme_Holo_Light_Dialog,
                        mylistener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });*//*


        DisplayDate = findViewById(R.id.tvdate);

        // Set up the DatePickerDialog.OnDateSetListener
        mylistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Display the selected date in your TextView
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                DisplayDate.setText(selectedDate);
            }
        };

        // Set up the click listener for displaying the DatePickerDialog
        DisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        mylistener,
                        year,
                        month,
                        day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        et_user = findViewById(R.id.et_User_name);
        et_pass = findViewById(R.id.et_Password);
        et_repass = findViewById(R.id.et_RePassword);
        et_Email=findViewById(R.id.et_Email);
        et_phone=findViewById(R.id.et_Phone);
        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);

        CB=findViewById(R.id.checkBoxAgree);

        btn_login = findViewById(R.id.Btn_login);
        btn_register = findViewById(R.id.Btn_register);
        db_helper = new DBhelper(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //input data , Registration process
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pass, repass ,email,phone ,spin1,spin2;
                user = et_user.getText().toString();
                pass = et_pass.getText().toString();
                repass = et_repass.getText().toString();
                email = et_Email.getText().toString();
                phone = et_phone.getText().toString();
                spin1 = spinner1.getSelectedItem().toString();
                spin2 = spinner2.getSelectedItem().toString();
                String selectedDate = DisplayDate.getText().toString();


                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass) || TextUtils.isEmpty(email)
                        || TextUtils.isEmpty(phone) || TextUtils.isEmpty(spin1) || TextUtils.isEmpty(spin2) ) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields ", Toast.LENGTH_LONG).show();
                    return;

                }
                if (!CB.isChecked())
                {
                    Toast.makeText(RegisterActivity.this, "Please Agree on Terms and Conditions  ", Toast.LENGTH_LONG).show();
return;
                }

                // Check password length
                if (pass.length() < 8) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 8 characters long", Toast.LENGTH_LONG).show();
                    return;
                }

                // Check phone number length
                if (phone.length() != 11) {
                    Toast.makeText(RegisterActivity.this, "Phone number must be 12 digits long", Toast.LENGTH_LONG).show();
                    return;
                }else
                //if all  data filled properly
                {
                    //password match repassword
                   if (pass.equals(repass)) {

                    if (db_helper.checkUserNameExist(user)) {
                        Toast.makeText(RegisterActivity.this, "User already exists ", Toast.LENGTH_LONG).show();
                        return;

                    }
                    boolean register_flag;
                    register_flag = db_helper.insertData(user, pass,email,selectedDate,spin1,spin2,phone);
                    if (register_flag == true) {
                        Toast.makeText(RegisterActivity.this, "Registration success :) ", Toast.LENGTH_LONG).show();
                        // Clear input fields
                        et_user.setText("");
                        et_pass.setText("");
                        et_repass.setText("");
                        et_Email.setText("");
                        et_phone.setText("");
                        spinner1.setSelection(0); // Assuming "Select Country" is the first item
                        spinner2.setSelection(0); // Assuming "Select City" is the first item
                        CB.setChecked(false);

// Navigate to the main activity
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Close the current activity


                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed :( ", Toast.LENGTH_LONG).show();

                    }

                } else {
                        Toast.makeText(RegisterActivity.this, "Please rewrite password", Toast.LENGTH_LONG).show();

                    }


            }
        }});


        ////////Location//////
        Spinner spinnerCountry = findViewById(R.id.spinner1);
        Spinner spinnerCity = findViewById(R.id.spinner2);

        // Sample data


        String[] countries = {"Select Country", "Egypt","United States", "United Kingdom", "Canada", "Australia", "Germany", "France", "Japan", "India", "China"};
        String[] cities = {"Select City","Cairo","Alex", "New York", "London", "Toronto", "Sydney", "Berlin", "Paris", "Tokyo", "Mumbai", "Beijing"};


        // Create ArrayAdapter for the Spinners
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);

        // Set the dropdown style
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attach the ArrayAdapter to the Spinners
        spinnerCountry.setAdapter(countryAdapter);
        spinnerCity.setAdapter(cityAdapter);

    }
}
*/
