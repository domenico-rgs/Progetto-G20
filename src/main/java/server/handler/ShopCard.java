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
		
		
		try {
			resp.getWriter().write(Rythm.render("shop.html", Cinema.getCinema().getShopCard().getItems(), 
					Cinema.getCinema().getShopCard().getTotal()));
			//per ora faccio cosi, si può implementare meglio
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
			break;
		}
		
		

	}

}
