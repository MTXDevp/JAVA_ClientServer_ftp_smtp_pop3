package Peval4.ServidorFtpStmpPop3Maven;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.DownloadHandler;
import com.teamdev.jxbrowser.chromium.DownloadItem;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.DownloadEvent;
import com.teamdev.jxbrowser.chromium.events.DownloadListener;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import java.io.File;
import java.util.ArrayList;

public class DescargarArchivo {

    static DOMElement boton ;
    static DOMElement cajaTexto ;
    static ArrayList<File> dropppedFiles;
    static Browser browser;
    static BrowserView view;


    public DescargarArchivo(){

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {

                if (event.isMainFrame()) {

                    DOMDocument document = event.getBrowser().getDocument();
                    //boton = document.findElement(By.id("boton"));
                    //cajaTexto = document.findElement(By.id("caja"));

                }
            }
        });
    }


    public void BajarArchivo(){

        browser.setDownloadHandler(new DownloadHandler() {

            public boolean allowDownload(DownloadItem download) {

                download.addDownloadListener(new DownloadListener() {

                    public void onDownloadUpdated(DownloadEvent event) {

                        DownloadItem download = event.getDownloadItem();

                        if (download.isCompleted()) {
                            System.out.println("Descarga Completada!");
                        }
                    }
                });
                System.out.println("Dest file: " + download.getDestinationFile().getAbsolutePath());
                return true;
            }
        });
    }
}
