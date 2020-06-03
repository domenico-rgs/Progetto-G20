package server.server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

public final class Index{
	
	
	
	public static void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		
		//richiedere i film in modio vario da cinema
		
		List<String> title = new ArrayList<>();
		List<String> images = new ArrayList<>();
		
		Map<String, Object> params = new HashMap<>();
		
		title.add("1title");
		title.add("2title");
		title.add("3title");
		title.add("4title");
		title.add("5title");
		images.add("unavaliable.png");
		params.put("titleMovieList", title);
		params.put("fiveMovieImg", images);
		
		resp.getWriter().write(Rythm.render("index.html", params));
		
		
		
	}
	
	public static void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		
		switch (req.getParameter("button")) {
		
		case "Login" :
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			//fai qualcosa per il login
		
			resp.sendRedirect("/indexUser");
			break;
		
		case "Create account":
			
			//reindirizza pag creazione utente
			resp.sendRedirect("/createAccount");
			break;
		
		default:
			//non fa nulla
			break;
		
	}
	}
}
