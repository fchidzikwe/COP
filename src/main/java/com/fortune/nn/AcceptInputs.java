package com.fortune.nn;

public class AcceptInputs {

	public double normaliseDDD(String mode) {

		
		System.out.println("in normalise ddd method");
		double realValue = 0;
	
		if(mode.equalsIgnoreCase("one")){
			
			realValue = 0;
			
			System.out.println("for no the doule values is "+realValue);
			return realValue;
		}
		
		else if (mode.equalsIgnoreCase("two")) {
			realValue = (double)  1 / 3;
			System.out.println("for mild the doule values is "+realValue);
			return realValue;

		}
		
		
		else if (mode.equalsIgnoreCase("three")) {
		
			realValue =(double)  2 / 3;
			System.out.println("for moderate the doule values is "+realValue);
			return realValue;
		}
		
		else{
			
			realValue = 1;
			System.out.println("for severe the doule values is "+realValue);
			return realValue;
			
		}
		
		
	}

	public double normaiseAge(int age) {
			double realValue =0;
		
		realValue = (double) (age - 1) / 99;
		
		System.out.println("age is"+ realValue);
		return  realValue;
	}
	
	public double normaliseBP(String mode) {
	double realValue =0;

		if(mode.equalsIgnoreCase("one")){
		
			return 0;
		}
		
		else if (mode.equalsIgnoreCase("two")) {
			realValue = (double) 1/2;
			
			return  realValue;
		}
		
		else{
	
			return 1;
			
		}
		
	
	}
	
	public double normaliseLegCramps(String mode) {

		if(mode.equalsIgnoreCase("one")){
			
			return 1;
		}
		
		else{
			
			return 0;
		
		
		}
		
		

	}
		
	public double normaliseGender(String mode) {
		
	if(mode.equalsIgnoreCase("one")){
			return 1;
		}
		
		else{
			return 0;
			
		}

	}
	
	public double normaliseSunkenEyes(String mode) {

if(mode.equalsIgnoreCase("two")){
			
			
			return 0;
		}
		
		else{
	
			return 1;
			
		}
		

	}

	

}
