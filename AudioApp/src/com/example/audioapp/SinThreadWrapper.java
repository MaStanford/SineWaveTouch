package com.example.audioapp;

public class SinThreadWrapper {
	
	SinWave wave = new SinWave();
	
	SinThreadWrapper(SinWave wave){
		this.wave = wave;
	}
	
	
	//This wraps the functions in a new thread.
	public void playthatfunkymusic(){
		final Thread thread = new Thread(new Runnable() {
			public void run() {
					wave.genTone();
			}
		});
		thread.start();
	}

}
