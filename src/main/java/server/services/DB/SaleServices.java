package server.services.DB;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import server.domain.cinema.Movie;
import server.domain.cinema.Ticket;
import server.domain.movie.MovieShowing;

public class SaleServices {
	private SessionFactory sessionFactory;
	private Session s;

	public SaleServices(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	void addTicket(Movie movie, String seat, MovieShowing movieShowing, double price) {
		s= sessionFactory.openSession();

		try {
			s.beginTransaction();
			Ticket t = new Ticket(movie.getTitle(), seat, movieShowing.toString(), price);
			s.save(t);
			s.getTransaction().commit();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			s.close();
		}
	}

	void deleteTicket(String id){
		s = sessionFactory.openSession();

		try {
			s.beginTransaction();
			String sql ="delete from ticket where id is "+id+";";
			NativeQuery query = s.createSQLQuery(sql);
			query.executeUpdate();
			s.getTransaction().commit();
		}catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
	}

	Ticket getTicket(String id) {
		s = sessionFactory.openSession();
		try {
			s.beginTransaction();
			String sql ="select * from ticket where id is "+id+";";
			Query query = s.createQuery(sql);
			return (Ticket) query.getSingleResult();
		}catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		return null;
	}


}
