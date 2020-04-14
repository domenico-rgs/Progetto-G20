package it.unipv.www.g20.model.operator;

/**This interface permits to modify operator's password and to log in/log out an operator.*/
public interface Identifiable {
	/**
	 * It permits to set a new password for an operator.
	 * @param oldPassword the old password to substitute
	 * @param newPassword the new password to insert
	 * @return true if password is changed
	 */
	public boolean setPassword(String oldPassword, String newPassword);

	/**
	 * It permits to log in the operator.
	 * @param password operator's password
	 * @return true if the operator is logged.
	 */
	public boolean login (String password);

	/**
	 * It permits to log out, modifying the variable "logged".
	 */
	public void logout();
}
