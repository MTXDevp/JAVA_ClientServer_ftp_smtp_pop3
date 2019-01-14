package Peval4.ServidorFtpStmpPop3Maven;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class AutentificarCorreo {
	
	boolean correcto = false;
	
	public boolean AutentificarCorreo(String host, String usuario, String contraseña) {
		
		  Folder folder = null;
		    Properties prop = new Properties();
	        // Deshabilitamos TLS
	        prop.setProperty("mail.pop3.starttls.enable", "false");
	        // Hay que usar SSL
	        prop.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        prop.setProperty("mail.pop3.socketFactory.fallback", "false");
	        // Puerto 995 para conectarse.
	        prop.setProperty("mail.pop3.port", "995");
	        prop.setProperty("mail.pop3.socketFactory.port", "995");

	        Session sesion = Session.getInstance(prop);
	        Store store = null;
	        try {
	            store = sesion.getStore("pop3");
	            store.connect(host, usuario, contraseña);
	            
	            correcto = true;

	        } catch (NoSuchProviderException e) {
	            System.out.println(e.getMessage());
	            correcto = false;
	        } catch (MessagingException e) {
	        	System.out.println(e.getMessage());
	        	correcto = false;
	        }
	        
	        return correcto;
	}

}
