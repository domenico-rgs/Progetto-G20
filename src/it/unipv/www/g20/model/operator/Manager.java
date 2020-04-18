package it.unipv.www.g20.model.operator;

/**
 * This class identifies a manager of the cinema.
 * @see Operator
 */
public class Manager extends Operator {

	public Manager(String nickname) {
		super(nickname);
		setType(TypeOperator.MANAGER);
	}

}
