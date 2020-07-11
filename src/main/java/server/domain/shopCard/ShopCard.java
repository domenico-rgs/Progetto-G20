package server.domain.shopCard;

import java.util.ArrayList;
import java.util.List;

public class ShopCard {
	
	/* uno shop card per cinema
	 * si potrebbe implementare uno shopcard per connessine, ma richiede tempo (ne vale la pena?)
	 */
	
	private List<Item> items;
	private List<String> bufferDiscountCode;
	private double total;
	
	public ShopCard () {
		items = new ArrayList<>();
		bufferDiscountCode = new ArrayList<>();
		total = 0;
	}
	
	public void addItem(String title, String date, String theatre, String seat, double price,String idsh) {
		
		//implementare eliminazione dupblicati
		items.add(new Item(title,date,theatre,seat,price,idsh));
		total += price;
	}
	
	public void addCode(String code) {
		this.bufferDiscountCode.add(code);
	}
	
	public void refresh() {
		items.clear();
		bufferDiscountCode.clear();
		total = 0;
	}

	
	public double getTotal() {
		return this.total;
	}
	
	public void changeTotal(double discount) {
		this.total -= discount;
	}
	
	public List<Item> getItems() {
		return this.items;
	}

	public List<String> getCode() {
		return this.bufferDiscountCode;
	}
}
