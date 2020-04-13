package it.unipv.www.g20.model.theatre;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.unipv.www.g20.model.exception.NotFoundException;
import it.unipv.www.g20.model.movie.Movie;
import it.unipv.www.g20.model.movie.MovieShowing;

public class Theatre implements Organizable{
	private String name;
	private final List<MovieShowing> showingList;
	private final ArrayList<Seat> seatList;
	private int row;
	private int column;

	public Theatre(String theaterName, int row, int column) {
		setName(theaterName);
		this.row=row;
		this.column=column;
		showingList = new ArrayList<>();
		seatList = new ArrayList<>();
		createSeats();
	}

	@Override
	public boolean addMovieShowing(Movie movie, Calendar date) {
		for(int i = 0; i<showingList.size(); i++) {
			showingList.get(i).getTimestamp();
			if((date.before(showingList.get(i).getTimestamp()))&&(date.after(Calendar.MINUTE+movie.getDuration())))
				showingList.add(new MovieShowing(movie, date));
		}
		return false;
	}


	@Override
	public int searchMovieShowing(MovieShowing showing) throws NotFoundException {
		for(int i=0; i<showingList.size(); i++) {
			if(showingList.get(i).equals(showing))
				return i;
		}
		throw new NotFoundException("Movie Showing not found!");
	}

	@Override
	public boolean deleteMovieShowing(MovieShowing showing) {
		try {
			final int pos = searchMovieShowing(showing);
			showingList.remove(pos);
			return true;
		}catch(final NotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public void printShowingList() {
		String s = "Showing List: \n";
		for (final MovieShowing element : showingList) {
			s+= element.toString() +" \n";
		}
		System.out.println(s);

	}
	
	
	public void printAvailableSeats() {
		String s = "Available seats: \n";
		for (final Seat element : seatList) {
			if(element.isAvailable())
				s+= element.toString() +" \n";
		}
		System.out.println(s);
	}

	private void createSeats() {
		for(int i=0; i<row; i++) {
			for(int j=0; j<column; j++) {
				seatList.add(new Seat(Character.toString(65+i)+j));
			}
		}
	}

	public int searchSeat(String id) throws NotFoundException {
		for(int i=0; i<seatList.size(); i++) {
			if(seatList.get(i).getId().equalsIgnoreCase(id))
				return i;
		}
		throw new NotFoundException("Seat not found!");
	}
	
	public boolean setSeat(String id) {
		try {
			Seat seat = seatList.get(searchSeat(id));
			seat.setAvalaible(false);
			return true;
		} catch (NotFoundException e) {
			return false;
		}
	}

	public int getCapacity() {
		return row*column;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Theatre:\n[name=" + name + "\n showingList=" + showingList + "\n seatList=" + seatList + "]";
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

}