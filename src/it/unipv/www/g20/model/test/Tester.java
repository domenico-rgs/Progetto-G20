package it.unipv.www.g20.model.test;

import java.text.ParseException;

import it.unipv.www.g20.model.cinema.Cinema;
import it.unipv.www.g20.model.exception.NotAvailableException;
import it.unipv.www.g20.model.exception.SearchException;

public class Tester {

	public static void main(String[] args) {
		final Cinema cinema = new Cinema("Cinema Lupo");

		try {
			cinema.addTheatre("Art Attack", 10, 10);
			cinema.addMovie("Matrix");
			cinema.createMovieShowing("Matrix", "Art Attack", "17/04/2020 18:00", 12.3);

			cinema.addTicket("Art Attack", "17/04/2020 18:00", "A4");

			cinema.addTicket("Art Attack", "17/04/2020 18:00", "B6");

			cinema.addTicket("Art Attack", "17/04/2020 18:00", "A4");

		} catch (SearchException | ParseException | NotAvailableException e) {
			System.out.println(e.getMessage());
		}finally {
			System.out.println(cinema.printProgramming());
			System.out.println(cinema.printTicketList());
		}

	}

}
