package com.example.gitartuner.view;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gitartuner.R;


public class TestActivity extends AppCompatActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        GuitarDatabase db = GuitarDatabaseImpl.getInstance(this);
//        GuitarDao guitarDao = db.getGuitarDao();
//
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        executorService.execute(() -> {
//            // Выполнение insert запросов Room
//            guitarDao.insertGuitar(new GuitarDto("Fender Stratocaster", 6));
//        });
//// вставка данных
//
//
//// получение всех гитар
//        LiveData<List<GuitarDto>> guitars = guitarDao.getAllGuitars();
//        guitars.observe(this, guitarsList -> {
//            guitarsList.size();
//        });
//        executorService.execute(() -> {
//            // Выполнение insert запросов Room
//            guitarDao.insertGuitar(new GuitarDto("Fender Stratocaster", 6));
//        });
//        executorService.execute(() -> {
//            // Выполнение insert запросов Room
//            guitarDao.updateGuitar(new GuitarDto(1, "Fender Telecaster", 6));
//        });
//         // где id — это идентификатор гитары, которую нужно обновить
//
//         guitars = guitarDao.getAllGuitars();
//        guitars.observe(this, Guitars -> {
//
//        });
    }




}