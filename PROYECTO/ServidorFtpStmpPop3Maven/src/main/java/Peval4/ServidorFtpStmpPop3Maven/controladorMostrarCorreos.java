package Peval4.ServidorFtpStmpPop3Maven;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.WebStorage;
import javax.mail.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Clase encargada de conectarse con el servidor que corresponda y carge los
 * correos en el HTML correspondiente pasando los datos a traves de javaScript
 * 
 * @author Rafael Valls.
 * @version 1.0.
 */
public class controladorMostrarCorreos extends Thread {

	/**
	 * <Browser> browserAux: Variable que hace referncia al contexto (elementos) de
	 * la vista.
	 */
	private Browser browserAux;

	/**
	 * <Folder> folder: Variable que hace referencia a la carpeta del correo.
	 */
	private Folder folder = null;

	/**
	 * <Properties> prop: Variable que hace referencia a las propiedades de
	 * configuración del correo.
	 */
	private Properties prop = new Properties();

	/**
	 * Constructor encargado de realizar la conexion con el servidor de correos.
	 * 
	 * @param <Browser> browser: Variable que hace referencia al contexto de la
	 *        vista.
	 * @param <String> usuario: Variable que contiene la información sobre el
	 *        usuario.
	 * @param <String> contraseña: Variable que contiene la informacion sobre la
	 *        contraseña.
	 * @param <String> host: Variable que contiene el host del servidor que tiene
	 *        que conectarse, dependera del tipo de correo.
	 */
	public controladorMostrarCorreos(Browser browser, String usuario, String contraseña, String host) {
		this.browserAux = browser;

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

	/**
	 * Método encargado de obtener los datos de los correos y plasmarlos en la vista
	 * enviandolos atraves de javaScript.
	 */
	public void run() {
		try {
			// Inicializamo las variables.
			Message[] mensajes = folder.getMessages();
			ArrayList<String> arrayFrom = new ArrayList<>();
			ArrayList<String> arraySubject = new ArrayList<>();
			ArrayList<String> arrayFecha = new ArrayList<>();
			ArrayList<String> arrayCuerpo = new ArrayList<>();

			// Recogemos los datos de los correos y los vamos almacenando en los respectivos
			// Arraylist.
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
			
			// Mostramos una nueva ventana donde se visualizaran todos los correos.
			File file = new File(ControladorLogin.class.getResource("Disenio/Html/visualizarCorreo.html").getFile());
			browserAux.loadURL(file.toString());

			// Mediante el localStorage estamos enviando los datos de los correos a la vista mediante javaScript.
			browserAux.executeJavaScript("localStorage");
			WebStorage webStorage = browserAux.getLocalWebStorage();
			// webStorage.clear();
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
