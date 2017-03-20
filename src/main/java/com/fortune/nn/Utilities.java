package com.fortune.nn;

public class Utilities {
	
	public static String MYDIR = "src/main/resources/";

	
	public static String getUnormalisedTrainingDataPath(){
		
		 return MYDIR.toString()+ "/" + "iris.csv";
		
	}
	

	public static String getNormalisedTrainingDataPath(){
		
		
		return MYDIR.toString()+ "/" + "iris_normalized.csv";
	}
	
	
	public static String pathToSaveNnOnPause(){
		
		return MYDIR +"/" +"pausedNN";
	}
	
public static String pathToSaveNnOnFinish(){
		
		return MYDIR +"/" +"finalNN";
	}
	
	
	


	
	
}
