package com.example.audioapp;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class SinWave extends Wave{

    /**
     * This bad boy over herr fills up an array with floats.
     * Really simple stuff.
     */
    public void genTone(){   
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

	public double getFreqOfTone() {
		return freqOfTone;
	}

	public void setFreqOfTone(double freqOfTone) {
		this.freqOfTone = freqOfTone;
	}

	public boolean isTouchDown() {
		return touchDown;
	}

	public void setTouchDown(boolean touchDown) {
		this.touchDown = touchDown;
	}

}
