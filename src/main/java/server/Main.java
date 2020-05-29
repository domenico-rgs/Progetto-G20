package server;

import server.ApplicationServer;
import server.CinemaServlet;

public class Main {
    public static void main(String[] argv) throws Exception {
    	 int portNumber = 8080;
       
    	 
         //Creare istanza ApplicationServer
    	 new ApplicationServer(8080, new CinemaServlet()).start();
    }
}
