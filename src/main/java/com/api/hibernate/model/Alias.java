package com.api.hibernate.model;

public class Alias {
	

	public static enum JOIN_TYPE {
		INNER(0), LEFT(1), RIGHT(2);
		
		private final int type;
		
		JOIN_TYPE(final int type) {
			this.type = type;
		}
		
		public int getValue() { return this.type; }
	}

	private String prop;
	private String alias;
	private JOIN_TYPE joinType;
	public String getProp() {
		return prop;
	}
	public void setProp(String prop) {
		this.prop = prop;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public JOIN_TYPE getJoinType() {
		return joinType;
	}
	public void setJoinType(JOIN_TYPE joinType) {
		this.joinType = joinType;
	}
	@Override
	public String toString() {
		return "Alias [prop=" + prop + ", alias=" + alias + ", joinType=" + joinType + "]";
	}
	
	
}
