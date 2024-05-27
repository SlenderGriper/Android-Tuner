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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;


public class AudioRecorder {
    private static final int SAMPLE_RATE = 8000;
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static final int BUFFER_SIZE =512+512 ;
    private AudioRecord audioRecord;
    private boolean isRecording = false;
    private TextView noteText;
    private LineChart lineChart;
    private Activity context;
    private LineDataSet dataSet;
    private List<Entry> entries;
    SoundFrequencyAnalyzer soundFrequencyAnalyzer;
    TunerMath math;


    public AudioRecorder(Activity context, TextView noteText, LineChart lineChart) {
        this.context = context;
        this.noteText = noteText;
        this.lineChart = lineChart;
        initSetup();
    }

    @SuppressLint("MissingPermission")
    public void initSetup() {
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE);


        audioRecord.startRecording();
        isRecording = true;


        soundFrequencyAnalyzer = new SoundFrequencyAnalyzer(SAMPLE_RATE, BUFFER_SIZE);
        math = new TunerMath(SAMPLE_RATE);
    }

    public void startRecordNote(FrequencyGetter frequencyGetter) {
        soundFrequencyAnalyzer.setFrequencyGetter(frequencyGetter);
        soundFrequencyAnalyzer.startRecord();
    }

    public void startRecording() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRecording) {
                    short[] audioData = new short[BUFFER_SIZE];
                    audioRecord.read(audioData, 0, BUFFER_SIZE);

                    double[] data = math.Fourier(audioData);
                    String note = soundFrequencyAnalyzer.CalculeteNote(data);

                    List<Entry> newEntries = new ArrayList<>(); // Create a separate list for new entries
                    for (int i = 0; i < data.length/2; i++) {
                        newEntries.add(new Entry((float) soundFrequencyAnalyzer.indexToFruquency(i), (float) data[i])); // Add new entries to the separate list
                    }

                    entries = newEntries; // Add new entries to the main entries list
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (dataSet == null) {
                                dataSet = new LineDataSet(entries, "Audio Data");
                                LineData lineData = new LineData(dataSet);
                                lineData.addEntry(new Entry(0, 10), 0);
                                lineChart.setData(lineData);
                            } else {
                                dataSet.setValues(entries);
                                lineChart.notifyDataSetChanged();
                            }
                            noteText.setText(note);
                        }
                    });

                    lineChart.invalidate();

                }
            }
        }).start();
    }


}
