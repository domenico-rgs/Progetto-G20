package server.handler.adminHandler;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.exception.SeatException;

public class AddTheatre {

	public static String doAction(HttpServletRequest req) {
		String theatreName = req.getParameter("name");
		String config = req.getParameter("config");
		
		if(theatreName.equals(""))
			theatreName = null;

		try {
			Cinema.getCinema().createTheatre(theatreName, config);
		} catch (SQLException e) {
			System.out.println(e);
			return "Problem with database or " + theatreName + " already exists";
		}catch(SeatException e) {
			System.out.println(e);
			return "Configuration is errated";
		}catch (Exception e) {
			System.out.println(e);
			return e.toString();
		}
		return theatreName + " succefully added. Reload to see changes";

	}
}
