package server.domain.cinema;

/**
 * This class is referred to a movie
 * @see MovieShowing
 */
public class Movie {
	private String title, plot, pathCover;
	private int duration; //in minutes
	private TypeCategory category;

	public Movie() {}

	public Movie(String title, int duration, String plot, String pathCover, TypeCategory category) {
		this.title=title;
		this.duration=duration;
		this.category=category;
		this.plot=plot;
		this.pathCover=pathCover;
	}

	/**
	 * this methods can modify a movie
	 * @param pathCover this is the path of the movie's cover
	 * @param plot movie's plot
	 * @param category movie's category
	 */
	public void editMovie(String pathCover, String plot, TypeCategory category) {
		setCategory(category);
		setPlot(plot);
		setPathCover(pathCover);
	}

	public void setCategory(TypeCategory category) {
		this.category = category;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public void setPathCover(String pathCover) {
		this.pathCover = pathCover;
	}

	public String getTitle() {
		return title;
	}

	public String getPathCover() {
		return pathCover;
	}

	public int getDuration() {
		return duration;
	}

	public TypeCategory getCategory() {
		return category;
	}

	public String getPlot() {
		return plot;
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
