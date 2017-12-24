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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="movie")
public class Movie {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="movie_title")
	@NotNull(message="field is required")
	@Size(min=2,message="Must contain more than one letter")
	private String movieTitle;
	@Column(name="director")
	//@Pattern(regexp="/*(^[A-Z]{1}[a-z]{1,30}$)*/", message="Must a a valid name")
	@NotNull(message="field is required")
	@Pattern(regexp="^[A-Z]{1}[a-zA-Z]{1,45}",message="Invalid director name(First letter capital)")
	private String director;
	//if a movie get's deleted, its movie genre gets deleted too
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="movie_genre",
				joinColumns=@JoinColumn(name="movie_id"),
				inverseJoinColumns=@JoinColumn(name="genre_id"))
	private List<Genre> genres;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="movie",cascade=CascadeType.ALL)
	private List<Review> reviews;
	
	public Movie() {
		
	}
	
	public Movie(String movieTitle, String director, List<Genre> genres, List<Review> reviews) {
		this.movieTitle = movieTitle;
		this.director = director;
		this.genres = genres;
		this.reviews = reviews;
	}

	public void addReview(Review review) {
		System.out.println("inside public void addReview(Review review) {");
		if(reviews == null) {
			System.out.println("create new ArrayList");
			reviews = new ArrayList<Review>();
		}
		System.out.println("reviews.add(review); : "+review);
		reviews.add(review);
		System.out.println("review.setMovie(this);");
		review.setMovie(this);
		System.out.println("end of call");
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}




	@Override
	public String toString() {
		return "+++Movie(ToStringMethod)+++ [id=" + id + ", movieTitle=" + movieTitle + ", director=" + director + ", genres=" + genres
				+ ", reviews=" +  "+++++MovieEnd+++++]";
	}





	public void addGenre(Genre theGenre) {
		if(genres==null) {
			genres = new ArrayList<Genre>();
		}
		genres.add(theGenre);
	}

	public List<Genre> getGenres() {
		return genres;
	}



	public void setGenres(List<Genre> genres) {
		System.out.println("LOG: setGenre::::::: "+genres);
		this.genres = genres;
	}



	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}





	public List<Review> getReviews() {
		return reviews;
	}





	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	
	
	
}
