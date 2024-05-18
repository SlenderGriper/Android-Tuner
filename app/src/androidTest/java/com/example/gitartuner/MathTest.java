package com.example.gitartuner;

import static org.junit.Assert.assertEquals;

import com.example.gitartuner.model.SoundFrequencyAnalyzer;
import com.example.gitartuner.model.TunerMath;

import org.junit.Test;

public class MathTest {
    @Test
    public void MathTest() {
        int sampleRate = 44100; // Sample rate in Hz
        int numSamples = sampleRate*1; // Number of samples
        short[] samples = new short[numSamples]; // Array of samples

        // Generating some example samples (e.g. sine wave)
        for (int i = 0; i < numSamples; i++)
        {
            double time = (double)i / sampleRate;
            samples[i] =(short) (Math.sin(2 * Math.PI * 440 * time)*Short.MAX_VALUE); // 440 Hz sine wave
        }

        TunerMath math=new TunerMath();
        double[] result=math.Fourier(samples);

        SoundFrequencyAnalyzer soundFrequencyAnalyzer=new SoundFrequencyAnalyzer(sampleRate,numSamples);
        int maxIndex = soundFrequencyAnalyzer.maxAmplitudeToIndex(result);
        double frequency = soundFrequencyAnalyzer.indexToFruquency(maxIndex) ;
        assertEquals(soundFrequencyAnalyzer.FrequencyToNote(frequency) , "A4");
    }
}
