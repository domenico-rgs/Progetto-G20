package it.unipv.www.g20.model.operator;

/**
 * This is a generic operator of the cinema (manager or cashier)
 */
public class Administrator {
	private String name;

	public Administrator(String name) {
		setNickname(name);
	}

	public String getNickname() {
		return name;
	}


	public void setNickname(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Operator: " + name;
	}
}
