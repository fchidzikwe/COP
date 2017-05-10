package com.fortune.enums;

public enum WaterSupply {

	STANDARD("STANDARD"), GOOD("GOOD"), POOR("POOR");

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
