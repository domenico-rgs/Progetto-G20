package server.domain.shopCard;

public class Item {
	
	private String title;
	private String date;
	private double price;
	private String theatreName;
	private String seat;
	
	public Item (String title, String date, String theatre, String seat, double price) {
		this.title = title;
		this.date = date;
		this.theatreName = theatre;
		this.seat = seat;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public String getDate() {
		return date;
	}

	public double getPrice() {
		return price;
	}

	public String getTheatreName() {
		return theatreName;
	}

	public String getSeat() {
		return seat;
	}
	
	

}
