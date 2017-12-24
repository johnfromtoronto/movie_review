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
import com.springmvc.entity.WrapperReview;
import com.springmvc.service.MovieService;

@Controller
@RequestMapping("/movie")
public class MovieController {
	
//		@Autowired
//		private MovieDAO movieDAO;
		@Autowired
		private MovieService movieService;
	
		@InitBinder//init binder intercepts all requests and changes the request using - webdatabinder binds an editor to a datatype
		public void initBinder(WebDataBinder databinder) {
			StringTrimmerEditor ste = new StringTrimmerEditor(true);//if set to true, empty strings are converted to null
			
			databinder.registerCustomEditor(String.class, ste);
		}
		@RequestMapping("/home")
		public String home() {
			return "home-page";
		}
		
		// gets the list of movies from db, then sends it to list-movies
		@RequestMapping("/list")
		public String list(Model theModel) {
			System.out.println("BEGINn: public String list(Model theModel) {");
//			SessionFactory factory = new Configuration()
//					.configure("hibernate.cfg.xml")//provide the config file
//					.addAnnotatedClass(Movie.class)//provide the class with the annotations on it
//					.addAnnotatedClass(Genre.class)
//					.addAnnotatedClass(Review.class)
//					.buildSessionFactory();
//			Session session = factory.getCurrentSession();
//			try {
//				session.beginTransaction();
//				//get movies from db
////				Query<Movie> query = session.createQuery("from Movie");
////				List<Movie> movies = query.getResultList();
////				for(Movie movie: movies) {
////					System.out.println(movie.getGenres());//lazy load all the genres
////				}
////				Movie selectedMovie = session.get(Movie.class, 1);
////				theModel.addAttribute("movie",selectedMovie);
//				
//				List<Movie> movies = movieDAO.getMovies();
//				
//				theModel.addAttribute("movies",movies);
//				session.getTransaction().commit();
//			}finally{
//				session.close();//
//				factory.close();
//			}
			
			
			//List<Movie> movies = movieDAO.getMovies();
			List<Movie> movies = movieService.getMovies();
			
			theModel.addAttribute("movies",movies);
			
			System.out.println("END: public String list(Model theModel) {");
			return "list-movies";
		}
		
