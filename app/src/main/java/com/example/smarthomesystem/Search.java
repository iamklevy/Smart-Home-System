package com.example.smarthomesystem;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Search extends AppCompatActivity {
    private ListView listViewResults;
    private EditText editTextSearch;
    private ArrayAdapter<String> adapter;

    private final List<String> dataList = Arrays.asList("Fan", "Temperature", "Light", "Message", "Password", "Entry Attack");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listViewResults = findViewById(R.id.listViewResults);
        editTextSearch = findViewById(R.id.search);
        listViewResults.setVisibility(View.GONE);
        adapter = new ArrayAdapter<>(this, R.layout.search_text_results, dataList);
        listViewResults.setAdapter(adapter);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        listViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                showToast("Selected item: " + selectedItem);
            }

        });

    }

    private void filter(String text) {
        if (text.isEmpty()) {
            listViewResults.setVisibility(View.GONE);
        } else {
            listViewResults.setVisibility(View.VISIBLE);
        }

        List<String> filteredList = new ArrayList<>();
        for (String item : dataList) {
            if (item.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter = new ArrayAdapter<>(this, R.layout.search_text_results, filteredList);
        listViewResults.setAdapter(adapter);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}