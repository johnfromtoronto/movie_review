package com.springmvc.dao;

import java.util.List;

import com.springmvc.entity.Movie;

public interface MovieDAO {
	List<Movie> getMovies();

	void saveMovie(Movie theMovieByUser);

	void deleteMovieById(int movieId);
}
