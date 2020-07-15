package server.handler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;
import server.domain.exception.PaymentException;
import server.domain.exception.SeatException;



public class ShopCart implements IHandler {
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
		String realShopId = Cinema.getCinema().getShopCart().getID();
		
		if (shopId == null || !(shopId.contentEquals(realShopId))) {
			resp.getWriter().write(Rythm.render("404.html"));
			return;
		}
		
		String idsh = Cinema.getCinema().getShopCart().getIdSh();
		String []seats = Cinema.getCinema().getShopCart().getSeats();
		

		try {

			resp.getWriter().write(Rythm.render("shop.html", Cinema.getCinema().getMovieShowing(idsh),
					seats,
					Cinema.getCinema().ticketsPrice(idsh, seats),
					Cinema.getCinema().getShopCart().getTotal()));
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
			if (Cinema.getCinema().getShopCart().hasCode(code)) {
				resp.getWriter().write("already");
				break;
			}
			
			
			if (code == null || code.contentEquals("")) {
				resp.getWriter().write("not");
				break;
			}
				

			try {
				finalPrice = Cinema.getCinema().applyDiscountOnPrice(code, price);
				Cinema.getCinema().getShopCart().addCode(code);
				Cinema.getCinema().getShopCart().setTotal(finalPrice);
				finalPrice = (double) Math.round(finalPrice * 100) / 100;
				resp.getWriter().write(String.valueOf(finalPrice));
			} catch(Exception e) {
				e.printStackTrace();
				resp.getWriter().write("not");
			}
			break;
				
		case "buy":
			String codeCard = req.getParameter("codeCard");
			String date = req.getParameter("date");
			String cvv = req.getParameter("cvv");
			String email = req.getParameter("email");

			System.out.println(date);


			try {
				boolean value = Cinema.getCinema().buyTicket(codeCard, date, cvv, email);
				resp.getWriter().write(String.valueOf(value));
				
				//acquisto va a buon fine
				if(value) {
					String id = Cinema.getCinema().getShopCart().getIdSh();
					String[] seats = Cinema.getCinema().getShopCart().getSeats();
					//false = set busy
					Cinema.getCinema().setAvailability(id, seats, false);
					Cinema.getCinema().getShopCart().refresh();
				}

			}catch (Exception e) {
				e.printStackTrace();
				resp.getWriter().write("Impossible buy this ticket");
			}
			break;
		}
	}
}
