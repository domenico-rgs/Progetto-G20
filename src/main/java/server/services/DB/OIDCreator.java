package server.services.DB;

public class OIDCreator {
	private static OIDCreator instance = null;
	private int showingCode;


	private OIDCreator() {
	}

	/**
	 * 'Pattern Singleton Implementation'
	 *
	 * If the object has not already been instanced, it is instanced and it is returned.
	 * @return instance(OIDCreator)
	 */
	public static OIDCreator getInstance(){
		if(instance == null)
			instance = new OIDCreator();
		return instance;
	}

	public synchronized String getShowingCode() {
		showingCode++;
		return "P"+showingCode;
	}
	
	public void setShowingCode(String showingCode) {
		if(showingCode == null)
			this.showingCode=0;
		else
			this.showingCode = Integer.parseInt(showingCode.substring(1));
    }
}