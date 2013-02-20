package com.example.audioapp;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;

/**
 * Need math to figure out how long a period is and loop it.  
 * track.setloopPoints() give it first sample and length of the period
 * Have the play be looped until an onUp then pause and release the AudioTrack.
 * Possibly figure out a way to ramp down the sample amplitude at the onUp
 *  
 * 
 * 
 * @author mstanford
 *
 */

public class MainActivity extends Activity {

	//How long the tone is for
    private final int duration = 10; 
    //Sample rate.
    private final int sampleRate = 8000;
    //Samples will be duration * samples per sec
    private final int numSamples = duration * sampleRate;
    //The freq of the tone.  
    private double freqOfTone = 440; // hz
    //create an array that can hold how many samples we need
    double sample[] = new double[(int) numSamples];
    AudioTrack audioTrack;
    /**
     * PCM needs to be bytes.  16-bit PCM will be 2 bytes ber sample.
     * So take the num samples, slap a *2 on it and your good
     */
    byte generatedSnd[] = new byte[2 * numSamples + 100];
    
    //
    boolean touchDown = false;
    
    /**
     * ONCREATE
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        //Grab the view for the listener
        View mainView = (View) findViewById(R.id.view);
        
        /**
         * Create a gesture detector. Contruct with context and a gesture
         * listener.
         */
        final GestureDetector gdt = new GestureDetector(this, new GestureListener());

        mainView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                	freqOfTone = event.getY();
                	touchDown = true;
                	playthatfunkymusic();
                    return true;                    
                  } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                	touchDown = false;
                    return true;
                  }
                gdt.onTouchEvent(event);
                return true;
            }

        });
    }
    
    //This wraps the functions in a new thread.
    public void playthatfunkymusic(){
        final Thread thread = new Thread(new Runnable() {
            public void run() {
                genTone();
            }
        });
        thread.start();
    }
    
    /**
     * This bad boy over herr fills up an array with floats.
     * Really simple stuff.
     */
    void genTone(){   
        //Ramp time is the denominator of the total sample.
        //This really should be a ration of samples per cycle.
        //do this math to get a smooth sound
        int rampTime = 2000;
        //Fill the sample array.
        for (int i = 0; i < numSamples; ++i) {
        	//Here is the brains of the operation.  Don't axe me what it does
        	//But it pretty much unrolls a sin curve into a wave
            sample[i] = Math.sin(freqOfTone * 2 * Math.PI * i / (sampleRate));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalized.
        int PCMindex = 0;
        int i;
        //The ramp.  It is how many samples will be ramped up.
        int ramp = numSamples/rampTime;                           


        /**
         * Ramp up the tone to avoid clicks
         */
        for (i = 0; i< ramp; ++i) {      
        	//Grab the values from the sample array for each i
            double dVal = sample[i];
            //Ramp down the value.  
            //Convert a -1.0 - 1.0 double into a short. 32767 should be contant for max 16 bit signed int value  
            final short val = (short) ((dVal * 32767 * i/ramp));
            //Put the value into the PCM byte array
            //Break the short into a byte using a bit mask. 
            //First bit mask adds the second byte.  Second bit mask adds the second byte.
            generatedSnd[PCMindex++] = (byte) (val & 0x00ff);
            generatedSnd[PCMindex++] = (byte) ((val & 0xff00) >>> 8);
        }

        for (i = ramp; i< numSamples - ramp; ++i) {            
        	//Grab the values from the sample array for each i
            double dVal = sample[i];
            //Convert a -1.0 - 1.0 double into a short. 32767 should be contant for max 16 bit signed int value
            final short val = (short) ((dVal * 32767));
            //Put the value into the PCM byte array
            //Break the short into a byte using a bit mask. 
            //First bit mask adds the second byte.  Second bit mask adds the second byte.
            generatedSnd[PCMindex++] = (byte) (val & 0x00ff);
            generatedSnd[PCMindex++] = (byte) ((val & 0xff00) >>> 8);
        }

        for (i = (int) (numSamples - ramp); i< numSamples; ++i) {
        	//Grab the values from the sample array for each i
            double dVal = sample[i];
            //Convert a -1.0 - 1.0 double into a short. 32767 should be contant for max 16 bit signed int value 
            //Ramp down the values
            final short val = (short) ((dVal * 32767 * (numSamples-i)/ramp ));
            //Put the value into the PCM byte array
            //Break the short into a byte using a bit mask. 
            //First bit mask adds the second byte.  Second bit mask adds the second byte.
            generatedSnd[PCMindex++] = (byte) (val & 0x00ff);
            generatedSnd[PCMindex++] = (byte) ((val & 0xff00) >>> 8);
        }
        
        //Try to play it, throws an excepetion if somehting happens
        try {
        	//Put the stream type, the sample rate, the channe setup, the encoding, the number of bytes(for us its generatedsnd.length but can use samples*2) and the audiotrack mode
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,sampleRate,AudioFormat.CHANNEL_OUT_MONO,AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,AudioTrack.MODE_STATIC);
            //Write the PCM to the audioTrack
            audioTrack.write(generatedSnd, 0, generatedSnd.length);
            //Play the Track
        	audioTrack.play(); 
            while(touchDown){ 
            }
            audioTrack.release();
        }
        catch (Exception e){
        }
    }
     
    /**
     * empty cuz I figured a more better way of done doing it.
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
