package it.unipv.www.g20.model.operator;

public interface Identifiable {
	public boolean setPassword(String oldPassword, String newPassword);
	public boolean login (String password);
}
