package com.fortune.nn;

import java.io.File;

import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;
import org.encog.util.arrayutil.NormalizeArray;

public class NormaliseInputs {
	
	
	

	    public static  double[] testNormalize()
	    {
	        NormalizeArray norm = new NormalizeArray();
	        norm.setNormalizedHigh(1);
	        norm.setNormalizedLow(0);
	        double[] input = { 2,0,3,63,1,0,1,1 };
	        double[] output = norm.process(input);
	        
	        return output;
	    
	    }
	    
	    
	    
	    
	 public static void main(String args[]) {
		// TODO Auto-generated method stub
		 BasicNetwork network = (BasicNetwork)EncogDirectoryPersistence.loadObject(new File(Utilities.pathToEncogPersistance()));
		 
		 	double[] output = new double[1];
		    network.compute(new double[]{1,1,0.3333333333333333,0.2328767123287671,1,1,1,1}, output);
		    System.out.println("Network output: " + output[0]);

	}

}
