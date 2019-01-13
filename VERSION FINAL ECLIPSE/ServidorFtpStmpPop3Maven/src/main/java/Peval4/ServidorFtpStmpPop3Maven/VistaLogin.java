package Peval4.ServidorFtpStmpPop3Maven;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

public class VistaLogin {

    Browser browser;
    BrowserView view;
    DOMElement botonRegistrarse;

    public VistaLogin(){

        browser = new Browser();
        view = new BrowserView(browser);

        view.setDragAndDropEnabled(true);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //String ruta = "C:\\Users\\USUARIO\\Desktop\\ClienteServidor_Ftp_Smtp_Pop3\\public\\login.html";
        //browser.loadURL(ruta);
    }

    public Browser getBrowser() {
        return browser;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public BrowserView getView() {
        return view;
    }

    public void setView(BrowserView view) {
        this.view = view;
    }

    public DOMElement getBotonRegistrarse() {
        return botonRegistrarse;
    }

    public void setBotonRegistrarse(DOMElement botonRegistrarse) {
        this.botonRegistrarse = botonRegistrarse;
    }
}
