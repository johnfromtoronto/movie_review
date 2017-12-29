package com.springmvc.service;

import java.util.List;

import com.springmvc.entity.Movie;

public interface MovieService {
	
	public List<Movie> getMovies();

	public void saveMovie(Movie theMovieByUser);

	public void deleteMovieById(int movieId);

	public Movie getMovieById(int movieId);

	public Movie getMovieReviewsById(int movieId);
}
