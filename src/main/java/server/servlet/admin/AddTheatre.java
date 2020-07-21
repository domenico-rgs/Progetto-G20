package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.TheatreHandler;
import server.exception.SearchException;
import server.exception.SeatException;

public class AddTheatre {

	public static String doAction(HttpServletRequest req) {
		String theatreName = req.getParameter("name");
		String config = req.getParameter("config");

		if(theatreName.equals(""))
			return "Please insert correct value for name";

		try {
			TheatreHandler.getInstance().createTheatre(theatreName, config);
			return theatreName + " succefully added. Reload to see changes";

		} catch (SQLException e) {
			return theatreName+ " already exists or problem with database";
		}catch(SeatException e) {
			return "Configuration is errated";
		} catch (SearchException e) {
			return "You cannot edit the config of an existing theatre";
		}catch (Exception e) {
			e.printStackTrace();
			return "Something went wrong";
		}
	}
}
