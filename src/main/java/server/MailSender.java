package server;

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

import server.domain.cinema.Ticket;

public class MailSender {

	// Metodo che si occupa dell'invio effettivo della mail
	public static void sendTicketMail(String recipient, List<Ticket> ticket) throws FileNotFoundException, MessagingException {
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
		session.setDebug(true);

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
		for(Ticket t : ticket) {

			DataSource source = new FileDataSource(t.genPDF());
			messageBodyPart2.setDataHandler(new DataHandler(source));
			messageBodyPart2.setFileName(t.getCode()+".pdf");
			multipart.addBodyPart(messageBodyPart2);
		}

		// inserimento delle parti nel messaggio
		msg.setContent(multipart);

		Transport transport = session.getTransport("smtps"); //("smtp") per non usare SSL
		transport.connect("smtp.gmail.com", "progettog20@gmail.com", "ViaAdolfoFerrata5"); //correggere parametri
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}
}