package Peval4.ServidorFtpStmpPop3Maven;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultDialogHandler;
import javax.swing.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Clase que que permite acceder a los archivos del usuario y obtener la ruta de
 * dicho fichero.
 * 
 * @author Guillermo Barcia
 * @version 1.0.
 */
public class ControladorFTP {

	/**
	 * <String> filePath: Ruta del fichero.
	 */
	String filePath;

	/**
	 * <BrowserView> view: Variable que hace referencia a los objetos de la vista.
	 */
	BrowserView view;

	/**
	 * <Browser> browser: Variable que hace referencia al contexto de la vista.
	 */
	Browser browser = null;

	/**
	 * Controlador parametrizado
	 * 
	 * @param <Browser> browser: Variable que hace referencia al contenido de la
	 *        vista.
	 * @param <BrowserView> view: Variable que hace referencia a la vista.
	 * @param <FinishLoadingEvent> event: Variable que hace referencia al evento que
	 *        se encuentra la vista.
	 */
	public ControladorFTP(Browser browser, BrowserView view, FinishLoadingEvent event) {

		// Inicializamos las variables
		this.browser = browser;
		this.view = view;
		System.out.println("Estas en la ventana server-FTP");

		DOMDocument document = this.browser.getDocument();
		DOMElement boton = document.findElement(By.name("archivo"));

		// Evento onclick que se le aplica a un boton que pertenece al HTML.
		boton.addEventListener(DOMEventType.OnClick, (DOMEvent dome) -> {
			this.browser.setDialogHandler(new DefaultDialogHandler(view) {
				@Override
				public CloseStatus onFileChooser(final FileChooserParams params) {
					final AtomicReference<CloseStatus> result = new AtomicReference<CloseStatus>(CloseStatus.CANCEL);
					try {
						SwingUtilities.invokeAndWait(new Runnable() {
							@Override
							/**
							 * Método que se lanza a parte en forma de hilo para poder mostrar una ventana
							 * en la que el usuario puede elegir el fichero que desea subir al servidor.
							 */
							public void run() {
								if (params.getMode() == FileChooserMode.Open) {
									JFileChooser fileChooser = new JFileChooser();
									if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
										File selectedFile = fileChooser.getSelectedFile();
										filePath = selectedFile.getAbsolutePath();
										params.setSelectedFiles(selectedFile.getAbsolutePath());
										result.set(CloseStatus.OK);
									}
								}
							}
						});
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					return result.get();
				}
			});
		}, false);
		this.browser = event.getBrowser();
		JSValue value = this.browser.executeJavaScriptAndReturnValue("window");
		value.asObject().setProperty("Account", new getDireccionFTP());
	}

	/**
	 * Clase que envia la ruta del fichero a la clase ClienteSubirArchivo.
	 */
	public class getDireccionFTP {

		/**
		 * Clase que lanza la clase ClienteSubirArchivo.
		 * 
		 * @param <String> direccionFTP: Direccion en la que se encuentra el servidor.
		 */
		public void save(String direccionFTP) {
			ClienteSubirArchivo c = new ClienteSubirArchivo(filePath);
		}
	}
}
