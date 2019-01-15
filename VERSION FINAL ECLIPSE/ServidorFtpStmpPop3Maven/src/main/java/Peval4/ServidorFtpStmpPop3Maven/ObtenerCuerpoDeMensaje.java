package Peval4.ServidorFtpStmpPop3Maven;
import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import org.*;
import org.jsoup.Jsoup;

public class ObtenerCuerpoDeMensaje {

	
	public String getTextFromMessage(Message message){
	    String result = "";
	    try {
			if (message.isMimeType("text/plain")) {
			    result = message.getContent().toString();
			} else if (message.isMimeType("multipart/*")) {
			    MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			    result = getTextFromMimeMultipart(mimeMultipart);
			}
		} catch (MessagingException | IOException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getMessage());
		}
	    return result;
	}
	
	public String getTextFromMimeMultipart(
	        MimeMultipart mimeMultipart)  throws MessagingException, IOException{
	    String result = "";
	    int count = mimeMultipart.getCount();
	    for (int i = 0; i < count; i++) {
	        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
	        if (bodyPart.isMimeType("text/plain")) {
	            result = result + "\n" + bodyPart.getContent();
	            break; // without break same text appears twice in my tests
	        } else if (bodyPart.isMimeType("text/html")) {
	            String html = (String) bodyPart.getContent();
	            result = result + "\n" + Jsoup.parse(html).text();
	        } else if (bodyPart.getContent() instanceof MimeMultipart){
	            result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
	        }
	    }
	    return result;
	}
	
}
