package server.handler.adminHandler;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;

public class Discount {

	public static String doAction(HttpServletRequest req) {
		String code = req.getParameter("code");
				
		switch (req.getParameter("action")) {
		case "save":

			try {
				double value = Double.valueOf(req.getParameter("value"));
				Cinema.getCinema().createDiscountCode(code, value);
			}catch (NumberFormatException e) {
				e.printStackTrace();
				return "Value insert not correct";
			}catch (Exception e) {
				e.printStackTrace();
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
