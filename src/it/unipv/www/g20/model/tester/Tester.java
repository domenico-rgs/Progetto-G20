package it.unipv.www.g20.model.tester;

import java.util.Date;

import it.unipv.www.g20.controller.cinema.Cinema;
import it.unipv.www.g20.model.exception.SearchException;
import it.unipv.www.g20.model.movie.Movie;
import it.unipv.www.g20.model.movie.MovieShowing;
import it.unipv.www.g20.model.ticket.Ticket;
import it.unipv.www.g20.view.TextUI.AdminTextUI;

public class Tester {

	@SuppressWarnings({ "deprecation"})
	public static void main(String[] args) {
		AdminTextUI prova = new AdminTextUI(new Cinema("prova"));
		
		prova.start();
	}
}
