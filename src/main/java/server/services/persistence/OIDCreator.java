package server.services.persistence;

import org.apache.commons.lang3.RandomStringUtils;


/**
 * A class which contains all the OID ( Object Identifier ) for the objects of the system.
 * It is implemented through Singleton pattern implementation.
 */
@SuppressWarnings("deprecation")
public class OIDCreator {
	private static OIDCreator instance = null;
	private int showingCode;

	private OIDCreator() {}

	/**
	 * 'Pattern Singleton Implementation'
	 *
	 * If the object has not already been instanced, it is instanced and it is returned.
	 * @return instance(OIDCreator)
	 */
	public static OIDCreator getInstance(){
		if(instance == null) {
			instance = new OIDCreator();
		}
		return instance;
	}

	public synchronized String getShowingCode() {
		showingCode++;
		return "P"+showingCode;
	}

	public void setShowingCode(String showingCode) {
		if(showingCode == null) {
			this.showingCode=0;
		} else {
			this.showingCode = (int)Double.parseDouble(showingCode);
		}
	}

	public synchronized String getTicketCode() {
		return RandomStringUtils.randomAlphanumeric(8).toUpperCase();
	}
}