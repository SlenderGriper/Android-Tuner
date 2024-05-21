package com.example.gitartuner.model.adapter;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.dto.NoteStorage;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {


    private NoteStorage mData;

    public NoteAdapter(NoteStorage data) {
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
        holder.note.setSelection(mData.getNotes(position));
        holder.octave.setText(""+mData.getOctava(position));

        holder.octave.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mData.setOctava(position,Integer.parseInt(holder.octave.getText().toString()));
            }
        });

        holder.note.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int index=position;
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mData.setNotes(index,position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }

    @Override
    public int getItemCount() {
        return mData.getLenght();
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