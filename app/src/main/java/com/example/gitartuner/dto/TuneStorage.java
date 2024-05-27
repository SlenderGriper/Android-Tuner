package com.example.gitartuner.dto;

import com.example.gitartuner.model.NoteCalculator;

public class TuneStorage {
  private NoteStorage  currentNote;
  private NoteStorage  wantedNote;
  private int lenght;
  private NoteCalculator noteCalculator;

  private int selected;
  public TuneStorage(int lenght){
   this.lenght=lenght;
   currentNote=new NoteStorage(lenght);
   wantedNote=new NoteStorage(lenght);
   selected=-1;

   noteCalculator=new NoteCalculator();
  }
  public String getStringCurrent(int index){ return noteCalculator.noteIdToNote(currentNote.getNoteDto(index));}
  public String getStringWanted(int index){ return noteCalculator.noteIdToNote(wantedNote.getNoteDto(index));}
  public void setCurrent(int index,NoteDto value){
      currentNote.setNotes(index, value.note);
      currentNote.setOctava(index, value.octave);
  }
  public void setWanted(int index,NoteDto value){
      wantedNote.setNotes(index, value.note);
      wantedNote.setOctava(index, value.octave);
  }
  public void setWantedStorage(NoteStorage noteStorage){
      wantedNote=new NoteStorage(lenght);
      NoteDto item;
      for(int i=0;i<lenght;i++){
          item= noteStorage.getNoteDto(i);
          item=noteCorrector(item,12+3);
          wantedNote.addNoteDto(item,i);
      }
  }
    public NoteDto noteCorrector(NoteDto note,int deductible){
        int noteId=note.note+deductible;
        int octave=note.octave;

        while (noteId>=12){
            noteId-=12;
            octave+=1;
        }
        return new NoteDto(noteId,octave);
    }
    public  String getMassageArduino(int index){
      int current= currentNote.getNotes(index)+currentNote.getOctava(index)*12;
      int wanted= wantedNote.getNotes(index)+wantedNote.getOctava(index)*12;
      String dir;
      int sum=wanted-current;
       sum*=10;
      if(sum>0)dir="0 ";
      else {sum=-sum;dir=" 1 ";}
      return dir+Integer.toString(sum)+" ";
    }
  public int getLenght(){return lenght;}
  public int getSelected(){return selected;}
  public void setSelected(int value){selected=value;}
  public void resetSelected(){selected=-1;}


 }
