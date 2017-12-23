package com.springmvc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.dao.MovieDAO;
import com.springmvc.entity.Movie;

@Service
public class MovieImpl implements MovieService {
	
	@Autowired
	private MovieDAO movieDAO;

	@Override
	@Transactional
	public List<Movie> getMovies() {
		
		return movieDAO.getMovies();
		
	}

}