		// addMovie() - method that helps display a form for adding movies
		//@RequestMapping("/addMovie")
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
//			SessionFactory factory = new Configuration()
//					.configure("hibernate.cfg.xml")//provide the config file
//					.addAnnotatedClass(Movie.class)//provide the class with the annotations on it
//					.addAnnotatedClass(Genre.class)
//					.addAnnotatedClass(Review.class)
//					.buildSessionFactory();
//			Session session = factory.getCurrentSession();
			
//			try {
//				session.beginTransaction();
//				Movie theMovie = session.get(Movie.class, movieId);
			
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
//				session.getTransaction().commit();
//			}finally {
//				session.close();
//				factory.close();
//			}
			System.out.println("END: public String updateMovie(@RequestParam");
			return "add-movie-form";
		}
		
		@RequestMapping("/deleteMovie")
		public String deleteMovie(@RequestParam("movieId")int movieId) {
			System.out.println("BEGIN: public String deleteMovie(@RequestParam");
//			SessionFactory factory = new Configuration()
//					.configure("hibernate.cfg.xml")//provide the config file
//					.addAnnotatedClass(Movie.class)//provide the class with the annotations on it
//					.addAnnotatedClass(Genre.class)//provide the class with the annotations on it
//					.addAnnotatedClass(Review.class)//provide the class with the annotations on it
//					.buildSessionFactory();
//			Session session = factory.getCurrentSession();
//			
//			try {
//				session.beginTransaction();
//				Movie theMovie = session.get(Movie.class, movieId);
//				session.delete(theMovie);
//				session.getTransaction().commit();
//			}finally {
//				session.close();
//				factory.close();
//			}
			movieService.deleteMovieById(movieId);
			System.out.println("END: public String deleteMovie(@RequestParam");
			return "redirect:/movie/list";
		}
		
		// processMovieForm - method that accepts user movie and adds it to the db
		@RequestMapping("/processMovieForm")
		public String processMovieForm(@Valid @ModelAttribute("movie") Movie theMovieByUser, BindingResult br) {
			System.out.println("BEGIN: String processMovieForm(@Valid @ModelAttribute");
			if(br.hasErrors()) {
				System.out.println("END add-movie-form : String processMovieForm(@Valid @ModelAttribute");
				return "add-movie-form";
			}
			//using the service which delegates the task to dao to save the movie
			movieService.saveMovie(theMovieByUser);
			System.out.println("END: String processMovieForm(@Valid @ModelAttribute");
			return "movie-confirmation";
		}
		
		@RequestMapping("/viewReviews")
		public String viewReviews(@RequestParam("movieId")int movieId, Model theModel) {
			System.out.println("BEGIN: viewReviews()");
//			SessionFactory factory = new Configuration()
//					.configure("hibernate.cfg.xml")
//					.addAnnotatedClass(Movie.class)
//					.addAnnotatedClass(Genre.class)
//					.addAnnotatedClass(Review.class)
//					.buildSessionFactory();
//			Session session = factory.getCurrentSession();
//			try {
//				session.beginTransaction();
				//get the reviews for the given movie
				//Query<Review> query = session.createQuery("from Review where movie_id="+movieId);
				//List<Review> reviews = query.getResultList();
				
				//theModel.addAttribute("reviews",reviews);
				
				//stuff
//				System.out.println("error: "+movieId);
				//Movie theMovie = session.get(Movie.class, movieId);
				//System.out.println("error: "+movieId);
				//System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHH : theMovie: "+theMovie);
//				//System.out.println("the movie reviews: "+theMovie.getReviews());//lazy get
//				Query<Movie> query = session.createQuery("select i from Movie i "
//						+ "JOIN FETCH i.reviews "
//						+ "where i.id=:theMovieId",
//						Movie.class);
//				query.setParameter("theMovieId", movieId);
//				Movie theMovie = null;
//				
//				//Movie theMovie = query.getSingleResult();
//				List results = query.getResultList();
//				if(!results.isEmpty()) {
//					//if query was successful, get the movie from the results
//					theMovie = (Movie) results.get(0);
//					System.out.println("theMovieeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee: "+theMovie);
//					
//				}else {
//					System.out.println("FAilllllllllllllllllllllllllllllllleedd");
//					//if query failed, attempt to get the movie and it's review using .get()
//					theMovie = session.get(Movie.class, movieId);
//					System.out.println("theMovie lazy: "+theMovie.getReviews());
//				}
				Movie theMovie = movieService.getMovieReviewsById(movieId);
				theModel.addAttribute("movie",theMovie);
				
				
//				session.getTransaction().commit();
//			}finally {
//				session.close();
//				factory.close();
//			}
			System.out.println("END: viewReviews()");
			return "list-reviews";
		}
		@RequestMapping("/addReview")
		public String addReview(@RequestParam("movieId")int movieId,Model theModel) {
			System.out.println("BEGIN: addReview()");
			System.out.println("theModel: "+theModel);
			System.out.println("movieId: "+movieId);
			Review review = new Review();
			System.out.println("new review: "+review);
			SessionFactory factory = new Configuration()
					.configure("hibernate.cfg.xml")//provide the config file
					.addAnnotatedClass(Movie.class)//provide the class with the annotations on it
					.addAnnotatedClass(Genre.class)//provide the class with the annotations on it
					.addAnnotatedClass(Review.class)//provide the class with the annotations on it
					.buildSessionFactory();
			Session session = factory.getCurrentSession();
			try {
				session.beginTransaction();
				Movie theMovie = session.get(Movie.class, movieId);
				System.out.println("theMovie: "+theMovie);
				//theMovie.addReview(review);
				//System.out.println("theMovie:after addReview: "+theMovie);
				//session.saveOrUpdate(theMovie);
				WrapperReview wrapperReview = new WrapperReview(theMovie,review);
				review.setMovie(theMovie);//set the review's movie, and set the movie's review
				//theModel.addAttribute("movie",theMovie);
				theModel.addAttribute("wrapperReview",wrapperReview);
				//System.out.println("theMovie sent: "+theMovie);
				session.getTransaction().commit();
			}finally {
				session.close();
				factory.close();
			}
			System.out.println("review: "+review);
			
			//theModel.addAttribute("review",review);
			System.out.println("theModel: "+theModel);
			System.out.println("END: addReview()");
			return "write-review";
			//return "redirect:/movie/listReviews";
		}

		@PostMapping("/processReviewForm")
		public String processReviewForm(@ModelAttribute("wrapperReview") WrapperReview wrapperReview,Model theModel) {
			System.out.println("BEGIN: processReviewForm()");
			System.out.println("++++++++WrapperReview+++++: "+wrapperReview);
			System.out.println("++++++++WrapperReview.getReview()+++++: "+wrapperReview.getReview());
			System.out.println("++++++++WrapperReview.getMovie()+++++: "+wrapperReview.getMovie());
			System.out.println("theModel: "+theModel);
			
			//save the review to db
			SessionFactory factory = new Configuration()
					.configure("hibernate.cfg.xml")//provide the config file
					.addAnnotatedClass(Movie.class)//provide the class with the annotations on it
					.addAnnotatedClass(Genre.class)//provide the class with the annotations on it
					.addAnnotatedClass(Review.class)//provide the class with the annotations on it
					.buildSessionFactory();
			Session session = factory.getCurrentSession();
			
			try {
				//save review to db
				session.beginTransaction();
				//session.saveOrUpdate(reviewByUser);
				//session.saveOrUpdate(theMovie);
				//System.out.println("lazy load reviews:..."+theMovie.getReviews());
//				Query<Review> query = session.createQuery("from Review");
//				List<Review> reviews = query.getResultList();
				//Movie theMovie = session.get(Movie.class, reviewByUser.getMovieId());
				//Movie theMovie = reviewByUser.getMovie();
				//System.out.println("theMovie: "+theMovie);
				//theMovie.addReview(reviewByUser);
				int movieId =wrapperReview.getMovie().getId();
				System.out.println("movieId: "+movieId);
				Movie theMovie = session.get(Movie.class, movieId);
				System.out.println("theMovie: "+theMovie);
				Review theReview = wrapperReview.getReview();
				System.out.println("theReview: "+theReview);
				theReview.setMovie(theMovie);
				System.out.println("theReivew.setMovie");
				theMovie.addReview(theReview);
				System.out.println("lazy load reviews: "+theMovie.getReviews());//might be null?
				//theMovie.addReview(wrapperReview.getReview());
				//System.out.println("lazy getreviews: "+theMovie.getReviews());
				//session.saveOrUpdate(theMovie);
				session.saveOrUpdate(theReview);
				//session.saveOrUpdate(theMovie);
				
				theModel.addAttribute("movie", theMovie);
				
				session.getTransaction().commit();
				
			}finally {
				factory.close();
				session.close();
			}
			//theModel.addAttribute("movie", the)
			System.out.println("theModel: "+theModel);
			System.out.println("END: processReviewForm()");;
			return "list-reviews";
		}
		
}
