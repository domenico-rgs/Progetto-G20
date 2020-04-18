package it.unipv.www.g20.model.cinema;

import java.text.ParseException;

import it.unipv.www.g20.model.exception.NotAvailableException;
import it.unipv.www.g20.model.exception.NotPermittedException;
import it.unipv.www.g20.model.exception.SearchException;
import it.unipv.www.g20.model.operator.Operator;
import it.unipv.www.g20.model.operator.TypeOperator;

/** This interface organizes all activities of the Cinema */
public interface Manageable {

	/**
	 * It permits to add a movie.
	 *
	 * @param title movie's title
	 * @return true if movie is added
	 * @throws NotPermittedException
	 * @throws SearchException
	 */
	public boolean addMovie(String title, Operator op) throws NotPermittedException, SearchException;

	/**
	 * It permits to add a Cinema Cashier or a Cinema Manager.
	 *
	 * @param nickname identifiear of the operator
	 * @throws NotPermittedException
	 */
	public void addOperator(String nickname, TypeOperator type, Operator op) throws NotPermittedException;

	/**
	 * It permits to add a theatre.
	 *
	 * @param TheatreName theatre's name
	 * @param lines       number of rows of seats
	 * @param lineSeats   number of seat columns
	 * @return true if theatre is added.
	 * @throws NotPermittedException
	 * @throws SearchException
	 */
	public boolean addTheatre(String TheatreName, int lines, int lineSeats, Operator op)
			throws NotPermittedException, SearchException;

	/**
	 * Create and save a new ticket
	 * @param theatreName name of the theatre where the film will be shown
	 * @param date date of the movieShowing
	 * @param seatCode code of the seat booked (if available)
	 * @throws ParseException
	 * @throws NotAvailableException
	 * @throws SearchException
	 */
	public void addTicket(String theatreName, String date, String seatCode)
			throws ParseException, NotAvailableException, SearchException;

	/**
	 * Create a new MovieShowing
	 * @param movieTitle title of the movie
	 * @param theatreName where the movie will be projected
	 * @param date date of the projection
	 * @param price standard price of the projection
	 * @param op to check if the operator has the permission to do this action
	 * @throws NotPermittedException
	 * @throws ParseException
	 * @throws SearchException
	 */
	public void createMovieShowing(String movieTitle, String theatreName, String date, Double price, Operator op)
			throws NotPermittedException, ParseException, SearchException;

	/**
	 * It permits to delete a movie
	 *
	 * @param title movie's title
	 * @return true if movie is deleted.
	 * @throws NotPermittedException
	 * @throws SearchException
	 */
	public boolean deleteMovie(String title, Operator op) throws NotPermittedException, SearchException;

	/**
	 * It permits to delete a cashier or a manager.
	 *
	 * @param nickname operator's id
	 * @return true if the operator is deleted.
	 * @throws NotPermittedException
	 * @throws SearchException
	 */
	public boolean deleteOperator(String nickname, Operator op) throws NotPermittedException, SearchException;

	/**
	 * It permits to delete a theatre.
	 *
	 * @param TheatreName theatre's name
	 * @return true if theatre is deleted
	 * @throws NotPermittedException
	 * @throws SearchException
	 */
	public boolean deleteTheatre(String TheatreName, Operator op) throws NotPermittedException, SearchException;

	/**
	 * Detele a saved ticket
	 * @param code ticket's code
	 * @throws SearchException
	 */
	public void deleteTicket(String code) throws SearchException;
}
