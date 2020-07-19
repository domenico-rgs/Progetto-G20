package server.servlet.admin;

import javax.servlet.http.HttpServletRequest;

import server.domain.controller.BuyTicketHandler;
import server.domain.controller.MovieHandler;

public class DeleteTicket {
	
	public static String doAction(HttpServletRequest req) {
		
		String ticket = req.getParameter("code");
		
		try {
			BuyTicketHandler.getInstance().deleteTicket(ticket);
			return ticket + " deleted with success";
		} catch (Exception e) {
			return ticket + " not found or already deleted";
		}
	}

}
