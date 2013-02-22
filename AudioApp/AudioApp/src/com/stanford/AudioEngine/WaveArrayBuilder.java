package com.stanford.AudioEngine;

import java.util.ArrayList;

public class WaveArrayBuilder {
	
	WaveArrayBuilder(){

	}
	
	//create an arraylist of waves
	ArrayList<Wave> waveArray = new ArrayList<Wave>();
	
	/**
	 * Generate a list of waves for polyphony.
	 * The polyphony final is the limiting variable on size.
	 */
	ArrayList<Wave> generateWaveList(int i){
		switch(i){
		//Sin Wave was selected
		case 1:
			for(int j = 0; j < CONSTANTS.POLYPHONY;j++){
				waveArray.add(new SinWave());
			}
			break;
		default:
			break;
		}
		return waveArray;
	}
}
