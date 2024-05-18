package com.example.gitartuner.controller;

import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.model.adapter.GitarAdapter;

import java.util.ArrayList;
import java.util.List;

public class GitarDialogLogic {

    private List<String> itemList = new ArrayList<>();
    private GitarAdapter adapter;
    private RecyclerView recyclerView;
    private Button addGitar;
    private View view;
    public GitarDialogLogic(View view){
        this.view =view;
        recyclerView = view.findViewById(R.id.gitarList);
    }
    public void Initial(){

        itemList.add("test");
        itemList.add("test2");
        adapter = new GitarAdapter(itemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setStackFromEnd(true);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        addGitar=view.findViewById(R.id.addGitar);
        addGitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.add("New item");
                adapter.notifyItemInserted(itemList.size() - 1);
            }
        });
    }

}
