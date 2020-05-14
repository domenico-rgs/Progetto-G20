package application.model.movie;

import java.util.HashMap;

import application.model.exception.SearchException;
import application.model.theatre.Seat;

public class Availability {
	private HashMap <Seat,Boolean> availability;

	public Availability(HashMap<String, Seat> list) {
		availability=new HashMap<>();
		genAvailabilityList(list);

	}

	private void genAvailabilityList(HashMap<String, Seat> list) {
		HashMap<String,Seat> seats = list;
		for(String s : list.keySet())
			availability.put(seats.get(s), true);
	}

	public String printSeats() {
		String string="";
		for(Seat s : availability.keySet())
			string+=s.toString()+" - "+availability.get(s)+"\n";
		return string;
	}

	public boolean searchAvailability(String seat) throws SearchException {
		return availability.get(findSeat(seat));
	}

	private Seat findSeat(String seat) throws SearchException {
		for(Seat s : availability.keySet())
			if(s.getPosition().equalsIgnoreCase(seat))
				return s;
		throw new SearchException("Seat's not found");
	}

	public boolean changeAvailability(String seat, boolean value) throws SearchException {
		return !availability.replace(findSeat(seat), value);
	}
}
