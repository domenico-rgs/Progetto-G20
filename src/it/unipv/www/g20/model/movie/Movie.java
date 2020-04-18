package it.unipv.www.g20.model.movie;

/**
 * This class is referred to a movie.
 */

public class Movie {
	private String title;
	private int duration;
	private TypeCategory category;
	private String plot;

	public Movie(String title) {
		setTitle(title);
	}

	public Movie(String title, int duration, TypeCategory type, double ticketPrice) {
		setTitle(title);
		setDuration(duration);
		setCategory(type);
		setPlot("");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Movie other = (Movie) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public TypeCategory getCategory() {
		return category;
	}

	public int getDuration() {
		return duration;
	}

	public String getPlot() {
		return plot;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	public void setCategory(TypeCategory category) {
		this.category = category;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		String s = "MOVIE INFORMATION";
		s += " Title: " + title + "\n";
		s += " Duration: " + duration + " minutes\n";
		s += " Category: " + category.toString().toLowerCase() + "\n";
		return s;
	}
}
