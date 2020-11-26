package com.accenture.training.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "\"TRAINING_PRODUCTS_TBLPRODUCTS\"")
public class ProductsEntity {

	@Id
	@Column(name = "\"ID\"")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	
	@Column(name = "\"VALIDFROM\"")
	private LocalDateTime validFrom;
	@Column(name = "\"VALIDTO\"")
	private LocalDateTime validTo;

	@Column(name = "\"NAME\"")
	private String name;

	@Column(name = "\"MANUFACTURER\"")
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

	public LocalDateTime getValidFrom() {
		return validFrom;
	}

	public LocalDateTime getValidTo() {
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

	public void setValidFrom(LocalDateTime validFrom) {
		this.validFrom = validFrom;
	}

	public void setValidTo(LocalDateTime validTo) {
		this.validTo = validTo;
	}

}
