package com.api.hibernate.model;

import java.util.Collection;

import com.api.hibernate.model.Alias;
import com.api.hibernate.model.Restriction;

public class Restrictions {
	private Collection<Restriction> restrictions;
	private Collection<Alias> aliases;
	private Collection<Projection> projectionList;
	private Collection<Order> orderList;
	
	public Collection<Restriction> getRestrictions() {
		return restrictions;
	}
	public void setRestrictions(Collection<Restriction> restrictions) {
		this.restrictions = restrictions;
	}
	public Collection<Alias> getAliases() {
		return aliases;
	}
	public void setAliases(Collection<Alias> aliases) {
		this.aliases = aliases;
	}
	
	public Collection<Projection> getProjectionList() {
		return projectionList;
	}
	public void setProjectionList(Collection<Projection> projectionList) {
		this.projectionList = projectionList;
	}
	public Collection<Order> getOrderList() {
		return orderList;
	}
	public void setOrderList(Collection<Order> orderList) {
		this.orderList = orderList;
	}
	@Override
	public String toString() {
		return "Restrictions [restrictions=" + restrictions + ", aliases=" + aliases + ", projectionList="
				+ projectionList + ", orderList=" + orderList + "]";
	}	
}
