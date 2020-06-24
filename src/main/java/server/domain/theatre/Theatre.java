package server.domain.theatre;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


import javax.persistence.*;

import server.domain.exception.SeatException;

/**
 * The theatre of a Cinema, allows you to manage its projections
 */
@Entity
@Table(name = "theatre")
public class Theatre {
	@Id
	@Column(name="theatreName")
	private String theatreName;
	@Transient
	private HashMap<String, Seat> seatsList;
	@Column(name="filePath")
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
	private void createSeats(String file) throws IOException, SeatException {
		BufferedReader seats = new BufferedReader(new FileReader(file));
		String s;
		int rowNum=0;
		while((s = seats.readLine())!= null){
			String[] tmp = s.split("\\s+");
			addSeats(tmp, rowNum++);
		}
		seats.close();
	}

	private void addSeats(String[] row, int rowNum) throws SeatException {
		for(int j = 0; j<row.length-1; j++)
			if(row[j].equalsIgnoreCase("X"))
				seatsList.put(Character.toString(65 + rowNum) + j, new Seat(Character.toString(65 + rowNum) + j));
			else if (row[j].equalsIgnoreCase("P"))
				seatsList.put(Character.toString(65 + rowNum) + j, new PremiumSeat(Character.toString(65 + rowNum) + j));
			else if (row[j].equalsIgnoreCase("D"))
				seatsList.put(Character.toString(65 + rowNum) + j, new DisabledSeat(Character.toString(65 + rowNum) + j));
			else
				throw new SeatException("Unrecognized seat type, recheck the file");
	}

	//Serve ?? Se si, da costruire e aggiungere chiamata da cinema
	public String printConfiguration() throws IOException {
		//TO-DO
		return filePath;
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
