package com.fortune.predicter;

import java.util.ArrayList;
import java.util.Random;

public class NN {
	
	Random rand = new Random();
	 
    // Inputs for predicter problem
    final float inputs[][] = {
    		
    		
    		// density, date-range<3 days, water supply, # of cases>3
    		
    		
    		{0,0,0,0}, 				
    		{0,0,0,1}, 
    		{0,0,1,0}, 
    		{0,0,1,1},
    		{0,1,0,0}, 
    		{0,1,0,1}, 
    		{0,1,1,0}, 
    		{0,1,1,1},
    		{1,0,0,0}, 
    		{1,0,0,1}, 
    		{1,0,1,0}, 
    		{1,0,1,1},
    		{1,1,0,0}, 
    		{1,1,0,1}, 
    		{1,1,1,0}, 
    		{1,1,1,1}
    		
    
    };
    // Corresponding outputs
    final float expectedOutputs[][] = {
    		{ 0 }, 
    		{ 0 },
    		{ 0 },
    		{ 0 },
    		{ 0 }, 
    		{ 1 },
    		{ 0 },
    		{ 1 }, 
    		{ 0 },
    		{ 0 },
    		{ 0 }, 
    		{ 1 },
    		{ 0 },
    		{ 1 }, 
    		{ 1 },
    		{ 1 },
    		
    
    };
    double resultOutputs[][] = { { -1 }, { -1 }, { -1 }, { -1 },{ -1 }, { -1 }, { -1 }, { -1 },{ -1 }, { -1 }, { -1 }, { -1 }, { -1 }, { -1 }, { -1 }, { -1 }};
    double output[];
 
    final int INPUT=0;
    final int HIDDEN=1;
    final int OUTPUT=2;
     
    final static int SIZE = 49; // 4 8 1 layers, gives 49 weights total
    public double[] chromosome = new double[SIZE];
 
    final ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
    final ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
    final ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
    final Neuron bias = new Neuron();
    final static int layers[] = { 4, 8, 1 }; // input, hidden, output
    final static int randomWeightMultiplier = 30;
 
    float learningRate = 1f;
    float momentum = 0.9f;
 
    public NN() {       
                 
        // create all Neurons and connections
        // connections are created in the neuron class
        for (int i = 0; i < layers.length; i++) {
            if (i == INPUT) { // input layer
                for (int j = 0; j < layers[i]; j++) {
                    Neuron neuron = new Neuron();
                    inputLayer.add(neuron);
                }
            } else if (i == HIDDEN) { // hidden layer
                for (int j = 0; j < layers[i]; j++) {
                    Neuron neuron = new Neuron();
                    neuron.addInConnectionsS(inputLayer);
                    neuron.addBiasConnection(bias);
                    hiddenLayer.add(neuron);
                }
            }
 
            else if (i == OUTPUT) { // output layer
                for (int j = 0; j < layers[i]; j++) {
                    Neuron neuron = new Neuron();
                    neuron.addInConnectionsS(hiddenLayer);
                    neuron.addBiasConnection(bias);
                    outputLayer.add(neuron);
                }
            } else {
                System.out.println("!Error NeuralNetwork init");
            }
        }
 
        // initialize random weights
        int i = 0;
        for (Neuron neuron : hiddenLayer) {
            ArrayList<Connection> connections = neuron.getAllInConnections();
            for (Connection conn : connections) {
                double newWeight = getRandom();
                conn.setWeight(newWeight);
                chromosome[i++] = newWeight;
            }
        }
        for (Neuron neuron : outputLayer) {
            ArrayList<Connection> connections = neuron.getAllInConnections();
            for (Connection conn : connections) {
                double newWeight = getRandom();
                conn.setWeight(newWeight);
                chromosome[i++] = newWeight;
            }
        }
 
        // reset id counters
        Neuron.counter = 0;
        Connection.counter = 0;
    }
 
    // random
    double getRandom() {
        return randomWeightMultiplier * (rand.nextDouble() * 2 - 1); // [-1;1[
    }
 
    void setInput(float inputs[]) {
        // There is equally many neurons in the input layer as there are
        // input variables
        for (int i = 0; i < inputLayer.size(); i++) {
            inputLayer.get(i).setOutput(inputs[i]);
        }
    }
 
    double[] getOutput() {
        double[] outputs = new double[outputLayer.size()];
        for (int i = 0; i < outputLayer.size(); i++)
            outputs[i] = outputLayer.get(i).getOutput();
        return outputs;
    }
 
    /**
     * Calculate the output of the neural network based on the input The forward
     * operation
     */
    void activate() {
        for (Neuron n : hiddenLayer) {
            n.calculateOutput();
        }
 
        for (Neuron n : outputLayer) {
            n.calculateOutput();
        }
    }
 
    double[] getAllWeights() {
        double[] ds = new double[SIZE];
        int i = 0;
        // weights for the hidden layer
        for (Neuron n : hiddenLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                double w = con.getWeight();
                ds[i++] = w;
            }
        }
        // weights for the output layer
        for (Neuron n : outputLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                double w = con.getWeight();
                ds[i++] = w;
            }
        }
 
        return ds;
    }
 
    // chromosome
    public void setChromosome(double[] weights) {
        chromosome = weights;
        int i = 0;
        // weights for the hidden layer
        for (Neuron n : hiddenLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                double w = weights[i];
                con.setWeight(w);
                i++;
            }
        }
        // weights for the output layer
        for (Neuron n : outputLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                double w = weights[i];
                con.setWeight(w);
                i++;
            }
        }
    }
 
    public void printAllWeights() {
        // weights for the hidden layer
        for (Neuron n : hiddenLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                double w = con.getWeight();
                System.out.println("n=" + n.id + " c=" + con.id + " w=" + w);
            }
        }
        // weights for the output layer
        for (Neuron n : outputLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                double w = con.getWeight();
                System.out.println("n=" + n.id + " c=" + con.id + " w=" + w);
            }
        }
    }
 
    double fitness() {
        double error = 0;
        for (int p = 0; p < inputs.length; p++) {
            setInput(inputs[p]);
 
            activate();
 
            output = getOutput();
            resultOutputs[p] = output;
 
            for (int j = 0; j < expectedOutputs[p].length; j++) {
                double err = Math.pow(resultOutputs[p][j]
                        - expectedOutputs[p][j], 2);
                error += err;
            }
        }
 
        return 1 / error; // error can not be 0
    }
 
    void resultPrint() {
        for (int p = 0; p < inputs.length; p++) {
            System.out.print("INPUTS: ");
            for (int x = 0; x < layers[INPUT]; x++) {
                System.out.print(Float.toString(inputs[p][x]) + " ");
            }
 
            System.out.print("EXPECTED: ");
            for (int x = 0; x < layers[OUTPUT]; x++) {
                System.out.print(Float.toString(expectedOutputs[p][x]) + " ");
            }
 
            System.out.print("ACTUAL: ");
            for (int x = 0; x < layers[OUTPUT]; x++) {
                System.out.print(resultOutputs[p][x] + " ");
            }
            System.out.println();
        }
    }
 
    @Override
    public String toString() {
        return fitness() + "";
    }

}
