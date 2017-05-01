package com.fortune.nn;




public class Utilities {

	 
	 public static String CSVDIR = "upload";

	public static String NNDIR = "src/main/resources/";

	public static String filename = "unormalisedTrainingData.csv";
	public static String normalisedFileName = "normalisedTrainingData.csv";

	public static String getUnormalisedTrainingDataPath() {
		

		return CSVDIR + "/" + "unormalisedTrainingData.csv";

	}

	public static String getNormalisedTrainingDataPath() {
		
		
	return CSVDIR.toString() + "/" + "normalizedTrainingData.csv";
	}

	public static String pathToSaveNnOnPause() {

		return NNDIR + "/" + "partialTrainedNeuralNetwork";
	}

	public static String pathToSaveNnOnFinish() {

		return NNDIR + "/" + "trainedNeuralNetwork";
	}
	
	public static String pathToEncogPersistance() {

		return NNDIR + "/" + "trainedNet";
	}

}
