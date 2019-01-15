package Peval4.ServidorFtpStmpPop3Maven;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserContextParams;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import crack.JxBrowserHackUtil;
import crack.JxVersion;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class Main {

	static DOMElement boton;
	static DOMElement cajaTexto;
	static ArrayList<File> dropppedFiles;
	static Browser browser;
	static BrowserView view;

	public static void main(String[] args) {
		JxBrowserHackUtil.hack(JxVersion.V6_22);
		String identity = UUID.randomUUID().toString();
		BrowserContextParams params = new BrowserContextParams("temp/browser/" + identity);
		BrowserContext context1 = new BrowserContext(params);
		ControladorLogin cl = new ControladorLogin(context1);
		ServidorFTP.main(args);
	}
}