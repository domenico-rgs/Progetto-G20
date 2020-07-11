package server.domain.cinema;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.UUID;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

/**
 * This class identifies a bought ticket to show a movie.
 */
public class Ticket {
	private String code;
	private String movie;
	private String seat;
	private String showing;
	private double totalPrice;

	public Ticket() {}

	public Ticket(String movie, String seat, String showing, double totalPrice) {
		code = UUID.randomUUID().toString();
		this.movie=movie;
		this.showing=showing;
		this.seat=seat;
		this.totalPrice=totalPrice;
	}
	
	public File genPDF() throws FileNotFoundException {
		PdfWriter writer = new PdfWriter("Ticket"+code, new WriterProperties().setPdfVersion(PdfVersion.PDF_2_0));
		PdfDocument pdfDocument = new PdfDocument(writer);
		pdfDocument.setTagged();
		Document document = new Document(pdfDocument);
		document.add(new Paragraph(toString()));
		document.close();
		
		return new File("Ticket"+code);
	}

	@Override
	public String toString() {
		return "Ticket id: " + code + "\nDate: " + showing + "- Seat: " + seat +"\nMovie: " + movie + "\nPrice: € "
				+ totalPrice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	public String getCode() {
		return code;
	}


}
