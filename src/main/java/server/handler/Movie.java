package server.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

public class Movie implements IHandler {
	
	private static Movie instance = null;
	
	private String selectedMovie;
	
	 private Movie() {
	 }
	  
	
	 public static Movie getInstance() {
		 
		 if (instance == null) 
			 instance = new Movie(); 
	  
		 return instance; 
	    } 

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		this.selectedMovie = req.getParameter("title");
		
		//metodi di caricamento della pagina del film
		
		
		Map<String, Object > params = new HashMap<>();
		this.getMovieInf(params);
		
		
		resp.getWriter().write(Rythm.render("movieInformation.html", params));
		
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	private void getMovieInf(Map<String, Object> params) {
		
		//prendo valori dall'applicazione
		String imgUrl;
		String title;
		String description;
		String year;
		String duration;
		String genere;
		String price;
		
		//putto i valori nella mappa
		params.put("imgUrl", "prova");
		params.put("title", selectedMovie);
		params.put("description", "prova");
		params.put("year", "prova");
		params.put("duration", "prova");
		params.put("genere", "prova");
		params.put("price", "prova");
		
	}
	
}


