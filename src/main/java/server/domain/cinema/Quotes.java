package server.domain.cinema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** This class collects many quotes used for the homepage of the app */
public class Quotes {
	private ArrayList<String> quote, author;

	public Quotes() throws IOException {
		quote = new ArrayList<>();
		author = new ArrayList<>();
		populateCitations();
	}

	/**
	 * Get quotes from a file where first row indicates the quote and the second the film from which is taken
	 */
	private void populateCitations() throws IOException {
		BufferedReader inFile = new BufferedReader(new FileReader("src/main/resources/statics/quotes.txt"));
		String riga;
		while((riga=inFile.readLine())!=null) {
			quote.add(riga);
			author.add(inFile.readLine());
		}
		inFile.close();
	}

	/**
	 * Creates and return a list where the quote is saved in the first element and the film in the second
	 * @return the list with the author and his quote
	 */
	protected List<String> getQuotes() {
		int random = (int)(Math.random()*quote.size());
		List<String> quoteList = new ArrayList<>();
		quoteList.add(quote.get(random));
		quoteList.add(author.get(random));
		return quoteList;
	}
}
