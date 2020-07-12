package server.handler;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;
import server.domain.exception.SeatException;



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
		
		if (!valid) {
			resp.getWriter().write("Carrello scaduto, rifai ordinazione");
			return;
		}
			


		switch(req.getParameter("action")) {
		case "discount":
			//da sistemare in futuro ancora
			String code = req.getParameter("code");

			double discount = Cinema.getCinema().getDiscount(code);

			resp.getWriter().write(String.valueOf(discount));

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
