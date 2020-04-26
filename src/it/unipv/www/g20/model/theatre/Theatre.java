package it.unipv.www.g20.model.theatre;

import java.util.HashMap;
/**
 * The theatre of a Cinema, allows you to manage its projections
 */
public class Theatre {
	private String theatreName;
	private HashMap<String, Seat> seatsList;
	
	public Theatre(String theaterName, int row, int column) {
		seatsList = new HashMap<>();
		setTheatreName(theaterName);
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

	/**
	 * @return the capacity of the theatre
	 */

	public String getName() {
		return theatreName;
	}


	/**
	 * Returns, if any, the specified movie show scheduled in the theatre given the projection date
	 * (only a projection in a specific room is possible on a specific date)
	 * @param d the date of the projection
	 * @return the MovieShowing if exists
	 * @throws SearchException
	 */
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((theatreName == null) ? 0 : theatreName.hashCode());
		return result;
	}

	/**
	 * Prints all the projections programmed in the theatre
	 * @return a string with the list of projections
	 */
	public void setTheatreName(String name) {
		this.theatreName = name;
	}

	@Override
	public String toString() {
		return "Theatre: " + theatreName;
	}
	
	@SuppressWarnings("unused")
	private void createSeats(int row, int col) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				seatsList.put(Character.toString(65 + i) + j, new Seat(Character.toString(65 + i) + j));
			}
		}
	}
	public HashMap<String, Seat> getSeatsList() {
		return seatsList;
	}
	public void setSeatsList(HashMap<String, Seat> seatsList) {
		this.seatsList = seatsList;
	}
	public String getTheatreName() {
		return theatreName;
	}
	
}
