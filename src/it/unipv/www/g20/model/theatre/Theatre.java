package it.unipv.www.g20.model.theatre;

import java.util.HashMap;
import java.util.Set;

/**
 * The theatre of a Cinema, allows you to manage its projections
 */
public class Theatre {
	private String theatreName;
	private HashMap<String, Seat> seatsList;
	private int row;
	private int col;

	public Theatre(String theatreName, int row, int col) {
		seatsList = new HashMap<>();
		setTheatreName(theatreName);
		this.col=col;
		this.row=row;
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

	public Seat searchSeat(String position) {
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

}
