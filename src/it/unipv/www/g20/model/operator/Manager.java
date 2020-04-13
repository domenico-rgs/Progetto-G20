package it.unipv.www.g20.model.operator;

public class Manager extends Cashier {

	public Manager() {
		super();
		super.setType(TypeOperator.MANAGER);
	}
	
	public String toString() {
		return super.toString();
	}
}
