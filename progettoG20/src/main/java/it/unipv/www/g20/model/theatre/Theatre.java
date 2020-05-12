package it.unipv.www.g20.model.theatre;

import java.util.HashMap;
import java.util.Set;

import it.unipv.www.g20.model.exception.SearchException;

/**
 * The theatre of a Cinema, allows you to manage its projections
 */
public class Theatre {
	private String theatreName;
	private HashMap<String, Seat> seatsList;
	private int row;
	private int col;

	public Theatre(String theatreName, int row, int col) {
		seatsList = new HashMap<String, Seat>();
		setTheatreName(theatreName);
		this.col=col;
		this.row=row;
		createSeats(row,col);
	}

	/*
	 * Create the seats in the room by associating them with an id consisting of a
	 * letter that identifies the row and a number that identifies the column
	 */
	private void createSeats(int row, int col) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				seatsList.put(Character.toString(65 + i) + j, new Seat(Character.toString(65 + i) + j));
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Theatre other = (Theatre) obj;
		if (theatreName == null) {
			if (other.theatreName != null)
				return false;
		} else if (!theatreName.equals(other.theatreName))
			return false;
		return true;
	}

	public String getName() {
		return theatreName;
	}

	public Seat searchSeat(String position) throws SearchException{
		if(seatsList.get(position)==null)
			throw new SearchException("Seat's not found");
		return seatsList.get(position);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((theatreName == null) ? 0 : theatreName.hashCode());
		return result;
	}

	public void setTheatreName(String name) {
		theatreName = name;
	}

	@Override
	public String toString() {
		return "Theatre: " + theatreName;
	}

	public HashMap<String, Seat> getSeatsList() {
		return seatsList;
	}

	public Set<String> getKeySeats(){
		return seatsList.keySet();
	}
	public void setSeatsList(HashMap<String, Seat> seatsList) {
		this.seatsList = seatsList;
	}
	public String getTheatreName() {
		return theatreName;
	}

	public int getCapacity() {
		return row*col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}



}
