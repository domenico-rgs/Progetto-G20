package server.services.DB;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import server.domain.cinema.Movie;
import server.domain.showing.MovieShowing;
import server.domain.theatre.Theatre;

public class CinemaServices {
	private SessionFactory sessionFactory;
	private Session s;

	public CinemaServices(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	void addMovie(Movie movie) {
		s= sessionFactory.openSession();

		try {
			s.beginTransaction();
			s.save(movie);
			s.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			s.close();
		}
	}
	
	ArrayList<String> movieList() {
        s = sessionFactory.openSession();
        ArrayList<String> Mlist = new ArrayList<>();

        String sql = "FROM  Movie";
        try {
            Query query = s.createQuery(sql);
            List<Movie> list = (List<Movie>)query.getResultList();
            for (int i = 0; i < list.size(); i++) {
                Mlist.add(list.get(i).getTitle());
            }
            return Mlist;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            s.close();
        }
        return null;
    }
	
	void addTheatre(Theatre theatre) {
		s= sessionFactory.openSession();

		try {
			s.beginTransaction();
			s.save(theatre);
			s.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			s.close();
		}
	}
	
	void addMovieShowing(MovieShowing show) {
		s= sessionFactory.openSession();

		try {
			s.beginTransaction();
			s.save(show);
			s.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			s.close();
		}
	}
}
