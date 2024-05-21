package com.example.gitartuner.dto;

public class NoteStorage {
    public NoteDto[] notes;
    private int lenght;

    public NoteStorage(int lenght) {
        this.lenght=lenght;
        this.notes = new NoteDto[lenght];
        for (int i=0;i<lenght;i++){
            notes[i]=new NoteDto(0,0);
        }
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
    public int getLenght(){return lenght;}
    public void addNoteDto(NoteDto note,int index){notes[index]=new NoteDto(note.note,note.octave);}
    public void update(NoteStorage items){
        for(int i=0;i<lenght;i++)addNoteDto(items.notes[i],i);
    }
}
