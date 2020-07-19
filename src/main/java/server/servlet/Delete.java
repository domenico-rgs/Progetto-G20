package server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.controller.BuyTicketHandler;

/**
 * Servlet class that connects the web interface to the logic of deleting
 * a ticket given the ticket code and the payment card number
 *
 * Singleton class (State pattern)
 */
public class Delete implements IHandlerState {
	private static Delete instance = null;

	private Delete() {}

	public static Delete getInstance() {
		if (instance == null) {
			instance = new Delete();
		}
		return instance;
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().write(Rythm.render("delete.html"));
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String ticket = req.getParameter("ticketCode");
		String cardN = req.getParameter("cardNumber");
		System.out.println(ticket);

		try {
			BuyTicketHandler.getInstance().deleteTicket(ticket, cardN);
			resp.getWriter().write("Refund requested with success");
		}catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write("Ticket code or card number not correct");
		}
	}
}