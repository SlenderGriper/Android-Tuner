package com.example.gitartuner.controller.bluetooth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BluetoothCreator {

    static private BluetoothController bluetooth;
    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter btAdapter  ;

    public  BluetoothController getBluetoothController() {
        return bluetoothController;
    }

    private static BluetoothController bluetoothController;
    private Activity activity;

    public  BluetoothCreator(Activity activity){
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        this.activity=activity;
    }
    public  BluetoothController setBluetoothController(Context context, BluetoothAdapter btAdapter){
        if(bluetooth!=null)return bluetooth;
        bluetooth = new BluetoothController(context,btAdapter);
        return bluetooth;
    }

    @SuppressLint("MissingPermission")
    private void BluetoothEnableIntent() {
        Intent enableBtIntent = new Intent(btAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }
    public boolean checkConnected(Context context){

        if (btAdapter == null) {
            Toast.makeText(context, "Ошибка с блютуз модулем", Toast.LENGTH_LONG).show();
            return true;
        }
        if (!btAdapter.isEnabled()) {
            BluetoothEnableIntent();
            return false;
        }


        bluetoothController = setBluetoothController(context, btAdapter);

        if (!bluetoothController.isConnected()) {
            bluetoothController.onStartBluetooth();
            return false;
        }


        return true;


    }
}
