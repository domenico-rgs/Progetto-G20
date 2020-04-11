package it.unipv.po.model.operator;

public abstract class Operator {
	private String name;
	private String surname;
	private boolean editing;
	private int idOperator;
	private static int generatorIdOperator = 0;
	private String username;
	private String password;
	private boolean changedDefaultPassword;
	
	
	public Operator(String name, String surname) {
		this.name = name;
		this.surname = surname;
		password = "default";
		changedDefaultPassword = false;
		idOperator = generatorIdOperator;
		setUsername(generateUsername());
		generatorIdOperator++;
		
	}
	
	//metodo momentaneo per testare la password
	public String getPassword() {
		String s = "Password\n\n";
		s+= password + " psw changed: " + changedDefaultPassword+ "\n";
		return s;
	}
	
	//metodo per il cambio della password
	public boolean changePassword(String oldPassword, String newPassword) {
		if(oldPassword.equals(password)) {
			password = newPassword;
			changedDefaultPassword = true;
			return true;
		}
		
		return false;
	}
	
	//metodo che restituisce che tipo di operatore e'
	
	public TypeOperator getTypeOperator() {
		TypeOperator s = null;
		if(editing) {
			s= TypeOperator.MANAGER;
		}
		else if(!editing) {
			s= TypeOperator.CASHIER;
		}
		
		return s;
	}

/* metodo utile per la creazione di un username automatico utile per l-accesso tramite username e password */
	private String generateUsername() {
		String s = "";
		s += name+idOperator;
		return s;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getSurname() {
		return surname;
	}



	public void setSurname(String surname) {
		this.surname = surname;
	}



	public boolean isEditing() {
		return editing;
	}



	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isChangedDefaultPassword() {
		return changedDefaultPassword;
	}
	
	
}
