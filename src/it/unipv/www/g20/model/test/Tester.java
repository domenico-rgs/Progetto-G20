package it.unipv.www.g20.model.test;

import it.unipv.www.g20.model.cinema.Cinema;
import it.unipv.www.g20.model.operator.TypeOperator;
import it.unipv.www.g20.view.text.Text;

public class Tester {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Cinema cinema1 = new Cinema("Cinema Lupo", "Via Roma 10");
		cinema1.addTheatre("Sala1", 10, 20);
		System.out.println("id nuovo operatore: "+cinema1.addOperator(TypeOperator.CASHIER).getId()+"\n");
		Text gui = new Text(cinema1);
	}

}
