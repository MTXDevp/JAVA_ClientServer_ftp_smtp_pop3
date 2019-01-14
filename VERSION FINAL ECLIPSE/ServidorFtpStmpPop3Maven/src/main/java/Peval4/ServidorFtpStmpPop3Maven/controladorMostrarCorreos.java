package Peval4.ServidorFtpStmpPop3Maven;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.WebStorage;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEvent;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventListener;
import com.teamdev.jxbrowser.chromium.dom.events.DOMEventType;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.LoadEvent;

import javax.activation.DataHandler;
import javax.mail.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class controladorMostrarCorreos {

    Browser browserAux;

    public controladorMostrarCorreos(Browser browser, String usuario, String contraseña, String host) {
        this.browserAux = browser;

        System.out.println("ESTOY EN EL CONTROLADOR MOSTRAR CORREOS");

        Folder folder = null;
        Properties prop = new Properties();
        // Deshabilitamos TLS
        prop.setProperty("mail.pop3.starttls.enable", "false");
        // Hay que usar SSL
        prop.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.pop3.socketFactory.fallback", "false");
        // Puerto 995 para conectarse.
        prop.setProperty("mail.pop3.port", "995");
        prop.setProperty("mail.pop3.socketFactory.port", "995");

        Session sesion = Session.getInstance(prop);
        Store store = null;
        try {
            store = sesion.getStore("pop3");
            store.connect(host, usuario, contraseña);
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);

        } catch (NoSuchProviderException e) {
            System.out.println(e.getMessage());
        } catch (MessagingException e) {
        	System.out.println(e.getMessage());
        }
        try {
            /*
            //NO SE ESTA SANDO EL JSON ESTARIA BIEN IMPLEMENTARLO XDDDDDDDDDDDDDDDDDDDDDDDDDDD
            //creacion del archivo json que contendra los datos de los correos
            JSONString json = new JSONString("[1, \"Uno\"]");
            JSValue window = browser.executeJavaScriptAndReturnValue("window");
            window.asObject().setProperty("myObject", json);
            */
            Message[] mensajes = folder.getMessages();

            ArrayList<String> arrayFrom = new ArrayList<>();
            ArrayList<String> arraySubject = new ArrayList<>();
            ArrayList<String> arrayFecha = new ArrayList<>();
            ArrayList<String> arrayCuerpo = new ArrayList<>();
            String content = "";

            //RECOJEMOS TODOS LOS CABEZADOS DE CORREOS
            for (int i = 0; i < mensajes.length; i++) {
                System.out.println("From:" + mensajes[i].getFrom()[0].toString());
                System.out.println("Subject:" + mensajes[i].getSubject());
                System.out.println("Date : " + mensajes[i].getSentDate());

                    arrayFrom.add(mensajes[i].getFrom()[0].toString() + "||");
                    arraySubject.add(mensajes[i].getSubject() + "||");
                    arrayFecha.add(mensajes[i].getSentDate().toString() + "||");
                }
                //ENVIAMOS UN CORREO CON LOS TITULARES DE LOS CORREOS
            File file = new File(
                    ControladorLogin.class.getResource("Disenio/Html/visualizarCorreo.html").getFile()
            );
            browserAux.loadURL(file.toString());

                browserAux.executeJavaScript("localStorage");
                WebStorage webStorage = browserAux.getLocalWebStorage();
                webStorage.clear();
                webStorage.setItem("numCorreos", String.valueOf(mensajes.length));
                webStorage.setItem("from", arrayFrom.toString());
                webStorage.setItem("subject", arraySubject.toString());
                webStorage.setItem("fecha", arrayFecha.toString());
                //5webStorage.setItem("cuerpo", arrayCuerpo.toString());

                System.out.println("Hay " + mensajes.length + " mensajes");

                //YA SE CUAL ES EL ERROR LA VENTANA SE CARGA ANTES DE QUE SE EJECUTE ESTE CODIGO XD

                //EVENTO DE LA LUPA para buscar correos

/*
            browserAux.addLoadListener(new LoadAdapter() {
                                        @Override
                                        public void onFinishLoadingFrame(FinishLoadingEvent event) {

                                            if (event.isMainFrame()) {
                                                DOMDocument document = browserAux.getDocument();
                                                DOMElement boton = document.findElement(By.name("buscar"));
                                                boton.addEventListener(DOMEventType.OnClick, new DOMEventListener() {
                                                    public void handleEvent(DOMEvent event) {

                                                        WebStorage webStorage = browserAux.getLocalWebStorage();
                                                        String contenidoCajaBuscar = webStorage.getItem("contenidoCajaBuscar");

                                                        if(contenidoCajaBuscar.equals("")){
                                                            System.out.println("El contenido de la caja no puede estar vacio");
                                                        }else{

                                                            String findComas = ",";
                                                            String findCorchetes1 = "\\[";
                                                            String findCorchetes2 = "]";

                                                            String arrayFromClear  = arrayFrom.toString().replaceAll(findComas,"");
                                                            String arrayFromClear2  = arrayFromClear.replaceAll(findCorchetes1,"");
                                                            String arrayFromClear3  = arrayFromClear2.replaceAll(findCorchetes2,"");
                                                            String [] arrayFromClear4 = arrayFromClear3.split(Pattern.quote("||"));
                                                            ArrayList coincidencias = new ArrayList();

                                                            for (int i = 0; i < arrayFromClear4.length ; i++) {

                                                                if(arrayFromClear4[i].contains(contenidoCajaBuscar)){


                                                                    coincidencias.add(new CoincidenciasBuscar(arrayFromClear4[i], arraySubject.get(i)
                                                                    , arrayFecha.get(i), arrayCuerpo.get(i)));

                                                                    browserAux.executeJavaScript("localStorage");
                                                                    webStorage.clear();
                                                                    webStorage.setItem("fromBuscar", arrayFromClear4[i]);
                                                                    webStorage.setItem("subjectBuscar", arraySubject.get(i));
                                                                    webStorage.setItem("fechaBuscar", arrayFecha.get(i));
                                                                    webStorage.setItem("cuerpoBuscar", arrayCuerpo.get(i));

                                                                    File file = new File(
                                                                            ControladorLogin.class.getResource("Disenio/Html/visualizarCorreo.html").getFile()
                                                                    );
                                                                    browserAux.loadURL(file.toString());
                                                                }
                                                            }


                                                        }

                                                    }
                                                }, false);
                                            }
                                        }
                                    });

*/

                //MANDAR INFORMACION MEDIANTE JSON

                //JSValue window = browser.executeJavaScriptAndReturnValue("window");
                //window.asObject().setProperty("numCorreos", new JSONString("[numCorreos, \""+mensajes.length +"\"]"));
            /*
            //MANDAR INFORMACION MEDIANTE EL WEB STORAGE
            WebStorage webStorage = browserAux.getLocalWebStorage();
            webStorage.setItem("numCorreos", String.valueOf(mensajes.length));
            */
            /*
            JSONString jsonCorreos = new JSONString(
                    "[k1, \"UNO\"]"
                    +     "[k2, \"DOS\"]");
            WebStorage webStorage = browser.getLocalWebStorage();
            webStorage.setItem("correos", jsonCorreos.getValue());
*/

            } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public class CoincidenciasBuscar{


        String from;
        String subject;
        String fecha;
        String contenido;

        public CoincidenciasBuscar(String from, String subject, String fecha, String contenido){

            this.from = from;
            this.subject =subject;
            this.fecha = fecha;
            this.contenido = contenido;

        }


        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getContenido() {
            return contenido;
        }

        public void setContenido(String contenido) {
            this.contenido = contenido;
        }

    }

}












