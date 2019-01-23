package Peval4.ServidorFtpStmpPop3Maven;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.WebStorage;
import java.util.ArrayList;

/**
 * Clase encargada de llamar a los metodos para listar y descargar los fichero
 * que hayan sido seleccionados por el usuario.
 * 
 * @author Barcia.
 * @version 1.0.
 */
public class ControladorMostrarArchivos {

	/**
	 * Controlador parametrizado
	 * 
	 * @param <Browser> browser: Variable que permite obtener el contenido de la
	 *        vista.
	 */
	public ControladorMostrarArchivos(Browser browser) {
		// Inicializamos las variables.
		ClienteBajarArchivo c = new ClienteBajarArchivo();
		ArrayList<String> ficherosServidor = c.listarFicheros();

		// Obtenemos la información de que fichero a seleccionado el usuario mediante la
		// conexión de javaScript Java.
		WebStorage webStorage = browser.getLocalWebStorage();
		webStorage.clear();
		webStorage.setItem("fichero", ficherosServidor.toString());
	}
}
