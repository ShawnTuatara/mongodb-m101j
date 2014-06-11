package com.tengen.m101j.week3;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
public class Student {
	@Id
	private int studentId;

	private String name;

	private List<Score> scores;

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Score> getScores() {
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}
}
