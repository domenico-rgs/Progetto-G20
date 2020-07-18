package server.services.mail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import server.domain.cinema.Ticket;

public class MailSender {
	private static Properties props = new Properties();
	private static int port = 465; //port 25 to not use SSL
	private static String sender = "progettog20@gmail.com";
	private static String passSender = "ViaAdolfoFerrata5";
	private static String host = "smtp.gmail.com";
	private static Session session;


	/**
	 * Send the email containing the ticket in pdf format to the user who purchased it.
	 * @param recipient recipient email
	 * @param pdf PDF file to attach to the email
	 * @throws FileNotFoundException if there are problems with the PDF file
	 * @throws MessagingException if the recipient's or sender's address is incorrect
	 */
	public static void sendTicketMail(String recipient, List<Ticket> ticketList) throws FileNotFoundException, MessagingException {
		String header = "Here are the tickets you purchased from CinemaG20";
		String body = "Hi!\r\n" +
				"Attached to this email is the tickets you purchased on CinemaG20.\r\n" +
				"Thanks and enjoy!";
		sendMail(recipient,header,body, genPDF(ticketList));

	}

	/**
	 * Send the email to the cinema administrator with the data to refund the user who requested the cancellation of a ticket
	 * @param ticketCode ticket code to be refunded
	 * @param cardNumber card number on which to make the refund
	 * @param total total to be refunded (single ticket price)
	 * @throws FileNotFoundException if there are problems with the PDF file
	 * @throws MessagingException if the recipient's or sender's address is incorrect
	 */
	public static void sendRefundMail(String ticketCode, String cardNumber, double total) throws FileNotFoundException, MessagingException {
		String header = "Someone has requested a refund";
		String body = "Hello,\r\n" +
				"a user has requested a refund for the ticket " + ticketCode +
				" on card n°" + cardNumber +"\r\n" +
				" for a total of €"+ total;
		sendMail("progettog20@gmail.com",header,body,null);
	}


	/**
	 * Generates the pdf through the iTextPDF library including the information of the tickets sold
	 * @param ticketList
	 * @return
	 * @throws FileNotFoundException
	 */
	private static File genPDF(List<Ticket> ticketList) throws FileNotFoundException {
		PdfWriter writer = new PdfWriter("G20Ticket", new WriterProperties().setPdfVersion(PdfVersion.PDF_2_0));
		PdfDocument pdfDocument = new PdfDocument(writer);
		pdfDocument.setTagged();
		Document document = new Document(pdfDocument);
		for(Ticket t : ticketList) {
			document.add(new Paragraph(t.toString()));
		}
		document.close();

		return new File("G20Ticket");
	}

	private static void sendMail(String recipient, String header, String body, File attachments)  throws FileNotFoundException, MessagingException{
		setProperties();
		session = Session.getInstance(props, null);

		MimeMessage msg = buildMessage(recipient, header, body, attachments);

		send(msg);
	}

	private static void setProperties() {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.user", sender);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		// comment the following line to not use SSL
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
	}

	private static void send(MimeMessage msg) throws MessagingException {
		Transport transport = session.getTransport("smtps"); //("smtp") to not use SSL
		transport.connect(host, sender, passSender);
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}

	private static MimeMessage buildMessage(String recipient, String header, String body, File attachments) throws MessagingException {
		MimeBodyPart messageBodyPart1 = new MimeBodyPart();
		MimeBodyPart messageBodyPart2 = new MimeBodyPart();

		Multipart multipart = new MimeMultipart();
		MimeMessage msg = new MimeMessage(session);

		// header
		msg.setSubject(header);
		msg.setSentDate(new Date());
		msg.setFrom(new InternetAddress(sender));

		// recipient
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

		// body
		messageBodyPart1.setText(body);
		multipart.addBodyPart(messageBodyPart1);

		// attachment
		if(attachments != null) {
			multipart.addBodyPart(messageBodyPart2);
			DataSource source = new FileDataSource(attachments);
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName("G20Tickets.pdf");
			multipart.addBodyPart(messageBodyPart2);
		}

		msg.setContent(multipart);
		return msg;
	}
}