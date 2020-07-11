package server.domain.shopCard;

public class Item {

	private String title;
	private String date;
	private double price;
	private String theatreName;
	private String seat;
	private String idsh;

	public Item (String title, String date, String theatre, String seat, double price, String idsh) {
		this.title = title;
		this.date = date;
		this.theatreName = theatre;
		this.seat = seat;
		this.price = price;
		this.idsh = idsh;
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

	public String getIdsh() {
		return this.getIdsh();
	}


}
