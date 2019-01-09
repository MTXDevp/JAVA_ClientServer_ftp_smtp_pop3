import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.WebStorage;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextAdapter;
import com.teamdev.jxbrowser.chromium.events.ScriptContextEvent;

import javax.mail.*;
import java.util.Properties;

public class controladorMostrarCorreos {

    Browser browserAux;

    public controladorMostrarCorreos(Browser browser, String usuario, String contraseña, String host) {

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
            for (int i = 0; i < mensajes.length; i++) {
                System.out.println("From:" + mensajes[i].getFrom()[0].toString());
                System.out.println("Subject:" + mensajes[i].getSubject());
            }
            System.out.println("Hay " + mensajes.length + " mensajes");
            //SABIENDO EL NÚMERO DE MENSAJES DEBOS DE AÑADIRLOS AL JAVASCRIPT LUL
            //1.AÑADIR LIMITE AL FOR QUE CREA TR DE FORMA DINAMICA

            //A CONTINUACION PARASEREMOS UN OBJETO DE JAVA A JAVASCRIPT--------------NO ENTRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            //JS VALE es la clase para almacenar todos los valores js
            // Please make sure that you load required web page before you access its JavaScript and register Java objects
            //https://stackoverflow.com/questions/35329332/jxbrowser-6-1-javascript-java-bridge-api-is-not-working/35344616
            //LA CLASE EVENTS EN REALIDAD MANDA DATOS AL JS / HTML

            JSValue window = browser.executeJavaScriptAndReturnValue("window");
            window.asObject().setProperty("java", new Events());

        } catch (MessagingException e) {
            System.out.println("SE HA PRODUCIDO UN ERROR OBTENIENDOS LOS MENSAJES DEL SERVIDOR DE CORREO");
        }
    }//FINAL IF IS GMAIL

}



