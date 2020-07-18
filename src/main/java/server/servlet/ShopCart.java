package server.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.controller.BuyTicketHandler;
import server.domain.controller.DiscountHandler;
import server.domain.controller.MovieShowingHandler;
import server.domain.controller.TheatreHandler;



public class ShopCart implements IHandlerState {
	private static ShopCart instance = null;

	private ShopCart() {}

	//*singleton*/
	public static ShopCart getInstance() {
		if (instance == null) {
			instance = new ShopCart();
		}
		return instance;

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String shopId = req.getParameter("shopID");
		String realShopId = BuyTicketHandler.getInstance().getShopCart().getID();

		if (shopId == null || !(shopId.contentEquals(realShopId))) {
			resp.getWriter().write(Rythm.render("404.html"));
			return;
		}

		String idsh = BuyTicketHandler.getInstance().getShopCart().getIdSh();
		String []seats = BuyTicketHandler.getInstance().getShopCart().getSeats();


		try {

			resp.getWriter().write(Rythm.render("shop.html", MovieShowingHandler.getInstance().getMovieShowing(idsh),
					seats,
					BuyTicketHandler.getInstance().ticketsPrice(idsh, seats),
					BuyTicketHandler.getInstance().getShopCart().getTotal()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		switch(req.getParameter("action")) {
		case "discount":
			// to be fixed again in the future
			String code = req.getParameter("code");
			double price = Double.valueOf(req.getParameter("price"));
			double finalPrice;


			// if the ticket is already used in the same purchase
			if (BuyTicketHandler.getInstance().getShopCart().hasCode(code)) {
				resp.getWriter().write("already");
				break;
			}

			if (code == null || code.contentEquals("")) {
				resp.getWriter().write("not");
				break;
			}


			try {
				finalPrice = BuyTicketHandler.getInstance().applyDiscountOnPrice(code, price);
				BuyTicketHandler.getInstance().getShopCart().addCode(code);
				BuyTicketHandler.getInstance().getShopCart().setTotal(finalPrice);
				finalPrice = (double) Math.round(finalPrice * 100) / 100;
				resp.getWriter().write(String.valueOf(finalPrice));
			} catch(Exception e) {
				resp.getWriter().write("not");
			}
			break;

		case "buy":
			String codeCard = req.getParameter("codeCard");
			String date = req.getParameter("date");
			String cvv = req.getParameter("cvv");
			String email = req.getParameter("email");

			try {
				boolean value = BuyTicketHandler.getInstance().buyTicket(codeCard, date, cvv, email);
				resp.getWriter().write(String.valueOf(value));

				//acquisto va a buon fine
				if(value) {
					String id = BuyTicketHandler.getInstance().getShopCart().getIdSh();
					String[] seats = BuyTicketHandler.getInstance().getShopCart().getSeats();
					//false = set busy
					TheatreHandler.getInstance().setAvailability(id, false, seats);
					BuyTicketHandler.getInstance().getShopCart().refresh();
				}

			}catch (Exception e) {
				e.printStackTrace();
				resp.getWriter().write("Impossible buy this ticket");
			}
			break;
		}
	}
}
