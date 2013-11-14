package com.stanford.AudioEngine;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class PlayPCM {

	AudioTrack audioTrack;
	
	PlayPCM(){
		
	}
	
	/**
	 * Plays the PCM array it is given and listens to the touchDown
	 * of the Wave passed to it.  
	 * 
	 * Get the PCM array from the wave.gentone.
	 * 
	 * ex. play(wave.gentone,wave);
	 * 
	 * @param PcmArray
	 * @param wave
	 */
	public void play(byte[] PcmArray,Wave wave){
		
		/**
		 * Put the stream type, the sample rate, the channe setup, the encoding, 
		 * the number of bytes(for us its generatedsnd.length but can use samples*2) 
		 * and the audiotrack mode
		 */
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,CONSTANTS.SAMPLERATE,
        							AudioFormat.CHANNEL_OUT_MONO,AudioFormat.ENCODING_PCM_16BIT, 
        							(CONSTANTS.NUMBER_SAMPLES*2),AudioTrack.MODE_STATIC);
        
        //Try to play it, throws an excepetion if somehting happens
        try {
            audioTrack.write(PcmArray, 0, PcmArray.length);
            //Play the Track
        	audioTrack.play(); 
        	//Keep it smooth
            while(wave.touchDown){ 
            }
            //Release the audiotrack when the touch is up
            audioTrack.pause();
            audioTrack.release();
        }
        catch (Exception e){
        }
	}
	
}
