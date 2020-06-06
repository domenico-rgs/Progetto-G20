package server.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

public class CreateAccount implements IHandler {
	
	private static CreateAccount instance = null;
	
	
	 private CreateAccount() {
	 }
	  
	
	 public static CreateAccount getInstance() {
		 
		 if (instance == null) 
			 instance = new CreateAccount(); 
	  
		 return instance; 
	    } 

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.getWriter().write(Rythm.render("createAccount.html"));

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String email = req.getParameter("username");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String confirmPass = req.getParameter("confirmPass");
		
		//metodi per la creazione di un account

	}

}
