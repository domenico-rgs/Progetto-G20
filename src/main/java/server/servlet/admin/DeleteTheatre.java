package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.TheatreHandler;

public class DeleteTheatre {

	public static String doAction(HttpServletRequest req) {
		
		String name = req.getParameter("name");
		if (name == null || name.contentEquals(""))
			return "Please enter valid name";

		try {
			TheatreHandler.getInstance().deleteTheatre(name);
		}
		catch (SQLException e) {
			e.printStackTrace();
			return name + " not exists yet";
		} catch (Exception e) {
			e.printStackTrace();
			return "impossible to remove " + name;
		}
		return name + " successfully removed. Recharge to see changes";
	}
}
