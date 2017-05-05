package com.fortune.enums;

public enum WaterSupply {

	BEST("GOOD"), GOOD("STANDARD"), POOR("POOR");

	private final String quality;

WaterSupply(String quality) {

    this.quality = quality;
}

	public String getQuality() {

		return quality;
	}

	@Override
	public String toString() {

		return quality;
	}

}
