package it.unipv.www.g20.model.movie;

public class Movie {
	private String title;
	private int duration;
	private TypeCategory category;
	private String plot;
	private double price;

	public Movie(String title, int duration, TypeCategory type, double ticketPrice) {
		this.title= title;
		setDuration(duration);
		setCategory(type);
		setPrice(ticketPrice);
		plot = "";
	}

	@Override
	public String toString() {
		String s = "MOVIE INFORMATION";

		s += " Title: " +title + "\n";
		s += " Duration: " +duration+ " minutes\n";
		s += " Category: " +category.toString().toLowerCase()+ "\n";
		s += " Price: €" +price+ " \n";

		return s;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public void setDuration(int duration) {
		this.duration = duration;
	}



	public void setCategory(TypeCategory category) {
		this.category = category;
	}



	public void setPlot(String plot) {
		this.plot = plot;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public String getTitle() {
		return title;
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



	public double getPrice() {
		return price;
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
