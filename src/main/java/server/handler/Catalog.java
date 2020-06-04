package server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

public class Catalog implements IHandler {
	
	private static Catalog instance = null;

	
	 private Catalog() {
	 }
	  
	
	 public static Catalog getInstance() {
		 
		 if (instance == null) 
			 instance = new Catalog(); 
	  
		 return instance; 
	    } 

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(req.getParameter("search") == null ||
				req.getParameter("search").equals("all")) {
			
			resp.getWriter().write(Rythm.render("catalog.html"));
			
		}
		
		else {
			System.out.println(req.getParameter("search"));
		}
		
		

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}
	
	private void getListMovie(Map<String, Object> params) {
		
		List<String> titoli = new ArrayList<>();
		titoli.add("titolo1");
		titoli.add("titoli2");
		titoli.add("titoli3");
		
		params.put("movieList", titoli);
		
		return;
	}

}
