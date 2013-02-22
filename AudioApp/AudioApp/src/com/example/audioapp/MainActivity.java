package com.example.audioapp;

import com.example.audioapp.R.id;
import com.stanford.AudioEngine.AudioEngine;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.RadioButton;

/**
 * Need math to figure out how long a period is and loop it.  
 * track.setloopPoints() give it first sample and length of the period
 * Have the play be looped until an onUp then pause and release the AudioTrack.
 * Possibly figure out a way to ramp down the sample amplitude at the onUp
 * 
 * 
 * @author mstanford
 *
 */

public class MainActivity extends Activity {
	
	//The wave button- needs to be added to the UI
	RadioButton btnWave;
		
	//Create the audio engine
	AudioEngine ae = new AudioEngine();

	/**
	 * ONCREATE
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Grab the buttons
		btnWave = (RadioButton) findViewById(id.btn_sinWave);
		//Grab the view for the listener
		View mainView = (View) findViewById(R.id.view);
		//Init the audio engine
		ae.InitAudioEngine();

		/**
		 * Create a gesture detector. Contruct with context and a gesture
		 * listener.
		 */
		final GestureDetector gdt = new GestureDetector(this, new GestureListener());
		mainView.setOnTouchListener(new OnTouchListener() {
			//OnTouch method needs to be implemented.
			public boolean onTouch(View v, MotionEvent event) {
				//Grab the event and do the logic right here, no need to do it in the gesture listener.  
				if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
					ae.playWave(event.getY()*2,true,1);
					return true;                    
				} else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
					ae.stopWave(false,1);
					return true;
				}
				gdt.onTouchEvent(event);
				return true;
			}
		});
	}

	/**
	 * Gesture Detector needs a listener.  
	 */
	private class GestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
