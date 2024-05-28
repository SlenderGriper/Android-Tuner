package com.example.gitartuner.dto;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Guitar")
public class GuitarDto {
    public GuitarDto(){

    }

    public GuitarDto(int id, String name, int stringsCount) {
        this.id = id;
        this.name = name;
        this.stringsCount = stringsCount;
    }

    @PrimaryKey(autoGenerate = true)
    public  int id;
    public  String name;
    public int stringsCount;

    public GuitarDto(String name, int stringsCount) {
        this.name=name;
        this.stringsCount=stringsCount;
    }


}
