package com.example.smarthomesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Connection conn;
    FirebaseAuth auth;
    ArrayList<actions_recycler_list> actions_list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        auth = FirebaseAuth.getInstance();
        auth.getCurrentUser();
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.actions_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        actions_list = new ArrayList<>();

        conn = new Connection(this);


        actions_list.add(new actions_recycler_list(R.drawable.temperature, "Temperature"));
        actions_list.add(new actions_recycler_list(R.drawable.password, "Password"));
        actions_list.add(new actions_recycler_list(R.drawable.light, "Light"));
        actions_list.add(new actions_recycler_list(R.drawable.fan, "Fan"));
        actions_list.add(new actions_recycler_list(R.drawable.attack, "Entry Attack"));
        actions_list.add(new actions_recycler_list(R.drawable.message, "Message"));

        actions_recycler_adapter actions_adapter;
        actions_adapter = new actions_recycler_adapter(actions_list, this);

        if (conn.isConnectionAvailable()) {

            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAlpha(1);

        } else {
            recyclerView.setAlpha(0.5F);
         //   actions_adapter = new actions_recycler_adapter(actions_list, this);


        }
        recyclerView.setAdapter(actions_adapter);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemID = item.getItemId();

        if (itemID == R.id.menuprofile) {
            startActivity(new Intent(MainActivity.this, profile.class));
        }
        if (itemID == R.id.menusettings) {
            startActivity(new Intent(MainActivity.this, settings.class));
        }
        if (itemID == R.id.menulogout) {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            Toast.makeText(this, "logged out successfully", Toast.LENGTH_SHORT).show();

            finish();
        }
        if (itemID == R.id.menusearch) {
            startActivity(new Intent(MainActivity.this, Search.class));
        }
        if (itemID == R.id.menulogcat) {
            startActivity(new Intent(MainActivity.this, Logs.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.options_menu, menu);

        return true;
    }
}
