package server.domain.cinema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class collects many quotes used for the homepage of the app */
public class Quotes {
	private  Map<String, String> authorQuote;

	public Quotes() {
		authorQuote = new HashMap<>();
		populateCitations();
	}

	/**
	 * Get quotes from a file where first row indicates the quote and the second the film from which is taken
	 */
	private void populateCitations() {
		try {
			BufferedReader inFile = new BufferedReader(new FileReader("src/main/resources/statics/quotes.txt"));
			String quote;
			String author;
			while((quote=inFile.readLine())!=null) {  //file diviso in alternanza di citazioni -> autori
				author = inFile.readLine();
				authorQuote.put(author, quote);
			}
			inFile.close();
		} catch (Exception e) { // non dovrebbe capitare, nel caso avremo html vuoto
			return;
		}
		
	}

	/**
	 * Creates and return a list where the quote is saved in the first element and the film in the second
	 * @return the list with the author and his quote
	 */
	public List<String> getQuotes() {
		int random = (int)(Math.random()*authorQuote.size());
		List<String> quoteList = new ArrayList<>();
		List<String> tmp =  new ArrayList<>(authorQuote.keySet());
		String randomAuthor = tmp.get(random);
		quoteList.add(authorQuote.get(randomAuthor));
		quoteList.add(randomAuthor);
		return quoteList;
	}
}
