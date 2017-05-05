package com.fortune.utils;

public class FacilityEnumUtils {

	public static String processDensity(String value) {

		if (value.equalsIgnoreCase("one")) {

			return "LOW";

		}

		else if (value.equalsIgnoreCase("two")) {

			return "MEDIUM";

		}

		else {

			return "HIGH";

		}

	}

	public static String processWaterSupply(String value) {
		if (value.equalsIgnoreCase("one")) {

			return "POOR";

		}

		else if (value.equalsIgnoreCase("two")) {

			return "STANDARD";

		}

		else {

			return "GOOD";

		}

	}

}
