package com.example.gitartuner.model;

import com.example.gitartuner.dbo.GuitarDao;
import com.example.gitartuner.dto.GuitarDto;
import com.example.gitartuner.model.adapter.GitarAdapter;
import com.example.gitartuner.model.inteface.AdapterUpdate;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuitarDataBD {
    ExecutorService executorService;
    GuitarDao guitarDao;

    public GuitarDataBD(GuitarDao guitarDao) {
        this.executorService =  Executors.newCachedThreadPool();
        this.guitarDao = guitarDao;
    }
    public void AddData(GuitarDto item){
        executorService.execute(() -> {
            guitarDao.insertGuitar(item);
        });
    }
    public void getData(List<GuitarDto> itemList, AdapterUpdate adapterUpdate){
        executorService.execute(()-> {
            List<GuitarDto> items= guitarDao.getAllGuitars();
            for (GuitarDto item:
                    items) {
                itemList.add(item);
            }
            adapterUpdate.adapterUpdate();
        });
    }
}
