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

/**
 * Clase que controla la vista en la que se encuentra el usuario y realiza
 * operaciones distintas.
 * 
 * @author Guillermo Barcia, Rafael Valls.
 * @version 1.0.
 */
public class ControladorLogin {

	/**
	 * <Conexion> conexion: Variable que permite establecer la conexion con la base
	 * de datos.
	 */
	private static Conexion conexion;

	/**
	 * <Browser> browser: Variable que hace referencia al contenido de la vista.
	 */
	private static Browser browser;

	/**
	 * <BrowserView> view: Variable que hace referencia a los elementos de la vista.
	 */
	private BrowserView view;

	/**
	 * Controlador parametrizado
	 * 
	 * @param <BrowserContext> context: Coge el contexto de la vista.
	 */
	public ControladorLogin(BrowserContext context) {

		// conexion = new Conexion();
		LoggerProvider.setLevel(Level.OFF);

		// Creamos una vista en Java a la cual le añadiremos las vistas en HTML.
		browser = new Browser(context);
		view = new BrowserView(browser);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(view, BorderLayout.CENTER);
		frame.setSize(600, 450);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// Cargamos la vista en el JFrame.
		File file = new File(ControladorLogin.class.getResource("Disenio/Html/login.html").getFile());
		browser.loadURL(file.toString());

		// Aplicamos un evento a a ventana para que ejecute el codigo mientras se esta
		// cargando la vista.
		browser.addLoadListener(new LoadAdapter() {
			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent event) {

				if (event.isMainFrame()) {

					// Inicializamos variables
					String url = event.getValidatedURL();
					browser = event.getBrowser();

					if (url.endsWith("login.html")) {

						// Obtenemos los elementos de la vista para obtener informacion que introduce el
						// usuario.
						JSValue value = browser.executeJavaScriptAndReturnValue("window");
						// getUsuarioContraseña uc = new getUsuarioContraseña(browser, conexion);
						// value.asObject().setProperty("Account", uc);

					} else if (url.endsWith("registrar.html")) {

						// Obtenemos los elementos de la vista para obtener informacion que introduce el
						// usuario.
						JSValue value = browser.executeJavaScriptAndReturnValue("window");
						getDatosRegistro dr = new getDatosRegistro(browser, conexion);
						value.asObject().setProperty("Account", dr);

					} else if (url.endsWith("menu.html")) {
						// Cambiamos el tamaño del JFrame.
						frame.setSize(750, 400);
						
						// Inicializamos variables.
						DOMDocument document = browser.getDocument();
						DOMElement botonVisualizarCorreos = document.findElement(By.name("botonVisualizarCorreos"));

						// Aplicamos un evento a un boton de HTML para que cuando sea pulsado cargue la
						// ventana de visualizar los correos.
						botonVisualizarCorreos.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
							public void handleEvent(DOMEvent event) {

								// Sincronizamos un hilo para que pudiera verse la barra de carga al cargar los
								// correos
								synchronized (this) {
									hiloCargaCorreo h = new hiloCargaCorreo(document);
									h.start();
								}
								enventoCargarCorreos();
							}
						}, false);

						// Inicializamos variables
						DOMElement bajarFichero = document.findElement(By.name("bajarFichero"));

						// Añadimos un evento a un boton de HTML para que cuando sea pulsado muestre los
						// ficheros que contiene el servidor.
						bajarFichero.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
							public void handleEvent(DOMEvent event) {
								new ControladorMostrarArchivos(browser);

								// Cambiamos a la ventana de descarga de archivos.
								File file = new File(ControladorLogin.class
										.getResource("Disenio/Html/descargaArchivo.html").getFile());
								browser.loadURL(file.toString());
							}
						}, false);

					} else if (url.endsWith("servidorFTP.html")) {
						// Cambiamos el tamaño del JFrame.
						frame.setSize(750, 600);
						new ControladorFTP(browser, view, event);

					} else if (url.endsWith("descargaArchivo.html")) {
						// Cambiamos el tamaño del JFrame.
						frame.setSize(750, 600);
						JSValue value = browser.executeJavaScriptAndReturnValue("window");
						value.asObject().setProperty("Account", new getDireccionFTP());

					} else if (url.endsWith("VisualizarCorreo.html")) {
						WebStorage webStorage = browser.getLocalWebStorage();
						String actualizar = webStorage.getItem("actualizar");

						if (actualizar.equals("true")) {
							enventoCargarCorreos();
							webStorage.setItem("actualizar", "no");
						}
					} else if (url.endsWith("enviarCorreo.html")) {
						JSValue value = browser.executeJavaScriptAndReturnValue("window");
						getDatosCorreo dc = new getDatosCorreo(browser);
						value.asObject().setProperty("Account", dc);

					}
				}
			}
		});
	}

	/**
	 * Metodo que carga la bandeja de entrada.
	 */
	public void enventoCargarCorreos() {
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
}
