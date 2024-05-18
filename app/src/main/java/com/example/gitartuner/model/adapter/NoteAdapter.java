package com.example.gitartuner.model.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.dto.NoteDto;
import com.example.gitartuner.dto.TuneDTO;
import com.example.gitartuner.model.OnStringClickListener;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {


    private NoteDto[] mData;

    public NoteAdapter(NoteDto[] data) {
        mData = data;
    }

    @NonNull
    @Override
    public NoteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_note, parent, false);
        return new NoteAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.note.setSelection(mData[position].note);
        holder.octave.setText(""+mData[position].octava);

    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        Spinner note;
        EditText octave;

        public MyViewHolder(View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.note);
            octave= itemView.findViewById(R.id.octave);

        }
    }
}