package com.clienteservidorftp;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Main {
    
    static DOMElement link ;
            
    public static void main(String[] args) {
        
        Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);
 
     
        JFrame frame = new JFrame("RUTA LOCAL");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       // frame.add(addressPane, BorderLayout.NORTH);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
 
        String ruta = "C:\\Users\\USUARIO\\Desktop\\ClienteServidor-ftp-smtp-pop3\\Archivos HTML\\boton.html";
        //String ruta = "https://www.xataka.com/";
        browser.loadURL(ruta);
        
        DOMDocument document = browser.getDocument();
 
         browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    DOMDocument document = event.getBrowser().getDocument();
                    link = document.findElement(By.id("boton"));
                    
                    
                    Map<String, String> attributes = link.getAttributes();
                    
                    for (String attrName : attributes.keySet()) {
                        System.out.println(attrName + " = " + attributes.get(attrName));
                    }
                    
                }
            }
        });
         
         
         browser.addLoadListener(new LoadAdapter() {
             
                @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    System.out.println("Main frame has finished loading");
            
                    link.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
                        @Override
                            public void handleEvent(DOMEvent dome) {
                             // String ruta = "C:\\Users\\USUARIO\\Desktop\\ClienteServidor-ftp-smtp-pop3\\Archivos HTML\\boton.html";
                             
                                String ruta = "https://www.nike.com/";
                             browser.loadURL(ruta);
                     }
        }, false);
            
            
               }
    }
             
});
         
         
         
        
         
        /*
        DOMElement botonCapturado = document.findElement(By.id("boton"));
        
        botonCapturado.addEventListener(DOMEventType.OnClick, (DOMEvent dome) -> {
            
            System.out.println("LAS PULSAO :D");
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.   
        }, false);

        
   

        
     */   
    }
    
    public void function(){
        System.out.println("HOLA MUNDO");
    }
}