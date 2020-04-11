package it.unipv.po.model.cinema;

import it.unipv.po.model.operator.Operator;
import it.unipv.po.model.operator.TypeOperator;

public class MainTest {

	public static void main(String[] args) {
		Cinema cinema1 = new Cinema("odissea", "via nuova 7");
		cinema1.addOperator(TypeOperator.MANAGER, "Giuseppe", "anonimo");
		cinema1.addOperator(TypeOperator.MANAGER, "pippo", "anonimo");
		cinema1.addOperator(TypeOperator.MANAGER, "Giuseppe", "anonimo");
		cinema1.addOperator(TypeOperator.MANAGER, "Giuseppe", "anonimo");
		cinema1.addOperator(TypeOperator.MANAGER, "Giuseppe", "anonimo");
		cinema1.addOperator(TypeOperator.CASHIER, "Mark", "anonimo");
		
		cinema1.getOperatorUseSystemString();
		Operator op = cinema1.getOperator("Giuseppe0");
		System.out.println(op.getPassword());
		op.changePassword("default", "testNewPsw");
		System.out.println(op.getPassword());
		
	}

}
