package server.services.DB;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import server.domain.cinema.Movie;
import server.domain.cinema.TypeCategory;
import server.domain.exception.SearchException;
import server.domain.showing.MovieShowing;
import server.domain.theatre.Theatre;

public class CinemaServices {
	private SessionFactory sessionFactory;
	private Session s;

	public CinemaServices(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	void addMovie(String title, int duration, String plot, String pathCover, TypeCategory category) throws SearchException {
		s= sessionFactory.openSession();

		try {
			s.beginTransaction();
			Movie m = new Movie(title, duration, plot, pathCover, category);
			s.save(m);
			s.getTransaction().commit();
		}catch(Exception e) {
			throw new SearchException();
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
			List<Movie> list = query.getResultList();
			for (int i = 0; i < list.size(); i++)
				Mlist.add(list.get(i).getTitle());
			return Mlist;
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			s.close();
		}
		return null;
	}
	
	ArrayList<MovieShowing> getShowingList(Movie movie) {
		s = sessionFactory.openSession();
		ArrayList<MovieShowing> Slist = new ArrayList<>();

		String sql = "FROM  MovieShowing where movie=:movie";
		try {
			Query query = s.createQuery(sql);
            query.setParameter("movie",movie);
			List<MovieShowing> list = query.getResultList();
			for (int i = 0; i < list.size(); i++)
				Slist.add(list.get(i));
			return Slist;
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			s.close();
		}
		return null;
	}
	
	
	Theatre searchTheatre(String theatreName) {
		s = sessionFactory.openSession();

		String sql = "FROM Theatre where theatreName=:name";
		try {
			Query query = s.createQuery(sql);
            query.setParameter("name",theatreName);
            
            return (Theatre)query.getSingleResult();

		} catch (Exception e){
			e.printStackTrace();
		} finally {
			s.close();
		}
		return null;
	}
	
	Movie searchMovie(String movieTitle) {
		s = sessionFactory.openSession();

		String sql = "FROM Movie where title=:title";
		try {
			Query query = s.createQuery(sql);
            query.setParameter("title",movieTitle);
            
            return (Movie)query.getSingleResult();

		} catch (Exception e){
			e.printStackTrace();
		} finally {
			s.close();
		}
		return null;
	}
	
	MovieShowing searchShowing(String showingID) {
		s = sessionFactory.openSession();

		String sql = "FROM MovieShowing where id=:ID";
		try {
			Query query = s.createQuery(sql);
            query.setParameter("ID",showingID);
            
            return (MovieShowing)query.getSingleResult();

		} catch (Exception e){
			e.printStackTrace();
		} finally {
			s.close();
		}
		return null;
	}
	
	void editShowing(String id,  Theatre theatre, double price){
		 s = sessionFactory.openSession();

	        try {
	            s.beginTransaction();
	            MovieShowing ms = s.get(MovieShowing.class,id);	    
	            ms.editShowing(theatre, price);
	    
	            s.saveOrUpdate(ms);
	            s.getTransaction().commit();
	        } catch (Exception e){
	            e.printStackTrace();
	        } finally {
	            s.close();
	        }
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
