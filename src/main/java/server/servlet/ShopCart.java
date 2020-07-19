package server.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.controller.BuyTicketHandler;
import server.domain.controller.MovieShowingHandler;
import server.exception.ObjectNotFoundException;
import server.exception.PaymentException;


/**
 * This servlet is used to manage a purchase
 *
 * Singleton class (State pattern)
 */
public class ShopCart implements IHandlerState {
	private static ShopCart instance = null;

	private ShopCart() {}

	public static ShopCart getInstance() {
		if (instance == null) {
			instance = new ShopCart();
		}
		return instance;

	}

	@Override /**/
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String shopId = req.getParameter("shopID");
		String realShopId = BuyTicketHandler.getInstance().getShopCartID();

		if (shopId == null || !(shopId.contentEquals(realShopId))) {
			resp.getWriter().write(Rythm.render("404.html"));
			return;
		}

		String idsh = BuyTicketHandler.getInstance().getShopCartIdSh();
		String []seats = BuyTicketHandler.getInstance().getSelectedSeats();


		try {
			resp.getWriter().write(Rythm.render("shop.html", MovieShowingHandler.getInstance().getMovieShowing(idsh),
					seats,
					BuyTicketHandler.getInstance().ticketsPrice(idsh, seats),
					BuyTicketHandler.getInstance().getShopCartTotal()));
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

			if (code == null || code.contentEquals("")) {
				resp.getWriter().write("not");
				break;
			}


			try {
				finalPrice = BuyTicketHandler.getInstance().applyDiscountOnPrice(code, price);

				//codice gia usato
				if (finalPrice == -1.0) {
					resp.getWriter().write("already");
					break;
				}
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
			}catch (MessagingException e) {
				e.printStackTrace();
				resp.getWriter().write("Email not valid");
			}catch (PaymentException e) {
				e.printStackTrace();
				resp.getWriter().write("Something went wrong with payment, recheck your card data please");
			}catch (Exception e) {
				e.printStackTrace();
				resp.getWriter().write("Impossible buy this ticket, something went wront. Contact us!");
			}
			break;
		}
	}
}
