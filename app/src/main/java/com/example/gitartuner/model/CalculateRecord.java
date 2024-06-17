package com.example.gitartuner.model;

import android.app.Activity;
import android.widget.TextView;

import com.example.gitartuner.controller.AudioRecorder;
import com.example.gitartuner.model.inteface.DrawChart;
import com.example.gitartuner.model.inteface.FrequencyGetter;
import com.example.gitartuner.model.inteface.SendAudioData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class CalculateRecord implements SendAudioData {

    private LineDataSet dataSet;

    private TunerMath math;
    private SoundFrequencyAnalyzer soundFrequencyAnalyzer;

    private List<Entry>  entries;
    private LineData lineData;
    private DrawChart drawChart;
    public CalculateRecord(DrawChart drawChart) {
        this.drawChart=drawChart;
        soundFrequencyAnalyzer = new SoundFrequencyAnalyzer(AudioRecorder.SAMPLE_RATE, AudioRecorder.BUFFER_SIZE);
        math = new TunerMath(AudioRecorder.SAMPLE_RATE);
        init();
    }
    private void init(){



        entries= new ArrayList<Entry>(AudioRecorder.BUFFER_SIZE); // Create a separate list for new entries
        entries.add(new Entry(0, 10));
        entries.add(new Entry(0, 0));
        entries.add(new Entry(AudioRecorder.SAMPLE_RATE/2, 0));
        for (int i = 3; i < AudioRecorder.BUFFER_SIZE; i++) {
            entries.add(new Entry(0, 0)); // Add new entries to the separate list
        }
        dataSet = new LineDataSet(entries , "Audio Data");
         lineData = new LineData(dataSet);

        drawChart.StartData(lineData);

    }

    public void startRecordNote(FrequencyGetter frequencyGetter) {
        soundFrequencyAnalyzer.setFrequencyGetter(frequencyGetter);
        soundFrequencyAnalyzer.startRecord();
    }
    @Override
    public void sendData(short[] audioData) {
        double[] data = math.Fourier(audioData);

        for (int i = 0; i < data.length; i++) {
            entries.set(i,new Entry((float) soundFrequencyAnalyzer.indexToFruquency(i), (float) data[i])); // Add new entries to the separate list
        }
        String note = soundFrequencyAnalyzer.CalculeteNote(data);
        drawChart.UpdateData(note);



    }
}
