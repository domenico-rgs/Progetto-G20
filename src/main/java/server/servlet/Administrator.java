package server.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.TypeCategory;
import server.domain.controller.DiscountHandler;
import server.domain.controller.MovieHandler;
import server.domain.controller.TheatreHandler;

/**
 * This class is the servlet that binds the administrator page to logic 
 * Singleton class (State pattern)
 */
public class Administrator implements IHandlerState {
	private static Administrator instance = null;

	private Administrator() {}

	public static Administrator getInstance() {
		if (instance == null) {
			instance = new Administrator();
		}
		return instance;
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<String> categoryList = new ArrayList<>();

		for (TypeCategory cat: TypeCategory.values()) {
			categoryList.add(cat.toString());
		}

		try {
			resp.getWriter().write(Rythm.render("administrator.html",
					categoryList, MovieHandler.getInstance().getMovieList(),
					TheatreHandler.getInstance().getTheatreList(),
					DiscountHandler.getInstance().getDiscountList()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String message = "";


		try {
			message = (String)Class.forName("server.servlet.admin." + req.getParameter("requestPost")).
					getMethod("doAction", HttpServletRequest.class).invoke(null, req);
		}catch (Exception e) {
			e.printStackTrace();
		}
		resp.getWriter().write(message);
	}
}
