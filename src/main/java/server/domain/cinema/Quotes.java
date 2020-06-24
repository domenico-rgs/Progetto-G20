package server.domain.cinema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Quotes {
	private ArrayList<String> quote;
	private ArrayList<String> author;

	public Quotes() {
		quote = new ArrayList<>();
		author = new ArrayList<>();
		try {
			populateCitations();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void populateCitations() throws IOException {
		BufferedReader inFile = new BufferedReader(new FileReader("src/main/resources/statics/quotes.txt"));
		String riga;
		while((riga=inFile.readLine())!=null) {
			quote.add(riga);
			author.add(inFile.readLine());
		}
		inFile.close();
	}

	public List<String> getQuotes() {
		int random = (int)(Math.random()*quote.size());
		List<String> quoteList = new ArrayList<>();
		quoteList.add(quote.get(random));
		quoteList.add(author.get(random));
		return quoteList;
	}
}
