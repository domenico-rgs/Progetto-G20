package server.server.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rythmengine.Rythm;

public class MovieInformation {
	
	public static String selectedMovie = null;
	
	public static void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
	
		/* metodo per prendere le informazioni del film
		 * e renderizzare la pagina, per poi stamparla al mittente
		 */
		System.out.println(selectedMovie);
		resp.sendRedirect("/index");
		
	}
	
	public static void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		
		if (req.getParameter("saveMovie").contentEquals("true")) {
			selectedMovie = req.getParameter("title");
			return;
		}


		
		
	}

}
