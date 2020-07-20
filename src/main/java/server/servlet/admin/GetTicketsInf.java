package server.servlet.admin;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.rythmengine.Rythm;

import server.domain.controller.BuyTicketHandler;

public class GetTicketsInf {
	
	public static String doAction(HttpServletRequest req) {
		
		String id = req.getParameter("id");
		String resp;
		
		try {
			
			if (BuyTicketHandler.getInstance().getTicketsForShowing(id).size() == 0)
				return "No tickets for " + id;
			
			System.out.println("prova");
			
			resp = Rythm.render("imported/ticketTableAdmin.html",
					BuyTicketHandler.getInstance().getTicketsForShowing(id),
					id);
			return resp;
		} catch (SQLException e) {
			e.printStackTrace();
			return "No tickets for " +id;
		}catch (Exception e) {
			e.printStackTrace();
			return "Server Error";
		}
		
		
	}

}
