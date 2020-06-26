package server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.CinemaFacade;
import server.domain.cinema.TypeCategory;

public class Administrator implements IHandler {
	private static Administrator instance = null;

	private Administrator() {
	}


	public static Administrator getInstance() {
		if (instance == null)
			instance = new Administrator();

		return instance;
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		//passare values() a rythm non funziona
		List<String> categoryList = new ArrayList<>();

		for (TypeCategory cat: TypeCategory.values())
			categoryList.add(cat.toString());

		resp.getWriter().write(Rythm.render("administrator.html",
				categoryList, CinemaFacade.getCinema().getTitleMovieList()));
	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

		String message = "";

		/*
		 * java reflection che richiama il metodo doAction ()
		 * dalla classe dello stesso nome del requestPost
		 * sfrutto il polimorfismo tra classi (diciamo) per sostiture il caseSwitch
		 */
		try {
			message = (String)Class.forName("server.handler.adminHandler." + req.getParameter("requestPost")).
					getMethod("doAction", HttpServletRequest.class).invoke(null, req);
		}
		catch (Exception e) {
			e.printStackTrace();
		}


		resp.getWriter().write(message);
	}

}
