package com.fortune.nn;

import java.io.File;

import org.encog.Encog;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.propagation.resilient.RPROPType;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.EncogDirectoryPersistence;

public class TestNetworks {
	
	
	
	public static void trainRProp(){
		BasicNetwork network = NetworkUtilities.getNetwork();
		MLDataSet trainingData = NetworkUtilities.getTrainingDataSet();
		ResilientPropagation rProp = new ResilientPropagation(network,  trainingData);
		rProp.setRPROPType(RPROPType.iRPROPp);
		double prop = NetworkUtilities.startPROP(network, trainingData, Boolean.FALSE);
		NetworkUtilities.pauseTraining(network, trainingData);
		NetworkUtilities.continueTraining(network, trainingData);
	
		System.out.println("evaluation :" + prop);
		
		NetworkUtilities.printResults(network, trainingData);
		
		
		System.out.println("****************************************************************************");
		System.out.println("********************************************************************************");
		System.out.println("Saving Network");


		    EncogDirectoryPersistence.saveObject(new File(Utilities.pathToEncogPersistance()), network);

		Encog.getInstance().shutdown();
		
	}
	
	
	public static void traingMProp(){
		BasicNetwork network = NetworkUtilities.getNetwork();
		MLDataSet trainingData = NetworkUtilities.getTrainingDataSet();
		double mprop = NetworkUtilities.startPROP(network,trainingData, Boolean.TRUE);
		NetworkUtilities.pauseTraining(network, trainingData);
		NetworkUtilities.continueTraining(network, trainingData);
	
		System.out.println("evaluation :" + mprop);

		NetworkUtilities.printResults(network, trainingData);
		
		Encog.getInstance().shutdown();
		
	}

	public static void main(String[] args) {
		traingMProp();
		trainRProp();

	}

}
