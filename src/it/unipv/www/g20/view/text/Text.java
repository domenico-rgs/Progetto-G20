package it.unipv.www.g20.view.text;

import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;

import it.unipv.www.g20.model.booking.Booking;
import it.unipv.www.g20.model.cinema.Cinema;
import it.unipv.www.g20.model.operator.Operator;
import it.unipv.www.g20.model.operator.TypeOperator;
import it.unipv.www.g20.model.theatre.Theatre;

public class Text {
	public Text(Cinema cinema) {
		GUIText(cinema);
	}

	@SuppressWarnings("resource")
	public static void GUIText(Cinema cinema) {
		Scanner input = new Scanner(System.in);
		String s;
		while(true) {
			System.out.println("-- Prenotazione Cinema --");
			System.out.println("Menù principale");
			System.out.println("1)Login \n2)Uscita\n");
			System.out.print("Scelta: ");
			s=input.nextLine().trim();
			switch(Integer.parseInt(s)) {
			case 1:
				System.out.print("id: ");
				String id=input.nextLine();
				System.out.print("password: ");
				s=input.nextLine();
				Operator o1 = cinema.searchOperator(Integer.parseInt(id));
				o1.login(s);
				
				if(o1.getType().equals(TypeOperator.CASHIER)) {
					System.out.println("Menu Cassiere");
					System.out.println("1)Vendere biglietto \n2)Logout");
					System.out.print("Scelta: ");
					s=input.nextLine().trim();
					switch(Integer.parseInt(s)) {
					case 1:
						System.out.println("Inserisci la sala: ");
						String sala = input.nextLine();
						Theatre sal = cinema.searchTheatre(sala);
						sal.printAvailableSeats();
						System.out.println("Inserisci posto: ");
						s = input.nextLine();
						sal.setSeat(s);
						Booking book =cinema.addBooking(Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY));
						System.out.println("Inserisci numero biglietti: ");
						String num = input.nextLine();
						System.out.print("Inserisci info ticket:");
						String info = input.nextLine();
						if(Integer.parseInt(num.trim())==1)
							book.createTicket(info);
						else
							book.createTicket(info, Integer.parseInt(num));
						System.out.println(book.toString());
						break;
					case 2:
						o1.logout();
						break;
					}
				}
			case 2:
				System.exit(0);
			default: 
				System.out.println("Comando non riconosciuto");
			}
		}

	}

}
