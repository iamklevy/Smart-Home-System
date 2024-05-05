package com.example.smarthomesystem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Log_recycler_list {
    private int id;
    private String timestamp;
    private String description;
    private String type;
    private Integer img;

    public Log_recycler_list(int id, String timestamp, String description, String type, Integer img) {
        this.id = id;
        this.timestamp = timestamp;
        this.description = description;
        this.type = type;
        this.img = img;
    }

    @Override
    public String toString() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String timestamp = sdf.format(new Date());

        return "Log_recycler_list{" +
                "id=" + id +
                ", timestamp='" + timestamp + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}
