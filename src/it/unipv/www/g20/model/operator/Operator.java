/**This is a generic operator of the cinema (manager or cashier)*/
package it.unipv.www.g20.model.operator;

public abstract class Operator implements Identifiable{
	private final int id;
	private static int generatorIdOperator = 0;
	private String password;
	private boolean changedDefaultPassword;
	protected TypeOperator type;
	public boolean logged;

	public Operator() {
		password = "default";
		changedDefaultPassword = false;
		id = generatorIdOperator;
		generatorIdOperator++;
		logged=false;
	}
	
	/**It permits to modify password when the operator accesses for the first time.
	 * @param vecchiaPw old password to substitute
	 * @param nuovaPw new password to insert
	 * @return true if the password is modified. */
	public boolean isFirstLogin(String vecchiaPw, String nuovaPw) {
		if(!changedDefaultPassword) {
			setPassword(vecchiaPw, nuovaPw);
			changedDefaultPassword=true;
		}
		return false;
	}

	@Override
	public boolean setPassword(String oldPassword, String newPassword) {
		if(password.equals(oldPassword)) {
			password=newPassword;
			return true;
		}else {
			return false;
		}
	}

	public TypeOperator getType() {
		return type;
	}

	public int getId() {
		return id;
	}

	@Override
	public boolean login(String password) {
		if(this.password.equals(password))
			logged=true;
		return logged;
	}
	
	/**It permits to log out, modifying the variable "logged". */
	public void logout() {
		logged=false;
	}

	public boolean isLogged() {
		return logged;
	}

	public void setType(TypeOperator type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Operator:\n[id=" + id + "\n type=" + type + "\n logged=" + logged + "]";
	}


}
