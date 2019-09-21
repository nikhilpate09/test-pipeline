package com.np.rabbitmq;

import java.util.LinkedHashMap;

public class TestVO {
	private String name;
	
	private String city;
	
	private int id;
	
	private LinkedHashMap<String, String> params;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LinkedHashMap<String, String> getParams() {
		return params;
	}

	public void setParams(LinkedHashMap<String, String> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "TestVO [name=" + name + ", city=" + city + ", id=" + id + ", params=" + params + "]";
	}
	
}
