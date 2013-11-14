package com.stanford.AudioEngine;

public class SinWave extends Wave{

	/**
	 * Generates a PCM array then returns it
	 * This array needs to fill up the whole PcmArray 
     */
    public byte[] genTone(){   
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
            PcmArray[PCMindex++] = (byte) (val & 0x00ff);
            PcmArray[PCMindex++] = (byte) ((val & 0xff00) >>> 8);
        }

        for (i = ramp; i< numSamples - ramp; ++i) {            
        	//Grab the values from the sample array for each i
            double dVal = sample[i];
            //Convert a -1.0 - 1.0 double into a short. 32767 should be contant for max 16 bit signed int value
            final short val = (short) ((dVal * 32767));
            //Put the value into the PCM byte array
            //Break the short into a byte using a bit mask. 
            //First bit mask adds the second byte.  Second bit mask adds the second byte.
            PcmArray[PCMindex++] = (byte) (val & 0x00ff);
            PcmArray[PCMindex++] = (byte) ((val & 0xff00) >>> 8);
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
            PcmArray[PCMindex++] = (byte) (val & 0x00ff);
            PcmArray[PCMindex++] = (byte) ((val & 0xff00) >>> 8);
        }
        return PcmArray;
    }

}
