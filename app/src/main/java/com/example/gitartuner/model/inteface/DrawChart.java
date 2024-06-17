package com.example.gitartuner.model.inteface;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;

import java.util.List;

public interface DrawChart {
    void StartData(LineData entries);
    void UpdateData(String note);
}
