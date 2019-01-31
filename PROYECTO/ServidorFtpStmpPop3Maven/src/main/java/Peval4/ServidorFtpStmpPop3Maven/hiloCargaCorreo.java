package Peval4.ServidorFtpStmpPop3Maven;

import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;

/**
 * Clase que permite cargar en la vista una barra de carga.
 * 
 * @author Guillermo Barcia
 * @version 1.0.
 */
public class hiloCargaCorreo extends Thread {

	/**
	 * <DOMDocument> docuemt: Variable que permite acceder a los elementos del
	 * javaScript.
	 */
	private DOMDocument document;

	/**
	 * Constructor parametrizado.
	 * 
	 * @param <DOMDocument> document: Variable que permite acceder a los elementos
	 *        del javaScript.
	 */
	public hiloCargaCorreo(DOMDocument document) {
		this.document = document;
	}

	/**
	 * Método que inserta código en el HTML.
	 */
	public void run() {
		DOMElement div = document.findElement(By.id("segundario"));
		div.setInnerHTML(document.findElement(By.id("barraDescarga")).getInnerHTML());
	}
}
