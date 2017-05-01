package com.fortune.enums;

public enum Density {

	HIGH("HIGH"), MEDIUM("MEDIUM"), LOW("LOW");

	private final String name;

	Density(String name) {

		this.name = name;
	}

	public String getName() {

		return name;
	}

	@Override
	public String toString() {

		return name;
	}

}
