package com.example.gitartuner.controller;

import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.dto.NoteDto;
import com.example.gitartuner.model.adapter.GitarAdapter;
import com.example.gitartuner.model.adapter.NoteAdapter;

public class NoteDialogLogic {
    private View view;
    private Spinner note;
    private Spinner tuning;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteDto[] itemList;
    public NoteDialogLogic(View view){
        this.view=view;
        note=view.findViewById(R.id.tuning);
        tuning=view.findViewById(R.id.note);
        recyclerView = view.findViewById(R.id.gitarList);
    }
    public void Initial(){
        itemList=new NoteDto[3];
        itemList[0]=new NoteDto(3,4);
        itemList[1]=new NoteDto(11,3);
        itemList[2]=new NoteDto(6,3);
        adapter = new NoteAdapter(itemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }
    public void getData(){


    }


}
