package com.example.gitartuner.model;

import com.example.gitartuner.dto.NoteDto;
import com.example.gitartuner.dto.NoteStorage;

public class NoteCalculator {
    public NoteDto frequencyToNoteId(double frequency)
    {
        double A4Frequency = 440.0;
        int A4MIDI = 69;
        int length=12;

        int note_number = (int) Math.round(length * (Math.log(frequency / A4Frequency) / Math.log(2)) + A4MIDI);
        int note = (note_number+3) % length;
        int octave = (note_number) /length;

        return new NoteDto(note,octave);
    }
    public String noteIdToNote( NoteDto noteDto){
        String[] noteNames = {   "A", "A#", "B","C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
        if(noteDto.octave-1<1)return "";
        return noteNames[noteDto.note]+""+(noteDto.octave-1);
    }
    public NoteStorage getStringNote(int typesId,int firstNote,int length){

        int[] steps=stepString(typesId,length);
        return stringNote(steps,firstNote,length);

    }
    private int[] stepString(int typesId,int length) {
        int[] steps=new int[length-1];
        steps[0]=5;
        steps[1]=4;
        for(int i=2;i<length-1;i++){
            steps[i]=5;
        }
            switch (typesId) {

                //standard
                case 0:
                    steps[length-2]=5;
                    return steps;
                //drop
                case 1:
                    steps[length-2]=7;
                    return steps;
            }
        return new int[length];
    }
    private   NoteStorage stringNote(int[] step,int firstNote,int length){

        NoteStorage noteStorage=new NoteStorage(length);

        NoteDto note=new NoteDto(firstNote,4);
        noteStorage.addNoteDto(note,0);
        for (int i=1;i<length;i++){
            note=noteMinus(note,step[i-1]);
            noteStorage.addNoteDto(note,i);
        }
        return noteStorage;



    }
    private NoteDto noteMinus(NoteDto note,int deductible){
        int noteId=note.note-deductible;
        int octave=note.octave;
        if (noteId<0){
            noteId+=12;
            octave-=1;
        }
        return new NoteDto(noteId,octave);
    }


}
