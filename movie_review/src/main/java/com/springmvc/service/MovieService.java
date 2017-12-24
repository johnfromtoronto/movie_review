package com.springmvc.service;

import java.util.List;

import com.springmvc.entity.Movie;

public interface MovieService {
	public List<Movie> getMovies();

	public void saveMovie(Movie theMovieByUser);

	public void deleteMovieById(int movieId);
	//get the movie and it's genres 
	public Movie getMovieById(int movieId);
	//get the movie and it's reviews
	public Movie getMovieReviewsById(int movieId);
}
