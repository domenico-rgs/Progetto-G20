package it.unipv.po.model.operator;

public class Cashier extends Operator{
	
	
	public Cashier(String name, String surname) {
		super(name, surname);
		super.setEditing(false);
	}
}
