package com.example.audioapp;

public class ThreadWrapper {
	
	SinWave sinWave = new SinWave();
	
	ThreadWrapper(SinWave wave){
		this.sinWave = wave;
	}
	
	
	public ThreadWrapper(Wave wave) {
		this.sinWave = (SinWave) wave;
	}


	//This wraps the functions in a new thread.
	public void run(){
		final Thread thread = new Thread(new Runnable() {
			public void run() {
					sinWave.genTone();
			}
		});
		thread.start();
	}

}
