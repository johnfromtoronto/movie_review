package com.springmvc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="genre")
public class Genre {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="movie_genre")
	private String movieGenre;
	
//	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
//	@JoinTable(name="movie_genre",
//				joinColumns=@JoinColumn(name="genre_id"),
//				inverseJoinColumns=@JoinColumn(name="movie_id"))
//	private List<Movie> movies;
	
	public Genre() {
		
	}
	
	
	public Genre(String movieGenre) {
		this.movieGenre = movieGenre;
	}


	@Override
	public String toString() {
		return "Genre [id=" + id + ", movieGenre=" + movieGenre + "]";
	}


	public String getMovieGenre() {
		return movieGenre;
	}
	public void setMovieGenre(String movieGenre) {
		this.movieGenre = movieGenre;
	}


//	public List<Movie> getMovies() {
//		return movies;
//	}
//
//
//	public void setMovies(List<Movie> movies) {
//		this.movies = movies;
//	}
//	public void addMovie(Movie theMovie) {
//		if(movies==null) {
//			movies = new ArrayList<Movie>();
//		}
//		movies.add(theMovie);
//	}
	
	

}
