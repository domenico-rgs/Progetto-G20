package server.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;



public class ShopCard implements IHandler {

	private static ShopCard instance = null;

	private ShopCard() {
	}


	public static ShopCard getInstance() {

		if (instance == null)
			instance = new ShopCard();

		return instance;
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		


		try {
			Cinema.getCinema().setShopCardTotal();
			resp.getWriter().write(Rythm.render("shop.html", Cinema.getCinema().getMovieShowing(req.getParameter("id")),
					Cinema.getCinema().getShopCard().getSeats(),
					Cinema.getCinema().getShopCard().getTotal()));
		}
		catch (Exception e) {
			e.printStackTrace();
		}


	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		switch(req.getParameter("action")) {
		case "discount":

			String code = req.getParameter("code");

			double discount = Cinema.getCinema().getDiscount(code);

			resp.getWriter().write(String.valueOf(discount));

		case "buy":
			String codeCard = req.getParameter("codeCard");
			String date = req.getParameter("date");
			String cvv = req.getParameter("cvv");
			String email = req.getParameter("email");

			try {
				boolean value = Cinema.getCinema().buyTicket(codeCard, date, cvv, email);
				resp.getWriter().write(String.valueOf(value));
			}
			catch (Exception e) {
				e.printStackTrace();
				resp.getWriter().write("Impossible buy this ticket");
			}

			break;
		}



	}

}
