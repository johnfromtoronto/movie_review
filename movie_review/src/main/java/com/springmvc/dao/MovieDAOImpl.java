package com.springmvc.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springmvc.entity.Movie;

@Repository
public class MovieDAOImpl implements MovieDAO {
	//DAO needs a session factory which talks to the database for it
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Movie> getMovies() {
		System.out.println("BEGIN: List<Movie> getMovies()");
		//Session session = sessionFactory.getCurrentSession();
		Session session;

		try {
		    session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
		    session = sessionFactory.openSession();
		}
		
		System.out.println("m1");
		Query<Movie> query = session.createQuery("from Movie", Movie.class);
		List<Movie> movies = query.getResultList();
		for(Movie movie: movies) {
			System.out.println(movie.getGenres());//lazy load all the genres
		}
		System.out.println("m2");
		//session.getTransaction().commit();
		//session.close();
		System.out.println("END: List<Movie> getMovies()");
		return movies;
	}

}
