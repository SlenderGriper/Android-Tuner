package com.example.gitartuner.controller;

import android.app.Activity;
import android.media.AudioRecord;
import android.view.View;
import android.widget.Button;

import com.example.gitartuner.R;
import com.example.gitartuner.controller.bluetooth.BluetoothController;
import com.example.gitartuner.controller.bluetooth.BluetoothCreator;
import com.example.gitartuner.view.CalibrationFragment;

public class CalibrationController {
    private BluetoothController bluetoothController;
    public Activity activity;
    private AudioRecorder audioRecorder;
    public CalibrationController(Activity activity) {
        this.activity = activity;
        audioRecorder = AudioRecorder.Init();
    }

    public void viewCreate(View view){
        BluetoothCreator bluetoothCreator=new BluetoothCreator(activity);

        Button connect =view.findViewById(R.id.connect);
        connect.setOnClickListener(v -> {
            if(bluetoothCreator.checkConnected(view.getContext())){
                bluetoothController=bluetoothCreator.getBluetoothController();

            }
        });



    }
    public View view;
    public void OnStop(){
        if (bluetoothController!=null)bluetoothController.onPause();
        audioRecorder.stopRecording();

    }
}
