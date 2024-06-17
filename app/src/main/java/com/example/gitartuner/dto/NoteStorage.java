package com.example.gitartuner.dto;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.UUID;


@Entity(tableName = "GuitarTuning")
public class NoteStorage implements Serializable {
    @PrimaryKey
    @NonNull
    public UUID id;
    private int length;
    private String name;


    @Ignore
    public NoteDto[] notes;

    public void setLength(int length) {
        this.length = length;
    }

    public NoteStorage() {
        id=UUID.randomUUID();
    }

    public NoteStorage(int length, String name) {
        this.length = length;
        this.name = name;
        id=UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public NoteStorage(int length) {
        this.length =length;
        this.notes = new NoteDto[length];
        for (int i=0;i<length;i++){
            notes[i]=new NoteDto(0,0);
        }
        id=UUID.randomUUID();
    }
    public NoteStorage(NoteStorage noteStorage) {
        id = noteStorage.id;
        length = noteStorage.length;
        name = noteStorage.name;
        this.notes = new NoteDto[length];
        for (int i=0;i<length;i++){
            notes[i]=new NoteDto(noteStorage.notes[i].note,noteStorage.notes[i].octave);
        }
    }

    public void setNoteDto(NoteDto[] notes) {
        this.notes = notes;
    }
    public int getNotes(int index) {
        return notes[index].note;
    }

    public void setNotes(int index,int notes) {
        this.notes[index].note = notes;
    }
    public NoteDto getNoteDto(int index){return new NoteDto(notes[index].note,notes[index].octave);}

    public int getOctava(int index) {
        return notes[index].octave;
    }

    public void setOctava(int index,int octava) {
        notes[index].octave = octava;
    }
    public int getLength(){return length;}
    public void addNoteDto(NoteDto note,int index){notes[index]=new NoteDto(note.note,note.octave);}
    public void update(NoteStorage items){
        for(int i = 0; i< length; i++)addNoteDto(items.notes[i],i);
    }
}
