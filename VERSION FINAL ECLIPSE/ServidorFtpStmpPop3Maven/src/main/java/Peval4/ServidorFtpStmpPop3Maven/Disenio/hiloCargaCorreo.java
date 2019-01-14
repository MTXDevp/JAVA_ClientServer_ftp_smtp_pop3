package Peval4.ServidorFtpStmpPop3Maven.Disenio;

import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;

public class hiloCargaCorreo extends Thread {

	private DOMDocument document;
	public hiloCargaCorreo(DOMDocument document) {
		this.document = document;
	}

	public void run() {
		DOMElement div = document.findElement(By.id("segundario"));
		div.setInnerHTML(document.findElement(By.id("barraDescarga")).getInnerHTML());
	}
}
