package server.handler;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

import server.domain.cinema.Cinema;
import server.domain.exception.SearchException;

public class Catalog implements IHandler {
	
	
	//singleton
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
		
		if (req.getParameter("search").contentEquals("all")) {
			resp.getWriter().write(Rythm.render("catalog.html"));
		}


	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	

}
