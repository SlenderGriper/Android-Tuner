package com.example.gitartuner.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.controller.AudioRecorder;
import com.example.gitartuner.controller.BluetoothController;
import com.example.gitartuner.controller.MainContent;
import com.example.gitartuner.controller.SendButtonSwitch;
import com.example.gitartuner.databinding.ActivityMainBinding;
import com.example.gitartuner.dto.GuitarDto;
import com.example.gitartuner.model.adapter.TuneAdapter;
import com.github.mikephil.charting.charts.LineChart;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter btAdapter = null;
    private BluetoothController bluetoothController;
    private ActivityMainBinding binding;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private AudioRecorder audioRecorder;
 private MainContent mainContent;
 private int lenght=6;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView noteText=binding.note;
        LineChart lineChart = binding.chart1;

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
            } else {
                audioRecorder=new AudioRecorder(this,noteText,lineChart);
                audioRecorder.startRecording();
            }

        RecyclerView recyclerView = binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
         mainContent = new MainContent(this);

        mainContent.setAudioRecorder(audioRecorder);

        Button sendArduino=binding.tuneArduinoButton;
        SendButtonSwitch sendButtonSwitch =new SendButtonSwitch(sendArduino);

        Button note = binding.tune;
        note.setOnClickListener(v -> {

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.note_dialog, null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle(R.string.note_title);
            dialog.setView(view);

            NoteDialogLogic logic = new NoteDialogLogic(view);
            logic.initial(lenght);
            dialog.setPositiveButton("OK", (dialog1, which) -> {
                mainContent.setAdapterRecyclerView(recyclerView,logic.getData());
                sendButtonSwitch.enableDataFlag();
            });
            dialog.setNegativeButton("Cancel", (dialog1, which) -> {

            });
            dialog.create().show();
        });
        Button guitar = binding.gitar;
        guitar.setOnClickListener(v -> {

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.gitar_dialog, null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle(R.string.guitar_title);
            dialog.setView(view);
            GitarDialogLogic logic = new GitarDialogLogic(view);
            logic.Initial();
            dialog.setNegativeButton("Cancel", (dialog1, which) -> {
                GuitarDto guitarDto=logic.getData();
                if (guitarDto==null)return;
                TextView guitarInfo=binding.GitarInfoText;
                guitarInfo.setText(guitarDto.name);
                lenght=guitarDto.stringsCount;
            });
            dialog.create().show();
        });
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        Button connect = binding.connect;
        connect.setOnClickListener(v -> {
            if(btAdapter==null){
                Toast.makeText(MainActivity.this,"Ошибка с блютуз модулем", Toast.LENGTH_LONG).show();
                sendButtonSwitch.enableBluetoothFlag();
                return;
            }
            if(!btAdapter.isEnabled()){
                BluetoothEnableIntent();
                return;
            }

            if(bluetoothController==null)
                bluetoothController=new BluetoothController(MainActivity.this,btAdapter);
            else if(!bluetoothController.isConnected())
                bluetoothController.onStartBluetooth();

            if(bluetoothController.isConnected()) sendButtonSwitch.enableBluetoothFlag();

        });


        sendArduino.setOnClickListener(v -> {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.tune_dialog, null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("Настройка гитары");
            dialog.setView(view);

            RecyclerView recyclerView2=view.findViewById(R.id.mainContent);
            TuneAdapter tuneAdapter=new TuneAdapter(mainContent.getTuneStorage());
            tuneAdapter.setOnItemClickListener(bluetoothController);
            recyclerView2.setAdapter(tuneAdapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            layoutManager.setStackFromEnd(true);

            recyclerView2.setLayoutManager(layoutManager);
            dialog.setNegativeButton("Cancel", (dialog1, which) -> {

            });

            dialog.create().show();
        });


    }


    @Override
    public void onPause() {
        super.onPause();

        if (bluetoothController!=null)bluetoothController.onPause();
    }


    @SuppressLint("MissingPermission")
    private void BluetoothEnableIntent() {
        Intent enableBtIntent = new Intent(btAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                audioRecorder.startRecording();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }





}

