package server.domain.shopCard;

import java.util.ArrayList;
import java.util.List;

public class ShopCard {

	/* uno shop card per cinema
	 * si potrebbe implementare uno shopcard per connessine, ma richiede tempo (ne vale la pena?)
	 */
	String[] seats;
	String idSh;
	private List<String> bufferDiscountCode;
	private double total;

	public ShopCard () {
		bufferDiscountCode = new ArrayList<>();
		total = 0;
	}


	public void addCode(String code) {
		this.bufferDiscountCode.add(code);
	}

	public void addTotal(double price) {
		this.total += price;
	}

	public void refresh() {
		bufferDiscountCode.clear();
		total = 0;
	}


	public double getTotal() {
		return this.total;
	}

	public void changeTotal(double discount) {
		this.total -= discount;
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

	public List<String> getBufferDiscountCode() {
		return bufferDiscountCode;
	}


}


