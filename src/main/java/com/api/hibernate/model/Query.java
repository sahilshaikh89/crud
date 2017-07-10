package com.api.hibernate.model;

public class Query {
	
	public static enum TYPE {SELECT, UPDATE, DELETE, INSERT};
	
	private TYPE type;
	private String resultTransformer;
	private String hql;
	private String description;
	public TYPE getType() {
		return type;
	}
	public void setType(TYPE type) {
		this.type = type;
	}
	public String getResultTransformer() {
		return resultTransformer;
	}
	public void setResultTransformer(String resultTransformer) {
		this.resultTransformer = resultTransformer;
	}
	public String getHql() {
		return hql;
	}
	public void setHql(String hql) {
		this.hql = hql;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Query [type=" + type + ", resultTransformer=" + resultTransformer + ", hql=" + hql + ", description="
				+ description + "]";
	}
	
	
}
