package server.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Catalog implements IHandler {
	
	private static Catalog instance = null;
	
	private String selectedMovie;
	
	 private Catalog() {
	 }
	  
	
	 public static Catalog getInstance() {
		 
		 if (instance == null) 
			 instance = new Catalog(); 
	  
		 return instance; 
	    } 

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Map<String, Object > params = new HashMap<>();
		this.getListMovie(params); 

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}
	
	private void getListMovie(Map<String, Object> params) {
		
		
	}

}
