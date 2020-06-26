package server.handler.adminHandler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.CinemaFacade;
import server.domain.exception.SeatException;

public class AddTheatre {

	public static String doAction(HttpServletRequest req) {
		String theatreName = req.getParameter("name");
		String config = req.getParameter("config");


		try {
			CinemaFacade.getCinema().createTheatre(theatreName, config);
		} catch (IOException | SeatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return theatreName + " succefully added. Reload to see changes";

	}
}
