package server.handler;

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

import server.domain.cinema.CinemaFacade;

public class Theatre implements IHandler {

	private static Theatre instance = null;

	private List<String> selectedPos;


	private Theatre() {
		selectedPos = new ArrayList<>();
	}


	public static Theatre getInstance() {

		if (instance == null)
			instance = new Theatre();

		return instance;
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		selectedPos.clear();
		//matrice non uniforme di righe colonne, da passare all'html
		List<List<String>> config;

		try {

			String thName = CinemaFacade.getCinema().getShowing(req.getParameter("title"),
					req.getParameter("id")).getTheatreName();

			config = this.readConfig(thName);
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}

		//passo tutto a rythm
		resp.getWriter().write(Rythm.render("theatre.html", config, req.getParameter("id"),
				req.getParameter("title")));

	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		selectedPos.add(req.getParameter("seat"));



	}



	private List<List<String>> readConfig(String thName) {

		//matrice non uniforme di righe colonne, da passare all'html
		List<List<String>> config = new ArrayList<>();
		BufferedReader file;

		try {
			//ricerco il file, anche se dovrebbe farlo qualcun'altro
			file = new BufferedReader( new FileReader(new File("fileTest/"
					+ "configTheatre/" + thName)));

			//variabili temporanee
			String row;
			String[] col;

			//per ogni riga lezza
			while ((row = file.readLine()) != null) {

				//righe vuote
				if (row.contentEquals(""))
					continue;

				List<String> rowList = new ArrayList<>();
				col = row.split(" ");

				for (String seat: col)
					rowList.add(seat);
				config.add(rowList);
			}
			file.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}


		return config;
	}

	public List<String> getSelectedSeat() {
		return this.selectedPos;
	}

}
