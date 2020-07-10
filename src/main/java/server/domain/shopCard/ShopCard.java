package server.domain.shopCard;

import java.util.ArrayList;
import java.util.List;

public class ShopCard {
	
	/* uno shop card per cinema
	 * si potrebbe implementare uno shopcard per connessine, ma richiede tempo (ne vale la pena?)
	 */
	
	private List<Item> items;
	private double total;
	
	public ShopCard () {
		items = new ArrayList<>();
		total = 0;
	}
	
	public void addItem(String title, String date, String theatre, String seat, double price) {
		items.add(new Item(title,date,theatre,seat,price));
	}
	
	public void refresh() {
		items.clear();
	}
	
	public void getItems() {
		
	}
	
	public double getTotal() {
		this.total = 0;
		
		for(Item i: items) {
			total += i.getPrice();
		}
		
		return this.total;
	}
	
	public void changeTotal(double diff) {
		this.total -= diff;
	}

}
