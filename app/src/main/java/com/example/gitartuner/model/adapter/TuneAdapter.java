package com.example.gitartuner.model.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.controller.bluetooth.BluetoothController;
import com.example.gitartuner.dto.TuneStorage;

public class TuneAdapter extends RecyclerView.Adapter<TuneAdapter.MyViewHolder>{
    private TuneStorage mData;

    private BluetoothController bluetoothController;
    public void setOnItemClickListener(BluetoothController bluetoothController) {
        this.bluetoothController = bluetoothController;
    }
    public TuneAdapter(TuneStorage data) {
        mData = data;
    }

    @NonNull
    @Override
    public TuneAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_tune, parent, false);
        return new TuneAdapter.MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.stringChose.setText("tune");
        holder.wantedNote.setText(mData.getStringWanted(position));
        holder.currentNote.setText(mData.getStringCurrent(position));

        if(mData.getStringCurrent(position)=="")holder.stringChose.setEnabled(false);

        else holder.stringChose.setOnClickListener(view -> {
            if (bluetoothController != null) {
                bluetoothController.sendData(mData.getMassageArduino(position));
            }
           String test = mData.getMassageArduino(position);
        });
    }

    @Override
    public int getItemCount() {
        return mData.getLenght();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        Button stringChose;
        TextView currentNote;
        TextView wantedNote;
        public MyViewHolder(View itemView) {
            super(itemView);
            stringChose = itemView.findViewById(R.id.sendArduino);
            currentNote= itemView.findViewById(R.id.current);
            wantedNote= itemView.findViewById(R.id.wanted);

        }
    }
}
