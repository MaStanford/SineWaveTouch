package com.example.audioapp;

import java.util.ArrayList;

import com.example.audioapp.R.id;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
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
	
	
	//Set the polyphony
	final static int polyphony = 2;
	
	//The wave button- needs to be added to the UI
	RadioButton btnWave = new RadioButton(this);
	
	//create an arraylist of sinwaves
	ArrayList<Wave> waveArray = new ArrayList<Wave>();

	/**
	 * Generate a list of waves for polyphony.
	 * The polyphony final is the limiting variable on size.
	 */
	void generateWaveList(int i){
		switch(i){
		//Sin Wave was selected
		case 1:
			for(int j = 0; i < polyphony;i++){
				waveArray.add(new SinWave());
			}
			break;
		default:
			break;
		}
	}


	/**
	 * ONCREATE
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Grab the buttons
		btnWave = (RadioButton) findViewById(id.btn_sinWave);
		btnWave.isChecked();
		//Grab the view for the listener
		View mainView = (View) findViewById(R.id.view);
		//Generate a list of waves for polyphony reasons
		generateWaveList(1);

		/**
		 * Create a gesture detector. Contruct with context and a gesture
		 * listener.
		 */
		final GestureDetector gdt = new GestureDetector(this, new GestureListener());
		mainView.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				
				if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
					waveArray.get(1).setFreqOfTone(event.getY()*2);
					waveArray.get(1).setTouchDown(true);
					//Plays the note
					new SinThreadWrapper((SinWave) waveArray.get(1)).playthatfunkymusic();
					return true;                    
				} else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
					waveArray.get(1).setTouchDown(false);
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
