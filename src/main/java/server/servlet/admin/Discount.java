package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.DiscountHandler;
import server.exception.DiscountException;

public class Discount {

	public static String doAction(HttpServletRequest req) {
		String code = req.getParameter("code");

		if (code.contentEquals(""))
			return "Please insert correct data for code";

		switch (req.getParameter("action")) {
		case "save":

			try {
				double value = Double.valueOf(req.getParameter("value"));
				DiscountHandler.getInstance().createDiscountCode(code, value);
			}catch (NumberFormatException e) {
				return "Value not correct";
			}catch (SQLException e) {
				return "Code already exist";
			} catch (DiscountException e) {
				return e.getMessage();
			}catch (Exception e) {
				return "Something went wrong";
			}

			return "Code successfully added. Recharge to see changes";

		case "remove":
			try {
				DiscountHandler.getInstance().deleteDiscount(code);
			}catch (SQLException e) {
				return "Ticket already deleted";
			}catch (Exception e) {
				e.printStackTrace();
				return "Something went wrong";
			}
			return "Code removed with success";
		}
		return "There was a problem with javascript script";
	}
}
