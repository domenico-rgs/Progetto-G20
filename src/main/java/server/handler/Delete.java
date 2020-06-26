package server.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.CinemaFacade;
import server.domain.exception.SearchException;

public class Delete implements IHandler {

	private static Delete instance = null;


	private Delete() {
	}


	public static Delete getInstance() {

		if (instance == null)
			instance = new Delete();

		return instance;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.getWriter().write(Rythm.render("delete.html"));

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			CinemaFacade.getCinema().deleteBooking(req.getParameter("ticketCode"));
			resp.getWriter().write(Rythm.render("delete.html", true, "The reservation has been canceled, you will receive a refund as soon as possible on the payment method used for the purchase"));
		} catch (SearchException e) {
			resp.getWriter().write(Rythm.render("delete.html", false, "The ticket was not removed, the code may be incorrect"));
		}
	}

}
