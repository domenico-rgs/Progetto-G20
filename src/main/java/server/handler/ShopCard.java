package server.handler;

import java.io.IOException;
import java.util.List;

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

		List<String> seats = Theatre.getInstance().getSelectedSeat();
		String id = req.getParameter("id");
		String title = req.getParameter("title");
		System.out.println(seats.size());

		try {
			resp.getWriter().write(Rythm.render("shop.html", Cinema.getCinema().getMovieShowing(id),
					id, title, seats));
		}
		catch (Exception e) {
			e.printStackTrace();
		}


	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String code = req.getParameter("code");
		
		String discount = this.calculateDiscount(code);
		
		resp.getWriter().write(discount);

	}
	
	
	private String calculateDiscount(String code) {
		//fai qualcosa
		
		return "33";
	}



}
