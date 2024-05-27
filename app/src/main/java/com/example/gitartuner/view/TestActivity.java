package com.example.gitartuner.view;


import java.io.IOException;

import java.io.OutputStream;

import java.util.UUID;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;

import android.bluetooth.BluetoothAdapter;

import android.bluetooth.BluetoothDevice;

import android.bluetooth.BluetoothSocket;

import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.view.View.OnClickListener;

import android.widget.Button;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.gitartuner.R;
import com.example.gitartuner.controller.BluetoothController;


public class TestActivity extends AppCompatActivity {


    Button btnOn, btnOff;
    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter btAdapter = null;
    BluetoothController bluetoothController;

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        btnOn = (Button) findViewById(R.id.connect);
        btnOff = (Button) findViewById(R.id.tuneArduinoButton);
        TestActivity test = this;
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        btnOn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if(btAdapter==null){
                    Toast.makeText(TestActivity.this,"Ошибка с блютуз модулем", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!btAdapter.isEnabled())checkBTState();

                if(bluetoothController==null)bluetoothController=new BluetoothController(TestActivity.this,btAdapter);
                else if(!bluetoothController.isConnected())bluetoothController.onStartBluetooth();
                if(bluetoothController.isConnected()) bluetoothController.sendData("0");

            }

        });


    }



    @Override
    public void onPause() {
        super.onPause();

       if (bluetoothController!=null)bluetoothController.onPause();

    }


    @SuppressLint("MissingPermission")
    private void checkBTState() {
            Intent enableBtIntent = new Intent(btAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);


    }

}