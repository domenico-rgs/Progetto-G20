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
	
	public void editShowing(String showing, String theatre, double price) throws SearchException {
		searchShowing(showing).editShowing(theatre, price);
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
		searchShowing(showing).changeAvailability(seats, value);
	}

	public boolean searchAvailability(String showing, String seat) throws SeatException, SearchException {
		return searchShowing(showing).searchAvailability(seat);

	}

	public boolean deleteMovieShowing(String id){
		try {
			showingList.remove(searchShowing(id));
			return true;
		} catch (SearchException e) {
			return false;
		}
	}
	
	public MovieShowing searchShowing(String id) throws SearchException{
		for(int i=0; i<showingList.size(); i++)
			if(showingList.get(i).getId().equalsIgnoreCase(id))
				return showingList.get(i);
		throw new SearchException(id+"'s not found.");
	}

	public List<MovieShowing> getShowingList() {
		return showingList;
	}
}
