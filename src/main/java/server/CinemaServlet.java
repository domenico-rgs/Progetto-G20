package server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;
import org.rythmengine.RythmEngine;

import server.domain.cinema.Cinema;
import server.handler.IHandler;

@SuppressWarnings("serial")
public class CinemaServlet extends HttpServlet {
	/* variabili usate nella gestione del servlet
	 *
	 * conf: mappa di variabili da passare al template
	 * templates: mappa dei template renderizzati (da gestire le eccezioni)
	 * engine: motore di renderizzazione
	 */
	Map<String, Object> conf;
	Map<String, String> templates;
	RythmEngine engine;
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
			classHandler = (IHandler)Class.forName("server.handler." + reqHandler).getMethod("getInstance").invoke(null);
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
			classHandler = (IHandler)Class.forName("server.handler." + reqHandler).getMethod("getInstance").invoke(null);
			classHandler.doPost(req, resp);
		}catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write(Rythm.render("404.html"));
		}
	}


	/*
	 * Valuta il get richiesto e ne fa uno split se richiesti altri argomenti,
	 * dopo di che verra richiamato l'handler corretto in riferimento
	 * al primo argomento, che chiama la classe corretta
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
