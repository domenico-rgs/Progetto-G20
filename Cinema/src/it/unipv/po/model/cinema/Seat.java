package it.unipv.po.model.cinema;

public class Seat {
	//attributi
	private int seatNumber;
	//private SeatCategory seatType;    
	private boolean isAvalaible;
	
	
	
	public Seat(int seatNumber) {
		isAvalaible=true;
		this.seatNumber = seatNumber;
	}



	public boolean isAvalaible() {
		return isAvalaible;
	}



	public void setAvalaible(boolean isAvalaible) {
		this.isAvalaible = isAvalaible;
	}
	
	
	
}
