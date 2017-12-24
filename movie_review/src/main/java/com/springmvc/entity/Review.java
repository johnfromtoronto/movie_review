package com.springmvc.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="review")
public class Review {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="comment")
	private String comment;
	@Column(name="person_name")
	private String personName;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="movie_id")
	private Movie movie;
	
	public Review() {
		
	}
	public Review(String comment, String personName, Movie movie) {
		this.comment = comment;
		this.personName = personName;
		this.movie = movie;
	}
	@Override
	public String toString() {
		return "+++Review(ToStringMethod)+++ [id=" + id + ", comment=" + comment + ", personName=" + personName + ", movie=" + "+++ReviewEnd+++]";
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
