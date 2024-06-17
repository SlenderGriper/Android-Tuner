package com.example.gitartuner.dto;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Guitar")
public class GuitarDto implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public  int id;
    public  String name;
    public int stringsCount;
    public GuitarDto(){

    }

    public GuitarDto(int id, String name, int stringsCount) {
        this.id = id;
        this.name = name;
        this.stringsCount = stringsCount;
    }



    public GuitarDto(String name, int stringsCount) {
        this.name=name;
        this.stringsCount=stringsCount;
    }


}
