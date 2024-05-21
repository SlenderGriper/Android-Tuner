package com.example.gitartuner.model;

import android.app.Activity;

import com.example.gitartuner.model.inteface.FrequencyGetter;

public class SoundFrequencyAnalyzer {
   private int sampleRate;
    private int numSamples;
    private float maxAmplitude;
    private float noiseAmplitude;
    private float MinSoundAmplitude=2;
    private float checkSoundCount=5;
    private int indexNote;
    private int count;
    private boolean record;
    private FrequencyGetter frequencyGetter;
    private Activity context;


   public SoundFrequencyAnalyzer(int sampleRate, int numSamples){
        this.sampleRate=sampleRate;
        this.numSamples=numSamples;
       record=false;
    }
    public String CalculeteNote(double[] data){
        int index=maxAmplitudeToIndex(data);
        double fr=indexToFruquency(index);
        if(maxAmplitude<MinSoundAmplitude)return "";
        if(record) recordNote(index);
        NoteCalculator noteCalculator=new NoteCalculator();

        String note = noteCalculator.noteIdToNote(noteCalculator.frequencyToNoteId(fr));
        return note+" "+fr;
    }
    private void recordNote(int index){
        if(indexNote==index)
            count++;
        else {
            indexNote=index;
            count=0;
        }

        if(count>=checkSoundCount) {
            record = false;
            getRecordFrequency();
        }

    }
    public void setFrequencyGetter(FrequencyGetter frequencyGetter){
       this.frequencyGetter=frequencyGetter;
    }
    public void startRecord(){
        stopRecord();
       if(frequencyGetter!=null) record=true;
    }
    private void stopRecord(){
        count=0;
        indexNote=-1;
        record=true;
    }
    public void getRecordFrequency(){
       if(record)return ;

       double frequency=indexToFruquency(indexNote);


       frequencyGetter.getFrequency(frequency);
    }

    public int maxAmplitudeToIndex(double[] result){
         maxAmplitude = 0;
        int maxIndex = 0;
        for (int i = 0; i < result.length / 2; i++)
        {
            if (result[i] > maxAmplitude)
            {
                maxAmplitude = (float) result[i];
                maxIndex = i;

            }
        }
        return maxIndex;
    }
    public double indexToFruquency(int maxIndex){
        return (double)maxIndex * sampleRate / numSamples;
    }



}
