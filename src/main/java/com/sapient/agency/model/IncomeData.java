package com.sapient.agency.model;

import com.sapient.agency.utility.Enums.GENDER;

public class IncomeData {
	private String city;
	private String country;
	private String currency;
	private Double income;
	private GENDER gender;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getIncome() {
		return income;
	}
	public void setIncome(Double income) {
		this.income = income;
	}
	public GENDER getGender() {
		return gender;
	}
	public void setGender(GENDER gender) {
		this.gender = gender;
	}
	
	public IncomeData(String city, String country, String currency, Double income, GENDER gender) {
		super();
		this.city = city;
		this.country = country;
		this.currency = currency;
		this.income = income;
		this.gender = gender;
	}
	public IncomeData() {
		super();
	}
	public String toString() {
		return "[City="+this.city + ", Country="+this.country+", Gender="+this.gender.getValue()+", Currency="+this.currency+", Income="+this.income+" ]";
	}
	

}
