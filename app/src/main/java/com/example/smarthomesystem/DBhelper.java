package com.example.smarthomesystem;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "register.db";
    private static final int DB_VERSION = 2; // Update the database version
    private boolean isOfflineRegistration = false;

    public DBhelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the users table with the updated schema
        db.execSQL("CREATE TABLE users ("
                + "username TEXT PRIMARY KEY,"
                + "password TEXT,"
                + "email TEXT,"
                + "birthdate TEXT,"
                + "country TEXT,"
                + "city TEXT,"
                + "phone TEXT"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing table if it exists
        db.execSQL("DROP TABLE IF EXISTS users");
        // Recreate the table with the updated schema
        onCreate(db);
    }

    //to check that data inserted successfully or not
    public boolean insertData(String username, String password, String email, String birthdate, String country, String city, String phone) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("birthdate", birthdate);
        contentValues.put("country", country);
        contentValues.put("city", city);
        contentValues.put("phone", phone);

        long result = myDB.insert("users", null, contentValues);
        return result != -1;
    }

    boolean checkUserNameExist(String email)
    {
        SQLiteDatabase myDB=this.getWritableDatabase();
        Cursor cursor=myDB.rawQuery("select * from users where email = ? ",new String[]{email}); //-> there is no user
        if(cursor.getCount()>0)
            return true;
        else
            return false;


    }


    boolean checkUserName(String email,String password)
    {
        SQLiteDatabase myDB=this.getWritableDatabase();
        Cursor cursor=myDB.rawQuery("select * from users where email = ? and password = ?",new String[]{email,password}); //-->login faild
        if(cursor.getCount()>0)
            return true;
        else
            return false;


    }

    public Users getUserData(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Users user = null;

        // Define columns you want to retrieve
        String[] projection = {
                "username",
                "email",
                "phone",
                "country",
                "city",
                "birthdate"
        };

        // Perform the query
        Cursor cursor = db.query(
                "users",            // The table name
                projection,         // The columns to retrieve
                "email = ?",        // Selection criteria
                new String[]{email},// Selection arguments
                null,               // No GROUP BY clause
                null,               // No HAVING clause
                null                // No ORDER BY clause
        );

        // Check if the cursor contains any rows
        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve data from the cursor and create a Users object
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String userEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String country = cursor.getString(cursor.getColumnIndexOrThrow("country"));
            String city = cursor.getString(cursor.getColumnIndexOrThrow("city"));
            String birthdate = cursor.getString(cursor.getColumnIndexOrThrow("birthdate"));

            // Create a new Users object
            user = new Users(username, "", userEmail, phone, birthdate, country, city);
        }

        // Close the cursor and database when done
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return user;
    }

}
