package com.app.models;

import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "questions")
public class Question {
	@Id private String id;
	private String question_url;
	private String solution_url;
	private String course;
	private String source;
	List<Comment> comments;
	
	public Question() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Question(String question_url, String solution_url, String id, String course, String source, List<Comment> comments) {
		super();
		this.question_url = question_url;
		this.solution_url = solution_url;
		this.id = id;
		this.course = course;
		this.source = source;
		this.comments = comments;
	}

	public String getQuestion_url() {
		return question_url;
	}

	public void setQuestion_url(String question_url) {
		this.question_url = question_url;
	}

	public String getSolution_url() {
		return solution_url;
	}

	public void setSolution_url(String solution_url) {
		this.solution_url = solution_url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
	
}
