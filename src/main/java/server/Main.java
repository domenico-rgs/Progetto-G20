package server;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] argv) throws Exception {
    	
		
    	
    	ApplicationServer server = new ApplicationServer(8080, new CinemaServlet());
    	
    	server.start();
    	
       
    }
}
