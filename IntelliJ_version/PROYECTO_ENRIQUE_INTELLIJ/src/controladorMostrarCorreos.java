import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSONString;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.WebStorage;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;

import javax.mail.*;
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
            Message[] mensajes = folder.getMessages();

            //PENDIENTE
            //creacion del archivo json que contendra los datos de los correos
            JSONString json = new JSONString("[1, \"Uno\"]");
            JSValue window = browser.executeJavaScriptAndReturnValue("window");
            window.asObject().setProperty("myObject", json);


            ArrayList<String> titularCorreos = new ArrayList<>();

            //CREACION DEL ARCHIVO JSON CON LOS DATOS NECESARIOS
            for (int i = 0; i < mensajes.length; i++) {
                System.out.println("From:" + mensajes[i].getFrom()[0].toString());
                System.out.println("Subject:" + mensajes[i].getSubject());

                titularCorreos.add("||" + mensajes[i].getFrom()[0].toString());
                titularCorreos.add("||" + mensajes[i].getSubject());
            }

            //ENVIAMOS UN CORREO CON LOS TITULARES DE LOS CORREOS
            WebStorage webStorage1 = browser.getLocalWebStorage();
            webStorage1.setItem("titularCorreos",  titularCorreos.toString());


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
            WebStorage webStorage = browser.getLocalWebStorage();
            webStorage.setItem("numCorreos",  String.valueOf(mensajes.length));



        } catch (MessagingException e) {
            System.out.println("SE HA PRODUCIDO UN ERROR OBTENIENDOS LOS MENSAJES DEL SERVIDOR DE CORREO");
        }
    }//FINAL IF IS GMAIL


    }





