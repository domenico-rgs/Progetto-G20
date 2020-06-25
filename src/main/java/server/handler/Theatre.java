package server.handler;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.domain.cinema.Cinema;
import server.domain.showing.MovieShowing;

public class Theatre implements IHandler {

	private static Theatre instance = null;


	private Theatre() {
	}


	public static Theatre getInstance() {

		if (instance == null)
			instance = new Theatre();

		return instance;
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			
			String thName = Cinema.getCinema().getShowing(req.getParameter("title"), 
					req.getParameter("id")).getTheatreName();
			
			//ricerco il file, anche se dovrebbe farlo qualcun'altro
			Scanner myReader = new Scanner(new File("src/main/resources/theatreConf/"
					+ thName + ".txt"));


			
		
		}
		catch (Exception e) {
			
		}
		
		
		System.out.println(req.getParameter("id"));
		System.out.println(req.getParameter("title"));

	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
