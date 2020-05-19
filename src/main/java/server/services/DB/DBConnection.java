package server.services.DB;

import org.hibernate.SessionFactory;

import server.domain.movie.Movie;
import server.domain.movie.MovieShowing;

public class DBConnection {
    private SessionFactory sessionFactory;
    private TicketServices ticketServices;

	public DBConnection() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        ticketServices = new TicketServices(this.sessionFactory);
    }
	
	 /**
     * Create the ticket and store it in the DB
     */
    public void addTicket (Movie movie, String seat, MovieShowing movieShowing, double price) {
        ticketServices.addTicket(movie,seat,movieShowing, price);
    }
}
