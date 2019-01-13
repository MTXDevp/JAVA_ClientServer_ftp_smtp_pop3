package Java;
import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.events.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import javax.mail.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.logging.Level;

//USAR LOCAL STORAGE PARA GUARDAR USUARIO Y ONTRASEÃ‘A ES MAS SEGURO QUE USAR COOKIES?
//https://jxbrowser.support.teamdev.com/support/solutions/articles/9000035453-html5-local-session-storages
//https://platzi.com/blog/local-storage-html5/
//https://jxbrowser.support.teamdev.com/support/solutions/
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

		File file = new File(
			ControladorLogin.class.getResource("../Html/login.html").getFile()
		);
		browser.loadURL(file.toString());

		browser.addLoadListener(new LoadAdapter() {
			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent event) {

				if(event.isMainFrame()) {

					String url = event.getValidatedURL();
					browser = event.getBrowser();

					if(url.endsWith("login.html")) {

					}
					
				}
			}
		});
	}
}

