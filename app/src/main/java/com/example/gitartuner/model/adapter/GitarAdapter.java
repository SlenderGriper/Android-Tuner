package com.example.gitartuner.model.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;

import java.util.List;

public class GitarAdapter extends RecyclerView.Adapter<GitarAdapter.ViewHolder> {

    private List<String> itemList;

    public GitarAdapter(List<String> items) {
        itemList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_gitar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String item = itemList.get(position);
        holder.textView.setText(item);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.gitarTemplate);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}