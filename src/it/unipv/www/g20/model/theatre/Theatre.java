package it.unipv.www.g20.model.theatre;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.unipv.www.g20.model.exception.NotFoundException;
import it.unipv.www.g20.model.movie.Movie;
import it.unipv.www.g20.model.movie.MovieShowing;

public class Theatre implements Organizable{
	private String name;
	private List<MovieShowing> showingList;
	private ArrayList<Seat> seatList;
	private int row;
	private int column;

	public Theatre(String theaterName, int capacity) {
		setName(theaterName);
		showingList = new ArrayList<>();
		seatList = new ArrayList<>();
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
			int pos = searchMovieShowing(showing);
			showingList.remove(pos);
			return true;
		}catch(NotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public void printShowingList() {
		String s = "Showing List: \n";
		for (MovieShowing element : showingList) {
			s+= element.toString() +" \n";
		}
		System.out.println(s);

	}

	@SuppressWarnings("unused")
	private void createSeats(int capienza) {
		for(int i=0; i<row; i++) {
			for(int j=0; j<column; j++) {
				seatList.add(new Seat(" "));
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

	public int getCapacity() {
		return row*column;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
