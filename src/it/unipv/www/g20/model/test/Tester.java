package it.unipv.www.g20.model.test;

import java.text.ParseException;

import it.unipv.www.g20.model.cinema.Cinema;
import it.unipv.www.g20.model.exception.NotAvailableException;
import it.unipv.www.g20.model.exception.NotPermittedException;
import it.unipv.www.g20.model.exception.SearchException;
import it.unipv.www.g20.model.operator.Manager;
import it.unipv.www.g20.model.operator.Operator;
import it.unipv.www.g20.model.operator.TypeOperator;

public class Tester {

	public static void main(String[] args) {
		final Cinema cinema = new Cinema("Cinema Lupo");
		final Operator man = new Manager("Muciaccia"); // può fare tutto

		try {
			cinema.addOperator("Buchanan", TypeOperator.CASHIER, man); // può solo vendere ticket
			cinema.addTheatre("Art Attack", 10, 10, man);
			cinema.addMovie("Matrix", man);
			cinema.createMovieShowing("Matrix", "Art Attack", "17/04/2020 18:00", 12.3, man);

			cinema.addTicket("Art Attack", "17/04/2020 18:00", "A4");
			
			cinema.addTicket("Art Attack", "17/04/2020 18:00", "B6");

			cinema.addTicket("Art Attack", "17/04/2020 18:00", "A4");

		} catch (NotPermittedException | SearchException | ParseException | NotAvailableException e) {
			System.out.println(e.getMessage());
		}finally {
			System.out.println(cinema.printTicketList());
		}

	}

}
