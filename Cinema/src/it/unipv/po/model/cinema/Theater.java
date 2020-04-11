package it.unipv.po.model.cinema;

import java.util.ArrayList;
import java.util.List;

public class Theater {
	
	/* Id generale usato per la creazioni di id univoci per ogni sala
	 * (qual è l'utilità di un id??)
	 */
	private static int idGeneral = 0;
	
	private String theaterName; 
	private int idTheater;
	private int theaterOpeningHours;
	private int theaterClosingHours;
	private List<Movie> dailyMovies;
	private List<Seat> seatList;
	
	
	
	public Theater(String theaterName, int theaterOpeningHours,int theaterClosingHours, int capienza) {
		this.setTheaterName(theaterName);
		this.theaterOpeningHours = theaterOpeningHours;
		this.theaterClosingHours = theaterClosingHours;
		dailyMovies = new ArrayList<Movie>();
		seatList = new ArrayList<Seat>();
		
		createSeatsList(capienza);
		
		/* Assegnamento automatico dell'id della sala
		 * 
		 */
		idTheater = idGeneral;
		idGeneral++;
	}



	/* la variabile capienza nel costruttore verra usata dalla seguente funzione
	 * che crea una lista di Seat lunga quanto 'capienza'.
	 * Inoltre ogni sedia sarà numerata dalla variabile 'i' del ciclo for dentro
	 * la funzione
	 */
	
	private void createSeatsList(int capienza) {
		for(int i = 0; i < capienza; i++) {
			seatList.add(new Seat(i));
		}
	}
	
	
	/*
	 * Ritorna numero disponibilità dei posti per tale sala
	 */
	public int getAvailabilitySeats() {
		
		int available = 0;
		
		for(Seat s: seatList) {
			if(s.isAvalaible()) {
				available+= 1;
			}
		}
		return available;
	}
	
	
	/* Permette di aggiungere un movie alla lista giornaliera, specificando
	 * la fascia oraria di proiezione
	 */
	public boolean editMovieList(Movie movie, int fasciaOraria) {
		
		/* La variabile fascia orario ha i seguenti significati:
		 * 0: 18-20
		 * 1: 20-22
		 * 2: 22-24
		 */
		
		//verifica correttezza variabile fasciaOraria
		if (fasciaOraria < 0 || fasciaOraria > 2) return false;
		
		dailyMovies.add(fasciaOraria, movie);
		return true;
	}
	
	// restituisce la sedia dato il numero
	public Seat getSeat(int numberSeat) {
		
		try {
			return seatList.get(numberSeat);
		} 
		catch(IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	// Ritorna il numero di posti totali della sala
	public int getCapacity() {
		return seatList.size();
	}
	
	//getter and setter
	
	public String getTheaterName() {
		return theaterName;
	}



	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}



	public int getTheaterOpeningHours() {
		return theaterOpeningHours;
	}



	public void setTheaterOpeningHours(int theaterOpeningHours) {
		this.theaterOpeningHours = theaterOpeningHours;
	}



	public int getTheaterClosingHours() {
		return theaterClosingHours;
	}



	public void setTheaterClosingHours(int theaterClosingHours) {
		this.theaterClosingHours = theaterClosingHours;
	}

}
