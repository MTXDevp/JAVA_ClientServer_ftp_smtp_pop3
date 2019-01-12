
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

public class ControladorFTP {

	String filePath;
	BrowserView view;
	Browser browser = null;

	public ControladorFTP(Browser browser, BrowserView view, FinishLoadingEvent event) {

		this.browser = browser;
		this.view = view;
		System.out.println("Estas en la ventana server-FTP");

		DOMDocument document = this.browser.getDocument();
		DOMElement boton = document.findElement(By.name("archivo"));

		boton.addEventListener(DOMEventType.OnClick,
			(DOMEvent dome) -> {
				System.out.println("Estoy en el evento de click");
				this.browser.setDialogHandler(new DefaultDialogHandler(view) {
					@Override
					public CloseStatus onFileChooser(final FileChooserParams params) {
						final AtomicReference<CloseStatus> result = new AtomicReference<CloseStatus>(
							CloseStatus.CANCEL);
						try {
							SwingUtilities.invokeAndWait(new Runnable() {
								@Override
								public void run() {
									System.out.println("Estoy en el hilo para seleccionar un archivo");
									if(params.getMode() == FileChooserMode.Open) {
										JFileChooser fileChooser = new JFileChooser();
										if(fileChooser.showOpenDialog(view)
											== JFileChooser.APPROVE_OPTION) {
											File selectedFile = fileChooser.getSelectedFile();
											filePath = selectedFile.getAbsolutePath();
											params.setSelectedFiles(selectedFile.getAbsolutePath());
											result.set(CloseStatus.OK);
										}
									}
								}
							});
						} catch(InterruptedException e) {
							e.printStackTrace();
						} catch(InvocationTargetException e) {
							e.printStackTrace();
						}
						return result.get();
					}
				});
			}, false);
		this.browser = event.getBrowser();
		JSValue value = this.browser.executeJavaScriptAndReturnValue("window");
		System.out.println(filePath);
		value.asObject().setProperty("Account", new getDireccionFTP());
	}

	public class getDireccionFTP {

		public void save(String direccionFTP) {
			System.out.println("Nombre ftp : " + direccionFTP);
			System.out.println("Ruta: " + filePath);
			ClienteSubirArchivo c = new ClienteSubirArchivo(filePath);
		}
	}
}
