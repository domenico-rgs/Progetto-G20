package server.domain.cinema.theatre;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import server.exception.SeatException;

/** This class represents a cinema's theatre. */
public class Theatre {
	private String theatreName, filePath;
	private Map<String, Seat> seatsList;

	public Theatre(String theatreName) throws IOException, SeatException {
		seatsList = new HashMap<>();
		this.theatreName=theatreName;
	}

	/**
	 * Create the seats in the room by associating them with an id consisting of a
	 * letter that identifies the row and a number that identifies the column
	 * @param file file used to create the positioning of the seats in a theatre
	 * @throws IOException if there are problems with the file
	 * @throws SeatException problems recognizing the seat (e.g. wrong identifier (x, d, p))
	 */
	public void createSeats(String config) throws IOException, SeatException {
		String[] s = config.split("\\n");
		int rowNum=0;
		for(int i =0; i<s.length; i++) {
			String[] tmp = s[i].split("\\s+");
			addSeats(tmp, rowNum++); //adds seats for the current row to the seat list
		}
		createConfigFile(config);
	}

	/**
	 * This is a private method which adds seats to the seat list
	 * @param row seats lines
	 * @param rowNum number of seats lines
	 * @throws SeatException this exception occurs if there are no correct inputs in the lines
	 */
	private void addSeats(String[] row, int rowNum) throws SeatException {
		for(int j = 0; j<row.length; j++)
			if(row[j].equalsIgnoreCase("X")) {
				seatsList.put(Character.toString(65 + rowNum) + j, new Seat(Character.toString(65 + rowNum) + j));
			} else if (row[j].equalsIgnoreCase("P")) {
				seatsList.put(Character.toString(65 + rowNum) + j, new PremiumSeat(Character.toString(65 + rowNum) + j));
			} else if (row[j].equalsIgnoreCase("D")) {
				seatsList.put(Character.toString(65 + rowNum) + j, new DisabledSeat(Character.toString(65 + rowNum) + j));
			} else
				throw new SeatException("Unrecognized seat type, recheck the file");
	}

	/**
	 * It creates a configuration file.
	 * @param config this is the string which represents the theatre's seats configuration
	 * @return return path's configuration file
	 * @throws FileNotFoundException it occurs if the received string is not correct
	 */
	private String createConfigFile(String config) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new File("src/main/resources/theatreConf/" + theatreName+".txt"));
		config = config.replaceAll("[^\\S\\r\\n]+"," "); //the regex expression is used to remove "extra" spaces if they are added

		out.println(config);
		out.close();
		filePath =  "src/main/resources/theatreConf/" + theatreName+".txt";
		return filePath;
	}

	@Override
	public String toString() {
		return "Theatre: " + theatreName;
	}

	public Map<String, Seat> getSeatsList() {
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
