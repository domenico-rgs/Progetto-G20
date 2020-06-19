package server.domain.movie;

import java.util.HashMap;

import server.domain.exception.SeatException;
import server.domain.theatre.Seat;

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

	protected boolean searchAvailability(String seat) throws SeatException {
		return availability.get(findSeat(seat));
	}

	private Seat findSeat(String seat) throws SeatException {
		for(Seat s : availability.keySet())
			if(s.getPosition().equalsIgnoreCase(seat))
				return s;
		throw new SeatException("Seat's not found");
	}

	protected boolean changeAvailability(String initSeat, String finalSeat, boolean value) throws SeatException{
		if(!value)
			return occupySeat(initSeat, finalSeat);
		else
			return freeSeat(initSeat, finalSeat);
	}

	private boolean freeSeat(String initSeat, String finalSeat) throws SeatException {
		Seat[] seat = new Seat[finalSeat.charAt(1)-initSeat.charAt(1)];
		int j=0;
		for(int i=initSeat.charAt(1); i<(finalSeat.charAt(1)); i++) {
			seat[j]=findSeat(Character.toString(initSeat.charAt(0))+i);
			j++;
		}

		for(int i=0; i<seat.length; i++)
			availability.replace(seat[i], false);

		return true;
	}

	private boolean occupySeat(String initSeat, String finalSeat) throws SeatException {
		if(initSeat.charAt(0)!=finalSeat.charAt(0))
			throw new SeatException("You can only book seats in the same line");

		Seat[] seat = new Seat[finalSeat.charAt(1)-initSeat.charAt(1)];
		int j=0;
		for(int i=initSeat.charAt(1); i<(finalSeat.charAt(1)); i++) {
			seat[j]=findSeat(Character.toString(initSeat.charAt(0))+i);
			if(availability.get(seat[j])!=true)
				throw new SeatException("Some seats are alredy booked");
			j++;
		}


		for(int i=0; i<seat.length; i++)
			availability.replace(seat[i], true);
		return true;
	}
}
