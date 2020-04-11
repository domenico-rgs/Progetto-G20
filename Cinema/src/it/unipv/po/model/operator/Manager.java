package it.unipv.po.model.operator;

public class Manager extends Cashier {
	public Manager(String name, String surname) {
		super(name, surname);
		super.setEditing(true);
		
		
	}
}
