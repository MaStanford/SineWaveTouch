package com.example.audioapp;

import android.media.AudioTrack;

public class Wave {
	
    //The touchdown bool.
    protected boolean touchDown = false;
	//How long the tone is for
    protected int duration = 10; 
    //Sample rate.
    protected int sampleRate = 8000;
    //Samples will be duration * samples per sec
    protected int numSamples = duration * sampleRate;
    //The freq of the tone.  
    protected double freqOfTone = 440; // hz
    //create an array that can hold how many samples we need
    protected double sample[] = new double[(int) numSamples];
    //Audio track
    protected AudioTrack audioTrack = null;
    
    /**
     * PCM needs to be bytes.  16-bit PCM will be 2 bytes ber sample.
     * So take the num samples, slap a *2 on it and your good
     */
    protected byte generatedSnd[] = new byte[2 * numSamples + 100];
    
    
    /**
     * SETTER AND GETTERS
     * 
     */
	public void setFreqOfTone(float f) {
		this.freqOfTone = f;
	}
	public void setTouchDown(boolean b) {
		this.touchDown = b;
	}
	public void genTone() {
		return;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getSampleRate() {
		return sampleRate;
	}
	public void setSampleRate(int sampleRate) {
		this.sampleRate = sampleRate;
	}
	public int getNumSamples() {
		return numSamples;
	}
	public void setNumSamples(int numSamples) {
		this.numSamples = numSamples;
	}
	public double getFreqOfTone() {
		return freqOfTone;
	}
	public void setFreqOfTone(double freqOfTone) {
		this.freqOfTone = freqOfTone;
	}
	public double[] getSample() {
		return sample;
	}
	public void setSample(double[] sample) {
		this.sample = sample;
	}
	public AudioTrack getAudioTrack() {
		return audioTrack;
	}
	public void setAudioTrack(AudioTrack audioTrack) {
		this.audioTrack = audioTrack;
	}
}
