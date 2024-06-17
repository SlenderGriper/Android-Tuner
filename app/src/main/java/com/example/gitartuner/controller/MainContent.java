package com.example.gitartuner.controller;

import android.app.Activity;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.dto.NoteDto;
import com.example.gitartuner.dto.NoteStorage;
import com.example.gitartuner.dto.TuneStorage;
import com.example.gitartuner.model.CalculateRecord;
import com.example.gitartuner.model.DrawRecord;
import com.example.gitartuner.model.NoteCalculator;
import com.example.gitartuner.model.inteface.FrequencyGetter;
import com.example.gitartuner.model.inteface.OnStringClickListener;
import com.example.gitartuner.model.adapter.GettingNoteAdapter;
import com.github.mikephil.charting.charts.LineChart;

public class  MainContent implements OnStringClickListener, FrequencyGetter {
    private Activity activity;
    private AudioRecorder audioRecorder;
    private TuneStorage notes;
    private GettingNoteAdapter adapter;
private CalculateRecord calculateRecord;
private DrawRecord drawRecord;
    public void setNoteDraw(LineChart lineChart,TextView noteText) {
        this.lineChart=lineChart;
        this.noteText = noteText;
        drawRecord =new DrawRecord(activity,lineChart,noteText);
         calculateRecord =new CalculateRecord(drawRecord);
    }

    TextView noteText;
    LineChart lineChart;
    public MainContent(Activity activity){

        this.activity=activity;

    }
    public void setAdapterRecyclerView(RecyclerView recyclerView, NoteStorage noteStorage){
        notes=new TuneStorage(noteStorage.getLength());
        notes.setWantedStorage(noteStorage);

        adapter = new GettingNoteAdapter(notes);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);


    }

    public TuneStorage getTuneStorage(){return notes;}
    public void setAudioRecorder(AudioRecorder audioRecorder){

        this.audioRecorder=audioRecorder;


    }
    @Override
    public void onItemClick(int position) {

        calculateRecord.startRecordNote(this);
        notes.setSelected(position);
        adapter.notifyDataSetChanged();
        audioRecorder.startRecording(calculateRecord);
    }

    @Override
    public void getFrequency(double frequency) {
        NoteCalculator noteCalculator=new NoteCalculator();
        NoteDto noteDTO=noteCalculator.frequencyToNoteId(frequency);
        notes.setCurrent(notes.getSelected(), noteDTO);

        notes.resetSelected();
        activity.runOnUiThread(() -> adapter.notifyDataSetChanged());
        audioRecorder.stopRecording();

    }
}
