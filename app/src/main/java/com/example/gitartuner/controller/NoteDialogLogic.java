package com.example.gitartuner.controller;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.dto.NoteStorage;
import com.example.gitartuner.model.NoteCalculator;
import com.example.gitartuner.model.adapter.NoteAdapter;

public class NoteDialogLogic {
    private View view;
    private Spinner note;
    private Spinner tuning;
    private RecyclerView recyclerView;
    private  NoteAdapter adapter;
    private NoteStorage itemList;
    private int lenght;

    public NoteDialogLogic(View view) {
        this.view = view;
        note = view.findViewById(R.id.note);
        tuning = view.findViewById(R.id.tuning);
        recyclerView = view.findViewById(R.id.gitarList);
    }

    public void initial(int lenght) {
        this.lenght = lenght;
        itemList = new NoteStorage(lenght);
         adapter = new NoteAdapter(itemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        tuning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                stringNote();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        note.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                stringNote();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }


    private void stringNote(){
        int typesId=tuning.getSelectedItemPosition();
        int firstNote=note.getSelectedItemPosition();
        NoteCalculator noteCalculator=new NoteCalculator();

        NoteStorage newItemList= noteCalculator.getStringNote(typesId,firstNote,lenght);
        itemList.update(newItemList);
        adapter.notifyDataSetChanged();
    }
    public NoteStorage getData() {
        return itemList;
    }


}
