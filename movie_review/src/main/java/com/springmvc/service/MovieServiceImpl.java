package com.springmvc.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springmvc.dao.MovieDAO;
import com.springmvc.entity.Movie;

@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	private MovieDAO movieDAO;

	@Override
	@Transactional
	public List<Movie> getMovies() {
		System.out.println("BEGIN: SERVICE: public List<Movie> getMovies() {");
		List<Movie> theMovies = movieDAO.getMovies();
		System.out.println("END: SERVICE: public List<Movie> getMovies() {");
		return theMovies;
	}

	@Override
	@Transactional
	public void saveMovie(Movie theMovieByUser) {
		System.out.println("BEGIN: public void saveMovie(Movie theMovieByUser) ");
		movieDAO.saveMovie(theMovieByUser);
		System.out.println("END: public void saveMovie(Movie theMovieByUser) ");
	}

	@Override
	@Transactional
	public void deleteMovieById(int movieId) {
		System.out.println("BEING: SERVICE: public void deleteMovieById(int movieId) ");
		movieDAO.deleteMovieById(movieId);
		System.out.println("END: SERVICE: public void deleteMovieById(int movieId) ");
	}

}