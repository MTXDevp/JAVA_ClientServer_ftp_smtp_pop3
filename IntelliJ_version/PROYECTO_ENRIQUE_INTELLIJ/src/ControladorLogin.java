
import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.LoadEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import javax.mail.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;

//USAR LOCAL STORAGE PARA GUARDAR USUARIO Y ONTRASEÑA ES MAS SEGURO QUE USAR COOKIES?
//https://jxbrowser.support.teamdev.com/support/solutions/articles/9000035453-html5-local-session-storages
//https://platzi.com/blog/local-storage-html5/

//https://jxbrowser.support.teamdev.com/support/solutions/
//pusheo
public class ControladorLogin {

    static Conexion conexion;
    VistaLogin vloging;
    DOMDocument document;
    DOMElement botonIniciarSesion;
    DOMElement cajaIniciarSesion;
    DOMElement botonRegistrarse;
    static Browser browser;
    BrowserView view;
    DOMElement cajaRegistrarse;
    String extUsuario;
    String extContraseña;


    ControladorLogin(){

        conexion = new Conexion();

        LoggerProvider.setLevel(Level.OFF);

        browser = new Browser();
        view = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(view, BorderLayout.CENTER);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        File file = new File(
                ControladorLogin.class.getResource("Disenio/Html/login.html").getFile()
        );
        browser.loadURL(file.toString());

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {

                if (event.isMainFrame()) {

                    String url = event.getValidatedURL();
                    browser = event.getBrowser();

                    if(url.endsWith("login.html")){

                        //Metodo que nos permite interactuar con el html y en concreto con el bloque js que coje las credenciales del formulario login
                        //la clase a la que nos lleva contiene la consulta a la base de datos la cual comprueba la existencia del usuario en concreto
                        JSValue value = browser.executeJavaScriptAndReturnValue("window");
                        getUsuarioContraseña uc = new getUsuarioContraseña();
                        value.asObject().setProperty("Account", uc);

                    }else if(url.endsWith("registrar.html")){

                        JSValue value = browser.executeJavaScriptAndReturnValue("window");
                        getDatosRegistro dr = new getDatosRegistro();
                        value.asObject().setProperty("Account", dr);

                    }else if(url.endsWith("visualizarCorreo.html")){

                        System.out.println("Estas en la ventana de visualizacion de correos");

                    }else if(url.endsWith("enviarCorreo.html")){

                        DOMDocument document = browser.getDocument();
                        DOMElement boton = document.findElement(By.name("enviar"));
                        DOMElement cajaDestinatario = document.findElement(By.name("destinatario"));
                        DOMElement cajaAsunto = document.findElement(By.name("asunto"));
                        DOMElement cajaContenido = document.findElement(By.name("contenidoCorreo"));

                            System.out.println("Estas en la ventana de enviar correo");
                            JSValue value = browser.executeJavaScriptAndReturnValue("window");//Cojemos la ventana completa con todos sus elementos
                            getDatosCorreo dc= new getDatosCorreo(browser);
                            value.asObject().setProperty("Account", dc);
                        }
                    }// final si es el main frame
                }
        });
    }

    //CLASE ENCARGADA DE RESCATAR LOS DATOS USUARIO/CONTRASEÑA DESDE EL HTML
    public static class getUsuarioContraseña {

        String externalUsuario;
        String externalContraseña;

        public String getExternalUsuario() {
            return externalUsuario;
        }

        public String getExternalContraseña() {
            return externalContraseña;
        }

        public void save(String usuario, String contraseña) {
            System.out.println("Usuario    = " + usuario);
            System.out.println("Contraseña = " + contraseña);

            conexion.CheckLogin(usuario, contraseña);
            try {

                //si el usuario y la contraseña coinciden
                if(conexion.getRs().next()){

                    externalUsuario = usuario;
                    externalContraseña = contraseña;

                    File file = new File(
                            ControladorLogin.class.getResource("Disenio/Html/menu.html").getFile()
                    );
                    browser.loadURL(file.toString());

                    //cargar controlador menu para una mayor encapsulacion

                }else{
                    JOptionPane.showMessageDialog(null, "CREDENCIALES ERRONEAS", "ERROR", JOptionPane.WARNING_MESSAGE);

                    //en el caso de que las crecenciales no sean correctas volvemos a cargar la ventana de login
                    File file = new File(
                            ControladorLogin.class.getResource("Disenio/Html/login.html").getFile()
                    );
                    browser.loadURL(file.toString());
                }
            } catch (SQLException e) {
                System.out.println("SE HA PRODUCIDO UN ERROR CONSULTANDO LA BASE DE DATOS");
                System.out.println("DETALLES : ");
                System.out.println(e.getMessage());
            }
        }
    }// FINAL CLASE GET USUARIO Y CONTRASEÑA


    //CALSE GET DATOS MENSAJE ----------------> AÑADIR TRY CATCH
    public class getDatosCorreo{

        String usuario;
        String contraseña;

        public String getUsuario() {
            return usuario;
        }
        public String getContraseña() {
            return contraseña;
        }
        /**
         * @param b variable de tipo Browser que nos permitira acceder al javascript del html para hacer uso
         *          del local storage y recuperar los datos de inicio de sesion
         */
        public getDatosCorreo(Browser b){

            b.executeJavaScript("localStorage");
            WebStorage webStorage = b.getLocalWebStorage();
            usuario = webStorage.getItem("usuario");
            contraseña = webStorage.getItem("contraseña");
        }
        public void save(String destinatario, String asunto, String contenido) throws MessagingException {

            System.out.println("Destinatario : " + destinatario);
            System.out.println("Asunto : " + asunto);
            System.out.println("Contenido : " + contenido);


            EnviarMail em = new EnviarMail();
            em.EnviarMail(destinatario, getUsuario(), getUsuario(),
                  getContraseña(), asunto, contenido);

        }
    }

    //IMPLEMENTAR RESCATADO DE DATOS ARRIBA COMO CON EL LOGIN
    //CLASE ENCARGADA DE RESCATAR LOS DATOS DE REGISTRO DESDE EL HTML
    public static class getDatosRegistro{

        public getDatosRegistro(){
            System.out.println("entro en get datos registro");
        }
        public void save(String correo, String usuario, String contraseña, String contraseña2) {
            System.out.println("Correo Electrónico    = " + correo);
            System.out.println("Usuario = " + usuario);
            System.out.println("Contraseña = " + contraseña);
            System.out.println("Contraseña2 = " + contraseña2);

            if(!contraseña.equals(contraseña2)){

                JOptionPane.showMessageDialog(null, "LAS CONTRASEÑAS NO COINCIDEN", "ERROR", JOptionPane.WARNING_MESSAGE);
            }else{
                conexion.InsertNewUsuario(usuario, contraseña, correo);
                System.out.println("Nuevo usuario insertado con exito");
            }
        }
    }
    //ESTA CLASE?
    public static class getDireccionFTP {
        public void save(String direccionFTP) {

            System.out.println("FTP : " + direccionFTP);
            ControladorFTP cFTP = new ControladorFTP(direccionFTP);

        }
    }
}


