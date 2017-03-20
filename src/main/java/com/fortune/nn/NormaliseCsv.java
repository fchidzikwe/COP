package com.fortune.nn;


import java.io.File;


import org.encog.ConsoleStatusReportable;
import org.encog.util.csv.CSVFormat;
import org.encog.util.normalize.DataNormalization;
import org.encog.util.normalize.input.InputField;
import org.encog.util.normalize.input.InputFieldCSV;
import org.encog.util.normalize.input.InputFieldCSVText;
import org.encog.util.normalize.output.OutputFieldRangeMapped;
import org.encog.util.normalize.output.nominal.OutputOneOf;
import org.encog.util.normalize.target.NormalizationStorageCSV;


public class NormaliseCsv{ 
	

	
	//	public static String MYDIR = "src/main/resources/";
	
		public void doThings(){
			try {
	            File rawFile = new File(Utilities.getUnormalisedTrainingDataPath());

	            // download Iris data from UCI
	            if (rawFile.exists()) {
	                System.out.println("Data already downloaded to: " + rawFile.getPath());
	            } else {
	                System.out.println("Please make sure u have uploaded data to : " + rawFile.getPath());
	                
	            }

	            // define the format of the iris data

	            DataNormalization norm = new DataNormalization();
	            InputField inputSepalLength, inputSepalWidth, inputPetalLength, inputPetalWidth;
	            InputFieldCSVText inputClass;

	            norm.addInputField(inputSepalLength = new InputFieldCSV(true, rawFile, 0));
	            norm.addInputField(inputSepalWidth = new InputFieldCSV(true, rawFile, 1));
	            norm.addInputField(inputPetalLength = new InputFieldCSV(true, rawFile, 2));
	            norm.addInputField(inputPetalWidth = new InputFieldCSV(true, rawFile, 3));
	            norm.addInputField(inputClass = new InputFieldCSVText(true, rawFile, 4));
	            inputClass.addMapping("Iris-setosa");
	            inputClass.addMapping("Iris-versicolor");
	            inputClass.addMapping("Iris-virginica");

	            // define how we should normalize

	            norm.addOutputField(new OutputFieldRangeMapped(inputSepalLength, 0, 1));
	            norm.addOutputField(new OutputFieldRangeMapped(inputSepalWidth, 0, 1));
	            norm.addOutputField(new OutputFieldRangeMapped(inputPetalLength, 0, 1));
	            norm.addOutputField(new OutputFieldRangeMapped(inputPetalWidth, 0, 1));
	            norm.addOutputField(new OutputOneOf(inputClass, 1, 0));
	            // define where the output should go
	            
	            
	            
	            File outputFile = new File(Utilities.getNormalisedTrainingDataPath());
	            norm.setCSVFormat(CSVFormat.ENGLISH);
	            norm.setTarget(new NormalizationStorageCSV(CSVFormat.ENGLISH, outputFile));

	            // process
	            norm.setReport(new ConsoleStatusReportable());
	            norm.process();
	            System.out.println("Output written to: " + outputFile.getPath());

	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
			
		}
		
	
		
       
}
