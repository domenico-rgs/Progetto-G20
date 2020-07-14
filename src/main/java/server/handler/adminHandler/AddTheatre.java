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
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			return "Error with creation of new theatre. Please try again";
		}catch(SeatException e) {
			return "Configuration is errated";
		}
		return theatreName + " succefully added. Reload to see changes";

	}
}
