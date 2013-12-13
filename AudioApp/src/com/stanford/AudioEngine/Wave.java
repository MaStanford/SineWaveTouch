package com.stanford.AudioEngine;

public abstract class Wave {
	
    //The touchdown bool.
    protected boolean touchDown = false;
	//How long the tone is for
    protected int duration = CONSTANTS.DURATION; 
    //Sample rate.
    protected int sampleRate = CONSTANTS.SAMPLERATE;
    //Samples will be duration * samples per sec
    protected int numSamples = CONSTANTS.NUMBER_SAMPLES;
    //The freq of the tone.  
    protected float freqOfTone; // hz
    //create an array that can hold how many samples we need
    protected double sample[] = new double[numSamples];
    
    /**
     * PCM needs to be bytes.  16-bit PCM will be 2 bytes ber sample.
     * So take the num samples, slap a *2 on it and your good
     */
    protected byte PcmArray[] = new byte[2 * numSamples];
        
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
	
	/*
	 * Creates the PCM array to be sent into PlayPCM
	 * Also need to pass the audioTrack through.
	 */
	public byte[] genTone() {
		return PcmArray;
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
	public double[] getSample() {
		return sample;
	}
	public void setSample(double[] sample) {
		this.sample = sample;
	}
}
