package server.domain.theatre;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

//import javax.persistence.*;	//importazione  non richiesta

import server.domain.exception.SeatException;

/**
 * The theatre of a Cinema, allows you to manage its projections
 */
//@Entity
//@Table(name = "theatre")
public class Theatre {
	//@Id
	//@Column(name="theatreName")
	private String theatreName;
	private HashMap<String, Seat> seatsList;
	//@Column(name="filePath")
	private String filePath;

	public Theatre() {}

	public Theatre(String theatreName, String filePath) throws IOException, SeatException {
		seatsList = new HashMap<>();
		this.theatreName=theatreName;
		this.filePath=filePath;
		createSeats(filePath);
	}

	/*
	 * Create the seats in the room by associating them with an id consisting of a
	 * letter that identifies the row and a number that identifies the column
	 */
	@SuppressWarnings("resource")
	private void createSeats(String file) throws IOException, SeatException {
		BufferedReader seats = new BufferedReader(new FileReader(file));
		String s;
		int i=0;
		while((s = seats.readLine())!= null){
			String[] tmp = s.split("\\s+");
			for(int j = 0; j<tmp.length-1; j++) {
				switch (tmp[j]) {
				case "X":
					seatsList.put(Character.toString(65 + i) + j, new Seat(Character.toString(65 + i) + j));
					break;
				case "P":
					seatsList.put(Character.toString(65 + i) + j, new PremiumSeat(Character.toString(65 + i) + j));
					break;
				case "D":
					seatsList.put(Character.toString(65 + i) + j, new DisabledSeat(Character.toString(65 + i) + j));
					break;
				default:
					throw new SeatException("Unrecognized seat type, recheck the file");
				}
				i++;
			}
		}
	}

	@SuppressWarnings("resource")
	public String printConfiguration() throws IOException {
		BufferedReader seats = new BufferedReader(new FileReader(filePath));
		String s;
		StringBuilder string = new StringBuilder();
		while((s = seats.readLine())!= null)
			string.append(s+"\n");
		return string.toString();
	}

	@Override
	public String toString() {
		return "Theatre: " + theatreName;
	}

	public HashMap<String, Seat> getSeatsList() {
		return seatsList;
	}

	public String getTheatreName() {
		return theatreName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((filePath == null) ? 0 : filePath.hashCode());
		result = (prime * result) + ((theatreName == null) ? 0 : theatreName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Theatre other = (Theatre) obj;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (theatreName == null) {
			if (other.theatreName != null)
				return false;
		} else if (!theatreName.equals(other.theatreName))
			return false;
		return true;
	}
}
