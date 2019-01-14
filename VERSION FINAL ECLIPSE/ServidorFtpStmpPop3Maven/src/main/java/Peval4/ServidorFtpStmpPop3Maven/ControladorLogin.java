package Peval4.ServidorFtpStmpPop3Maven;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.events.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import Peval4.ServidorFtpStmpPop3Maven.Disenio.hiloCargaCorreo;

import javax.mail.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;

//USAR LOCAL STORAGE PARA GUARDAR USUARIO Y ONTRASEÑA ES MAS SEGURO QUE USAR COOKIES?
//https://jxbrowser.support.teamdev.com/support/solutions/articles/9000035453-html5-local-session-storages
//https://platzi.com/blog/local-storage-html5/
//https://jxbrowser.support.teamdev.com/support/solutions/
public class ControladorLogin {

	static Conexion conexion;
	VistaLogin vloging;
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

						// Metodo que nos permite interactuar con el html y en concreto con el bloque js
						// que coje las credenciales del formulario login
						// la clase a la que nos lleva contiene la consulta a la base de datos la cual
						// comprueba la existencia del usuario en concreto
						JSValue value = browser.executeJavaScriptAndReturnValue("window");
						getUsuarioContraseña uc = new getUsuarioContraseña();
						value.asObject().setProperty("Account", uc);

					} else if (url.endsWith("registrar.html")) {

						JSValue value = browser.executeJavaScriptAndReturnValue("window");
						getDatosRegistro dr = new getDatosRegistro();
						value.asObject().setProperty("Account", dr);

					} else if (url.endsWith("menu.html")) {
						// while(url.endsWith("menu.html")) {

						// }

						// DEBEMOS DE EJECUTAR UN EVENTO SOBRE EL BOTON PARA MOSTRAR LOS CORREOS PUESTO
						// QUE SINO NO
						// PODREMOS CONTROLAR EL CICLO DE EJECION DEL PROGRAMA
						DOMDocument document = browser.getDocument();
						DOMElement botonVisualizarCorreos = document.findElement(By.name("botonVisualizarCorreos"));
						botonVisualizarCorreos.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
							public void handleEvent(DOMEvent event) {
								// CAMPTAMOS LOS DATOS DE SESION DESDE EL LOCAL STORAGE DEPENDIENDO DEL DOMINIO
								// MANDAREMOS UNOS DATOS U OTROS
								synchronized (this) {
								hiloCargaCorreo h = new hiloCargaCorreo(document);
								h.start();
								}
									System.out.println("Estas en la ventana de visualizacion de correos");
									browser.executeJavaScript("localStorage");
									WebStorage webStorage = browser.getLocalWebStorage();
									String usuario = webStorage.getItem("usuario");
									String contraseña = webStorage.getItem("contraseña");

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
					} else if (url.endsWith("visualizarCorreo.html")) {
						/*
						 * //CAMPTAMOS LOS DATOS DE SESION DESDE EL LOCAL STORAGE DEPENDIENDO DEL
						 * DOMINIO MANDAREMOS UNOS DATOS U OTROS
						 * System.out.println("Estas en la ventana de visualizacion de correos");
						 * browser.executeJavaScript("localStorage"); WebStorage webStorage =
						 * browser.getLocalWebStorage(); String usuario = webStorage.getItem("usuario");
						 * String contraseña = webStorage.getItem("contraseña");
						 * 
						 * if(usuario.endsWith("@hotmail.es")|| usuario.endsWith("@hotmail.com")){
						 * 
						 * System.out.println("te conectas con una cuenta de hotmail"); new
						 * controladorMostrarCorreos(browser, usuario, contraseña, "pop3.live.com");
						 * 
						 * }else if(usuario.endsWith("@gmail.es")|| usuario.endsWith("@gmail.com")){
						 * 
						 * System.out.println("te conectas con una cuenta de gmail"); new
						 * controladorMostrarCorreos(browser, usuario, contraseña, "pop.gmail.com"); }
						 */
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

	public class getDireccionFTP {

		public void save(String nombreFichero) {
			System.out.println("Nombre Fichero : " + nombreFichero);
			new ClienteBajarArchivo(nombreFichero);
		}
	}

	public static class getUsuarioContraseña {

		String externalUsuario;
		String externalContraseña;

		public String getExternalUsuario() {
			return externalUsuario;
		}

		public String getExternalContraseña() {
			return externalContraseña;
		}

		public void save(String usuario, String contraseña) {
			System.out.println("Usuario    = " + usuario);
			System.out.println("Contraseña = " + contraseña);

			conexion.CheckLogin(usuario, contraseña);
			try {
				// si el usuario y la contraseña coinciden
				if (conexion.getRs().next()) {

					System.out.println("Se ha encontrado al usuario");
					externalUsuario = usuario;
					externalContraseña = contraseña;

					// Si el login es correcto iremos a la ventana del menu
					File file1 = new File(ControladorLogin.class.getResource("Disenio/Html/menu.html").getFile());
					browser.loadURL(file1.toString());
				} else {
					System.out.println("El usuario no se encuentra registrado");
					JOptionPane.showMessageDialog(null, "CREDENCIALES ERRONEAS", "ERROR", JOptionPane.WARNING_MESSAGE);

					// en el caso de que las crecenciales no sean correctas volvemos a cargar la
					// ventana de login
					File file2 = new File(ControladorLogin.class.getResource("Disenio/Html/login.html").getFile());
					browser.loadURL(file2.toString());
				}
			} catch (SQLException e) {
				System.out.println("SE HA PRODUCIDO UN ERROR CONSULTANDO LA BASE DE DATOS");
				System.out.println("DETALLES : ");
				System.out.println(e.getMessage());
			}
		}
	}// FINAL CLASE GET USUARIO Y CONTRASEÑA

// CALSE GET DATOS MENSAJE ----------------> AÑADIR TRY CATCH
	public class getDatosCorreo {

		String usuario;
		String contraseña;

		public String getUsuario() {
			return usuario;
		}

		public String getContraseña() {
			return contraseña;
		}

		/**
		 * @param b variable de tipo Browser que nos permitira acceder al javascript del
		 *          html para hacer uso del local storage y recuperar los datos de
		 *          inicio de sesion
		 */
		public getDatosCorreo(Browser b) {

			b.executeJavaScript("localStorage");
			WebStorage webStorage = b.getLocalWebStorage();
			usuario = webStorage.getItem("usuario");
			contraseña = webStorage.getItem("contraseña");
		}

		public void save(String destinatario, String asunto, String contenido) throws MessagingException {

			System.out.println("Destinatario : " + destinatario);
			System.out.println("Asunto : " + asunto);
			System.out.println("Contenido : " + contenido);

			EnviarMail em = new EnviarMail();
			em.EnviarMail(destinatario, getUsuario(), getUsuario(), getContraseña(), asunto, contenido);
		}
	}

// IMPLEMENTAR RESCATADO DE DATOS ARRIBA COMO CON EL LOGIN
// CLASE ENCARGADA DE RESCATAR LOS DATOS DE REGISTRO DESDE EL HTML
	public static class getDatosRegistro {

		public getDatosRegistro() {
			System.out.println("entro en get datos registro");
		}

		public void save(String correo, String contraseña, String contraseña2) {
			System.out.println("Correo Electrónico    = " + correo);
			System.out.println("Contraseña = " + contraseña);
			System.out.println("Contraseña2 = " + contraseña2);

			if (correo.equals("") || contraseña.equals("") || contraseña2.equals("")) {

				JOptionPane.showMessageDialog(null, "No puedes dejar campos vacios", "ERROR",
						JOptionPane.ERROR_MESSAGE);

			} else if (!contraseña.equals(contraseña2)) {

				JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "ERROR", JOptionPane.ERROR_MESSAGE);
				File file = new File(ControladorLogin.class.getResource("Disenio/Html/registrar.html").getFile());
				browser.loadURL(file.toString());

			} else {

				boolean correoValido = false;

				if (correo.endsWith("@hotmail.es") || correo.endsWith("@hotmail.com")) {

					AutentificarCorreo ac = new AutentificarCorreo();

					if (ac.AutentificarCorreo("pop3.live.com", correo, contraseña)) {

						correoValido = true;

					} else {

						JOptionPane.showMessageDialog(null, "CORREO NO VÁLIDO", "ERROR", JOptionPane.ERROR_MESSAGE);

					}

				} else if (correo.endsWith("@gmail.es") || correo.endsWith("@gmail.com")) {

					AutentificarCorreo ac = new AutentificarCorreo();

					if (ac.AutentificarCorreo("pop.gmail.com", correo, contraseña)) {

						correoValido = true;

					} else {

						JOptionPane.showMessageDialog(null, "CORREO NO VÁLIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
					}

				}

				if (correoValido == true) {

					conexion.InsertNewUsuario(correo, contraseña);
					System.out.println("Nuevo usuario insertado con exito");

				} else {

					File file = new File(ControladorLogin.class.getResource("Disenio/Html/registrar.html").getFile());
					browser.loadURL(file.toString());

				}

			}
		}

	}
}
