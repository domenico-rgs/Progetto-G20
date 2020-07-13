package server.handler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;
import server.domain.exception.SeatException;
import server.domain.payment.discount.PricingStrategyFactory;
import server.domain.payment.discount.TicketPricingStrategy;



public class ShopCard implements IHandler {

	private static ShopCard instance = null;
	private static final int timeWait = 1; //in minuti
	private ShopTimer waiting;
	private boolean valid;

	private ShopCard() {
	}


	public static ShopCard getInstance() {

		if (instance == null)
			instance = new ShopCard();

		return instance;
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//avvia il thread di attesa
		waiting = new ShopTimer(timeWait, this);
		waiting.start();
		valid = true;
		String id = req.getParameter("id");
		String[] seats = Cinema.getCinema().getShopCard().getSeats();

		try {
			
			resp.getWriter().write(Rythm.render("shop.html", Cinema.getCinema().getMovieShowing(id),
					seats,
					Cinema.getCinema().ticketsPrice(id, seats),
					Cinema.getCinema().getShopCard().getTotal()));
		}
		catch (Exception e) {
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
			//da sistemare in futuro ancora
			String code = req.getParameter("code");
			double price = Double.valueOf(req.getParameter("price"));
			double finalPrice;
			
			System.out.println(code);
			System.out.println(price);
			
			//se il biglietto è gia usato nello stesso acquisto
			if (Cinema.getCinema().getShopCard().hasCode(code)) {
				resp.getWriter().write("-1.0");
			}

			try {
				finalPrice = PricingStrategyFactory.getInstance().getCodeStrategy(code).getTotalPrice(price);
				Cinema.getCinema().getShopCard().addCode(code);
				Cinema.getCinema().getShopCard().setTotal(finalPrice);
				finalPrice = (double) Math.round(finalPrice * 100) / 100;
			}
			catch (Exception e) {
				e.printStackTrace();
				finalPrice = 0.0;
			}

			resp.getWriter().write(String.valueOf(String.valueOf(finalPrice)));
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
				
				//interropre il thread di attesa se funziona
				if (value) 
					this.waiting.interrupt();
			}
			catch (Exception e) {
				e.printStackTrace();
				resp.getWriter().write("Impossible buy this ticket");
			}

			break;
		}

	}
	
	public void timeBreak() {
		String id = Cinema.getCinema().getShopCard().getIdSh();
		String[] seats = Cinema.getCinema().getShopCard().getSeats();
		
		//true = posti liberati
		try {
			Cinema.getCinema().setAvailability(id, seats, true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SeatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Cinema.getCinema().getShopCard().refresh();
		valid = false;		
	}

}
