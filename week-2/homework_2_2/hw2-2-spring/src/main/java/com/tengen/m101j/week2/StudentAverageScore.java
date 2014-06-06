package com.tengen.m101j.week2;

import org.springframework.data.annotation.Id;

public class StudentAverageScore {
	@Id
	private String id;

	private double average;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}
}
