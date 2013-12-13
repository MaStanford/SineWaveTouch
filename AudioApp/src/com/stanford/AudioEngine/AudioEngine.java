package com.stanford.AudioEngine;

import java.util.ArrayList;
import android.media.AudioTrack;


/**
 * AudioEngine creates an api for generating tones.
 * Create an AudioEngine object then initialize it. 
 * The engine can be expanded for polyphony and other waveforms.
 * 
 * 
 * @author mstanford
 *
 */

public class AudioEngine {
	
	final int SIN_WAVE = 1;
	final int SQUARE_WAVE = 2;
	final int SAW_WAVE = 3;
	
	//The wave array 
	ArrayList<Wave> waveArray;
	//Set a constant for that.
	int waveType = SIN_WAVE;
	//Create an audioTrack
	AudioTrack audioTrack;
	
	/**
	 * No constructor needed yet
	 */
	public AudioEngine(){
	}

	/**
	 * Initialize the Audio Engine. 
	 * Creates the Array of Waves.  Returns true if successfull.
	 * @return
	 */
	public boolean InitAudioEngine(){

		waveArray = new WaveArrayBuilder().generateWaveList(SIN_WAVE);
		return true;
	}

	/**
	 * Returns the list of waves generated by initAudioEngine
	 * @return 
	 */
	public ArrayList<Wave> getWaveArray() {
		return waveArray;
	}

	/**
	 * Sets the array of waves in case you want to set it yourself
	 * 
	 * @param waveArray
	 */
	public void setWaveArray(ArrayList<Wave> waveArray) {
		this.waveArray = waveArray;
	}

	/**
	 * Returns the type of wave currently being processed.
	 * @return
	 */
	public int getWaveType() {
		return waveType;
	}

	/**
	 * sets the wave type and remakes the array of waves.  
	 * @param waveType
	 */
	public void setWaveType(int waveType) {
		this.waveType = waveType;
		this.InitAudioEngine();
	}	
	
	/**
	 * Play a wave with the given frequency
	 * The boolean is used for the setTouchDown on the genTone in Wave class
	 * The int is the wave to play in the array of waves
	 * @param freq
	 * @param b
	 * @param i
	 * @return
	 */
	public boolean playWave(float freq,boolean b,int i){
		waveArray.get(i).setFreqOfTone(freq);
		waveArray.get(i).setTouchDown(b);
		new ThreadWrapper(waveArray.get(i)).run();
		return true;
	}
	
	public boolean stopWave(boolean b,int i){
		waveArray.get(i).setTouchDown(b);
		return true;
	}
	
}
