package com.fortune.predicter;

import java.util.LinkedList;
import java.util.Random;

public class Hybrid {

	LinkedList<NN> population = new LinkedList<NN>();
    final static int populationSize = 100;
    static Random rand = new Random();
 
    public Hybrid() {
        for (int i = 0; i < populationSize; i++) {
            NN c = new NN();
            population.add(c);
        }
    }
 
    public static void main(String[] args) {
        long BEGIN = System.currentTimeMillis();
 
        Hybrid h = new Hybrid();
        h.run(1000); // evolutions
 
        long END = System.currentTimeMillis();
        System.out.println("Time: " + (END - BEGIN) / 1000.0 + " sec.");
    }
 
    void run(int evolutions) {
        int count = 0;
        System.out.println("Optimal fitness value >= " + (1 / 0.001));
        System.out.println("Fitness random init");
        print();
 
        while (count < evolutions) {
            count++;
            produceNextGen();
        }
 
        System.out.println("\nFitness Result after evolutions " + evolutions);
        print();
 
        System.out.println("First candidate test print");
        NN nn = population.getFirst();
        nn.printAllWeights();
        System.out.println("sumSquaredErrors = " + (1 / nn.fitness()));
        nn.resultPrint();
    }
 
    void print() {
        System.out.println("-- print");
        for (NN c : population) {
            System.out.println(c);
        }
    }
 
    /**
     * Selection strategy: Tournament method Replacement strategy: steady state
     * find 4 random in population not same let 2 fight, and 2 fight the winners
     * makes 2 children
     */
    void produceNextGen() {
        LinkedList<NN> newpopulation = new LinkedList<NN>();
        while (newpopulation.size() < populationSize) {
            int size = population.size();
            int i = rand.nextInt(size);
            int j, k, l;
            j = k = l = i;
            while (j == i)
                j = rand.nextInt(size);
            while (k == i || k == j)
                k = rand.nextInt(size);
            while (l == i || l == j || k == l)
                l = rand.nextInt(size);
 
            NN c1 = population.get(i);
            NN c2 = population.get(j);
            NN c3 = population.get(k);
            NN c4 = population.get(l);
 
            double f1 = c1.fitness();
            double f2 = c2.fitness();
            double f3 = c3.fitness();
            double f4 = c4.fitness();
 
            NN w1, w2;
 
            if (f1 > f2)
                w1 = c1;
            else
                w1 = c2;
            if (f3 > f4)
                w2 = c3;
            else
                w2 = c4;
 
            NN child1, child2;
 
            // Method uniform crossover
            NN[] childs = newChilds(w1, w2);
            child1 = childs[0];
            child2 = childs[1];
 
            double mutatePercent = 0.01;
            boolean m1 = rand.nextDouble() <= mutatePercent;
            boolean m2 = rand.nextDouble() <= mutatePercent;
 
            if (m1)
                mutate(child1);
            if (m2)
                mutate(child2);
 
            boolean isChild1Good = child1.fitness() >= w1.fitness();
            boolean isChild2Good = child2.fitness() >= w2.fitness();
 
            newpopulation.add(isChild1Good ? child1 : w1);
            newpopulation.add(isChild2Good ? child2 : w2);
        }
 
        population = newpopulation;
    }
 
    void mutate(NN c) {
        int i = rand.nextInt(NN.SIZE);
        double range = getRandom() * 2; // range [-1;1[ * 2
        c.chromosome[i] = c.chromosome[i] + range;
        c.setChromosome(c.chromosome); // update
    }
 
    double getRandom() {
        return (rand.nextDouble() * 2) - 1; // [-1;1[
    }
 
    // Uniform crossover
    static NN[] newChilds(NN c1, NN c2) {
        NN child1 = new NN();
        NN child2 = new NN();
 
        double[] chromosome1 = new double[NN.SIZE];
        double[] chromosome2 = new double[NN.SIZE];
 
        for (int i = 0; i < NN.SIZE; i++) {
            boolean b = rand.nextDouble() >= 0.5;
            if (b) {
                chromosome1[i] = c1.chromosome[i];
                chromosome2[i] = c2.chromosome[i];
            } else {
                chromosome1[i] = c2.chromosome[i];
                chromosome2[i] = c1.chromosome[i];
            }
        }
        child1.setChromosome(chromosome1);
        child2.setChromosome(chromosome2);
 
        return new NN[] { child1, child2 };
    }
	
}
