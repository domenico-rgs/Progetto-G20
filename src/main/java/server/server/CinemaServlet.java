package G20.server;

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

public class CinemaServlet extends HttpServlet {
	
	List<String> title = new ArrayList<>();
	List<String> images = new ArrayList<>();
	Map<String, Object> conf;
	
	public CinemaServlet() {
		conf = new HashMap<>();
        conf.put("home.template", "templates");
        Rythm.init(conf);
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
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getPathInfo().equals("/")) {
			this.prova();
			resp.getWriter().write(Rythm.render("index.html", title, images));
		}
		
		if (req.getPathInfo().equals("/catalog")) {
			this.prova();
			resp.getWriter().write(Rythm.render("catalog.html"));
		}
		
	
	}
		

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
