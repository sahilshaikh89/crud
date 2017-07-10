package com.api.hibernate.model;

public class Projection {

	public static enum PROJECTIONS {
		distinct, rowCount, count, countDistinct, max, min, avg, sum, groupProperty, property 
	}
	
	private PROJECTIONS projection;
	private String prop;
	public PROJECTIONS getProjection() {
		return projection;
	}
	public void setProjection(PROJECTIONS projection) {
		this.projection = projection;
	}
	public String getProp() {
		return prop;
	}
	public void setProp(String prop) {
		this.prop = prop;
	}
	
	@Override
	public String toString() {
		return "Projection [projection=" + projection + ", prop=" + prop + "]";
	}
	
}
