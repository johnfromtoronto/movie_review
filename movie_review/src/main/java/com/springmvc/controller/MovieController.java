package com.springmvc.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvc.entity.Genre;
import com.springmvc.entity.Movie;
import com.springmvc.entity.Review;
import com.springmvc.entity.WrapperMovieReview;
import com.springmvc.service.MovieService;

/**
 * MovieController.java
 * Purpose: prepare model and JSP views. CRUD operations on database.
 * @author John
 *
 */

@Controller
@RequestMapping("/movie")
public class MovieController {

		@Autowired
		private MovieService movieService;
	
		/*
		 * Binds requests parameters to bean objects. Trims all string requests.
		 * 
		 * @param databinder binds request parameters to bean objects
		 */
		@InitBinder
		public void initBinder(WebDataBinder databinder) {
			StringTrimmerEditor ste = new StringTrimmerEditor(true);
			
			databinder.registerCustomEditor(String.class, ste);
		}
		/*
		 * Redirects request to home-page
		 * 
		 * @return string value of the view to redirect to home page
		 */
		@RequestMapping("/home")
		public String home() {
			return "home-page";
		}
		
		/*
		 * Retrieves a list of movies from database and adds to the model
		 * 
		 * @param theModel the model that will receive our list of movies
		 * 
		 * @return string sends user to view that displays the list of movies
		 */
		@RequestMapping("/list")
		public String list(Model theModel) {
			System.out.println("BEGINn: public String list(Model theModel) {");
			List<Movie> movies = movieService.getMovies();
			
			theModel.addAttribute("movies",movies);
			
			System.out.println("END: public String list(Model theModel) {");
			return "list-movies";
		}
		/*
		 * Creates a new movie(empty) object and adds it to the model
		 * 
		 * @param theModel model that gets the new movie
		 * 
		 * @return string sends user to a form page to fill in movie data
		 */
		@GetMapping("/addMovie")
		public String addMovie(Model theModel) {
			System.out.println("BEGIN: public addMovie(Model theModel) {");
			Movie theMovie = new Movie();
			theModel.addAttribute("movie",theMovie);
			System.out.println("END: public addMovie(Model theModel) {");
			return "add-movie-form";
			
		}
		/*
		 * Find one movie from database and put into model. Finding correct movie by it's id.
		 * 
		 * @param movieId id of movie
		 * @param theModel will add movie to this model
		 * 
		 * @return string redirects to a form page to update movie data
		 */
		@RequestMapping("/updateMovie")
		public String updateMovie(@RequestParam("movieId")int movieId, Model theModel) {
			System.out.println("BEGIN: public String updateMovie(@RequestParam");
			//get the movie from the db
			Movie theMovie = movieService.getMovieById(movieId);
			List<Genre> movieGenres = theMovie.getGenres();
			for(Genre genre:movieGenres) {
				String movieGenre = genre.getMovieGenre();
				switch(movieGenre) {
				case "Horror":
					theModel.addAttribute("isHorrorSet", true);
				break;
				case "Comedy":
					theModel.addAttribute("isComedySet", true);
				break;
				case "Action":
					theModel.addAttribute("isActionSet", true);
				break;
				}
			}
			System.out.println("m2");
			theModel.addAttribute("movie",theMovie);
			System.out.println("END: public String updateMovie(@RequestParam");
			return "add-movie-form";
		}
		
		/*
		 * Deletes movie from database using id provided
		 * 
		 * @param movieId id of movie to delete
		 * 
		 * @return string return to list of movies
		 */
		@RequestMapping("/deleteMovie")
		public String deleteMovie(@RequestParam("movieId")int movieId) {
			System.out.println("BEGIN: public String deleteMovie(@RequestParam");
			movieService.deleteMovieById(movieId);
			System.out.println("END: public String deleteMovie(@RequestParam");
			return "redirect:/movie/list";
		}
		/*
		 * Process the movie form and perform form validation
		 * 
		 * @param theMovieByUser the movie object with data that user entered
		 * @param br the BindingResult object containing error information if any
		 * 
		 * @return string sends to confirmation page, confirming form submission successful
		 */
		@RequestMapping("/processMovieForm")
		public String processMovieForm(@Valid @ModelAttribute("movie") Movie theMovieByUser, BindingResult br) {
			System.out.println("BEGIN: String processMovieForm(@Valid @ModelAttribute");
			System.out.println("theMovieByUser: "+theMovieByUser);
			if(br.hasErrors()) {
				System.out.println("END add-movie-form : String processMovieForm(@Valid @ModelAttribute");
				return "add-movie-form";
			}
			movieService.saveMovie(theMovieByUser);
			System.out.println("END: String processMovieForm(@Valid @ModelAttribute");
			return "movie-confirmation";
		}
		/*
		 * Retrieve all reviews on a specific movie
		 * 
		 * @param movieId id of movie to retrieve
		 * @param theModel insert reviews into this model
		 * 
		 * @return string view that will display list of reviews on a given movie
		 */
		@RequestMapping("/viewReviews")
		public String viewReviews(@RequestParam("movieId")int movieId, Model theModel) {
			System.out.println("BEGIN: viewReviews()");
			Movie theMovie = movieService.getMovieReviewsById(movieId);
			theModel.addAttribute("movie",theMovie);
				
			System.out.println("END: viewReviews()");
			return "list-reviews";
		}
		/*
		 * Create a new review object for user to fill in data for a given movie
		 * 
		 * @param movieId id of movie
		 * @param theModel will contain movie and new review
		 * 
		 * @return string form page that allows user to fill in a review
		 */
		@RequestMapping("/addReview")
		public String addReview(@RequestParam("movieId")int movieId,Model theModel) {
			System.out.println("BEGIN: addReview()");			
			Review theReview = new Review();
			Movie theMovie = movieService.getMovieById(movieId);
			theReview.setMovie(theMovie);//set movie and visa-versa
			WrapperMovieReview wrapperMovieReview = new WrapperMovieReview(theMovie, theReview);
			theModel.addAttribute("wrapperMovieReview",wrapperMovieReview);
			System.out.println("END: addReview()");
			return "write-review";
		}
		/*
		 * Process the review form
		 * 
		 * @param wrapperMovieReview the wrapper object containing movie and review
		 * @param theModel will contain the movie and all it's reviews
		 * 
		 * @return string sends user to view that displays list of reviews for a given movie
		 */
		@PostMapping("/processReviewForm")
		public String processReviewForm(@ModelAttribute("wrapperReview") WrapperMovieReview wrapperMovieReview,Model theModel) {
			System.out.println("BEGIN: processReviewForm()");
			//get the movie, get the review, set them on each other, do cascade save on theReview
			System.out.println("theMOVIE: "+wrapperMovieReview.getMovie());
			Movie theMovie = wrapperMovieReview.getMovie();
			Review theReview = wrapperMovieReview.getReview();
			theReview.setMovie(theMovie);
			theMovie.addReview(theReview);
			movieService.saveMovie(theMovie);
			//send back the movie with the reviews
			Movie movieFetch = movieService.getMovieById(wrapperMovieReview.getMovie().getId());
			System.out.println("lazy init reviews: "+movieFetch.getReviews());
			theModel.addAttribute("movie", movieFetch);//get the movie and all its reviews from the db
			System.out.println("END: processReviewForm()");;
			return "list-reviews";
		}
		
}
