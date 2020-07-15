package server.domain.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**This class works as a shopping cart */
public class ShopCart {
	private String[] seats;
	private String idSh, tmpID;
	private List<String> bufferDiscountCode;
	private double total;

	public ShopCart () {
		bufferDiscountCode = new ArrayList<>();
		total = 0;
		idSh = null;
		tmpID = null;
	}

	/**
	 * This method permits to add a new discount code to buffer to check if will be used again
	 * @param code discount code
	 */
	public void addCode(String code) {
		this.bufferDiscountCode.add(code);
	}

	/**
	 * This method check if a discount code is already used for the same purchase
	 * @param code discount code
	 * @return true if the code has already been used otherwise false
	 */
	public boolean hasCode (String code) {
		return this.bufferDiscountCode.contains(code);
	}

	/**
	 * This methods permits to add a price to the total
	 * @param price adding price
	 */
	public void addTotal(double price) {
		this.total += price;
	}


	/**
	 * It modifies the value of "total"
	 * @param discount discount value based on ticket price
	 */
	public void changeTotal(double discount) {
		this.total -= discount;
	}

	/**
	 * This methods resets the shopping cart
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
		return (double) Math.round(this.total * 100) / 100; //two decimal
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
