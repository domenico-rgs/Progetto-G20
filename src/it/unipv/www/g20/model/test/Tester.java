package it.unipv.www.g20.model.test;

import java.text.ParseException;

import it.unipv.www.g20.model.cinema.Cinema;
import it.unipv.www.g20.model.exception.SearchException;

public class Tester {

	public static void main(String[] args) {
		Cinema cin1 = new Cinema("Uci");
		//AdminTextUI admin = new AdminTextUI(cin1);
		//admin.start();
		/* test metodi senza interfaccia grafica */
		try {
			cin1.addTheatre("sala1", 4, 4);
			//cin1.addTheatre("sala1", 4, 4);	// test ok nell'usare lo stesso nome

		} catch (SearchException e) {
			e.printStackTrace();
		}
		try {
			cin1.addMovie("MrRobot");
			cin1.addMovie("JamesBond");

			//cin1.addMovie("MrRobot");			//test ok nell'aggiungere stesso nome di film
		} catch (SearchException e) {
			e.printStackTrace();
		}
		try {
			cin1.createMovieShowing("MrRobot", "sala1", "24/04/2020 20:30", 8.90);
			cin1.createMovieShowing("MrRobot", "sala1", "60/04/2020 10:30", 8.90);		//data errata ma viene accettata lo stesso
			cin1.createMovieShowing("MrRobot", "sala1", "24/04/2020 60:30", 8.90);		//orario errato ma viene accettato lo stesso
			cin1.createMovieShowing("MrRobot", "sala1", "24/04/2020 20:39", 90000.0);
			//cin1.createMovieShowing("MrRobot", "sala1", "24-04-2020 20:30", 8.90);	//tipo di data non valido
			cin1.createMovieShowing("JamesBond", "sala1", "24/04/2020 20:31", 8.90);  	//primo errore la proiezione deve avere un lasso di tempo prestabilito
			// cin1.createMovieShowing("MrRobot", "sala1", "24/04/2020 20:30", 8.90); 	//test ok aggiunta proiezione uguale
			//cin1.createMovieShowing("MrRobot2", "sala1", "24/04/2020 20:33", 8.90); 	//condizione in cui controlla l-esistenza di un film
			//cin1.createMovieShowing("MrRobot", "sala2", "24/04/2020 20:30", 8.90);  	//condizione in cui si controlla l'esistenza della sala
		} catch (ParseException | SearchException e) {

			e.printStackTrace();
		}

		System.out.println(cin1.printProgramming());



	}

}
