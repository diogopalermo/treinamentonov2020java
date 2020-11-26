package com.accenture.training.dto;

import java.io.Serializable;

public class ProductsTO implements Serializable {

	private static final long serialVersionUID = -1219767538649208397L;

	private String id;
	
	private String validFrom;
	private String validTo;

	private String name;

	private String manufacturer;

	public String getId() {
		return id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getName() {
		return name;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public String getValidTo() {
		return validTo;
	}
	public void setId(String id) {
		this.id = id;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

}
