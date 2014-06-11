package com.tengen.m101j.week3;

public class Score {
	private String type;

	private double score;

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
		builder.append("Score [type=").append(type).append(", score=").append(score).append("]");
		return builder.toString();
	}
}
