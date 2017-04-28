package com.fortune.nn;

import java.io.File;
import java.util.Arrays;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.TrainingContinuation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.util.csv.CSVFormat;
import org.encog.util.obj.SerializeObject;
import org.encog.util.simple.EncogUtility;

public class NetworkUtilities {
	
	public static TrainingContinuation cont ;
	
	public static BasicNetwork getNetwork(){
		BasicNetwork network = new BasicNetwork();
		network.addLayer(new BasicLayer(null,true,8));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),true,60));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),true,50));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),true,20));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),false,1));
		network.getStructure().finalizeStructure();
		network.reset();
		
		return network;
		
	}
	
	
	public static MLDataSet getTrainingDataSet(){
		
		//NormaliseCsv normaliseCsv = new NormaliseCsv();
		NormaliseTrainingData normaliseCsv = new NormaliseTrainingData();
		normaliseCsv.doThings();
		
		MLDataSet trainingDataSet = EncogUtility.loadCSV2Memory(Utilities.getNormalisedTrainingDataPath(),
				getNetwork().getInputCount(), getNetwork().getOutputCount(), 
				true, CSVFormat.ENGLISH, false);

			
			
			return trainingDataSet;
		
	}
	
	
	public static void pauseTraining(BasicNetwork network, MLDataSet dataset){
		
		ResilientPropagation propagation = new ResilientPropagation(network, dataset);
		
		 cont = propagation.pause();
		
		System.out.println("**************getting last gradients and update values**********");
		System.out.println(Arrays.toString((double[])cont.getContents().get(ResilientPropagation.LAST_GRADIENTS)));
		System.out.println(Arrays.toString((double[])cont.getContents().get(ResilientPropagation.UPDATE_VALUES)));
		
		System.out.println("****************Saving current progress to file***********");
		
		try
		{
			SerializeObject.save(new File(Utilities.pathToSaveNnOnPause()), cont);
			cont = (TrainingContinuation)SerializeObject.load(new File(Utilities.pathToSaveNnOnPause()));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public  static void continueTraining(BasicNetwork network, MLDataSet dataset){
		System.out.println("********************continue training from previous network************");
		
		ResilientPropagation propagationContinue = new ResilientPropagation(network, dataset);
		
		
		propagationContinue.resume(cont);
		
		EncogUtility.trainToError(propagationContinue,0.10);
		
		System.out.println("**************getting last gradients and update values**********");
		System.out.println(Arrays.toString((double[])cont.getContents().get(ResilientPropagation.LAST_GRADIENTS)));
		System.out.println(Arrays.toString((double[])cont.getContents().get(ResilientPropagation.UPDATE_VALUES)));
		
		System.out.println("****************Saving final Updates to file***********");
		
		try
		{
			SerializeObject.save(new File(Utilities.pathToSaveNnOnFinish()), cont);
			cont = (TrainingContinuation)SerializeObject.load(new File(Utilities.pathToSaveNnOnFinish()));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
		
	}
	
	public void cancelTraining(){
		
		Encog.getInstance().shutdown();
	}
	
	public static double startPROP(BasicNetwork network, MLDataSet dataset, Boolean isMProp) {

		ResilientPropagation train = new ResilientPropagation(network, dataset);
		if(isMProp){
			System.out.println("****************training using MProp");
			// 0 - let encog decide according to machine numer of threads to use
			train.setThreadCount(0);
		}
		
		else{
			System.out.println("****************training using RProp");
			// 1 - using only i thread equivalent to rProp
			train.setThreadCount(1);
			
		}
		
		
		long start = System.currentTimeMillis();
		
		System.out.println(BasicNetwork.TAG_BEGIN_TRAINING);
		EncogUtility.trainToError(train, 0.10);
		//System.out.println("Final MPROP final error: " + network.getWeight(fromLayer, fromNeuron, toNeuron));
		System.out.println(BasicNetwork.TAG_END_TRAINING);

		

		long stop = System.currentTimeMillis();
		double diff = ((double) (stop - start)) / 1000.0;
		System.out.println("MPROP time taken to train:" + diff + " seconds.");
		System.out.println("Final MPROP final error: " + network.calculateError(dataset));
		System.out.println("*****weights: "+network.dumpWeights());
		return diff;
	}
	
	
	public static void printResults(BasicNetwork network, MLDataSet dataset){
		System.out.println("************************************NEURAL NETWORK RESULTS:****************************************");
		for(MLDataPair pair: dataset ) {
			final MLData output = network.compute(pair.getInput());
			System.out.println(pair.getInput().getData(0) + "," + pair.getInput().getData(1) + "," + pair.getInput().getData(2)
					+ "," + pair.getInput().getData(3) + "," + pair.getInput().getData(4) + "," + pair.getInput().getData(5)
					+ "," + pair.getInput().getData(6) + "," + pair.getInput().getData(7)
					+ ", actual=" + output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
		}
	}

}
