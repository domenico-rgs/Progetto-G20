package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.TheatreHandler;
import server.exception.ObjectNotFoundException;
import server.exception.SearchException;

public class DeleteTheatre {

	public static String doAction(HttpServletRequest req) {

		String name = req.getParameter("name");
		if (name == null || name.contentEquals(""))
			return "Please enter valid name";

		try {
			TheatreHandler.getInstance().deleteTheatre(name);
			return name + " successfully removed. Recharge to see changes";

		}
		catch (SQLException e) {
			return name + " not exists yet";
		} catch (ObjectNotFoundException e) {
			return name + " not found or already deleted";
		} catch (SearchException e) {
			return "Cannot remove "+name+" because it's used";
		}catch (Exception e) {
			e.printStackTrace();
			return "Something went wrong";
		}
	}
}
