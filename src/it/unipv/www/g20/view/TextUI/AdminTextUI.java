package it.unipv.www.g20.view.TextUI;

import java.util.Date;
import java.util.Scanner;

import it.unipv.www.g20.controller.cinema.Cinema;
import it.unipv.www.g20.model.exception.SearchException;
import it.unipv.www.g20.model.movie.MovieShowing;

public class AdminTextUI {

	private Scanner scanner;
	private String inputString;
	private String[] stringList;
	private Cinema cinema;


	public AdminTextUI (Cinema c) {

		scanner = new Scanner(System.in);
		cinema = c;

	}

	//metodo di avvio, unico chiamabile al di fuori
	public void start() {
		System.out.println(cinema.getName().toUpperCase());
		System.out.println("Scrivi \"e\" per uscire, \"b\" per tornare indietro\n");

		/*
		 * ciclo che attende di accettare comandi, in caso di input sbagliati
		 * ritorna l'errore e ripete il ciclo tramite il 'continue';
		 * in caso di inserimento di exit, esce dal ciclo con un break
		 * ritornando un messaggio di uscita
		 */
		while (true) {
			System.out.println("Menu Principale\n**************");

			System.out.println("Scegli un opzione:\n"
					+ "1 - Gestisci Ticket \n"
					+ "2 - Gestisci Sale\n"
					+ "3 - Gestisci Film");

			System.out.print("Scelta: ");
			inputString = scanner.nextLine();

			if (inputString.equalsIgnoreCase("e"))
				break;
			if (inputString.equalsIgnoreCase("b")) {
				System.out.println("Sei gia al menù principale");
				continue;
			}

			/*
			 * verifica se effettivamente sia stato inserito un numero
			 * e se esso sia valido, allora continua con
			 * la funzione richiamata
			 */
			try {
				switch (Integer.parseInt(inputString)) {
				case 1:
					manageTicket();
					break;
				case 2:
					manageTheater();
					break;
				case 3:
					manageFilm();
					break;
				default:
					System.out.println("Scelta non disponibile");
				}
			}
			catch (NumberFormatException e) {
				System.out.println("Scelta non valida");
			}
		}
	}

