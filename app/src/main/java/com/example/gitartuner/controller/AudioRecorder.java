package com.example.gitartuner.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.widget.TextView;

import com.example.gitartuner.model.inteface.FrequencyGetter;
import com.example.gitartuner.model.SoundFrequencyAnalyzer;
import com.example.gitartuner.model.TunerMath;
import com.example.gitartuner.model.inteface.SendAudioData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


public class AudioRecorder {
    public static final int SAMPLE_RATE = 4000;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    public static final int BUFFER_SIZE =512;
    private AudioRecord audioRecord;
    private boolean isRecording = false;
    private static AudioRecorder record;


    @SuppressLint("MissingPermission")
    private AudioRecorder() {
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE);
    }


    @SuppressLint("MissingPermission")
    public static AudioRecorder Init() {
        if(record==null) record=new AudioRecorder();
        return record;






    }

    public void stopRecording(){
        isRecording = false;
        audioRecord.stop();
    }

    public void startRecording(SendAudioData send) {
        isRecording = true;
        audioRecord.startRecording();
        new Thread(() -> {
            while (isRecording) {
                short[] audioData = new short[BUFFER_SIZE];
                audioRecord.read(audioData, 0, BUFFER_SIZE);
                send.sendData(audioData);




            }
        }).start();
    }


}
