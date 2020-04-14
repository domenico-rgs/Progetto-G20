package it.unipv.www.g20.model.booking;

/** 
 * This class identifies a bought ticket to show a movie.
 * @see Booking
 */
public class Ticket {
	private final String id; //the id is composed of a part that identifies the booking and another that identifies the ticket in the series
	private String info; //probably to be modified, in theory it should contain information on the film, theater, screening time and price

	public Ticket(String id, String info) {
		this.id=id;
		this.info=info;
	}


	public String getId() {
		return id;
	}


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}


	@Override
	public String toString() {
		return "Ticket: " + id + ", info=" + info + "\n";
	}

}
