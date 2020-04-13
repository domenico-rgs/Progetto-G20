package it.unipv.www.g20.model.booking;


public class Ticket {
	private final String id;
	private String info;

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

}
