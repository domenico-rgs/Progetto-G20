package server.handler.adminHandler;

import javax.servlet.http.HttpServletRequest;

import server.domain.cinema.Cinema;
import server.domain.payment.discount.PricingStrategyFactory;

public class Discount {
	
	public static String doAction(HttpServletRequest req) {
		
		switch (req.getParameter("action")) {
		case "save":
			String code = req.getParameter("code");
			
			try {
				double value = Double.valueOf(req.getParameter("value"));
				PricingStrategyFactory.getInstance().createDiscountCode(code, value);
			}
			catch (NumberFormatException e) {
				return "Value insert not correct";
			}
			catch (Exception e) {
				return "problem with server";
			}
			
			return "Code successfully added. Recharge to see changes";

		case "remove":
			try {
				//PricingStrategyFactory.getInstance().deleteCode(req.getParameter("code"));
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
