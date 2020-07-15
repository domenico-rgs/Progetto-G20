package server.domain.payment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import server.domain.cinema.Cinema;
import server.domain.exception.SeatException;

public class ShopCart {
	/**this class works as a shopping cart */
	private String[] seats;
	private String idSh;
	private List<String> bufferDiscountCode;
	private double total;
	private String tmpID;

	public ShopCart () {
		bufferDiscountCode = new ArrayList<>();
		total = 0;
		idSh = null;
		tmpID = null;
	}

	/**
	 * this method permits to add a new discount code to buffer to check if will be used again
	 * @param code discount code
	 */
	public void addCode(String code) {
		this.bufferDiscountCode.add(code);
	}

	/**
	 * this method check if a discount code is already used for the same purchase
	 * @param code discount code
	 */
	public boolean hasCode (String code) {
		return this.bufferDiscountCode.contains(code);
	}

	/**
	 *  this methods permits to add a price to the total
	 * @param price adding price
	 */
	public void addTotal(double price) {
		this.total += price;
	}

	/**
	 * this methods resets the shopping cart
	 */
	public void refresh() {
		bufferDiscountCode.clear();
		total = 0;
		idSh = null;
		seats = null;
		tmpID = null;
	}
	
	public String generateID() {
		UUID id = UUID.randomUUID();
		this.tmpID = id.toString();
		return this.tmpID;
	}

	public String getID(){
		return this.tmpID;
	}


	public double getTotal() {
		//ritorna il prezzo con due cifre decimali
		return (double) Math.round(this.total * 100) / 100;
	}

	/**
	 * it modifies the value of "total"
	 * @param discount
	 */
	public void changeTotal(double discount) {
		this.total -= discount;
	}

	public void setTotal(double newPrice) {
		this.total = newPrice;
	}

	public String[] getSeats() {
		return seats;
	}

	public void setSeats(String[] seats) {
		this.seats = seats;
	}

	public String getIdSh() {
		return idSh;
	}

	public void setIdSh(String idSh) {
		this.idSh = idSh;
	}
	
}
