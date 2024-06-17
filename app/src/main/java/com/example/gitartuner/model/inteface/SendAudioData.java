package com.example.gitartuner.model.inteface;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

public interface SendAudioData {
     void sendData(short[] entries);
}
