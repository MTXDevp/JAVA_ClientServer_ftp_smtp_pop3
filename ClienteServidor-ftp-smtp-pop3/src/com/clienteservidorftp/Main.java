package com.clienteservidorftp;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.CloseStatus;
import com.teamdev.jxbrowser.chromium.FileChooserMode;
import com.teamdev.jxbrowser.chromium.FileChooserParams;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultDialogHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileSystemView;

public class Main {
    
    static DOMElement boton ;
    static DOMElement cajaTexto ;
     static ArrayList<File> dropppedFiles;
     static Browser browser;
     static BrowserView view;
            
    public static void main(String[] args) {
        
        
        browser = new Browser();
        view = new BrowserView(browser);
        view.setDragAndDropEnabled(true);
     
        JFrame frame = new JFrame("RUTA LOCAL");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       // frame.add(addressPane, BorderLayout.NORTH);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
 
        //String ruta1 = "C:\\Users\\USUARIO\\Desktop\\ClienteServidor-ftp-smtp-pop3\\Archivos HTML\\boton.html";
         String ruta2 = "C:\\Users\\USUARIO\\Desktop\\ClienteServidor-ftp-smtp-pop3\\Archivos HTML\\SubirArch.html";
        //String ruta = "https://www.xataka.com/";
        browser.loadURL(ruta2);
        
        DOMDocument document = browser.getDocument();
 
         browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                
                if (event.isMainFrame()) {
                    
                    DOMDocument document = event.getBrowser().getDocument();
                    boton = document.findElement(By.id("boton"));
                    cajaTexto = document.findElement(By.id("caja"));
         
                    SelectFile();
                    
                }
            }
        });
        
    }
    
    public void function(){
        System.out.println("HOLA MUNDO");
    }
    
    public static void SelectFile(){
      
        browser.setDialogHandler(new DefaultDialogHandler(view) {
            @Override
            public CloseStatus onFileChooser(final FileChooserParams params) {
                final AtomicReference<CloseStatus> result = new AtomicReference<CloseStatus>(
                        CloseStatus.CANCEL);

                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            if (params.getMode() == FileChooserMode.Open) {
                                
                                JFileChooser fileChooser = new JFileChooser();
                                
                                if (fileChooser.showOpenDialog(view)== JFileChooser.APPROVE_OPTION) {
                                    
                                    File selectedFile = fileChooser.getSelectedFile();
                                    
                                    params.setSelectedFiles(selectedFile.getAbsolutePath());
                                    System.out.println(selectedFile.getAbsolutePath());
                                    result.set(CloseStatus.OK);
                                }
                            }
                        }
                    });
                     }catch (InterruptedException e) {
                            e.printStackTrace();
                    } catch (InvocationTargetException e) {
                           e.printStackTrace();
                }

                return result.get();
            }
        });
    }
}