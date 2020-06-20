package server.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Theatre implements IHandler {
	
	private static Theatre instance = null;


	private Theatre() {
	}


	public static Theatre getInstance() {

		if (instance == null)
			instance = new Theatre();

		return instance;
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
