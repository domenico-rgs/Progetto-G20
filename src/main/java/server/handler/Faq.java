package server.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

/**the control class that returns the static faq page*/
public class Faq implements IHandler {
	private static Faq instance = null;

	private Faq() {}


	//*singleton*/
	public static Faq getInstance() {
		if (instance == null) {
			instance = new Faq();
		}
		return instance;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().write(Rythm.render("faq.html"));
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
