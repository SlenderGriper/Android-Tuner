package com.example.gitartuner.dto;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "Note",
        foreignKeys = @ForeignKey(entity = NoteStorage.class,
        parentColumns = "id",
        childColumns = "note_id",
        onDelete = CASCADE))
public class NoteDto {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public UUID note_id;
    public int note;
    public int octave;
    @Ignore
    public NoteDto(int note,int octave){
    this.note=note;
    this.octave=octave;
    }
    public NoteDto(int id,int note,int octave){
        this.id=id;
        this.note=note;
        this.octave=octave;
    }
}
