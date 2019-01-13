package Peval4.ServidorFtpStmpPop3Maven;


import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.WebStorage;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Barcia
 */
public class ControladorMostrarArchivos {

	public ControladorMostrarArchivos(Browser browser) {
		ClienteBajarArchivo c = new ClienteBajarArchivo();
		ArrayList<String> ficherosServidor = c.listarFicheros();

		WebStorage webStorage = browser.getLocalWebStorage();
		webStorage.clear();
		webStorage.setItem("fichero", ficherosServidor.toString());
		System.out.println(webStorage.getItem("fichero"));
	}
}
