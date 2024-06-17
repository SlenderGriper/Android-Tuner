package com.example.gitartuner.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.R;
import com.example.gitartuner.controller.AudioRecorder;
import com.example.gitartuner.controller.bluetooth.BluetoothController;
import com.example.gitartuner.controller.MainContent;
import com.example.gitartuner.controller.SendButtonSwitch;
import com.example.gitartuner.controller.bluetooth.BluetoothCreator;
import com.example.gitartuner.databinding.FragmentMainBinding;
import com.example.gitartuner.dto.GuitarDto;
import com.example.gitartuner.dto.NoteStorage;
import com.example.gitartuner.model.adapter.TuneAdapter;
import com.github.mikephil.charting.charts.LineChart;

public class MainFragment extends Fragment {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;



    private FragmentMainBinding  binding;

    private AudioRecorder audioRecorder;
    private MainContent mainContent;
    private int lenght=6;
    private RecyclerView recyclerView;
    private SendButtonSwitch sendButtonSwitch;
    private String guitarInfoText;

    private BluetoothController bluetoothController;
    public MainFragment(){
        super(R.layout.fragment_main);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainContent = new MainContent(getActivity());
        sendButtonSwitch = new SendButtonSwitch();
        guitarInfoText="";
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        TextView guitarInfo = binding.GitarInfoText;
        guitarInfo.setText(guitarInfoText);

        TextView noteText = binding.note;
        LineChart lineChart = binding.chart1;
        mainContent.setNoteDraw(lineChart,noteText);


        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
        } else {
            audioRecorder = AudioRecorder.Init();
        }

         recyclerView = binding.recyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        mainContent.setAudioRecorder(audioRecorder);

        Button sendArduino = binding.tuneArduinoButton;
         sendButtonSwitch.setButton(sendArduino);

        Button note = binding.tune;
        note.setOnClickListener(v -> {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            Bundle bundle = new Bundle();
            bundle.putInt("key", lenght);

            Fragment fragment=new NoteFragment();
            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.fragment_container_view,fragment )
            .addToBackStack("main")
            .commit();
            getParentFragmentManager().setFragmentResultListener("noteKey", this, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                    NoteStorage receivedNote = (NoteStorage) result.getSerializable("note");
                    mainContent.setAdapterRecyclerView(recyclerView, receivedNote);
                    sendButtonSwitch.enableDataFlag();
                }
            });

        });
        Button guitar = binding.gitar;
        guitar.setOnClickListener(v -> {

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container_view, new GuitarFragment());
            fragmentTransaction.addToBackStack("main");
            fragmentTransaction.commit();

            getParentFragmentManager().setFragmentResultListener("guitarKey", this, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                    GuitarDto guitarDto = (GuitarDto) result.getSerializable("guitar");
                    guitarInfoText = guitarDto.name;
                    binding.GitarInfoText.setText(guitarInfoText);
                    lenght = guitarDto.stringsCount;
                }
            });
        });

        BluetoothCreator bluetoothCreator=new BluetoothCreator(getActivity());
        Button connect = binding.connect;
        connect.setOnClickListener(v -> {
            if(bluetoothCreator.checkConnected(getContext())){
                bluetoothController=bluetoothCreator.getBluetoothController();

            }
        });


        sendArduino.setOnClickListener(v -> {
            LayoutInflater inflater2 = getLayoutInflater();
            View view2 = inflater2.inflate(R.layout.tune_dialog, null);
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setTitle("Настройка гитары");
            dialog.setView(view2);

            RecyclerView recyclerView2 = view2.findViewById(R.id.mainContent);
            TuneAdapter tuneAdapter = new TuneAdapter(mainContent.getTuneStorage());
            tuneAdapter.setOnItemClickListener(bluetoothController);
            recyclerView2.setAdapter(tuneAdapter);

            LinearLayoutManager layoutManager = new LinearLayoutManager(view2.getContext());
            layoutManager.setStackFromEnd(true);

            recyclerView2.setLayoutManager(layoutManager);
            dialog.setNegativeButton("Cancel", (dialog1, which) -> {

            });

            dialog.create().show();
        });

        return binding.getRoot();
    }




    @Override
    public void onStop() {
        super.onStop();
        if (bluetoothController!=null)bluetoothController.onPause();
        audioRecorder.stopRecording();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                audioRecorder = AudioRecorder.Init();
            } else {
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
