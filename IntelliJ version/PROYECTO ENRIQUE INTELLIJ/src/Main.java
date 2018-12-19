import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import org.jboss.logging.Logger.Level;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;


public class Main {
    
     static DOMElement boton ;
     static DOMElement cajaTexto ;
     static ArrayList<File> dropppedFiles;
     static Browser browser;
     static BrowserView view;


    public static void main(String[] args) {


        ControladorLogin cl = new ControladorLogin();

    }


}