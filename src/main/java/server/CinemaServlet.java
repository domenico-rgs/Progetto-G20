package server;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;
import org.rythmengine.RythmEngine;

import server.server.handler.*;

public class CinemaServlet extends HttpServlet {
	
	
	/* variabili usate nella gestione del servlet
	 * 
	 * conf: mappa di variabili da passare al template
	 * templates: mappa dei template renderizzati (da gestire le eccezioni)
	 * engine: motore di renderizzazione
	 */
	//Cinema c = new Cinema();
	Map<String, Object> conf;
	Map<String, String> templates;
	RythmEngine engine;
	String templateResp;
	
	public CinemaServlet() {
		
		//inizializzazione delle variabili
		conf = new HashMap<>();
        conf.put("home.template", "templates");
        templates = new HashMap<>();  
        Rythm.init(conf);

	}
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String classHandler = this.parsePath(req.getPathInfo());
		
		try {
			Class.forName("server.server.handler." + classHandler).getMethod("doGet",
					HttpServletRequest.class, HttpServletResponse.class).invoke(null,
							req, resp);
		}
		catch (Exception classNotFoundException) {
			resp.getWriter().write(Rythm.render("404 Error"));
		}
		
	
	}
		

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		

		String classHandler = this.parsePath(req.getPathInfo());
		
		try {
			Class.forName("server.server.handler." + classHandler).getMethod("doPost",
					HttpServletRequest.class, HttpServletResponse.class).invoke(null,
							req, resp);
		}
		catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write(Rythm.render("404 Error"));
		}
		
	}
	
	private String parsePath(String path) {
		
		if (path.contentEquals("/")) return "Index";
		if (path.contentEquals("/favicon.ico")) return "Index";
		
		path = path.substring(1);
		path = path.substring(0,1).toUpperCase() + path.substring(1);
		
		return path;
		
	}

}
