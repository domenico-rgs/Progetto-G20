package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.TheatreHandler;
import server.exception.SeatException;

public class AddTheatre {

	public static String doAction(HttpServletRequest req) {
		String theatreName = req.getParameter("name");
		String config = req.getParameter("config");

		if(theatreName.equals(""))
			return "Please insert correct value for name";

		try {
			TheatreHandler.getInstance().createTheatre(theatreName, config);
		} catch (SQLException e) {
			System.out.println(e);
			return theatreName+ " already exists or problem with database";
		}catch(SeatException e) {
			System.out.println(e);
			return "Configuration is errated";
		}catch (Exception e) {
			System.out.println(e);
			return e.getMessage();
		}
		return theatreName + " succefully added. Reload to see changes";
	}

}