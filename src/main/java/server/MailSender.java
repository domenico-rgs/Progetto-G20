package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
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

public class MailSender {
	//URGE REFACTOR
	// Metodo che si occupa dell'invio effettivo della mail
	public static void sendTicketMail(String recipient, File pdf) throws FileNotFoundException, MessagingException {
		int port = 465; //porta 25 per non usare SSL

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.user", "progettog20@gmail.com"); //mittente
		props.put("mail.smtp.host", "smtp.gmail.com"); //host
		props.put("mail.smtp.port", port);

		// commentare la riga seguente per non usare SSL
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.socketFactory.port", port);

		// commentare la riga seguente per non usare SSL
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		Session session = Session.getInstance(props, null);
		session.setDebug(false);

		// Creazione delle BodyParts del messaggio
		MimeBodyPart messageBodyPart1 = new MimeBodyPart();
		MimeBodyPart messageBodyPart2 = new MimeBodyPart();

		// COSTRUZIONE DEL MESSAGGIO
		Multipart multipart = new MimeMultipart();
		MimeMessage msg = new MimeMessage(session);

		// header del messaggio
		msg.setSubject("Here are the tickets you purchased from CinemaG20");
		msg.setSentDate(new Date());
		msg.setFrom(new InternetAddress("progettog20@gmail.com"));

		// destinatario
		msg.addRecipient(Message.RecipientType.TO,
				new InternetAddress(recipient));

		// corpo del messaggio
		messageBodyPart1.setText("Hi!\r\n" +
				"Attached to this email is the tickets you purchased on CinemaG20.\r\n" +
				"Thanks and enjoy!");
		multipart.addBodyPart(messageBodyPart1);

		// allegato al messaggio
		DataSource source = new FileDataSource(pdf);
		messageBodyPart2.setDataHandler(new DataHandler(source));
		messageBodyPart2.setFileName("G20Tickets.pdf");
		multipart.addBodyPart(messageBodyPart2);

		// inserimento delle parti nel messaggio
		msg.setContent(multipart);

		Transport transport = session.getTransport("smtps"); //("smtp") per non usare SSL
		transport.connect("smtp.gmail.com", "progettog20@gmail.com", "ViaAdolfoFerrata5"); //correggere parametri
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}

	// Metodo che si occupa dell'invio effettivo della mail
	public static void sendRefundMail(String ticketCode, String cardNumber, double total) throws FileNotFoundException, MessagingException {
		int port = 465; //porta 25 per non usare SSL

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.user", "progettog20@gmail.com"); //mittente
		props.put("mail.smtp.host", "smtp.gmail.com"); //host
		props.put("mail.smtp.port", port);

		// commentare la riga seguente per non usare SSL
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.socketFactory.port", port);

		// commentare la riga seguente per non usare SSL
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		Session session = Session.getInstance(props, null);
		session.setDebug(false);

		// Creazione delle BodyParts del messaggio
		MimeBodyPart messageBodyPart1 = new MimeBodyPart();

		// COSTRUZIONE DEL MESSAGGIO
		Multipart multipart = new MimeMultipart();
		MimeMessage msg = new MimeMessage(session);

		// header del messaggio
		msg.setSubject("Here are the tickets you purchased from CinemaG20");
		msg.setSentDate(new Date());
		msg.setFrom(new InternetAddress("progettog20@gmail.com"));

		// destinatario
		msg.addRecipient(Message.RecipientType.TO,
				new InternetAddress("progettog20@gmail.com"));

		// corpo del messaggio
		messageBodyPart1.setText("Hello,\r\n" +
				"a user has requested a refund for the ticket: " + ticketCode + "\r\n" +
				"on card n°" +"\r\n" + cardNumber +
				"for a total of €"+ total);
		multipart.addBodyPart(messageBodyPart1);

		// inserimento delle parti nel messaggio
		msg.setContent(multipart);

		Transport transport = session.getTransport("smtps"); //("smtp") per non usare SSL
		transport.connect("smtp.gmail.com", "progettog20@gmail.com", "ViaAdolfoFerrata5"); //correggere parametri
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}
}