package com.example.gitartuner.dto;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

public class GuitarTuningDto implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int length;
    public String name;


    public GuitarTuningDto(int id,int lenght, String name) {
        this.length = lenght;
        this.name = name;
    }


    public GuitarTuningDto() {
    }
}
