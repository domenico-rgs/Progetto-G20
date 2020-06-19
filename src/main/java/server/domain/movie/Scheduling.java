package server.domain.movie;

import java.util.Date;
import java.util.HashMap;

import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.domain.theatre.Theatre;

public class Scheduling {
	private HashMap<String, MovieShowing> showingList;

	public Scheduling() {
		showingList = new HashMap<>();
	}

	public MovieShowing createMovieShowing(Date date, Theatre theatre, Double price){
		MovieShowing tmp = new MovieShowing(date, theatre, price);
		return showingList.put(tmp.getId(), tmp);
	}

	/*private boolean overlappingControl(Date date) {
		for(String s : showingList.keySet()) {
			MovieShowing show = showingList.get(s);
			long t= show.getDate().getTime();
			Date after=new Date(t + duration);
			if(date.compareTo(show.getDate())>=0 && date.compareTo(after)<=0)
				return true;
		}
		return false;
	}*/

	public boolean changeAvailability(String showing, String initSeat, String finalSeat, boolean value) throws SeatException{
		return showingList.get(showing).changeAvailability(initSeat, finalSeat, value);
	}

	public boolean searchAvailability(String showing, String seat) throws SeatException {
		return showingList.get(showing).searchAvailability(seat);

	}

	public boolean deleteMovieShowing(String id) throws SearchException{
		if(searchShowing(id)!=null) {
			showingList.remove(id);
			return true;
		}else
			return false;
	}

	public MovieShowing searchShowing(String id) throws SearchException{
		if (!(showingList.containsKey(id)))
			throw new SearchException(id+"'s not found.");
		else
			return showingList.get(id);
	}
}
