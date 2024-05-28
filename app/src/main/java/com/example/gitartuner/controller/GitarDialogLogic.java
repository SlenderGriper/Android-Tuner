package com.example.gitartuner.controller;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.dbo.GuitarDao;
import com.example.gitartuner.dbo.GuitarDatabase;
import com.example.gitartuner.dbo.GuitarDatabaseImpl;
import com.example.gitartuner.dto.GuitarDto;
import com.example.gitartuner.model.adapter.GitarAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GitarDialogLogic {

    private List<GuitarDto> itemList = new ArrayList<>();
    private GitarAdapter adapter;
    private RecyclerView recyclerView;
    private Button addGitar;
    private View view;
    GuitarDatabase db;
    GuitarDao guitarDao;
    public GitarDialogLogic(View view){
        this.view =view;
        recyclerView = view.findViewById(R.id.gitarList);

        db=GuitarDatabaseImpl.getInstance(view.getContext());
        guitarDao = db.getGuitarDao();


        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            itemList= guitarDao.getAllGuitars();
            Initial();
        });

    }
    public void Initial(){


        adapter = new GitarAdapter(itemList,guitarDao);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setStackFromEnd(true);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        addGitar=view.findViewById(R.id.addGitar);
        EditText name=view.findViewById(R.id.nameTextEdit);
        EditText stringCount=view.findViewById(R.id.stringCountTextEdit);
        addGitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String countText=stringCount.getText().toString();
                String guitarText=name.getText().toString();
                GuitarDto item=new GuitarDto(auntificatorGuitar(countText),auntificatorCount(guitarText));
                itemList.add(item);
                adapter.notifyItemInserted(itemList.size() - 1);
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    guitarDao.insertGuitar(item);
                });
            }
        });
    }
    private String auntificatorGuitar(String guitarText){
        if(guitarText.isEmpty())return "Guitar";
        return  guitarText;
    }
    private int auntificatorCount(String countText){
        if(countText.isEmpty())return 6;

        int count=Integer.parseInt(countText);
        if(count<3)count=3;
        else if(count>12)return 12;
        return count;
    }
    public  GuitarDto getData(){
        int selected=adapter.getSelected();
        if(selected==-1)return null;
        return itemList.get(selected);
    }

}
