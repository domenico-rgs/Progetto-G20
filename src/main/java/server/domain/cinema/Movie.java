package server.domain.cinema;

import server.domain.movie.MovieShowing;
import server.domain.movie.TypeCategory;

/**
 * This class is referred to a movie
 * @see MovieShowing
 */
//@Entity
//@Table(name="movie")
public class Movie {
	//@Id
	//@Column(name="title")
	private String title;
	//@Column(name="duration")
	private int duration; //in minutes
	//@Column(name="category")
	private TypeCategory category;
	//@Column(name="plot")
	private String plot;
	private String pathCover;

	public Movie() {}

	public Movie(String title, int duration, String plot, String pathCover, TypeCategory category) {
		this.title=title;
		this.duration=duration;
		this.category=category;
		this.plot=plot;
		this.pathCover=pathCover;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		String s = "Title: " + title + "\n";
		s += " Duration: " + duration + " minutes\n";
		s += " Category: " + (category == null ? "not set":category.toString().toLowerCase())+ "\n";
		s += " Plot: \n" + plot + "\n";
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Movie other = (Movie) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}


}
