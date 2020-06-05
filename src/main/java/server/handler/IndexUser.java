package server.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexUser implements IHandler {
	
	private static IndexUser instance = null;
	
	private String userLogged = null;
	
	private String selectedMovie;
	
	 private IndexUser() {
	 }
	  
	
	 public static IndexUser getInstance() {
		 
		 if (instance == null) 
			 instance = new IndexUser(); 
	  
		 return instance; 
	    } 

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
