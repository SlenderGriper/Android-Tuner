package com.example.gitartuner.view;


import java.io.IOException;

import java.io.OutputStream;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;

import android.bluetooth.BluetoothAdapter;

import android.bluetooth.BluetoothDevice;

import android.bluetooth.BluetoothSocket;

import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.view.View.OnClickListener;

import android.widget.Button;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.gitartuner.R;
import com.example.gitartuner.controller.BluetoothController;
import com.example.gitartuner.dbo.GuitarDao;
import com.example.gitartuner.dbo.GuitarDatabase;
import com.example.gitartuner.dbo.GuitarDatabaseImpl;
import com.example.gitartuner.dto.GuitarDto;


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