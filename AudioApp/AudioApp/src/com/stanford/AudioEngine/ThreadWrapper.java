package com.stanford.AudioEngine;

public class ThreadWrapper {
	
	Wave wave;
	
	/**
	 * Send in a wave that already has its feilds populated
	 * @param wave
	 * @param audioTrack
	 */
	ThreadWrapper(SinWave wave){
		this.wave = wave;
	}
	
	/**
	 * Send in a wave that already has its fields populated
	 * @param wave
	 * @param audioTrack
	 */
	public ThreadWrapper(Wave wave) {
		this.wave = (SinWave) wave;
	}


	/**
	 * Runs the genTone of the specific wave you want to run.
	 * Overwrite the waves genTone to create a new wave form.
	 */
	public void run(){
		final Thread thread = new Thread(new Runnable() {
			public void run() {
				//Create a new play object
				PlayPCM tone = new PlayPCM();
				//Feed the tone object a PCM array and a wave
				tone.play(wave.genTone(), wave);
			}
		});
		thread.start();
	}

}
