package com.ibc.ibchelper.entity;

public enum Language {
	EN("English"), RO("Română"), RU("русский");
	
	private final String displayValue;

	private Language(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}	
	
}
