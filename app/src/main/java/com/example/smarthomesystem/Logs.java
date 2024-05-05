    package com.example.smarthomesystem;

    import android.os.Bundle;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.recyclerview.widget.GridLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    public class Logs extends AppCompatActivity {
        LogsManager logsManager = LogsManager.getInstance();

        RecyclerView recyclerView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_log);
            Toolbar toolbar = findViewById(R.id.logs_toolbar);
            setSupportActionBar(toolbar);
            recyclerView = findViewById(R.id.logs_recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(this,1));
            logs_recycler_adapter logs_adapter = new logs_recycler_adapter(logsManager.getLogsList(),this);
            recyclerView.setAdapter(logs_adapter);
        }

    }
