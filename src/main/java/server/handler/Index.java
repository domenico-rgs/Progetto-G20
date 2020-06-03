package server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

public class Index implements IHandler {
	
	private static Index instance = null;
		
	 private Index() {
		 this.prova(); //test;
	 }
	  
	
	 public static Index getInstance() {
		 
		 if (instance == null) 
			 instance = new Index(); 
	  
		 return instance; 
	    } 
	
	//metodi di prova
	List <String> titoli = new ArrayList<>();
	
	private void prova() {
		titoli.add("title1");
		titoli.add("title2");
		titoli.add("title3");
		titoli.add("title4");
		titoli.add("title5");
	}
	
	

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		
		//metodi da applicazione per prendere i film
		
		resp.getWriter().write(Rythm.render("index.html", titoli));
		

	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		
		//metodi di raccolta dei dati di login
		switch (req.getParameter("button")) {
		
		case "Login" :
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			
			System.out.println(username + password);
			
			//fai qualcosa per la verifica
			//poi rendirizza alla pagina corretta
			
			resp.sendRedirect("/index");
			break;
			
		case "CreateAccount":
			
			//reindirizza all'html di creazione
			resp.sendRedirect("/index");
			break;
		
		}
		

	}

}
