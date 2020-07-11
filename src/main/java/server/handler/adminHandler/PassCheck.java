package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

public class PassCheck {

	private final static String password = "password";

	public static String doAction(HttpServletRequest req) {

		String pass = req.getParameter("passText");

		if (pass.contentEquals(password))
			return "true";

		else
			return "false";

	}

}
