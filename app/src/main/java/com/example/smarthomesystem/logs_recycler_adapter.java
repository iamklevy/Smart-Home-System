package com.example.smarthomesystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class logs_recycler_adapter extends RecyclerView.Adapter<logs_recycler_adapter.ViewHolder> {

    List<Log_recycler_list> logs_list;
    Context context;

    public logs_recycler_adapter(List<Log_recycler_list> logs_list, Context context) {
        this.logs_list = logs_list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.logs_recyclerview_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.log_img.setImageResource(logs_list.get(position).getImg());
        holder.log_timestamp.setText(logs_list.get(position).getTimestamp());
        holder.log_description.setText(logs_list.get(position).getDescription());
        Glide.with(this.context).load(logs_list.get(position).getImg()).into(holder.log_img);
    }

    @Override
    public int getItemCount() {
        return logs_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView log_img;
        TextView log_timestamp;
        TextView log_description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            log_img = itemView.findViewById(R.id.log_img);
            log_timestamp = itemView.findViewById(R.id.log_timestamp);
            log_description = itemView.findViewById(R.id.log_description);
        }

    }
}