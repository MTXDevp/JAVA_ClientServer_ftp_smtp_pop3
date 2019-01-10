
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.CloseStatus;
import com.teamdev.jxbrowser.chromium.FileChooserMode;
import com.teamdev.jxbrowser.chromium.FileChooserParams;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultDialogHandler;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.*;

public class SubirArchivo {

	static DOMElement boton;
	static DOMElement cajaTexto;
	static ArrayList<File> dropppedFiles;
	static Browser browser;
	static BrowserView view;

	public SubirArchivo() {
		browser.addLoadListener(new LoadAdapter() {
			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent event) {

				if(event.isMainFrame()) {

					DOMDocument document = event.getBrowser().getDocument();
					//boton = document.findElement(By.id("boton"));
					//cajaTexto = document.findElement(By.id("caja"));
				}
			}
		});
	}

	public static void SubirArchivo() {

		browser.setDialogHandler(new DefaultDialogHandler(view) {
			@Override
			public CloseStatus onFileChooser(final FileChooserParams params) {
				final AtomicReference<CloseStatus> result = new AtomicReference(CloseStatus.CANCEL);

				try {
					SwingUtilities.invokeAndWait(new Runnable() {
						@Override
						public void run() {
							if(params.getMode() == FileChooserMode.Open) {

								JFileChooser fileChooser = new JFileChooser();

								if(fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {

									File selectedFile = fileChooser.getSelectedFile();

									params.setSelectedFiles(selectedFile.getAbsolutePath());
									System.out.println(selectedFile.getAbsolutePath());
									result.set(CloseStatus.OK);
								}
							}
						}
					});
				} catch(InterruptedException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
				return result.get();
			}
		});
	}
}
