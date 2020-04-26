package it.unipv.www.g20.model.TextUI;

import java.util.Scanner;

import it.unipv.www.g20.model.cinema.Cinema;

public class AdminTextUI {

	private Scanner scanner;
	private String inputString;


	///////////////////////////////////////
	/* INTEFACCIA ANCORA NON FUNZIONANTE !!!!!!
	 * SI ASPETTA CHE VENGA COMPLETATO IL CODICE PER POTERLA
	 * COMPLETARE !!!!!!!!!
	 *
	 */
	///////////////////////////////////////


	public AdminTextUI (Cinema c) {

		scanner = new Scanner(System.in);

	}

	/* i metodi commentati attendono un'effetiva
	 * implementazione nelle classi del modello
	 */

	//metodo di avvio, unico chiamabile al di fuori
	public void start() {

		System.out.println("BENVENUTO NELLA NOSTRA APP!"); //giusto di prova

		System.out.println("exit: scrivi per uscire\n" + "back: scrivi per tornare indietro");
		System.out.println("Premi invio per continuare\n");

		scanner.nextLine();

		System.out.println("Menu Principale");

		/*ciclo che attende di accettare comandi, in caso di input sbagliati
		 * ritorna l'errore e ripete il ciclo tramite il 'continue';
		 * in caso di inserimento di exit, esce dal ciclo con un break
		 * ritornando un messaggio di uscita
		 */
		while (true) {

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


			/* vale il discorso come nel metodo start */
			try {
				int choice = Integer.parseInt(inputString);

				switch (choice) {
				case 1:
					break;
				case 2:
					break;
				case 3:
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
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
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
	}


	private void manageFilm() {
		System.out.println("Menu di gestione dei biglietti");

		while (true) {

			System.out.println("Scegli un opzione\n"
					+ "1 - Aggiungi Film\n"
					+ "2 - Rimuovi Film\n"
					+ "3 - Modifica Film");


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
					break;
				case 2:
					break;
				case 3:
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

	}

	//metodi che scaturiscono dai secondi menu



}
