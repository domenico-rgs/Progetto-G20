package it.unipv.www.g20.view.TextUI;

import java.util.Date;
import java.util.Scanner;

import it.unipv.www.g20.controller.cinema.Cinema;
import it.unipv.www.g20.model.exception.SearchException;
import it.unipv.www.g20.model.movie.Movie;
import it.unipv.www.g20.model.movie.MovieShowing;
import it.unipv.www.g20.model.theatre.Theatre;
import it.unipv.www.g20.model.ticket.Ticket;
import it.unipv.www.g20.model.ticket.TicketLedger;

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
		System.out.println("enter \"e\" to exit, \"b\" to back\n");

		/*
		 * ciclo che attende di accettare comandi, in caso di input sbagliati
		 * ritorna l'errore e ripete il ciclo tramite il 'continue';
		 * in caso di inserimento di exit, esce dal ciclo con un break
		 * ritornando un messaggio di uscita
		 */
		while (true) {
			System.out.println("Main Menù\n**************");

			System.out.println("Take a choice:\n"
					+ "1 - Manage Ticket \n"
					+ "2 - Manage Theatre\n"
					+ "3 - Manage Movie");

			System.out.print("Input: ");
			inputString = scanner.nextLine();

			if (inputString.equalsIgnoreCase("e")) {
				System.out.println("Closing app...");
				System.exit(0);
			}
			if (inputString.equalsIgnoreCase("b")) {
				System.out.println("You are already on the main menu");
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
					System.out.println("Unavailable input");
				}
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid input");
			}
		}
	}

	/*
	 * metodi generali che scaturiscono dal primo menu
	 * [mancano le opzioni quando cerco di modificare il biglietto]
	 */
	private void manageTicket() {
		
		System.out.println("\nTicket management Menu\n*******************");
		
		while (true) {
			System.out.println("\nTake a choice\n"
					+ "1 - Buy Ticket\n"
					+ "2 - Delete Ticket\n"
					+ "3 - Change Ticket");

			System.out.print("Input: ");
			inputString = scanner.nextLine();

			if (inputString.contentEquals("e")) {
				System.out.println("Closing app...");
				System.exit(0);
			}
			if (inputString.contentEquals("b"))
				break;

			try {

				switch (Integer.parseInt(inputString)) {
				
				case 1:
					System.out.println("\nAvailable Moviei: \n"+cinema.printMovie());
					System.out.print("Insert Movie: ");
					String film = scanner.nextLine();
					System.out.println("Available showing:\n"+ cinema.searchMovie(film).printMovieShowing());
					System.out.print("Insert showing: ");
					String showing = scanner.nextLine();
					System.out.println(cinema.searchMovie(film).searchShowing(showing).printSeats());
					System.out.print("Insert seat: ");
					String sedile = scanner.nextLine();
					System.out.println("\n"+cinema.bookSeat(cinema.searchMovie(film),sedile, showing));
					break;
					
				case 2:
					System.out.print("Insert ticket code to remove: ");
					inputString = scanner.nextLine();
					if(cinema.deleteBooking(inputString));
					System.out.println("Succesfully removed");
					break;
					
				case 3:
					System.out.println("Insert ticket code to change: ");
					inputString = scanner.nextLine();
					Ticket ticket = TicketLedger.getTicketLedger().searchTicket(inputString);
					changeTicket(ticket);
					break;
					
				default:
					System.out.println("Unavaliable input");
				}
			}catch (NumberFormatException e) {
				System.out.println("Invalid input");
				continue;
			}catch (SearchException e) {
				System.out.println(e.getMessage());
				continue;
			}
			//non toccarlo, puo tornare altre eccezioni che devo verificare
			catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
	}
	/*
	 * [quando cerco di modificare la sala non posso modificare nulla (posti...) perchè si ripresenta sempre lo stesso menù]
	 * [non posso visualizzare nessuna proiezione]
	 * [se cerco di aggiungere una proiezione mi ritorna sempre null senza capire l'errore]
	 * [manca tutto il menù opzioni quando cerco di modificare una proiezione]
	 */
	@SuppressWarnings("deprecation")
	private void manageTheater() {
		System.out.println("\nTheatre management menu\n*******************");

		while (true) {
			System.out.println("\nTake a choice\n"
					+ "1 - Add theatre\n"
					+ "2 - Remove theatre\n"
					+ "3 - Change theatre\n"
					+ "4 - Show available theatre\n"
					+ "5 - Show available showing\n"
					+ "6 - Add showing\n"
					+ "7 - Remove showing\n"
					+ "8 - Change showing");


			System.out.print("Input: ");
			inputString = scanner.nextLine();

			if (inputString.contentEquals("e")) {
				System.out.println("Closing app...");
				System.exit(0);
			}
			if (inputString.contentEquals("b"))
				break;

			
			try {
				switch (Integer.parseInt(inputString)) {
				
				case 1:
					System.out.print("Name: ");
					String nomeSala = scanner.nextLine();
					System.out.print("Row and column, separeted by a comma: ");
					stringList = scanner.nextLine().split(",");
					if(cinema.createTheatre(nomeSala, Integer.parseInt(stringList[0]), Integer.parseInt(stringList[1])))
						System.out.println("Succesfully added with name " + nomeSala);
					break;
					
				case 2:
					System.out.println("Theatre's name to remove");
					cinema.deleteTheatre(scanner.nextLine()); //in caso di problemi ritorna eccezione con messaggio
					System.out.println("Succesfully removed ");
					break;
					
				case 3:
					System.out.println("Theatre's name to change: ");
					inputString = scanner.nextLine();
					Theatre theatre = cinema.searchTheatre(inputString);
					this.changeTheatre(theatre);
					break;
					
				case 4:
					System.out.println(cinema.printTheatres());
					break;
					
				case 5:
					System.out.println("Unavailable option");
					break;
					
				case 6:
					//MANNAGGIA A DATE MODULO DI MERDAAAA
					//è pure deprecato, ma quanto siamo arcaici
					System.out.println("ATTENZIONE: FUNZIONE NON ANCORA FUNZIONANTE"); 
					System.out.print("Inserisci film: ");
					String film = scanner.nextLine();
					System.out.print("Inserisci il nome della sala: ");
					String sala = scanner.nextLine();
					System.out.print("Inserisci data (dd-mm-yyyy-hh-mm): ");
					stringList = scanner.nextLine().split("-"); //non mi piace, sarÃ  una soluzione temporanea, dobbiamo capire bene come funziona Date
					System.out.print("Inserisci prezzo: ");
					String prezzo = scanner.nextLine();
					//non va da controllare
					MovieShowing mS = cinema.searchMovie(film).addMovieShowing(new Date(Integer.parseInt(stringList[0]),Integer.parseInt(stringList[1]),Integer.parseInt(stringList[2]),Integer.parseInt(stringList[3]),Integer.parseInt(stringList[4])),
							cinema.searchTheatre(sala),Double.parseDouble(prezzo));
					System.out.println("Aggiunto con successo con codice: "+mS.getId());
					break;
					
				case 7:
					System.out.print("Movie: ");
					film = scanner.nextLine();
					System.out.print("Showing's ID: ");
					String proiezione = scanner.nextLine();
					//niente if, ritorna la searchException
					cinema.searchMovie(film).deleteMovieShowing(proiezione);
					System.out.println("Succesfully removed");
					break;
					
				case 8:
					System.out.println("Film name to change showing: ");
					Movie movie = cinema.searchMovie(scanner.nextLine());
					movie.printMovieShowing();
					System.out.println("Showing'g ID to change: ");
					MovieShowing showing = movie.searchShowing(scanner.nextLine());
					this.changeShowing(showing);
					break;
					
				default:
					System.out.println("Unavailable input");
				}
			}catch (NumberFormatException e) {
				System.out.println("Invalid input");
				continue;
			}catch (SearchException e) {
				System.out.println(e.getMessage());
				continue;
			}catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
	}

/*
 * [quando cerchi di modificare un film non succede nulla]
 */
	private void manageFilm() {
		System.out.println("Movie management menu\n************");

		while (true) {
			System.out.println("\nTake a choice\n"
					+ "1 - Add movie\n"
					+ "2 - Remove movie\n"
					+ "3 - Change movie\n"
					+ "4 - Show available movie");


			System.out.print("Input: ");
			inputString = scanner.nextLine();

			if (inputString.contentEquals("e")) {
				System.out.println("Closing app...");
				System.exit(0);
			}if (inputString.contentEquals("b"))
				break;

			
			try {
				switch (Integer.parseInt(inputString)) {
				
				case 1:
					System.out.print("Name: ");
					String titolo = scanner.nextLine();
					System.out.print("Duration: ");
					String durata = scanner.nextLine();
					//ritorna eccezione, niente if
					cinema.addMovie(titolo, Integer.parseInt(durata));
					System.out.println(titolo + " succesfully added");
					break;
					
				case 2:
					System.out.println("Movie name to remove: ");
					cinema.deleteMovie(scanner.nextLine());
					System.out.println("Succesfully removed");
					break;
					
				case 3:
					System.out.println("Movie name to remove: ");
					Movie movie = cinema.searchMovie(scanner.nextLine());
					this.changeMovie(movie);
					break;
					
				case 4:
					System.out.println(cinema.printMovie());
					System.out.print("Enter to continue");
					scanner.nextLine();
					break;
					
				default:
					System.out.println("Unavailable input");
				}
			}catch (NumberFormatException e) {
				System.out.println("Invalid input");
				continue;
			}catch (SearchException e) {
				System.out.println(e.getMessage());
				continue;
			}catch (Exception e) {
				System.out.println(e.getMessage());
				continue;
			}
		}
	}
	
	//metodi che scaturiscono dai secondi menu
	private void changeTicket(Ticket ticket) {
		
		System.out.println("\nEdit ticket menu\n*******************");
		
		/* MANCANO i setter nel ticket, li devo aggiungere?
		 * è necessario modificare un biglietto?
		 */
		while (true) {
			System.out.println("\nTake a choice\n"
					+ "1 - vuoto\n"
					+ "2 - vuoto\n"
					+ "3 - vuoto");

			System.out.print("Input: ");
			inputString = scanner.nextLine();

			if (inputString.contentEquals("e")) {
				System.out.println("Closing app...");
				System.exit(0);
			}
			if (inputString.contentEquals("b"))
				break;

			try {

				switch (Integer.parseInt(inputString)) {
				
				case 1:
					break;
					
				case 2:
					break;
					
				case 3:
					break;
					
				default:
					System.out.println("Unavailable input");
				}
			}catch (NumberFormatException e) {
				System.out.println("Invalid input");
				continue;
			}
		}
		
	}
	
	private void changeTheatre(Theatre sala) {
		
		System.out.println("\nEdit theatre menu\n*******************");
		
		/* il setter in theatre non va bene, un utente vorrebbe cambiare
		 * solo righe e colonne, poi è compito della classe sala implementare un 
		 * algoritmo che ricrea la seatList
		 */
		
		while (true) {
			System.out.println("\nTake a choice\n"
					+ "1 - Change row\n"
					+ "2 - Change column\n"
					+ "3 - Total change");

			System.out.print("Input: ");
			inputString = scanner.nextLine();

			if (inputString.contentEquals("e")) {
				System.out.println("Closing app...");
				System.exit(0);
			}
			if (inputString.contentEquals("b"))
				break;

			try {

				switch (Integer.parseInt(inputString)) {
				
				case 1:
					break;
					
				case 2:
					break;
					
				case 3:
					break;
					
				default:
					System.out.println("Unavailable input");
				}
			}catch (NumberFormatException e) {
				System.out.println("Invalid input");
				continue;
			}
		}
		
	}
	
	private void changeShowing(MovieShowing showing) {
		
		System.out.println("\nEdit showing menu\n*******************");
		
		/* come per ticket, mancano i setter
		 */
		
		while (true) {
			System.out.println("\nTake a choice\n");

			System.out.print("Input: ");
			inputString = scanner.nextLine();

			if (inputString.contentEquals("e")) {
				System.out.println("Closing app...");
				System.exit(0);
			}
			if (inputString.contentEquals("b"))
				break;

			try {

				switch (Integer.parseInt(inputString)) {
				
				case 1:
					break;
					
				case 2:
					break;
					
				case 3:
					break;
					
				default:
					System.out.println("Invalid input");
				}
			}catch (NumberFormatException e) {
				System.out.println("Invalid input");
				continue;
			}
		}
		
	}
	private void changeMovie(Movie movie) {
		
		System.out.println("\nEdit movie menu\n*****************");
		
		/* come per film, mancano i setter
		 */
		
		while (true) {
			System.out.println("\nTake a choice\n"
					+ "1 - Change name\n"
					+ "2 - Change plot\n"
					+ "3 - Change duration\n");

			System.out.print("Scelta: ");
			inputString = scanner.nextLine();

			if (inputString.contentEquals("e")) {
				System.out.println("Chiusura app...");
				System.exit(0);
			}
			if (inputString.contentEquals("b"))
				break;

			try {

				switch (Integer.parseInt(inputString)) {
				
				case 1:
					System.out.println("New name: ");
					movie.setTitle(scanner.nextLine());
					break;
					
				case 2:
					System.out.println("New plot: ");
					movie.setPlot(scanner.nextLine());
					break;
					
				case 3:
					System.out.println("New duration: ");
					inputString = scanner.nextLine();
					movie.setDuration(Integer.parseInt(inputString));
					break;
					
				default:
					System.out.println("Invalid input");
				}
			}catch (NumberFormatException e) {
				System.out.println("Invalid input");
				continue;
			}
		}
		
	}
}
