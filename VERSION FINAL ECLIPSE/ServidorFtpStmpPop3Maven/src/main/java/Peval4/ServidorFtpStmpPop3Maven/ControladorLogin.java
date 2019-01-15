package Peval4.ServidorFtpStmpPop3Maven;

import java.awt.BorderLayout;
import java.io.File;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.WebStorage;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import Peval4.ServidorFtpStmpPop3Maven.ComunicacionJsJava.getDatosCorreo;
import Peval4.ServidorFtpStmpPop3Maven.ComunicacionJsJava.getDatosRegistro;
import Peval4.ServidorFtpStmpPop3Maven.ComunicacionJsJava.getDireccionFTP;
import Peval4.ServidorFtpStmpPop3Maven.ComunicacionJsJava.getUsuarioContraseña;


public class ControladorLogin {

	static Conexion conexion;
	DOMDocument document;
	DOMElement botonIniciarSesion;
	DOMElement cajaIniciarSesion;
	DOMElement botonRegistrarse;
	static Browser browser;
	BrowserView view;
	DOMElement cajaRegistrarse;
	String extUsuario;
	String extContraseña;

	ControladorLogin(BrowserContext context) {

		conexion = new Conexion();

		LoggerProvider.setLevel(Level.OFF);

		browser = new Browser(context);
		view = new BrowserView(browser);

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(view, BorderLayout.CENTER);
		frame.setSize(800, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		File file = new File(ControladorLogin.class.getResource("Disenio/Html/login.html").getFile());
		browser.loadURL(file.toString());

		browser.addLoadListener(new LoadAdapter() {
			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent event) {

				if (event.isMainFrame()) {

					String url = event.getValidatedURL();
					browser = event.getBrowser();

					if (url.endsWith("login.html")) {

						JSValue value = browser.executeJavaScriptAndReturnValue("window");
						getUsuarioContraseña uc = new getUsuarioContraseña(browser, conexion);
						value.asObject().setProperty("Account", uc);

					} else if (url.endsWith("registrar.html")) {

						JSValue value = browser.executeJavaScriptAndReturnValue("window");
						getDatosRegistro dr = new getDatosRegistro(browser, conexion);
						value.asObject().setProperty("Account", dr);

					} else if (url.endsWith("menu.html")) {
				
						DOMDocument document = browser.getDocument();
						DOMElement botonVisualizarCorreos = document.findElement(By.name("botonVisualizarCorreos"));
						
						botonVisualizarCorreos.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
							public void handleEvent(DOMEvent event) {
	
								synchronized (this) {
								hiloCargaCorreo h = new hiloCargaCorreo(document);
								h.start();
								}
									System.out.println("Estas en la ventana de visualizacion de correos");
									
									WebStorage webStorage = browser.getLocalWebStorage();
									
										String usuario = webStorage.getItem("usuario");
										String contraseña = webStorage.getItem("contraseña");
										
										System.out.println("usuario login : " + usuario);
										System.out.println("usuario pass : " + contraseña);
										
										
										if (usuario.endsWith("@hotmail.es") || usuario.endsWith("@hotmail.com")) {
											System.out.println("te conectas con una cuenta de hotmail");
											controladorMostrarCorreos c = new controladorMostrarCorreos(browser, usuario, contraseña, "pop3.live.com");
											c.start();

										} else if (usuario.endsWith("@gmail.es") || usuario.endsWith("@gmail.com")) {
											System.out.println("te conectas con una cuenta de gmail");
											controladorMostrarCorreos c = new controladorMostrarCorreos(browser, usuario, contraseña, "pop.gmail.com");
											c.start();
										}
							}
						}, false);

						DOMElement bajarFichero = document.findElement(By.name("bajarFichero"));
						bajarFichero.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
							public void handleEvent(DOMEvent event) {
								// CAMPTAMOS LOS DATOS DE SESION DESDE EL LOCAL STORAGE DEPENDIENDO DEL DOMINIO
								// MANDAREMOS UNOS DATOS U OTROS
								System.out.println("Estas en la ventana descargarArchivo");
								new ControladorMostrarArchivos(browser);
								File file = new File(ControladorLogin.class
										.getResource("Disenio/Html/descargaArchivo.html").getFile());
								browser.loadURL(file.toString());
							}
						}, false);
						
					} else if (url.endsWith("servidorFTP.html"))

					{
						ControladorFTP cf = new ControladorFTP(browser, view, event);

					} else if (url.endsWith("descargaArchivo.html")) {

						JSValue value = browser.executeJavaScriptAndReturnValue("window");
						value.asObject().setProperty("Account", new getDireccionFTP());
					} else if (url.endsWith("VisualizarCorreo.html")) {
						
						
						

							WebStorage webStorage = browser.getLocalWebStorage();
						
							String actualizar = webStorage.getItem("actualizar");
							
							System.out.println("ESTA ================> " + actualizar);
						
						
						
						
					} else if (url.endsWith("enviarCorreo.html")) {

						DOMDocument document = browser.getDocument();
						DOMElement boton = document.findElement(By.name("enviar"));
						DOMElement cajaDestinatario = document.findElement(By.name("destinatario"));
						DOMElement cajaAsunto = document.findElement(By.name("asunto"));
						DOMElement cajaContenido = document.findElement(By.name("contenidoCorreo"));

						System.out.println("Estas en la ventana de enviar correo");
						JSValue value = browser.executeJavaScriptAndReturnValue("window");// Cojemos la ventana completa
																							// con todos sus elementos
						getDatosCorreo dc = new getDatosCorreo(browser);
						value.asObject().setProperty("Account", dc);

					}
				} // final si es el main frame
			}
		});
	}
}

