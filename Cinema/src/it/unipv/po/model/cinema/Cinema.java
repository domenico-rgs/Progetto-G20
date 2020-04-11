package it.unipv.po.model.cinema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.glass.ui.CommonDialogs.Type;
import com.sun.javafx.collections.MappingChange.Map;

import it.unipv.po.model.booking.Booking;
import it.unipv.po.model.booking.BookingManager;
import it.unipv.po.model.operator.Cashier;
import it.unipv.po.model.operator.Manager;
import it.unipv.po.model.operator.Operator;
import it.unipv.po.model.operator.TypeOperator;

//cinema

public class Cinema<E> {
	
	/* Nell'eventualità che venga creato più di un cinema, questa variabile
	 * statica verrà usata per assegnare automaticamente un id diverso ad ogni 
	 * istanza di cinema (Da valutarne l'effetiva utilità di un id).
	 * (nel caso si voglia creare più di un cinema [e perchè mai??] , non va bene usare
	 * il singleton per il bookingManager)
	 */
	private static int idGeneral = 0;
	
	// attributi
	private String cinemaName;
	private int idCinema;
	private String address;
	private HashMap<String, Theater> theaterList;
	private HashMap<String, Movie> movieList;
	private BookingManager bookingManager;  //Una sola istanza per cinema
	
	//aggiungo la lista degli operatori che lavorano al cinema ed uso un hashmap per facilitare la ricerca
	private HashMap<String, Operator> operatorList;
	
	
	public Cinema(String name, String address) {
		this.address = address;
		this.cinemaName = name;
		this.idCinema = idGeneral;
		
		//Ne esite solo uno (pattern Singleton)
		bookingManager = BookingManager.getInstance();
		
		theaterList = new HashMap<String, Theater>();
		movieList = new HashMap<String, Movie>();
		operatorList = new HashMap<String, Operator>();
		
		
		idGeneral++;
	}
	
	//metodi 
	
	/*metodo per la restituzione dell oggetto operatore*/
	public Operator getOperator(String username) {
	
		Operator op = operatorList.get(username);
	
		return op;
	}
	
	/*metodo per la restituizione degli operatori che utilizzano il sistema  */
	
	public String getOperatorUseSystemString() {
		String s = "List Operator\n\n";
	
		for(Entry<String, Operator> list: operatorList.entrySet()) {
			String key = list.getKey();
			Operator op = list.getValue();
			TypeOperator typeOp = op.getTypeOperator();
			s += key + " " +typeOp +"\n";
		}
		/*
		for(String key1: operatorList.keySet()) {
			s+= key1.toString() + "\n";
		}
		*/
		System.out.println(s);
		
		return s;
	}
	
	/* metodo per l-aggiunta di operatori al sistema
	 * */
	public boolean addOperator(TypeOperator type, String name, String surname) {
		if(type.equals(TypeOperator.CASHIER)) {
			Operator cashier = new Cashier(name, surname);
			operatorList.put(cashier.getUsername(), cashier);
			return true;
		}
		else if(type.equals(TypeOperator.MANAGER)) {
			Operator manager = new Manager(name, surname);
			operatorList.put(manager.getUsername(), manager);
			return true;
		}
		return false;
	}
	
	
	/* Aggiunta delle sale e dei film. é consigliato usare un nome ben preciso
	 * che identifica le sale ed i film in modo univoco nell'hashMap (ex: 'Sala 1')
	 */
	
	public boolean addTheater(String name, int openHour, int closeHour, int capienza) {
		
		if(theaterList.containsKey(name)) return false;
		
		Theater room = new Theater(name, openHour, closeHour, capienza);
		theaterList.put(name, room);
		return true;
		
	}

	public void addMovie(String name, double duration, double date, MovieCategory type, boolean underage, double price ) {
		Movie film = new Movie(name, duration, date, type, underage, price);
		movieList.put(name, film);
	}
	
	
	/* Funzioni che restitutiscono sale e film tramite ricerca della chiave 'name'
	 * nelle rispettive hashMap
	 */
	public Movie getMovie(String name) {
		try {
			return movieList.get(name);
		}
		catch(NullPointerException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	//da rimuovere il try-catch
	public Theater getTheater(String name) {
		try {
			return theaterList.get(name);
		}
		catch(NullPointerException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/* ritorna la lista delle chiavi dell'hashmap della listaSale
	 * utile nell'interfaccia grafica.
	 * uso un array di string per evitare di usare il set nei controllers
	 */
	
	public String[] getNameTheaterList() {
		
		String[] nameList = new String[theaterList.size()];
		nameList = theaterList.keySet().toArray(nameList);
		
		return nameList;
	}
	
	/* Restituisce quanti posti liberi ha una sala, dato il 
	 * nome di una sala (che deve essere contenuta nell'hashMap)
	 * (Lato grafico si potrebbe creare un menu a tendina che restituisce tutte le sale
	 * attualmente esistenti, usando un getter delle chiavi della lista).
	 */
	public int getIsAvailableSeatsTheatre(String name) {
		return theaterList.get(name).getAvailabilitySeats();
	}
	
	//da verificare implementazione di remove e change per le sale
	public void removeTheater(String name) {
		theaterList.remove(name);
	}
	
	
	// getters informativi
	public String getCinemaName() {
		return cinemaName;
	}

	public int getIdCinema() {
		return idCinema;
	}

	public String getAddress() {
		return address;
	}
	
}
