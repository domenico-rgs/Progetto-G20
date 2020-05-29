package server.domain.movie;

import java.util.Date;
import java.util.HashMap;

import server.domain.exception.SearchException;
import server.domain.theatre.Theatre;

public class Scheduling {
	private HashMap<String, MovieShowing> showingList;
	
	public Scheduling() {
		showingList = new HashMap<>();
	}
	
	public MovieShowing createMovieShowing(Date date, Theatre theatre, Double price){
		if(!overlappingControl(date)) {
			MovieShowing tmp = new MovieShowing(date, theatre, price);
			return showingList.put(tmp.getId(), tmp);
		}
		return null;
	}
	
	private boolean overlappingControl(Date date) {
		for(String s : showingList.keySet()) {
			MovieShowing show = showingList.get(s);
			long t= show.getDate().getTime();
			Date after=new Date(t + duration);
			if(date.compareTo(show.getDate())>=0 && date.compareTo(after)<=0)
				return true;
		}
		return false;
	}

	public MovieShowing deleteMovieShowing(String id) throws SearchException{
		if (!(showingList.containsKey(id)))
			throw new SearchException(id+"'s not found.");
		return showingList.remove(id);
	}

	public MovieShowing searchShowing(String id) throws SearchException{
		if(showingList.get(id)==null)
			throw new SearchException("Movieshowing's not found");
		return showingList.get(id);
	}
}
