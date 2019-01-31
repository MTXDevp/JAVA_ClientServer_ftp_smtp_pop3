package Peval4.ServidorFtpStmpPop3Maven;

import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;

import crack.JxBrowserHackUtil;
import crack.JxVersion;
import java.util.UUID;

/**
 * Clase principal que se ejecuta para arrancar la aplicación.
 * 
 * @author Guillermo Barcia, Guillermo Ramos, Rafael Valls.
 * @version 1.0.
 */
public class Main {

	public static void main(String[] args) {
		
		
		
		//storage.clear();
		// Este código es para hackear la licencia de jxBrowser
		JxBrowserHackUtil.hack(JxVersion.V6_22);
		String identity = UUID.randomUUID().toString();
		BrowserContextParams params = new BrowserContextParams("temp/browser/" + identity);
		BrowserContext context1 = new BrowserContext(params);

		// Arrancamos la aplicación y el servidor
		new ControladorLogin(context1);
		ServidorFTP.main(args);
	}

}