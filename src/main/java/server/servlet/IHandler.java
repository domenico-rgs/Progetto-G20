package server.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**interface implemented by handlers*/
public interface IHandler {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException;
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
