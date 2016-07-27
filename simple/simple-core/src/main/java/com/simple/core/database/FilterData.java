package com.simple.core.database;

public class FilterData {

	private String filterDef;
	private String filterParamName;
	private Object filterParamValue;

	public FilterData(String filterDef, String filterParamName, Object filterParamValue) {
		this.filterDef = filterDef;
		this.filterParamName = filterParamName;
		this.filterParamValue = filterParamValue;
	}

	public String getFilterDef() {
		return filterDef;
	}

	public void setFilterDef(String filterDef) {
		this.filterDef = filterDef;
	}

	public String getFilterParamName() {
		return filterParamName;
	}

	public void setFilterParamName(String filterParamName) {
		this.filterParamName = filterParamName;
	}

	public Object getFilterParamValue() {
		return filterParamValue;
	}

	public void setFilterParamValue(Object filterParamValue) {
		this.filterParamValue = filterParamValue;
	}

}
