package com.api.hibernate.model;

public class Order {
	
	public static enum ORDER_BY { asc,  desc};
	
	private ORDER_BY order;
	private String prop;
	public ORDER_BY getOrder() {
		return order;
	}
	public void setOrder(ORDER_BY order) {
		this.order = order;
	}
	public String getProperty() {
		return prop;
	}
	public void setProperty(String property) {
		this.prop = property;
	}
	@Override
	public String toString() {
		return "Order [order=" + order + ", property=" + prop + "]";
	}
	
}
