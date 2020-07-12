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

		try {
			Cinema.getCinema().createTheatre(theatreName, config);
		} catch (IOException | SeatException | SQLException e) {
			e.printStackTrace();
			return "Error with creation of new theatre. Please try again";
		}

		return theatreName + " succefully added. Reload to see changes";

	}
}
