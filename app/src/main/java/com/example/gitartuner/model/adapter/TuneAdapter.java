package com.example.gitartuner.model.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.dto.TuneDTO;
import com.example.gitartuner.model.OnStringClickListener;

import java.util.List;

public class    TuneAdapter extends RecyclerView.Adapter<TuneAdapter.MyViewHolder> {

    private TuneDTO  mData;
    private OnStringClickListener onStringClickListener;
    public void setOnItemClickListener(OnStringClickListener onItemClickListener) {
        this.onStringClickListener = onItemClickListener;
    }
    public TuneAdapter(TuneDTO data) {
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.stringChose.setText(mData.getWanted(position));
        holder.stringCount.setText(Integer.toString(mData.getLenght()-position));
        holder.currentNote.setText(mData.getCurrent(position));

        if(position==mData.getSelected()){
            holder.stringChose.setBackgroundColor(Color.rgb(0,200,0));
        }
        else holder.stringChose.setBackgroundColor(Color.GRAY);

        holder.stringChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onStringClickListener != null) {
                    onStringClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.getLenght();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        Button stringChose;
        TextView currentNote;
        TextView stringCount;
        public MyViewHolder(View itemView) {
            super(itemView);
            stringChose = itemView.findViewById(R.id.stringChose);
            currentNote= itemView.findViewById(R.id.currentNote);
            stringCount= itemView.findViewById(R.id.stringCount);

        }
    }
}