package server.servlet;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;


import server.domain.cinema.theatre.Seat;
import server.domain.controller.BuyTicketHandler;
import server.domain.controller.MovieShowingHandler;
import server.domain.controller.TheatreHandler;

public class Theatre implements IHandlerState {
	private static Theatre instance = null;

	private Theatre() {
	}

	//*singleton*/
	public static Theatre getInstance() {
		if (instance == null) {
			instance = new Theatre();
		}
		return instance;
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// non-uniform array of column rows, to be passed to HTML
		List<List<String>> config;
		List<String> freeSeats = new ArrayList<>();

		try {
			for (Seat s: TheatreHandler.getInstance().getFreeSeatsForShowing(req.getParameter("id"))) {
				freeSeats.add(s.getPosition());
			}

			String thName = MovieShowingHandler.getInstance().getMovieShowing(req.getParameter("id")).getTheatreName();

			config = this.readConfig(thName);
		}catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// we pass everything to rhythm

		String[] alfaphet = {"A", "B", "C", "D", "E", "F", "G"};

		resp.getWriter().write(Rythm.render("theatre.html", config, req.getParameter("id"),
				req.getParameter("title"), freeSeats, 0, 0, "none", alfaphet));
	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String [] seats = req.getParameter("seat").split("-");


		try {


			BuyTicketHandler.getInstance().updateShopCartItems(req.getParameter("id"), seats);

			String shopID = BuyTicketHandler.getInstance().getShopCart().generateID();
			resp.getWriter().write(shopID);
		}catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write("Error");
		}
	}

	private List<List<String>> readConfig(String thName) {
		// non-uniform array of column rows, to be passed to HTML
		List<List<String>> config = new ArrayList<>();
		BufferedReader file;

		try {
			//i'm search the file
			file = new BufferedReader( new FileReader(new File("src/main/resources/"
					+ "theatreConf/" + thName+ ".txt")));

			//temporary variable
			String row;
			String[] col;


			while ((row = file.readLine()) != null) {

				//empty lines
				if (row.contentEquals("")) {
					continue;
				}

				List<String> rowList = new ArrayList<>();
				col = row.split(" ");

				for (String seat: col) {
					rowList.add(seat);
				}
				config.add(rowList);
			}
			file.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}
}