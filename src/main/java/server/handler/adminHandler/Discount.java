package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;

public class Discount {

	public static String doAction(HttpServletRequest req) {

		switch (req.getParameter("action")) {
		case "save":
			String code = req.getParameter("code");

			try {
				double value = Double.valueOf(req.getParameter("value"));
				Cinema.getCinema().createDiscountCode(code, value);
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
				return "Value insert not correct";
			}
			catch (Exception e) {
				e.printStackTrace();
				return "problem with server";
			}

			return "Code successfully added. Recharge to see changes";

		case "remove":
			try {
				//da scrivere
			}
			catch (Exception e) {
				e.printStackTrace();
				return "problem with server";
			}
			return "Code removed with success";

		}

		return "problem with javascript script";
	}


}
