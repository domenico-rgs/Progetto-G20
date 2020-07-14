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
	private static final int timeWait = 1; //in minuti
	private ShopTimer waiting;
	private boolean valid;

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
		try {
			if(waiting.isAlive()) {
				waiting.interrupt();
			}
		}catch (Exception e) {
			//nothing
		}


		// start the wait thread
		waiting = new ShopTimer(timeWait, this);
		waiting.start();
		valid = true;
		String id = req.getParameter("id");
		String[] seats = Cinema.getCinema().getShopCart().getSeats();

		try {

			resp.getWriter().write(Rythm.render("shop.html", Cinema.getCinema().getMovieShowing(id),
					seats,
					Cinema.getCinema().ticketsPrice(id, seats),
					Cinema.getCinema().getShopCart().getTotal()));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!valid) {
			resp.getWriter().write("Carrello scaduto, rifai ordinazione");
			return;
		}
		switch(req.getParameter("action")) {
		case "discount":
			// to be fixed again in the future
			String code = req.getParameter("code");
			double price = Double.valueOf(req.getParameter("price"));
			double finalPrice;

			// if the ticket is already used in the same purchase
			if (Cinema.getCinema().getShopCart().hasCode(code)) {
				resp.getWriter().write("-1.0");
				break;
			}

			try {
				finalPrice = Cinema.getCinema().applyDiscountOnPrice(code, price);
				Cinema.getCinema().getShopCart().addCode(code);
				Cinema.getCinema().getShopCart().setTotal(finalPrice);
				finalPrice = (double) Math.round(finalPrice * 100) / 100;
				resp.getWriter().write(String.valueOf(String.valueOf(finalPrice)));
				break;
			} catch (SQLException | IOException | SeatException e) {
				e.getStackTrace();
			}catch(PaymentException e1) {
				finalPrice=price;
				resp.getWriter().write(String.valueOf(String.valueOf(finalPrice)));
				break;
			}
				
		case "buy":
			String codeCard = req.getParameter("codeCard");
			String date = req.getParameter("date");
			String cvv = req.getParameter("cvv");
			String email = req.getParameter("email");

			System.out.println(date);


			try {
				boolean value = Cinema.getCinema().buyTicket(codeCard, date, cvv, email);
				resp.getWriter().write(String.valueOf(value));

				//interrupt the wait thread if it works
				if (value) {
					this.waiting.interrupt();
				}
			}catch (Exception e) {
				e.printStackTrace();
				resp.getWriter().write("Impossible buy this ticket");
			}
			break;
		}
	}

	public void timeBreak() {
		String id = Cinema.getCinema().getShopCart().getIdSh();
		String[] seats = Cinema.getCinema().getShopCart().getSeats();

		//true == free seat
		try {
			Cinema.getCinema().setAvailability(id, seats, true);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SeatException e) {
			e.printStackTrace();
		}
		Cinema.getCinema().getShopCart().refresh();
		valid = false;
	}
}
