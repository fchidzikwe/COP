package com.fortune.nn;



public class Diagnosis {


	public static int getIndexOfMax(double array[]) {
		if (array.length == 0) {
			return -1; // array contains no elements
		}
		double max = array[0];
		int pos = 0;

		for (int i = 1; i < array.length; i++) {
			if (max < array[i]) {
				pos = i;
				max = array[i];
			}
		}
		
		System.out.println("maximum element is :"+array[pos] + " "+ "the index is :"+pos);
		return pos;
	}


	public static String getDiagnosis(double [] value){
			
			String diagnosis = null;
			int index = getIndexOfMax(value);
			switch (index) {

			case 0:
				diagnosis= "mild";
				break;

			case 1:
				diagnosis = "severe";
				break;

			case 2:
				diagnosis = "no cholera";
				break;

			case 3:
				diagnosis= "moderate";
				break;

			}

			
			return diagnosis;
		}

		



}
