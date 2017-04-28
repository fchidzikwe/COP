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

public class NormaliseTrainingData {

	public void doThings() {
		try {
			File rawFile = new File(Utilities.getUnormalisedTrainingDataPath());
			System.out.println("********************************************");
			System.out.println("in doThings......rawFile path is: " + rawFile);

			if (rawFile.exists()) {
				System.out.println("Data already downloaded to: " + rawFile.getPath());
			} else {

				System.out.println("Training data not found,Upload trainig data first in: " + rawFile.getPath());
			}

			DataNormalization norm = new DataNormalization();
			InputField inputDiarrhoea, inputVommiting, inputDehydration, inputAge, inputBloodPressure, inputLegCramps,
					inputGender, inputSunkenEyes;

			InputFieldCSVText inputClass;

			norm.addInputField(inputDiarrhoea = new InputFieldCSV(true, rawFile, 0));
			norm.addInputField(inputVommiting = new InputFieldCSV(true, rawFile, 1));
			norm.addInputField(inputDehydration = new InputFieldCSV(true, rawFile, 2));
			norm.addInputField(inputAge = new InputFieldCSV(true, rawFile, 3));
			norm.addInputField(inputBloodPressure = new InputFieldCSV(true, rawFile, 4));
			norm.addInputField(inputLegCramps = new InputFieldCSV(true, rawFile, 5));
			norm.addInputField(inputGender = new InputFieldCSV(true, rawFile, 6));
			norm.addInputField(inputSunkenEyes = new InputFieldCSV(true, rawFile, 7));
			norm.addInputField(inputClass = new InputFieldCSVText(true, rawFile, 8));
			
			inputClass.addMapping("no-cholera");
			inputClass.addMapping("mild");
			inputClass.addMapping("moderate");
			inputClass.addMapping("severe");

			// define how we should normalize

			norm.addOutputField(new OutputFieldRangeMapped(inputDiarrhoea, 0, 1));
			norm.addOutputField(new OutputFieldRangeMapped(inputVommiting, 0, 1));
			norm.addOutputField(new OutputFieldRangeMapped(inputDehydration, 0, 1));
			norm.addOutputField(new OutputFieldRangeMapped(inputAge, 0, 1));
			norm.addOutputField(new OutputFieldRangeMapped(inputBloodPressure, 0, 1));
			norm.addOutputField(new OutputFieldRangeMapped(inputLegCramps, 0, 1));
			norm.addOutputField(new OutputFieldRangeMapped(inputGender, 0, 1));
			norm.addOutputField(new OutputFieldRangeMapped(inputSunkenEyes, 0, 1));

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
