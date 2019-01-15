package Peval4.ServidorFtpStmpPop3Maven;

import java.io.File;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.swing.JOptionPane;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.WebStorage;

/**
 * 
 * @author USUARIO
 * Clase con los eventos necesarios para transportar los datos del usuario desde nuestra vista a nuestras clases java
 * mediantes funciones JS, alojadas en nuestros html
 * 
 *
 */
public class ComunicacionJsJava {
    public void Close(int numCorreo) {

        System.out.println("Numero de correos " + numCorreo);

    }
    
	public static class getDireccionFTP {

		public void save(String nombreFichero) {
			System.out.println("Nombre Fichero : " + nombreFichero);
			new ClienteBajarArchivo(nombreFichero);
		}
	}

    
	public static class getUsuarioContraseña {

		Browser browser;
		Conexion conexion;
		
		public getUsuarioContraseña(Browser browser, Conexion conexion) {
			
			this.browser = browser;
			this.conexion = conexion;
			
		}

		public void save(String usuario, String contraseña) {
			System.out.println("Usuario    = " + usuario);
			System.out.println("Contraseña = " + contraseña);

			this.conexion.CheckLogin(usuario, contraseña);
			try {
				// si el usuario y la contraseña coinciden
				if (this.conexion.getRs().next()) {

					System.out.println("Se ha encontrado al usuario");
					
					// Si el login es correcto iremos a la ventana del menu
					File file1 = new File(ControladorLogin.class.getResource("Disenio/Html/menu.html").getFile());
					this.browser.loadURL(file1.toString());
				} else {
					System.out.println("El usuario no se encuentra registrado");
					JOptionPane.showMessageDialog(null, "CREDENCIALES ERRONEAS", "ERROR", JOptionPane.WARNING_MESSAGE);

					// en el caso de que las crecenciales no sean correctas volvemos a cargar la
					// ventana de login
					File file2 = new File(ControladorLogin.class.getResource("Disenio/Html/login.html").getFile());
					this.browser.loadURL(file2.toString());
				}
			} catch (SQLException e) {
				System.out.println("SE HA PRODUCIDO UN ERROR CONSULTANDO LA BASE DE DATOS");
				System.out.println("DETALLES : ");
				System.out.println(e.getMessage());
			}
		}
	}// FINAL CLASE GET USUARIO Y CONTRASEÑA
	
	public static class getDatosCorreo {

		String usuario;
		String contraseña;

		public String getUsuario() {
			return usuario;
		}

		public String getContraseña() {
			return contraseña;
		}

		/**
		 * @param b variable de tipo Browser que nos permitira acceder al javascript del
		 *          html para hacer uso del local storage y recuperar los datos de
		 *          inicio de sesion
		 */
		public getDatosCorreo(Browser b) {

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
			em.EnviarMail(destinatario, getUsuario(), getUsuario(), getContraseña(), asunto, contenido);
		}
	}
	
	public static class getDatosRegistro {

		Browser browser;
		Conexion conexion;
		
		public getDatosRegistro(Browser browser, Conexion conexion) {
		this.browser = browser;
		this.conexion = conexion;
		}

		public void save(String correo, String contraseña, String contraseña2) {
			System.out.println("Correo Electrónico    = " + correo);
			System.out.println("Contraseña = " + contraseña);
			System.out.println("Contraseña2 = " + contraseña2);

			if (correo.equals("") || contraseña.equals("") || contraseña2.equals("")) {

				JOptionPane.showMessageDialog(null, "No puedes dejar campos vacios", "ERROR",
						JOptionPane.ERROR_MESSAGE);

			} else if (!contraseña.equals(contraseña2)) {

				JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "ERROR", JOptionPane.ERROR_MESSAGE);
				File file = new File(ControladorLogin.class.getResource("Disenio/Html/registrar.html").getFile());
				this.browser.loadURL(file.toString());

			} else {

				boolean correoValido = false;

				if (correo.endsWith("@hotmail.es") || correo.endsWith("@hotmail.com")) {

					AutentificarCorreo ac = new AutentificarCorreo();

					if (ac.AutentificarCorreo("pop3.live.com", correo, contraseña)) {

						correoValido = true;

					} else {

						JOptionPane.showMessageDialog(null, "CORREO NO VÁLIDO", "ERROR", JOptionPane.ERROR_MESSAGE);

					}

				} else if (correo.endsWith("@gmail.es") || correo.endsWith("@gmail.com")) {

					AutentificarCorreo ac = new AutentificarCorreo();

					if (ac.AutentificarCorreo("pop.gmail.com", correo, contraseña)) {

						correoValido = true;

					} else {

						JOptionPane.showMessageDialog(null, "CORREO NO VÁLIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
					}

				}

				if (correoValido == true) {

					this.conexion.InsertNewUsuario(correo, contraseña);
					System.out.println("Nuevo usuario insertado con exito");

				} else {

					File file = new File(ControladorLogin.class.getResource("Disenio/Html/registrar.html").getFile());
					this.browser.loadURL(file.toString());

				}

			}
		}

	}


}
