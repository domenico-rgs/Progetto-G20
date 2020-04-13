package it.unipv.www.g20.model.cinema;

import java.util.Calendar;

import it.unipv.www.g20.model.booking.Booking;
import it.unipv.www.g20.model.movie.TypeCategory;

public interface Manageable {
	public boolean deleteTheatre(String nome);
	public boolean addMovie(String title, int duration, TypeCategory category, Double ticketPrice);
	public boolean deleteMovie(String title);
	public Booking addBooking(Calendar date);
	public boolean deleteBooking(String nome);
	public boolean addTheatre(String name, int row, int column);
}
