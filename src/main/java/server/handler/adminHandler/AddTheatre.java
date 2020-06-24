package server.handler.adminHandler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;
import server.domain.exception.SeatException;

public class AddTheatre {
public static String doAction(HttpServletRequest req) {
		String theatreName = req.getParameter("name");
		String config = req.getParameter("config");


		try {
			Cinema.getCinema().createTheatre(theatreName, config);
		} catch (SearchException e) {
			return "Error: " + theatreName + " already exists";
		}catch (IOException | SeatException e) {
			e.printStackTrace();
		}

		return theatreName + " succefully added. Reload to see changes";

	}
}
