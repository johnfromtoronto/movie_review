package com.springmvc.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springmvc.entity.Genre;
import com.springmvc.entity.Movie;

@Repository
public class MovieDAOImpl implements MovieDAO {
	//DAO needs a session factory which talks to the database for it
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<Movie> getMovies() {
		System.out.println("BEGIN: DAO: List<Movie> getMovies()");
		Session session = sessionFactory.getCurrentSession();
//		Session session;
//
//		try {
//		    session = sessionFactory.getCurrentSession();
//		} catch (HibernateException e) {
//		    session = sessionFactory.openSession();
//		}
		
		System.out.println("m1");
		Query<Movie> query = session.createQuery("from Movie", Movie.class);
		List<Movie> movies = query.getResultList();
		for(Movie movie: movies) {
			System.out.println(movie.getGenres());//lazy load all the genres
		}
		System.out.println("m2");
		//session.getTransaction().commit();
		//session.close();
		System.out.println("END: DAO: List<Movie> getMovies()");
		return movies;
	}

	@Override
	public void saveMovie(Movie theMovieByUser) {
		System.out.println("BEGIN: DAO: public void saveMovie(Movie theMovieByUser)");
		Session session = sessionFactory.getCurrentSession();
//		Session session;
//		try {
//			System.out.println("calling .getCurrentSession");
//		    session = sessionFactory.getCurrentSession();
//		} catch (HibernateException e) {
//			System.out.println("calling .openSession");
//		    session = sessionFactory.openSession();
//		}
		//get the genres that user selected, 
		List<Genre> genres = theMovieByUser.getGenres();//used for looping and checking what genres the movie has
		List<Genre> setGenre = new ArrayList<Genre>();//used as a placeholder List to store references to genres from db
		
		Genre horror = session.get(Genre.class, 1);//Horror reference from db
		Genre comedy = session.get(Genre.class, 2);//Comedy reference from db
		Genre action = session.get(Genre.class, 3);//Action reference from db
		for(Genre genre : genres) {
			String movieGenre=genre.getMovieGenre();
			switch(movieGenre) {
			case "Horror":
				System.out.println("switch statement, add horror");
				setGenre.add(horror);
				break;
			case "Comedy":
				System.out.println("switch statement, add comedy");
				setGenre.add(comedy);
				break;
			case "Action":
				System.out.println("switch statement, add action");
				setGenre.add(action);
				break;
			default:
				System.out.println("switch statement, default");
				break;
			}
		}
		System.out.println("setting the genre");
		theMovieByUser.setGenres(setGenre);//set the genre without add new genres
		System.out.println("lazy loading the genres from movie");
		theMovieByUser.getGenres();//lazy load
		System.out.println("saving the movie");
		session.saveOrUpdate(theMovieByUser);//SAVE Or UPDATE
		
		System.out.println("TheMovieReviews: "+theMovieByUser.getReviews());
		System.out.println("END: DAO: public void saveMovie(Movie theMovieByUser)");
	}

	@Override
	public void deleteMovieById(int movieId) {
		System.out.println("BEGIN: DAO: public void deleteMovieById(int movieId)");
		Session session = sessionFactory.getCurrentSession();
		//		Session session;
//		try {
//			System.out.println("calling .getCurrentSession");
//		    session = sessionFactory.getCurrentSession();
//		} catch (HibernateException e) {
//			e.printStackTrace();
//			System.out.println("calling .openSession");
//		    session = sessionFactory.openSession();
//		    //session = sessionFactory.getCurrentSession();
//		}
//		Movie theMovie = session.get(Movie.class, movieId);
//		session.delete(theMovie);
		//delete all Reviews referencing the movie, then delete the movie
		Query query = session.createQuery("delete from Review where movie_id=:movieId");
		query.setParameter("movieId", movieId);
		query.executeUpdate();
		query = session.createQuery("delete from Movie where id=:movieId");
		query.setParameter("movieId", movieId);//Named parameter [movieId] not set
		query.executeUpdate();


		
		//session.close();
		System.out.println("END: DAO: public void deleteMovieById(int movieId)");
	}

	@Override
	public Movie getMovieById(int movieId) {
		System.out.println("BEGIN: DAO: public Movie getMovieById(int movieId) {");
		Session session = sessionFactory.getCurrentSession();
		
		Movie theMovie = session.get(Movie.class, movieId);
		System.out.println("lazy init genres: "+theMovie.getGenres());
		System.out.println("lazy init reviews: "+theMovie.getReviews());
		//theMovie.getGenres();//lazy load these guys
		//System.out.println(".getGenres: "+theMovie.getGenres());
		System.out.println("END: DAO: public Movie getMovieById(int movieId) {");
		return theMovie;
		
	}

	@Override
	public Movie getMovieReviewsById(int movieId) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Movie> query = session.createQuery("select i from Movie i "
				+ "JOIN FETCH i.reviews "
				+ "where i.id=:theMovieId",
				Movie.class);
		query.setParameter("theMovieId", movieId);
		Movie theMovie = null;
		
		//Movie theMovie = query.getSingleResult();
		List results = query.getResultList();//this will attempt to get the movie and all its reviews
		if(!results.isEmpty()) {
			//if query was successful, get the movie from the results
			theMovie = (Movie) results.get(0);
			System.out.println("theMovieeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee: "+theMovie);
			
		}else {
			System.out.println("FAilllllllllllllllllllllllllllllllleedd");
			//if query failed, attempt to get the movie and it's review using .get()
			theMovie = session.get(Movie.class, movieId);
			System.out.println("theMovie lazy: "+theMovie.getReviews());
		}
		return theMovie;
	}

}
