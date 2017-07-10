package com.crud.sample.beans.remote;

public class State {

	private String country;
	private String name;
	private String abbr;
	private String largestCity;
	private String capital;
	private long area;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	public String getLargestCity() {
		return largestCity;
	}
	public void setLargestCity(String largestCity) {
		this.largestCity = largestCity;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public long getArea() {
		return area;
	}
	public void setArea(long area) {
		this.area = area;
	}
	public State(String country, String name, String abbr, String largestCity, String capital) {
		super();
		this.country = country;
		this.name = name;
		this.abbr = abbr;
		this.largestCity = largestCity;
		this.capital = capital;		
	}
	public State(){
		//EMpty constructor
	}
	
	
}