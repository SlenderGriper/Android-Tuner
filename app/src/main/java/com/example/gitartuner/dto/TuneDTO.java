package com.example.gitartuner.dto;

 public class TuneDTO {
  private String[]  currentNote;

  private String[]  wantedNote;
  private int lenght;
  private int selected;
  public TuneDTO(int lenght){
   this.lenght=lenght;
   currentNote=new String[lenght];
   wantedNote=new String[lenght];
   selected=-1;
  }
  public String getCurrent(int index){ return currentNote[index];}
  public String getWanted(int index){ return wantedNote[index];}
  public void setCurrent(int index,String value){ currentNote[index]=value;}
  public void setWanted(int index,String value){  wantedNote[index]=value;}
  public int getLenght(){return lenght;}
  public int getSelected(){return selected;}
  public void setSelected(int value){selected=value;}
  public void resetSelected(){selected=-1;}


 }
