package com.example.gitartuner.model;

import android.app.Activity;
import android.widget.TextView;

import com.example.gitartuner.model.inteface.DrawChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

public class DrawRecord implements DrawChart {
    private Activity activity;
    private LineChart lineChart;
    private TextView noteText;
    public DrawRecord(Activity activity, LineChart lineChart, TextView noteText) {
        this.activity = activity;
        this.lineChart = lineChart;
        this.noteText=noteText;

    }
    @Override
    public void StartData(LineData lineData) {
        lineChart.setData(lineData);
    }

    @Override
    public void UpdateData(String note) {
        activity.runOnUiThread(() -> {


            synchronized (lineChart) {
                lineChart.notify();
                lineChart.invalidate();
            }

            noteText.setText(note);

        });
    }
}
