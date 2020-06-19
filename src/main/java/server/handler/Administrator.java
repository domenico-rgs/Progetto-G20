package server.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

public class Administrator implements IHandler {
	private static Administrator instance = null;


	private Administrator() {
	}


	public static Administrator getInstance() {

		if (instance == null)
			instance = new Administrator();

		return instance;
	}


	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.getWriter().write(Rythm.render("administrator.html"));


	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
