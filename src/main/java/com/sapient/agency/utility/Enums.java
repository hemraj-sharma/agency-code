package com.sapient.agency.utility;

public class Enums {

	private Enums() {
		super();
	}
	
	public static enum GENDER{
		MALE("M"),FEMALE("F");
		
		private String value;
		
		private GENDER(String value){
			this.value = value;
		}
		public String getValue(){
			return value;
		}
	}
}
