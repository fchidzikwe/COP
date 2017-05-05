package com.fortune.nn;

import java.io.File;
import java.io.Serializable;

import org.encog.Encog;
import org.encog.app.analyst.AnalystFileFormat;
import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.csv.normalize.AnalystNormalizeCSV;
import org.encog.app.analyst.script.normalize.AnalystField;
import org.encog.app.analyst.wizard.AnalystWizard;
import org.encog.neural.networks.training.propagation.TrainingContinuation;
import org.encog.util.csv.CSVFormat;
import org.encog.util.obj.SerializeObject;


public class NormalizeFile {

	public static void dumpFieldInfo(EncogAnalyst analyst) {
		
		System.out.println("************************in dumpFeildInfo Method");
		System.out.println("Fields found in file:");
		for (AnalystField field : analyst.getScript().getNormalize()
				.getNormalizedFields()) {

			StringBuilder line = new StringBuilder();
			line.append(field.getName());
			line.append(",action=");
			line.append(field.getAction());
			line.append(",min=");
			line.append(field.getActualLow());
			line.append(",max=");
			line.append(field.getActualHigh());
			System.out.println(line.toString());
		}
	}

	public static void start() {
		EncogAnalyst savedAnalyst = null;
	
			File sourceFile = new File(Utilities.getUnormalisedTrainingDataPath());
			File targetFile = new File(Utilities.getNormalisedTrainingDataPath());

			EncogAnalyst analyst = new EncogAnalyst();
			AnalystWizard wizard = new AnalystWizard(analyst);
			wizard.wizard(sourceFile, true, AnalystFileFormat.DECPNT_COMMA);
			
			System.out.println("********************before call to dumpFieldInfo**********************");

			dumpFieldInfo(analyst);
			
			System.out.println("***********************after call to dumpFieldInfo*********************");

			final AnalystNormalizeCSV norm = new AnalystNormalizeCSV();
			norm.analyze(sourceFile, true, CSVFormat.ENGLISH, analyst);
			norm.setProduceOutputHeaders(true);
			
			System.out.println("---------------before normalising");
			
			
			 
	       

	          
	         
			
			norm.normalize(targetFile);
			System.out.println("---------------after normalising");
			
			System.out.println("saving analyts...........");
			
			try
			{
				SerializeObject.save(new File(Utilities.pathToSaveAnalsyt()), (Serializable) savedAnalyst);
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			System.out.println("normalisation completed............preparing to shutdown");
			Encog.getInstance().shutdown();
			System.out.println("shutdown completed");
		
	}
	
	
	
}