	/*
	 * metodi generali che scaturiscono dal primo menu
	 */
	private void manageTicket() {
		System.out.println("\nMenu di gestione dei biglietti\n*******************");
		while (true) {
			System.out.println("\nScegli un opzione\n"
					+ "1 - Acquisto Ticket\n"
					+ "2 - Cancella Ticket\n"
					+ "3 - Modifica Ticket");

			System.out.print("Scelta: ");
			inputString = scanner.nextLine();

			if (inputString.contentEquals("e")) {
				System.out.println("Chiusura app...");
				System.exit(0);
			}else if (inputString.contentEquals("b"))
				break;

			try {

				switch (Integer.parseInt(inputString)) {
				case 1:
					System.out.println("\nFilm disponibili: \n"+cinema.printMovie());
					System.out.print("Inserisci film: ");
					String film = scanner.nextLine();
					System.out.println("Proiezioni disponibili:\n"+ cinema.searchMovie(film).printMovieShowing());
					System.out.print("Inserisci id proiezione: ");
					String showing = scanner.nextLine();
					System.out.println(cinema.searchMovie(film).searchShowing(showing).printSeats());
					System.out.print("Inserisci id sedile: ");
					String sedile = scanner.nextLine();
					System.out.println("\n"+cinema.bookSeat(cinema.searchMovie(film),sedile, showing));
					break;
				case 2:
					System.out.print("Inserisci il codice del biglietto da rimuovere: ");
					inputString = scanner.nextLine();
					if(cinema.deleteBooking(inputString));
					System.out.println("Rimosso con successo");
					break;
				case 3:
					//DOVE LA MIA FUNZIONE IN CINEMA ??? -> Ma io che cazzo ne so, scusi
					System.out.println("Funzione ancora non disponibile");
					break;
				default:
					System.out.println("Scelta non valida");
				}
			}catch (NumberFormatException e) {
				System.out.println("Input non valido");
				continue;
			}catch (SearchException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void manageTheater() {
		System.out.println("\nMenu di gestione delle sale\n*******************");

		while (true) {
			System.out.println("\nScegli un opzione\n"
					+ "1 - Aggiungi Sala\n"
					+ "2 - Rimuovi Sala\n"
					+ "3 - Modifica configurazione Sala\n"
					+ "4 - Mostra sale disponibili\n"
					+ "5 - Mostra proiezioni\n"
					+ "6 - Aggiungi proiezione\n"
					+ "7 - Rimuovi proiezione\n"
					+ "8 - Modifica proiezione");


			System.out.print("Scelta: ");
			inputString = scanner.nextLine();

			if (inputString.contentEquals("e")) {
				System.out.println("Chiusura app...");
				System.exit(0);
			}else if (inputString.contentEquals("b"))
				break;

			//vale il discorso come nel metodo start
			try {
				switch (Integer.parseInt(inputString)) {
				case 1:
					System.out.print("Inserisci nome: ");
					String nomeSala = scanner.nextLine();
					System.out.print("Inserisci riga e colonna separati da una virgola: ");
					stringList = scanner.nextLine().split(",");
					if(cinema.createTheatre(nomeSala, Integer.parseInt(stringList[0]), Integer.parseInt(stringList[1])))
						System.out.println("Aggiunto con successo con nome " + nomeSala);
					break;
				case 2:
					System.out.println("Inserire nome della sala da eliminare");
					if(cinema.deleteTheatre(scanner.nextLine()))
						System.out.println("Eliminata con successo ");
					break;
				case 3:
					System.out.println("Funzione non ancora disponibile");
					break;
				case 4:
					System.out.println(cinema.printTheatres());
					break;
				case 5:
					System.out.println("Funzione non ancora disponibile");
					break;
				case 6:
					System.out.println("ATTENZIONE: FUNZIONE NON ANCORA FUNZIONANTE");
					System.out.print("Inserisci film: ");
					String film = scanner.nextLine();
					System.out.print("Inserisci il nome della sala: ");
					String sala = scanner.nextLine();
					System.out.print("Inserisci data (dd-mm-yyyy-hh-mm): ");
					stringList = scanner.nextLine().split("-"); //non mi piace, sarà una soluzione temporanea, dobbiamo capire bene come funziona Date
					System.out.print("Inserisci prezzo: ");
					String prezzo = scanner.nextLine();
					//non va da controllare
					MovieShowing mS = cinema.searchMovie(film).addMovieShowing(new Date(Integer.parseInt(stringList[0]),Integer.parseInt(stringList[1]),Integer.parseInt(stringList[2]),Integer.parseInt(stringList[3]),Integer.parseInt(stringList[4])),
							cinema.searchTheatre(sala),Double.parseDouble(prezzo));
					System.out.println("Aggiunto con successo con codice: "+mS.getId());
					break;
				case 7:
					System.out.print("Inserisci film: ");
					film = scanner.nextLine();
					System.out.print("Inserisci l'id della proiezione: ");
					String proiezione = scanner.nextLine();
					if(cinema.searchMovie(film).deleteMovieShowing(proiezione) != null)
						System.out.println("Rimosso con successo");
					break;
				case 8:
					System.out.println("Funzione non disponibile");
					break;
				default:
					System.out.println("Scelta non valida");
				}
			}catch (NumberFormatException e) {
				System.out.println("Input non valido");
				continue;
			}catch (SearchException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
	}


	private void manageFilm() {
		System.out.println("Menu di gestione dei film\n************");

		while (true) {
			System.out.println("\nScegli un opzione\n"
					+ "1 - Aggiungi Film\n"
					+ "2 - Rimuovi Film\n"
					+ "3 - Modifica Film\n"
					+ "4 - Mostra lista Film");


			System.out.print("Scelta: ");
			inputString = scanner.nextLine();

			if (inputString.contentEquals("e")) {
				System.out.println("Chiusura app...");
				System.exit(0);
			}else if (inputString.contentEquals("b"))
				break;

			//vale il discorso come nel metodo start
			try {
				switch (Integer.parseInt(inputString)) {
				case 1:
					System.out.print("Inserisci titolo: ");
					String titolo = scanner.nextLine();
					System.out.print("Inserisci durata: ");
					String durata = scanner.nextLine();
					if(cinema.addMovie(titolo, Integer.parseInt(durata)))
						System.out.println(titolo + " aggiunto con successo");
					break;
				case 2:
					System.out.println("Inserire titolo del film da eliminare");
					if(cinema.deleteMovie(scanner.nextLine()))
						System.out.println("Rimosso con successo");
					break;
				case 3:
					System.out.println("Funzione non disponibile");
					break;
				case 4:
					System.out.println(cinema.printMovie());
					System.out.print("Premi invio per continuare");
					scanner.nextLine();
					break;
				default:
					System.out.println("Scelta non valida");
				}
			}catch (NumberFormatException e) {
				System.out.println("Input non valido");
				continue;
			}catch (SearchException e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
	}
	//metodi che scaturiscono dai secondi menu
}
