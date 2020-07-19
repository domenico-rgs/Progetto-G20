package server.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

@SuppressWarnings("serial")
public class ServletFacade extends HttpServlet {

	Map<String, Object> conf;
	//Map<String, String> templates;
	//RythmEngine engine;
	//String templateResp;

	public ServletFacade() {
		conf = new HashMap<>();
		conf.put("home.template", "templates");
		Rythm.init(conf);
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String reqHandler = this.parsePath(req.getPathInfo());
		IHandlerState classHandler;

		try {
			classHandler = (IHandlerState)Class.forName("server.servlet." + reqHandler).getMethod("getInstance").invoke(null);
			classHandler.doGet(req, resp);
		}catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write(Rythm.render("404.html"));
		}
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String reqHandler = this.parsePath(req.getRequestURI());
		IHandlerState classHandler;

		try {
			classHandler = (IHandlerState)Class.forName("server.servlet." + reqHandler).getMethod("getInstance").invoke(null);
			classHandler.doPost(req, resp);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			resp.getWriter().write(Rythm.render("404.html"));
		}
	}


	/**
	 * Transform the path per to make it appropriate to call the reflection method for the IHandlerState interface
	 * @param path required from client
	 * @return parsed String path
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
