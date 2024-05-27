package com.example.gitartuner.model;

import android.util.Log;

import java.util.ConcurrentModificationException;

public class TunerMath {
    private int sampleRate;
    private int len;
    private int order=2;
    private double cutoffFrequencyLow=60;
    private double cutoffFrequencyHigh=2000;
    public TunerMath(){
        sampleRate=44100;

    }
    public TunerMath(int sampleRate){
        this.sampleRate=sampleRate;
         cutoffFrequencyHigh=2000;
         cutoffFrequencyLow=60;
         order=4;
    }
            //фильтр баттерворта
    private double[] filterBand(double[] spectrum){
        int binWidth = sampleRate / len; // Hz
        double[] filterFrequency=new double[len];
        for(int i=0;i<(len);i++){
            int binFreq = binWidth * i;
            double gain = 1-1 / ( Math.sqrt( ( 1 +
                    Math.pow( binFreq / cutoffFrequencyLow, 2.0 * order ) ) ) );
            gain*= 1 / ( Math.sqrt( ( 1 +
                    Math.pow( binFreq / cutoffFrequencyHigh, 2.0 * order ) ) ) );
            filterFrequency[i] = spectrum[i]*gain;
        }
        return filterFrequency;
    }
    private double[] hammingWindow(int N) {
        double[] window = new double[N];

        for (int i = 0; i < N; i++) {
            window[i] = 1 - Math.abs((i - (N-1)/2) / ((N+1)/2));
        }
        return window;
    }
    private double[] blackmanNuttallWindow(int windowSize) {
        double[] window = new double[windowSize];
        double a0 = 0.35875;
        double a1 = 0.48829;
        double a2 = 0.14128;
        double a3 = 0.01168;
        for (int i = 0; i < windowSize; i++) {

            window[i] = a0
                        - a1 * Math.cos((2 * Math.PI * i) / (windowSize - 1))
                    + a2 * Math.cos((4 * Math.PI * i) / (windowSize - 1))
                    - a3 * Math.cos((6 * Math.PI * i) / (windowSize - 1));

        }
        return window;
    }

    public double[] Fourier(short[] wave){

        len=wave.length;
        double[] window=blackmanNuttallWindow   (len);
        Complex[] waveComplex=new Complex[len];

        for(int i=0;i< len;i++){
            waveComplex[i]=new Complex( ((double)wave[i]/Short.MAX_VALUE)*window[i],0);
        }

        Complex[] resultComplex=CooleyTukeyFFT(waveComplex);


        double[] result=new double[len];

        for(int i=0;i< len;i++){
            result[i]= resultComplex[i].Margin();
        }

        result=filterBand(result);
//       long maxi=Math.round(cutoffFrequencyLow*len/sampleRate);
//        for (int i=0;i<maxi;i++){
//            result[i]*= Math.sqrt(((double)i/maxi));
//        }

        return result;
    }

    private Complex[] CooleyTukeyFFT(Complex[] x)
    {
        int N = x.length;

        // Рекурсивное преобразование Фурье
        if (N == 1)
            return new Complex[] { x[0] };

        // Разделение массива x на четную и нечетную части
        Complex[] even = new Complex[N / 2];
        Complex[] odd = new Complex[N / 2];

        for (int i = 0; i < N / 2; i++)
        {
            even[i] = x[2 * i];
            odd[i] = x[2 * i + 1];
        }

        // Решение подзадачи с помощью первоначального алгоритма
        Complex[] evenResult = CooleyTukeyFFT(even);
        Complex[] oddResult = CooleyTukeyFFT(odd);

        // Объединение результатов подзадач
        Complex[] result = new Complex[N];
        for(int i = 0; i < N; i++)result[i]=new Complex();
        for (int i = 0; i < N / 2; i++)
        {
            double kth = -2 * i * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            result[i] = evenResult[i].Plus( wk.Times( oddResult[i]));
            result[i + N / 2] = evenResult[i].Minus( wk.Times( oddResult[i]));
        }

        return result;
    }

}
class Complex{
    private double _real;
    private double _imag;   // the imaginary part
    public double getReal(){
        return _real;
    }

    public Complex(){
        _real = 0;
        _imag = 0;
    }

    public Complex(double real, double imag) {
        _real = real;
        _imag = imag;
    }

    public Complex Plus(Complex b) {
        double real = _real + b._real;
        double imag = _imag + b._imag;
        return new Complex(real, imag);
    }

    public Complex Minus(Complex b) {
        double real = this._real - b._real;
        double imag = _imag - b._imag;
        return new Complex(real, imag);
    }

    public Complex Times(Complex b) {
        double real = this._real * b._real - _imag * b._imag;
        double imag = this._real * b._imag + _imag * b._real;
        return new Complex(real, imag);
    }
    public Complex Times(double b) {
        double real = this._real * b ;
        double imag =  _imag * b;
        return new Complex(real, imag);
    }

    public double Margin(){
       return Math.sqrt(_real * _real + _imag*_imag);
    }
}

