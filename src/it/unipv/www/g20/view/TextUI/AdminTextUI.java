package it.unipv.www.g20.view.TextUI;

import java.text.SimpleDateFormat;
import java.util.Scanner;


import it.unipv.www.g20.controller.cinema.Cinema;
import it.unipv.www.g20.model.exception.SearchException;
import it.unipv.www.g20.model.movie.Movie;
import java.util.Date;

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

		System.out.println("BENVENUTO NELLA NOSTRA APP!"); //giusto di prova

		System.out.println("exit: scrivi per uscire\n" + "back: scrivi per tornare indietro");
		System.out.println("Premi invio per continuare\n");

		scanner.nextLine();


		/*ciclo che attende di accettare comandi, in caso di input sbagliati
		 * ritorna l'errore e ripete il ciclo tramite il 'continue';
		 * in caso di inserimento di exit, esce dal ciclo con un break
		 * ritornando un messaggio di uscita
		 */
		while (true) {
			System.out.println("Menu Principale");

			System.out.println("Scegli un opzione\n"
					+ "1 - Gestisci Tiket \n"
					+ "2 - Gestisci Sale\n"
					+ "3 - Gestigli Film");


			inputString = scanner.nextLine();

			if (inputString.contentEquals("exit")) break;
			if (inputString.contentEquals("back")) {
				System.out.println("Sei gia al men� principale");
				continue;
			}

			/* verifica se effettivamente sia stato inserito un numero
			 * e se esso sia valido, allora continua con
			 * la funzione richiamata
			 */
			try {
				int choice = Integer.parseInt(inputString);

				switch (choice) {
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
					continue;
				}
				continue;
			}
			catch (NumberFormatException e) {
				System.out.println("Inserito dato non valido");
				continue;
			}
		}

		System.out.println("Chiusura app...grazie ed arrivederci"); //giusto per prova

	}

	/* metodi generali che scaturiscono dal primo menu
	 */

	private void manageTicket() {

		System.out.println("Menu di gestione dei biglietti");

		while (true) {

			System.out.println("Scegli un opzione\n"
					+ "1 - Acquisto Ticket\n"
					+ "2 - Cancella Ticket\n"
					+ "3 - Modifica Ticket");

			inputString = scanner.nextLine();

			if (inputString.contentEquals("exit")) {
				System.out.println("Chiusura app...grazie ed arrivederci");
				System.exit(0);
			}
			if (inputString.contentEquals("back")) break;


			try {
				int choice = Integer.parseInt(inputString);

				switch (choice) {
				case 1:
					System.out.println("Inserisci titolo film, numero sedia ed id della"
							+ "proiezione, separati da carattere di tabulazione");
					stringList = scanner.nextLine().split("\t");
					String id = cinema.bookSeat(cinema.searchMovie(stringList[0]), 
							stringList[1], stringList[2]).getCode();
					System.out.println("Aggiunto con successo con id: " + id);
					break;
					
				case 2:
					System.out.println("Inserisci codice biglietto da rimuovere");
					inputString = scanner.nextLine();
					cinema.deleteBooking(inputString);
					System.out.println("Rimosso con successo");
					break;
					
				case 3:
					/*DOVE LA MIA FUNZIONE IN CINEMA ???*/
					System.out.println("Funzione ancora non disponibile");
					break;
					
				default:
					System.out.println("Scelta non disponibile");
					continue;
				}
				continue;
			}
			catch (NumberFormatException e) {
				System.out.println("Inserito dato non valido");
				continue;
			}
			catch (SearchException e) {
				System.out.println(e.getMessage());
				continue;
			}
			catch (Exception e) {
				System.out.println("Nuovo errore non previsto");
				System.out.println(e.getLocalizedMessage());
				continue;
			}

		}

	}

	private void manageTheater() {
		System.out.println("Menu di gestione dei biglietti");

		while (true) {

			System.out.println("Scegli un opzione\n"
					+ "1 - Aggiungi Sala\n"
					+ "2 - Rimuovi Sala\n"
					+ "3 - Modifica configurazione Sala\n"
					+ "4 - Aggiungi proiezione\n"
					+ "5 - Rimuovi proiezione\n"
					+ "6 - Modifica proiezione");


			inputString = scanner.nextLine();

			if (inputString.contentEquals("exit")) {
				System.out.println("Chiusura app...grazie ed arrivederci");
				System.exit(0);
			}
			if (inputString.contentEquals("back")) break;


			/* vale il discorso come nel metodo start */
			try {
				int choice = Integer.parseInt(inputString);

				switch (choice) {
				case 1:
					System.out.println("Inserire nome, righe e colonne della sala"
							+ " da creare, separati da carattere di tabulazione");
					stringList = scanner.nextLine().split("\t");
					cinema.createTheatre(stringList[0], Integer.parseInt(stringList[1]),
							Integer.parseInt(stringList[2]));
					System.out.println("Aggiunto con successo con nome " + stringList[0]);
					break;
					
				case 2:
					System.out.println("Inserire nome sala da eliminare");
					inputString = scanner.nextLine();
					cinema.deleteTheatre(inputString);
					System.out.println("Eliminato con successo " + inputString);
					break;
					
				case 3:
					System.out.println("Funzione non disponibile");
					break;
					
				case 4:
					System.out.println("Inserire nome del film, nome della sala, data e prezzo "
							+ " della proiezione, separati da carattere di tabulazione");
					stringList = scanner.nextLine().split("\t");
					Movie movie = cinema.searchMovie(stringList[0]);
					SimpleDateFormat format = new SimpleDateFormat("dd-mmm-yyyy");
					String id = movie.addMovieShowing(
							format.parse(stringList[2]), cinema.searchTheatre(stringList[1]), 
							Double.parseDouble(stringList[3])).getId();
					System.out.println("Aggiunto con successo con codice " + id);
					break;
					
				case 5:
					System.out.println("Inserire nome del film ed id della "
							+ "proiezione da rimuovere, separati da carattere di tabulazione");
					stringList = scanner.nextLine().split("\t");
					cinema.searchMovie(stringList[0]).deleteMovieShowing(stringList[1]);
					System.out.println("Rimosso con successo");
					break;
					
				case 6:
					System.out.println("Funzione non disponibile");
					break;
					
				default:
					System.out.println("Scelta non disponibile");
					continue;
				}
				continue;
			}
			catch (NumberFormatException e) {
				System.out.println("Inserito dato non valido");
				continue;
			}
			catch (SearchException e) {
				System.out.println(e.getMessage());
				continue;
			}
			catch (Exception e) {
				System.out.println("Nuovo errore non catchato esplicitamente");
				System.out.println(e.getLocalizedMessage());
				continue;
			}

		}
	}


	private void manageFilm() {
		System.out.println("Menu di gestione dei biglietti");

		while (true) {

			System.out.println("Scegli un opzione\n"
					+ "1 - Aggiungi Film\n"
					+ "2 - Rimuovi Film\n"
					+ "3 - Modifica Film\n"
					+ "4 - Mostra lista Film");


			inputString = scanner.nextLine();

			if (inputString.contentEquals("exit")) {
				System.out.println("Chiusura app...grazie ed arrivederci");
				System.exit(0);
			}
			if (inputString.contentEquals("back")) break;


			/* vale il discorso come nel metodo start */
			try {
				int choice = Integer.parseInt(inputString);

				switch (choice) {
				case 1:
					System.out.println("Inserire nome e durata "
							+ "del film, separati da carattere di tabulazione");
					stringList = scanner.nextLine().split("\t");
					cinema.addMovie(stringList[0], Integer.parseInt(stringList[1]));
					System.out.println("Aggiunto con successo con nome " + stringList[0]);
					break;
					
				case 2:
					System.out.println("Inserire nome del fiml da eliminare");
					inputString = scanner.nextLine();
					cinema.deleteMovie(inputString);
					System.out.println("Rimosso con successo");
					break;
					
				case 3:
					System.out.println("Funzione non disponibile");
					break;
					
				case 4:
					System.out.println(cinema.printMovie());
					System.out.println("Premi invio per continuare");
					scanner.nextLine();
					break;
					
				default:
					System.out.println("Scelta non disponibile");
					continue;
				}
				continue;
			}
			catch (NumberFormatException e) {
				System.out.println("Inserito dato non valido");
				continue;
			}
			catch (SearchException e) {
				System.out.println(e.getMessage());
				continue;
			}
			catch (Exception e) {
				System.out.println("Nuovo errore non catchato esplicitamente");
				System.out.println(e.getLocalizedMessage());
				continue;
			}

		}

	}

	//metodi che scaturiscono dai secondi menu



}
