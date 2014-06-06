package com.tengen.m101j.week2;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class Score {
	@Id
	private String _id;

	@Field("student_id")
	private int studentId;

	private String type;

	private double score;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Score [_id=").append(_id).append(", studentId=").append(studentId).append(", type=").append(type).append(", score=").append(score)
				.append("]");
		return builder.toString();
	}
}
