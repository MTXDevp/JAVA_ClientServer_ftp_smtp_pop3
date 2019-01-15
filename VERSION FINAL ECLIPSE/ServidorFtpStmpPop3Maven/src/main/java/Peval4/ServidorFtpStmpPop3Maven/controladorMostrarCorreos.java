package Peval4.ServidorFtpStmpPop3Maven;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.WebStorage;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.LoadEvent;

import javax.activation.DataHandler;
import javax.mail.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class controladorMostrarCorreos extends Thread {

	Browser browserAux;
	Folder folder = null;
	Properties prop = new Properties();
	
    

	public controladorMostrarCorreos(Browser browser, String usuario, String contraseña, String host) {
		this.browserAux = browser;
		System.out.println("ESTOY EN EL CONTROLADOR MOSTRAR CORREOS");
		System.out.println("usuario : " + usuario);
		System.out.println("contraseña : " + contraseña);

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
			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);

		} catch (NoSuchProviderException e) {
			System.out.println(e.getMessage());
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
	}

	public void run() {
		try {
			Message[] mensajes = folder.getMessages();

			ArrayList<String> arrayFrom = new ArrayList<>();
			ArrayList<String> arraySubject = new ArrayList<>();
			ArrayList<String> arrayFecha = new ArrayList<>();
			ArrayList<String> arrayCuerpo = new ArrayList<>();
			String content = "";

			// RECOJEMOS TODOS LOS CABEZADOS DE CORREOS
			for (int i = 0; i < mensajes.length; i++) {
				System.out.println("From:" + mensajes[i].getFrom()[0].toString());
				System.out.println("Subject:" + mensajes[i].getSubject());
				System.out.println("Date : " + mensajes[i].getSentDate());
				
				 ObtenerCuerpoDeMensaje ocm = new ObtenerCuerpoDeMensaje();

                 arrayFrom.add(mensajes[i].getFrom()[0].toString() + "||");
                 arraySubject.add(mensajes[i].getSubject() + "||");
                 arrayFecha.add(mensajes[i].getSentDate().toString() + "||");
                 arrayCuerpo.add(ocm.getTextFromMessage(mensajes[i]) + "||");              
			}
			// ENVIAMOS UN CORREO CON LOS TITULARES DE LOS CORREOS
			File file = new File(ControladorLogin.class.getResource("Disenio/Html/visualizarCorreo.html").getFile());
			browserAux.loadURL(file.toString());

			browserAux.executeJavaScript("localStorage");
			WebStorage webStorage = browserAux.getLocalWebStorage();
			//webStorage.clear();
			webStorage.setItem("numCorreos", String.valueOf(mensajes.length));
			webStorage.setItem("from", arrayFrom.toString());
			webStorage.setItem("subject", arraySubject.toString());
			webStorage.setItem("fecha", arrayFecha.toString());
			webStorage.setItem("cuerpo", arrayCuerpo.toString());

			System.out.println("Hay " + mensajes.length + " mensajes");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
