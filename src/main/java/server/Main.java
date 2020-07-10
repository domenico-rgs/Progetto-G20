package server;

import java.time.LocalDateTime;

import server.domain.cinema.Cinema;
import server.domain.cinema.TypeCategory;
import server.domain.showing.MovieShowing;

public class Main {
	public static void main(String[] argv) throws Exception {
		
		//funzioni di testing
		

		ApplicationServer server = new ApplicationServer(8080, new CinemaServlet());
		
		server.start();
		
		
		


	}
}
