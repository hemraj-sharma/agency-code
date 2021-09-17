package com.sapient.agency.model;

public class OutputData {
	
	private String cityOrCountry;
	private String gender;
	private Double avgIncome;
	
	public String toString() {
		return "[CityOrCountry="+this.cityOrCountry + ", Gender="+this.gender +", AvgIncome="+this.avgIncome+" ]";
	}
	

	public String getCityOrCountry() {
		return cityOrCountry;
	}


	public void setCityOrCountry(String cityOrCountry) {
		this.cityOrCountry = cityOrCountry;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public Double getAvgIncome() {
		return avgIncome;
	}


	public void setAvgIncome(Double avgIncome) {
		this.avgIncome = avgIncome;
	}


	public OutputData() {
		// TODO Auto-generated constructor stub
	}

}
