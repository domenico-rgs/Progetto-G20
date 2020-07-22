package theatreTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import server.domain.cinema.theatre.DisableSeat;
import server.domain.cinema.theatre.PremiumSeat;
import server.domain.cinema.theatre.Seat;
import server.domain.cinema.theatre.Theatre;
import server.domain.cinema.theatre.TypeSeat;
import server.exception.SearchException;
import server.exception.SeatException;

public class TheatreTest {

	/**test of the instancing of the theater*/
	@Test
	public final void testTheatre() throws IOException, SeatException {
		
		Theatre theatre1 = new Theatre("theatreTest1");
		
		boolean testInstanced = true;	
		
		/**I use testInstanced boolean variable to check if it has not been instantiated, 
		 * if so it will come out true*/
		testInstanced = theatre1==null;
	
		/**if it is instantiated I expect the Boolean variable to be false*/
		assertEquals(false, testInstanced);
		
		/**the test will be passed if it is coincident testIntanced == false*/
		
	}

	
	/**test adding seats
	 * @throws SearchException */
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public final void testCreateSeats() throws IOException, SeatException, SearchException {
		boolean testSeats = true;   	//variable that I use to verify the result that in theory I should get
		boolean testSeats2 = true;   	//variable that I use to verify the result that in theory I should get
		Map<String, Seat> seatList = new HashMap<>();
		
		Theatre theatre2 = new Theatre("theatreTest12");    //instantiate a theater with the name "theaterTest"
		 
		
		/**Random string similar to the one that will appear for the configuration of the seats*/
		String config = "X P P P \nD D D X X X \nX X X X X X \nX X X X X X"; 
		String config2 = "G X X P P P \nD D D X X X  \nX X X X X X \nX X X X X X"; 
		
		
		// !!!! if the file with the configuration already exists, launch the search exception
		theatre2.createSeats(config);
		//theatre2.createSeats(config2);
		seatList = theatre2.getSeatsList();
		
		/**check if the hashmap is empty, if yes set testSeats to true*/
		testSeats = seatList.isEmpty();
		
		/**check if the seats are among the possible acceptable combinations, 
		 * default seat, premium seat or handicap seat*/
		for (Map.Entry<String, Seat> entry : seatList.entrySet()) {
			boolean testValue = false;
			
			testValue = entry.getValue().getClass().equals(Seat.class)|| entry.getValue().getClass().equals(PremiumSeat.class) || entry.getValue().getClass().equals(DisableSeat.class);
			if(testValue) {
				testSeats2 = false;
			}
		 }
		
		assertEquals(false, testSeats);
		assertEquals(false, testSeats2);
	}
	
	
	
	/**test if it creates the path and creates the file*/
	
	
	@Test
	public final void testCreateConfigFile() throws IOException, SeatException {
		boolean testSeats = false;   	//variable that I use to verify the result that in theory I should get
		
		/**Random string similar to the one that will appear for the configuration of the seats*/
		String config = "X X X P P P \nD D D X X X \n X X X X X X";    
		String filePath = "";
		Theatre theatre3 = new Theatre("theatreTest3");    //instantiate a theater with the name "theaterTest3"
		
		//filePath = theatre3.createConfigFile(config);
		
		/**check given the following path just created there is the file just created*/
		//testSeats = new File(filePath).isFile();
	
		
		//assertEquals(true, testSeats);
	}
	
	

}
