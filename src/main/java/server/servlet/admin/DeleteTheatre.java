package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.TheatreHandler;
import server.exception.ObjectNotFoundException;

public class DeleteTheatre {

	public static String doAction(HttpServletRequest req) {

		String name = req.getParameter("name");
		if (name == null || name.contentEquals(""))
			return "Please enter valid name";

		try {
			TheatreHandler.getInstance().deleteTheatre(name);
		}
		catch (SQLException e) {
			return name + " not exists yet";
		} catch (ObjectNotFoundException e) {
			return name + " not found or already deleted";
		}catch (Exception e) {
			e.printStackTrace();
			return "impossible to remove " + name;
		}
		return name + " successfully removed. Recharge to see changes";
	}
}
