package server.handler.adminHandler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.SeatException;

public class AddTheatre {

	public static String doAction(HttpServletRequest req) {
		String theatreName = req.getParameter("name");
		String config = req.getParameter("config");

		//verifica integrita della configurazione
		String[] test = config.split("-");
		for (String s: test) {
			if (s.length() != 1) return "Incorrect configuration";

			if (!s.contentEquals("P") || !s.contentEquals("X") || !s.contentEquals("D"))
				return "Incorrect seats type. Please check the legend";
		}


		try {
			Cinema.getCinema().createTheatre(theatreName, config);
		} catch (IOException | SeatException | SQLException e) {
			e.printStackTrace();
			return "Error with creation of new theatre. Please try again";
		}

		return theatreName + " succefully added. Reload to see changes";

	}
}
