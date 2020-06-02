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

public class CinemaServlet extends HttpServlet {
	
	List<String> title = new ArrayList<>();
	List<String> images = new ArrayList<>();
	
	/* variabili usate nella gestione del servlet
	 * 
	 * conf: mappa di variabili da passare al template
	 * templates: mappa dei template renderizzati (da gestire le eccezioni)
	 * engine: motore di renderizzazione
	 */
	Map<String, Object> conf;
	Map<String, String> templates;
	RythmEngine engine;
	
	public CinemaServlet() {
		
		//inizializzazione delle variabili
		conf = new HashMap<>();
        conf.put("home.template", "templates");
        templates = new HashMap<>();
        engine = new RythmEngine(conf);
        
        this.prova(); //prova
        this.initialize();
        
    	
		//funzioni di test
		
	}
	
	private void prova() {
		title.add("1title");
		title.add("2title");
		title.add("3title");
		title.add("4title");
		title.add("5title");
		images.add("unavaliable.png");
		conf.put("title", title);
		conf.put("fiveMovieImg", images);
	}
	
	private void initialize() {
		String index = engine.render("index.html", title, images);
		templates.put("index", index);
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		switch (req.getPathInfo()) {
			
		case "/":
			resp.getWriter().write(engine.substitute(templates.get("index")));
			break;
			
		case "/catalog":
			resp.getWriter().write(engine.render("catalog.html"));
			break;
		default:
			break;
		
		}
		
	
	}
		

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		switch (req.getParameter("butt")) {
		
		case "Login" :
			String name = req.getParameter("username");
			String pass = req.getParameter("password");
			System.out.println(name);
			System.out.println(pass);
			resp.sendRedirect("/catalog");
			break;
		
		case "Create account":
			System.out.println("prova creazione account");
			resp.sendRedirect("/");
			break;
		
		default:
			System.out.println(req.getPathInfo());
			resp.sendRedirect("/");
			break;
		}
		
	}
}
