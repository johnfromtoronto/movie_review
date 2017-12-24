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

@Controller
@RequestMapping("/movie")
public class MovieController {

		@Autowired
		private MovieService movieService;
	
		@InitBinder
		public void initBinder(WebDataBinder databinder) {
			StringTrimmerEditor ste = new StringTrimmerEditor(true);
			
			databinder.registerCustomEditor(String.class, ste);
		}
		
		@RequestMapping("/home")
		public String home() {
			return "home-page";
		}
		
		@RequestMapping("/list")
		public String list(Model theModel) {
			System.out.println("BEGINn: public String list(Model theModel) {");
			List<Movie> movies = movieService.getMovies();
			
			theModel.addAttribute("movies",movies);
			
			System.out.println("END: public String list(Model theModel) {");
			return "list-movies";
		}
		
		@GetMapping("/addMovie")
		public String addMovie(Model theModel) {
			System.out.println("BEGIN: public addMovie(Model theModel) {");
			Movie theMovie = new Movie();
			theModel.addAttribute("movie",theMovie);
			System.out.println("END: public addMovie(Model theModel) {");
			return "add-movie-form";
			
		}
		
		@RequestMapping("/updateMovie")
		public String updateMovie(@RequestParam("movieId")int movieId, Model theModel) {
			System.out.println("BEGIN: public String updateMovie(@RequestParam");
			//get the movie from the db
			Movie theMovie = movieService.getMovieById(movieId);
			System.out.println("m1");
			List<Genre> movieGenres = theMovie.getGenres();
			System.out.println("m1.2: "+movieGenres);
			System.out.println("m2");
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
		
		@RequestMapping("/deleteMovie")
		public String deleteMovie(@RequestParam("movieId")int movieId) {
			System.out.println("BEGIN: public String deleteMovie(@RequestParam");
			movieService.deleteMovieById(movieId);
			System.out.println("END: public String deleteMovie(@RequestParam");
			return "redirect:/movie/list";
		}
		
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
		
		@RequestMapping("/viewReviews")
		public String viewReviews(@RequestParam("movieId")int movieId, Model theModel) {
			System.out.println("BEGIN: viewReviews()");
			Movie theMovie = movieService.getMovieReviewsById(movieId);
			theModel.addAttribute("movie",theMovie);
				
			System.out.println("END: viewReviews()");
			return "list-reviews";
		}
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
