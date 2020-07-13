package server.domain.payment;

import java.util.ArrayList;
import java.util.List;

public class ShopCart {

	/**this class works as a shopping cart */
	String[] seats;
	String idSh;
	private List<String> bufferDiscountCode;
	private double total;

	public ShopCart () {
		bufferDiscountCode = new ArrayList<>();
		total = 0;
		idSh = null;
	}

	/**
	 * this method permits to add a new discount code
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
	}


	public double getTotal() {
		//ritorna il prezzo con due cifre decimali
		return (double) Math.round(this.total * 100) / 100;
	}

	/**
	 * it sets to 0 the value of "total"
	 */
	public void setZeroTotal() {
		total = 0;
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
