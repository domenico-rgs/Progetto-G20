package it.unipv.www.g20.model.TextUI;

import java.util.IllegalFormatConversionException;
import java.util.Scanner;

import it.unipv.www.g20.model.cinema.Cinema;

public class CustomerTextUI {
	
	private Scanner scanner;
	private String inputString;
	
	
	public CustomerTextUI () {
		
		scanner = new Scanner(System.in);
		
	}
	
	/* i metodi commentati attendono un'effetiva
	 * implementazione nelle classi del modello
	 */
	
	//metodo di avvio, unico chiamabile al di fuori
	public void start() {
		
		System.out.println("BENVENUTO NELLA NOSTRA APP!"); //giusto di prova
		
		System.out.println("exit: scrivi per uscire\n back: scrivi per tornare indietro");
		System.out.println("Premi un tasto per continuare ");
		
		scanner.nextLine();
		
		/*per ogni funzione sotto riportata, se ritornato false,
		 * il ciclo si ripete, magari a causa di errori, finche
		 * non si svologno con corretteza, ritornando true;
		 */
		while (true) {
			if(this.showCinemaMenu()) break;
			break; //in caso di errori a funzione
		}
		
		System.out.println("Chiusura app...grazie ed arrivederci"); //giusto per prova
		
	}
	
	/* metodo che mostra la lista dei cinema disponibile, (che dovrà essere
	 * restituita da qualche parte dal modello
	 */
	
	private boolean showCinemaMenu() {
		
		System.out.println("\n\nScegli un cinema: ");
		
		/*
		 * CinemaListaObject: una possibile classe che ha traccia di tutti i cinema
		 * getAvaiableCinemaList: restituisce una lista di idenficatori (nomi)
		 * 	dei cinema disponibili
		 */
		String[] cinemaLista = null;
		//String[] cinemaLista = CinemaListaObject.getAvaiableCinemaList();
		
		int count = 1;
		for (String nameCinema: cinemaLista) {
			System.out.println(count + " - " + nameCinema);
		}
		
	    inputString = scanner.nextLine();
	    
	    if (inputString == "exit") return true;
	    if (inputString == "back") return false;
	    
	    /* una volta verificata la correttezza dei dati, 
	     * chiamo la funzione showProjection Menu che, dato
	     * un cinema, ricavato tramite cinemaListaObject.
	     */
	    try {
	    	int choice = Integer.parseInt(inputString);
	    	//Cinema c = CinemaListaObject.getCinema(cinemaList[choice -1])
	    	
	    	while (true) {
	    		//if (this.showProjectonMenu(c)) break; 
	    		break; 
	    	}
	    }
	    catch (IllegalFormatConversionException e) {
	    	System.out.println("Dato Inserito non Corretto");
	    	return false;
	    }
	    
	    catch (IndexOutOfBoundsException e) {
	    	System.out.println("Cinema non presente");
	    	return false;
	    }
	    
	    return false;
	    
		
		
	}
	
	
	/* da provare l'idea di mettere i metodi piu importanti dei cinema
	 * nella ListaCinemaObject...attendo lo sviluppo del codice per completare
	 * questa ui.... è solo lo scheletro!!
	 */
	private void showProjectonMenu(Cinema cinema) {
		
		System.out.println("Eccolo le liste delle proiezioni per " /* + cinema.getName()*/);
		
	}
	

}
