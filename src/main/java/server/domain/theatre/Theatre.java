package server.domain.theatre;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import server.domain.exception.SeatException;

/**
 * The theatre of a Cinema, allows you to manage its projections
 */
public class Theatre {
	private String theatreName;
	private HashMap<String, Seat> seatsList;
	private String filePath;


	public Theatre(String theatreName) throws IOException, SeatException {
		seatsList = new HashMap<>();
		this.theatreName=theatreName;
	}

	/*
	 * Create the seats in the room by associating them with an id consisting of a
	 * letter that identifies the row and a number that identifies the column
	 */
	public void createSeats(String file) throws IOException, SeatException {
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

	public String createConfigFile(String config) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new File("src/main/resources/theatreConf/" + theatreName+".txt"));

		out.println(config);
		out.close();
		filePath =  "src/main/resources/theatreConf/" + theatreName+".txt";
		return filePath;
	}

	@Override
	public String toString() {
		return "Theatre: " + theatreName;
	}

	public HashMap<String, Seat> getSeatsList() {
		return seatsList;
	}

	public void setSeatsList(HashMap<String, Seat> seatsList) {
		this.seatsList = seatsList;
	}

	public String getFilePath() {
		return filePath;
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
