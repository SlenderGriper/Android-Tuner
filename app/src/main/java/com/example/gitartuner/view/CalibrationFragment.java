package com.example.gitartuner.view;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.gitartuner.R;
import com.example.gitartuner.controller.CalibrationController;
import com.example.gitartuner.controller.MainContent;
import com.example.gitartuner.controller.SendButtonSwitch;
import com.example.gitartuner.controller.bluetooth.BluetoothController;
import com.example.gitartuner.controller.bluetooth.BluetoothCreator;

public class CalibrationFragment  extends Fragment {

    private CalibrationController controller;
    public CalibrationFragment(){
        super(R.layout.calibration_fragment);

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new CalibrationController(getActivity());

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        controller.viewCreate(view);
        Button tuneArduinoButton = view.findViewById(R.id.back);
        tuneArduinoButton.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });


    }
    @Override
    public void onStop() {
        super.onStop();
        controller.OnStop();
    }
}
