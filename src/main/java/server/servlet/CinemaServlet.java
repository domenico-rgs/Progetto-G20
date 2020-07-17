package server.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;
import org.rythmengine.RythmEngine;

@SuppressWarnings("serial")
public class CinemaServlet extends HttpServlet {
	// Variables used in the servlet management
	Map<String, Object> conf; //map of variables to be passed to the template
	Map<String, String> templates; //rendered template map (to manage exceptions)
	RythmEngine engine; //rendering engine
	String templateResp;

	public CinemaServlet() {
		conf = new HashMap<>();
		conf.put("home.template", "templates");
		Rythm.init(conf);
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String reqHandler = this.parsePath(req.getPathInfo());
		IHandler classHandler;

		try {
			classHandler = (IHandler)Class.forName("server.servlet." + reqHandler).getMethod("getInstance").invoke(null);
			classHandler.doGet(req, resp);
		}catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write(Rythm.render("404.html"));
		}
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String reqHandler = this.parsePath(req.getRequestURI());
		IHandler classHandler;

		try {
			classHandler = (IHandler)Class.forName("server.servlet." + reqHandler).getMethod("getInstance").invoke(null);
			classHandler.doPost(req, resp);
		}catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write(Rythm.render("404.html"));
		}
	}


	/*
	 * Evaluate the required get and split it if other arguments are requested,
	 * after which the correct handler will be called in reference to the first argument,
	 * which calls the correct class
	 */
	private String parsePath(String path) {
		if (path.contentEquals("/") || path.contentEquals("/favicon.ico") || path.contentEquals("/home")) {
			path = "Index";
			return path;
		}

		path = path.substring(1);
		path = path.substring(0,1).toUpperCase() + path.substring(1);

		return path;
	}
}
