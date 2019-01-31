package Peval4.ServidorFtpStmpPop3Maven;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import org.jsoup.Jsoup;

/**
 * Clase que permite obtener el cuerpo del mensaje que se recibe.
 * 
 * @author Guillermo Barcia
 * @version 1.0.
 *
 */
public class ObtenerCuerpoDeMensaje {

	/**
	 * Método en el que se obtiene la información del correo.
	 * 
	 * @param <Message> message: Mensaje del cual se quiere obtener el cuerpo.
	 * @return <String> resutl: Cuerpo del mensaje.
	 */
	public String getTextFromMessage(Message message) {
		String result = "";
		try {
			if (message.isMimeType("text/plain")) {
				result = message.getContent().toString();
			} else if (message.getContent() instanceof MimeMultipart) {
				MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
				result = getTextFromMimeMultipart(mimeMultipart);
			}
		} catch (MessagingException | IOException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getMessage());
		}
		return result;
	}

	/**
	 * Método que formatea el cuerpo del correo para que sea legible
	 * 
	 * @param <MimeMultipart> mimeMultipart
	 * @return <String> result: Cuerpo del mensaje.
	 */
	public String getTextFromMimeMultipart(MimeMultipart mimeMultipart) {
		String result = "";
		int count;
		try {
			count = mimeMultipart.getCount();
			for (int i = 0; i < count; i++) {
				BodyPart bodyPart = mimeMultipart.getBodyPart(i);
				if (bodyPart.isMimeType("text/plain")) {
					result = result + "\n" + bodyPart.getContent();
					break; // without break same text appears twice in my tests
				} else if (bodyPart.isMimeType("text/html")) {
					String html = (String) bodyPart.getContent();
					result = result + "\n" + Jsoup.parse(html).text();
				} else if (bodyPart.getContent() instanceof MimeMultipart) {
					result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
				}
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
