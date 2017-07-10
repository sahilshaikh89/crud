package com.api.hibernate.model;

import java.util.Collection;

import org.hibernate.criterion.Restrictions;

public class Restriction {
		
	public static enum OPERATORS {eq, ne, like, ilike, gt, lt, le, ge, between, in, isNull, isNotNull, and, or, not, isEmpty, isNotEmpty,
		sizeEq, sizeNe, sizeGt, sizeLt, sizeGe, sizeLe, distinct};
		
	private OPERATORS op;
	private String prop;
	private Object value;
	private Collection<Restriction> restriction;
		
	public OPERATORS getOperator() {
		return op;
	}
	public void setOperator(OPERATORS operator) {
		this.op = operator;
	}
	public String getProperty() {
		return prop;
	}
	public void setProperty(String property) {
		this.prop = property;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Collection<Restriction> getRestriction() {
		return restriction;
	}
	public void setRestriction(Collection<Restriction> restriction) {
		this.restriction = restriction;
	}
	
	@Override
	public String toString() {
		return "Restriction [operator=" + op + ", property=" + prop + ", value=" + value + ", restriction="
				+ restriction + "]";
	}
}
