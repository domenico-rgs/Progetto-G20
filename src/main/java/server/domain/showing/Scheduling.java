package server.domain.showing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.domain.theatre.Theatre;

public class Scheduling {
	private List<MovieShowing> showingList;

	public Scheduling() {
		showingList = new ArrayList<>();
	}

	public MovieShowing createMovieShowing(Date date, Theatre theatre, Double price){
		MovieShowing tmp = new MovieShowing(date, theatre, price);
		if(showingList.add(tmp))
			return tmp;
		else
			return null;
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

	public void changeAvailability(String showing, String[] seats, boolean value) throws SeatException, SearchException{
		showingList.get(searchShowing(showing)).changeAvailability(seats, value);
	}

	public boolean searchAvailability(String showing, String seat) throws SeatException, SearchException {
		return showingList.get(searchShowing(showing)).searchAvailability(seat);

	}

	public boolean deleteMovieShowing(String id){
		try {
			int showing = searchShowing(id);
			showingList.remove(showing);
			return true;
		} catch (SearchException e) {
			return false;
		}
	}
	
	public int searchShowing(String id) throws SearchException{
		for(int i=0; i<showingList.size(); i++)
			if(showingList.get(i).getId().equalsIgnoreCase(id))
				return i;
		throw new SearchException(id+"'s not found.");
	}

	public List<MovieShowing> getShowingList() {
		return showingList;
	}
}
