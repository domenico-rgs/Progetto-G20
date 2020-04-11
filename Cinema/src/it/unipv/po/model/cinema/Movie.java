package it.unipv.po.model.cinema;

public class Movie {
	
	
	
	//attributi
	
	private String name;
	


	private double duration;
	private double releaseDate;
	private MovieCategory type;
	private boolean underage;
	private double price;
	private String description;

	public Movie(String name, double duration, double date, MovieCategory type, boolean underage, double price ) {
		this.name= name;
		this.setDuration(duration);
		this.setReleaseDate(date);
		this.setType(type);
		this.setUnderage(underage);
		this.setPrice(price);
		this.description = "";
	}
	
	
	
	@Override
	public String toString() {
		String s = "MOVIE INFORMATION";
		
		s += " Name: " +name + "\n";
		s += " Duration: " +duration+ " minutes\n";
		s += " Release date: " +releaseDate+ "\n";
		s += " Category: " +type+ "\n";
		s += " Price: " +price+ " €\n";
		
		if(underage) {
			s += " Underage: YES \n";
			return s;
		}
		
		s += " Underage: NO \n";
		return s;
	}



	public Double getDuration() {
		return duration;
	}



	public void setDuration(Double duration) {
		this.duration = duration;
	}



	public double getReleaseDate() {
		return releaseDate;
	}



	public void setReleaseDate(double releaseDate) {
		this.releaseDate = releaseDate;
	}



	public MovieCategory getType() {
		return type;
	}



	public void setType(MovieCategory type) {
		this.type = type;
	}



	public boolean isUnderage() {
		return underage;
	}



	public void setUnderage(boolean underage) {
		this.underage = underage;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setDuration(double duration) {
		this.duration = duration;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}

	
}
