package com.example.gitartuner.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
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
import com.example.gitartuner.controller.GitarDialogLogic;
import com.example.gitartuner.controller.MainContent;
import com.example.gitartuner.controller.NoteDialogLogic;
import com.example.gitartuner.databinding.ActivityMainBinding;
import com.example.gitartuner.model.TunerMath;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {



    private ActivityMainBinding binding;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private AudioRecorder audioRecorder;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView noteText=binding.note;
        LineChart lineChart = binding.chart1;
        audioRecorder=new AudioRecorder(this,noteText,lineChart);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
        } else {
            audioRecorder.startRecording();
        }

        RecyclerView recyclerView = binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        String[] test={"C","B","C","B","C","B"};
        MainContent mainContent = new MainContent(this);

        mainContent.setAudioRecorder(audioRecorder);

        Button note = binding.tune;
        note.setOnClickListener(v -> {

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.note_dialog, null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle(R.string.note_title);
            dialog.setView(view);

            NoteDialogLogic logic = new NoteDialogLogic(view);
            logic.initial(6);
            dialog.setPositiveButton("OK", (dialog1, which) -> {
                mainContent.setAdapterRecyclerView(recyclerView,logic.getData());
            });
            dialog.setNegativeButton("Cancel", (dialog1, which) -> {

            });
            dialog.create().show();
        });

        Button gitar = binding.gitar;
        gitar.setOnClickListener(v -> {

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.gitar_dialog, null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle(R.string.guitar_title);
            dialog.setView(view);
            GitarDialogLogic logic = new GitarDialogLogic(view);
            logic.Initial();

            dialog.setNegativeButton("Cancel", (dialog1, which) -> {

            });
            dialog.create().show();
        });






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

