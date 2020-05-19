package server.services.DB;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import server.domain.movie.Movie;
import server.domain.movie.MovieShowing;
import server.domain.ticket.Ticket;

public class TicketServices {
	private SessionFactory sessionFactory;
	private Session s;
	
	 public TicketServices(SessionFactory sessionFactory) {
	     this.sessionFactory = sessionFactory;
	 }
	
	void addTicket(Movie movie, String seat, MovieShowing movieShowing, double price) {
		s= sessionFactory.openSession();
		
		try {
			System.out.println("ok");
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
}
