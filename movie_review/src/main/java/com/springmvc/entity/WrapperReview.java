package com.springmvc.entity;

import java.io.Serializable;

public class WrapperReview implements Serializable{
	private Movie movie;
	private Review review;
	
	public WrapperReview() {
		
	}
	
	
	@Override
	public String toString() {
		return "WrapperReviewBEGIN [movie=" + movie + ", review=" + review + "END]";
	}


	public WrapperReview(Movie movie, Review review) {
		super();
		this.movie = movie;
		this.review = review;
	}


	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public Review getReview() {
		return review;
	}
	public void setReview(Review review) {
		this.review = review;
	}
	
	
}
