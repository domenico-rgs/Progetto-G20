package it.unipv.www.g20.model.movie;

import java.util.Calendar;

public class MovieShowing {
	private Calendar timestamp;
	private Movie movie;

	public MovieShowing(Movie movie, Calendar data) {
		timestamp=data;
		this.movie=movie;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public Movie getMovie() {
		return movie;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((movie == null) ? 0 : movie.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
		MovieShowing other = (MovieShowing) obj;
		if (movie == null) {
			if (other.movie != null)
				return false;
		} else if (!movie.equals(other.movie))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}


}
