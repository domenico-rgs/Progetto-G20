package server.handler.adminHandler;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;


/**this class together with others serve as a check on the admin page, precisely this
 * deals with connecting the web interface to logic and transmitting information to insert a discount code*/

public class Discount {

	public static String doAction(HttpServletRequest req) {
		String code = req.getParameter("code");

		if (code.contentEquals(""))
			return "Please insert correct data for code";

		switch (req.getParameter("action")) {
		case "save":

			try {
				double value = Double.valueOf(req.getParameter("value"));
				Cinema.getCinema().createDiscountCode(code, value);
			}catch (NumberFormatException e) {
				System.out.println(e.toString());
				return "Value not correct";
			}catch (SQLException e) {
				System.out.println(e.toString());
				return "Code already exist";
			}catch (Exception e) {
				System.out.println(e.toString());
				return "problem with server";
			}

			return "Code successfully added. Recharge to see changes";

		case "remove":
			try {
				Cinema.getCinema().deleteDiscount(code);
			}catch (SQLException e) {
				System.out.println(e);
				return "Ticket already deleted";
			}catch (Exception e) {
				System.out.println(e);
				return e.toString();
			}
			return "Code removed with success";
		}
		return "problem with javascript script";
	}
}
