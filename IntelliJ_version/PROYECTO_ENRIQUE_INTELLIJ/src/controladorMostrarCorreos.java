import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSONString;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.WebStorage;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;

import javax.activation.DataHandler;
import javax.mail.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class controladorMostrarCorreos {

    Browser browserAux;

    public controladorMostrarCorreos(Browser browser, String usuario, String contraseña, String host) {

        this.browserAux = browser;

        this.browserAux = browser;

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
            System.out.println("SE HA PRODUCIDO UN ERROR COMUNICANDONOS CON EL SERVIDOR DE CORREO");
        } catch (MessagingException e) {
            e.printStackTrace();
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
            String content ="";

            //RECOJEMOS TODOS LOS CABEZADOS DE CORREOS
            for (int i = 0; i < mensajes.length; i++) {
                System.out.println("From:" + mensajes[i].getFrom()[0].toString());
                System.out.println("Subject:" + mensajes[i].getSubject());
                System.out.println("Date : " + mensajes[i].getSentDate());

                //String content = mensajes[i].getContent().toString();
                Multipart multipart = (Multipart) mensajes[i].getContent();

                for (int j = 0; j < 1; j++) {

                    BodyPart bodyPart = multipart.getBodyPart(j);

                    String disposition = bodyPart.getDisposition();

                    if (disposition != null && (disposition.equalsIgnoreCase("ATTACHMENT"))) { // BodyPart.ATTACHMENT doesn't work for gmail
                        System.out.println("Mail have some attachment");

                        DataHandler handler = bodyPart.getDataHandler();
                        System.out.println("file name : " + handler.getName());
                    } else {
                        System.out.println("Body" + bodyPart.getContent());
                        content = bodyPart.getContent().toString();
                    }
                    arrayCuerpo.add(content + "||");
                    arrayFrom.add(mensajes[i].getFrom()[0].toString() + "||");
                    arraySubject.add(mensajes[i].getSubject() + "||");
                    arrayFecha.add(mensajes[i].getSentDate().toString() + "||");
                }
                //ENVIAMOS UN CORREO CON LOS TITULARES DE LOS CORREOS

                WebStorage webStorage = browser.getLocalWebStorage();
                webStorage.clear();
                webStorage.setItem("numCorreos", String.valueOf(mensajes.length));
                webStorage.setItem("from", arrayFrom.toString());
                webStorage.setItem("subject", arraySubject.toString());
                webStorage.setItem("fecha", arrayFecha.toString());
                webStorage.setItem("cuerpo", arrayCuerpo.toString());

                System.out.println("Hay " + mensajes.length + " mensajes");

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

            }
    }

        catch (MessagingException e) {
            System.out.println("SE HA PRODUCIDO UN ERROR ACCEDIENDO A LOS MENSAJES");
        } catch (IOException e) {
            System.out.println("ERROR ACCEDIENDO A LOS DATOS");
        }}}









