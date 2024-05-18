package com.example.gitartuner.controller;

import android.app.Activity;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gitartuner.dto.TuneDTO;
import com.example.gitartuner.model.FrequencyGetter;
import com.example.gitartuner.model.OnStringClickListener;
import com.example.gitartuner.model.adapter.TuneAdapter;

public class MainContent implements OnStringClickListener, FrequencyGetter {
    private Activity activity;
    private AudioRecorder audioRecorder;
    private TuneDTO notes;
    private TuneAdapter adapter;
    public MainContent(Activity activity){
        this.activity=activity;
    }
    public void setAdapterRecyclerView(RecyclerView recyclerView,String[] wantedNotes){
//        String[] types = {"standart", "drop", "open"};
//        String[] semitones = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        notes=new TuneDTO(wantedNotes.length);
        for (int i=0;i<wantedNotes.length;i++)
        {
           notes.setWanted(i,wantedNotes[i]);
        }

        adapter = new TuneAdapter(notes);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    public void setAudioRecorder(AudioRecorder audioRecorder){
        this.audioRecorder=audioRecorder;
    }
    @Override
    public void onItemClick(int position) {
        notes.setSelected(position);
        adapter.notifyDataSetChanged();
        audioRecorder.startRecordNote(this);
    }

    @Override
    public void getFrequency(double frequency) {
        String note = "" + frequency + "";
        notes.setCurrent(notes.getSelected(), note);

        notes.resetSelected();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
