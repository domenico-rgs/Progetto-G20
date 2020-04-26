package it.unipv.www.g20.model.tester;

import it.unipv.www.g20.controller.cinema.Cinema;
import it.unipv.www.g20.model.movie.Movie;

public class Tester {

	public static void main(String[] args) {
		Cinema cin1 = new Cinema("Uci");
		
		cin1.addMovie("Harry Potter and the order of fenix", 120);
		cin1.addMovie("The big short", 95);
		cin1.addMovie("The great Gatsby", 115);
		cin1.addMovie("The place", 200);
		
		cin1.createTheatre("theatre1", 10, 10);
		cin1.createTheatre("theatre2", 5, 4);
		cin1.createTheatre("theatre3", 3, 30);
		cin1.createTheatre("theatre4", 15, 5);
		
		cin1.searchMovie("The big short").addMovieShowing(date, theatre, price)
}
