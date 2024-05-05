package com.example.smarthomesystem;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LogsManager extends AppCompatActivity {

    private static LogsManager instance;
    private final List<Log_recycler_list> logsList;

    public LogsManager() {
        logsList = new ArrayList<>();
    }

    public static synchronized LogsManager getInstance() {
        if (instance == null) {
            instance = new LogsManager();
        }
        return instance;
    }

    public List<Log_recycler_list> getLogsList() {
        return logsList;
    }

    public void addLog(Log_recycler_list log) {
        logsList.add(log);
    }
}
