package com.example.gitartuner.controller;

import android.widget.EditText;

import com.example.gitartuner.dbo.GuitarDao;
import com.example.gitartuner.dto.GuitarDto;
import com.example.gitartuner.model.adapter.GitarAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GitarDialogController {
    private GitarAdapter adapter;
    private List<GuitarDto> itemList ;
    GuitarDao guitarDao;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    public void addGitar(EditText name,EditText stringCount){
        String countText=stringCount.getText().toString();
        String guitarText=name.getText().toString();
        GuitarDto item=new GuitarDto(auntificatorGuitar(guitarText),auntificatorCount(countText));
        itemList.add(item);
        adapter.notifyItemInserted(itemList.size() - 1);
        executorService.execute(() -> {
            guitarDao.insertGuitar(item);
        });
    }
    public void setAdapter(GitarAdapter adapter, List<GuitarDto> itemList){
        this.itemList=itemList;
        this.adapter=adapter;
        getStartBDData();
    }
    private void getStartBDData(){
        executorService.execute(()-> {
            List<GuitarDto>  items= guitarDao.getAllGuitars();
            for (GuitarDto item:
                    items) {
                itemList.add(item);
            }
        });
    }
    public  GuitarDto getData(){
        int selected=adapter.getSelected();
        if(selected==-1)return null;
        return itemList.get(selected);
    }
    public void setDao(GuitarDao guitarDao){
        this.guitarDao=guitarDao;
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
}
