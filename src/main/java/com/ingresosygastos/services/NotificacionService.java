package com.ingresosygastos.services;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ingresosygastos.entities.Abuela;
import com.ingresosygastos.entities.Ingreso;
import com.ingresosygastos.repositories.AbuelaRepository;
import com.ingresosygastos.repositories.IngresoRepository;

@Service
public class NotificacionService {

	@Autowired
	private IngresoRepository ingresoRepository;

	@Autowired
	private JavaMailSender mailsender;

	public void sendSimpleEmail(String id) {

		SimpleMailMessage message = new SimpleMailMessage();
		try {
			Ingreso ingreso = ingresoRepository.getById(id);
				
				message.setFrom("flordelbambu@gmail.com");
				message.setTo(ingreso.getAbuela().getEmail());
				message.setSubject(ingreso.getAbuela().getApellido() + " " + ingreso.getAbuela().getNombre());
				message.setText("Estimado familiar.\n Hemos registrado el cobro por $" +
					ingreso.getMontoCobrado() + 
					" de la residente " +
					ingreso.getAbuela().getApellido() + 
					" " + 
					ingreso.getAbuela().getNombre());
				
				mailsender.send(message);
		} catch (Exception e) {
			System.out.println("No se encontró el ingreso o no se pudo enviar el mail.");
		}
	}

	/*
	 * @Value("${spring.mail.username}") private String mailFrom;
	 * 
	 * @Value("${spring.mail.password}") private String mailPassword;
	 * 
	 * public boolean notificar(Long id, String mailTo) {
	 * 
	 * try { Abuela abuela = abuelaRepository.getById(id);
	 * 
	 * if (abuela != null) { enviarSimple(abuela, mailTo); enviarHTML(abuela,
	 * mailTo);
	 * 
	 * return true; } else { return false; } } catch (Exception e) {
	 * System.out.println("ERROR con " + e.getMessage()); return false; } }
	 * 
	 * @Async public void enviarSimple(Abuela abuela, String mailTo) {
	 * 
	 * SimpleMailMessage mensaje = new SimpleMailMessage();
	 * 
	 * mensaje.setTo(mailTo); mensaje.setFrom("flordelbambu@gmail.com");
	 * mensaje.setSubject("Recibo " + abuela.getApellido() + " " +
	 * abuela.getNombre()); mensaje.setText("En el dia de la fecha, hemos recibido "
	 * + +);
	 * 
	 * mailsender.send(mensaje); }
	 * 
	 * @Async private void enviarHTML(Abuela abuela, String mailTo) {
	 * 
	 * Properties prop = new Properties(); prop.put("mail.smtp.auth", true);
	 * prop.put("mail.smtp.starttls.enable", "true"); prop.put("mail.smtp.host",
	 * "smtp.gmail.com"); prop.put("mail.smtp.port", 587);
	 * prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	 * 
	 * Session session = Session.getInstance(prop, new Authenticator() {
	 * 
	 * @Override protected PasswordAuthentication getPasswordAuthentication() {
	 * return new PasswordAuthentication(mailFrom, mailPassword); } });
	 * 
	 * try {
	 * 
	 * Message message = new MimeMessage(session); message.setFrom(new
	 * InternetAddress(mailFrom)); message.setRecipients(Message.RecipientType.TO,
	 * InternetAddress.parse(mailTo)); message.setSubject("Mail Subject");
	 * 
	 * String msg = "<h1>" + abuela.getApellido() + " " + abuela.getNombre() +
	 * "</h1>";
	 * 
	 * MimeBodyPart mimeBodyPart = new MimeBodyPart(); mimeBodyPart.setContent(msg,
	 * "text/html");
	 * 
	 * // DESCOMENTAR PARA ENVIAR ARCHIVOS DESDE EL SERVIDOR O BUCKET //
	 * MimeBodyPart attachmentBodyPart = new MimeBodyPart(); //
	 * attachmentBodyPart.attachFile(new File("pom.xml"));
	 * 
	 * Multipart multipart = new MimeMultipart();
	 * multipart.addBodyPart(mimeBodyPart); //
	 * multipart.addBodyPart(attachmentBodyPart);
	 * 
	 * message.setContent(multipart);
	 * 
	 * Transport.send(message);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */
}