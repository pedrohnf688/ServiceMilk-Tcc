package com.pa2.milk.api.model.dto;

public class LaudoMediaDto {

	private Integer id;
	private String batch;

	public LaudoMediaDto() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	@Override
	public String toString() {
		return "LaudoMediaDto [id=" + id + ", batch=" + batch + "]";
	}

}
