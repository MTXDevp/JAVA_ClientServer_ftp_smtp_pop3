package Peval4.ServidorFtpStmpPop3Maven;

import java.io.File;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.WebStorage;

/**
 * Clase con los eventos necesarios para transportar los datos del usuario desde
 * nuestra vista a nuestras clases java mediantes funciones JS, alojadas en
 * nuestros HTML.
 * 
 * @author Rafael Valls
 *
 */
public class ComunicacionJsJava {

	/**
	 * <Browser> browser: Variable que hace referencia al contenido que hay en el
	 * 'JFrame'.
	 */
	private static Browser browser;

	/**
	 * <Conexion> conexion: Variable que permite la establecer la conexión on la
	 * base de datos.
	 */
	private static Conexion conexion;

	/**
	 * Método para imprimir número de correo.
	 * 
	 * @param <int> numCorreo: Número de correos.
	 */
	public void numeroCorreo(int numCorreo) {
		System.out.println("Numero de correos " + numCorreo);
	}

	/**
	 * Clase que manda la informacion del nombre del fichero que el usuario quiere
	 * descargar.
	 */
	public static class getDireccionFTP {

		/**
		 * Método que recoge la información (nombre del fichero) del HTML y lo envia a
		 * Java.
		 * 
		 * @param <String> nombreFichero: Nombre del fichero que el usuario quiere
		 *        descargar.
		 */
		public void save(String nombreFichero) {
			new ClienteBajarArchivo(nombreFichero);
		}
	}

	/**
	 * Clase que permite obtener los datos del usuario.
	 */
	public static class getUsuarioContraseña {

		/**
		 * Constructor parametrizado.
		 * 
		 * @param <Browser>b: Variable que hace referencia al contexto de la vista.
		 * @param <Conexion>c: Variable que hace referencia a la conexión con la base de
		 *        datos.
		 */
		public getUsuarioContraseña(Browser b, Conexion c) {
			browser = b;
			conexion = c;
		}

		/**
		 * Método que permite obtener los datos personales del usuario.
		 * 
		 * @param <String> usuario: Variable que contiene el dato usuario del cliente.
		 * @param <String> contraseña: Variable que contiene el dato contraseña del
		 *        cliente.
		 */
		public void save(String usuario, String contraseña) {

			// Comprobar si los datos personales del usuario son correctos.
			conexion.CheckLogin(usuario, contraseña);
			try {
				// Si el usuario y la contraseña coinciden
				if (conexion.getRs().next()) {

					System.out.println("Credenciales correctas");

					// Si el login es correcto cargamos la ventana de menú
					File file = new File(ControladorLogin.class.getResource("Disenio/Html/menu.html").getFile());
					browser.loadURL(file.toString());
				} else {
					System.out.println("Credenciales incorrectas, el usuario no se encuentra registrado");
					JOptionPane.showMessageDialog(null, "Credenciales Erroneas", "Error", JOptionPane.WARNING_MESSAGE);

					// En caso de que el usuario y la contraseña coinciden volvemos a cargar la
					// ventana de login.
					File file = new File(ControladorLogin.class.getResource("Disenio/Html/login.html").getFile());
					browser.loadURL(file.toString());
				}
			} catch (SQLException e) {
				System.out.println("SE HA PRODUCIDO UN ERROR CONSULTANDO LA BASE DE DATOS");
				System.out.println("DETALLES: ");
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Clase para obtener los datos del usuario para acceder al correo.
	 */
	public static class getDatosCorreo {

		/**
		 * <String> usuario: Variable que almacena el usuario del cliente.
		 */
		private String usuario;

		/**
		 * <String> contraseña: Variable que almacena la contraseña del cliente.
		 */
		private String contraseña;

		/**
		 * Constructor de la clase que nos permite acceder al javaScript del HTML para
		 * hacer uso del 'localStorage' y recuperar los datos de inicio de sesión.
		 * 
		 * @param <Browser> b: Variable que hace referencia al contexto de la vista.
		 */
		public getDatosCorreo(Browser b) {
			// Este código permite que podamos acceder al localStorage de javaScript.
			b.executeJavaScript("localStorage");
			WebStorage webStorage = b.getLocalWebStorage();

			// Este cógido permite recoger los valores que hayan en el localStorage de
			// javaScript.
			usuario = webStorage.getItem("usuario");
			contraseña = webStorage.getItem("contraseña");
		}

		/**
		 * Método para obtener el usuario del cliente.
		 * 
		 * @return <String> usuario: Dato usuario del cliente.
		 */
		public String getUsuario() {
			return usuario;
		}

		/**
		 * Método para obtener la contraseña del cliente
		 * 
		 * @return <String> contraseña: Dato contraseña del cliente.
		 */
		public String getContraseña() {
			return contraseña;
		}

		/**
		 * Método que permite acceder a los datos de los correos electrónicos.
		 * 
		 * @param <String> destinatario: Variable que indica quien es el destinatario
		 *        del correo.
		 * @param <String> asunto: Variable que indica el asunto que tiene el correo.
		 * @param <String> contenido: Variable que indica el contenido del correo.
		 */
		public void save(String destinatario, String asunto, String contenido) {
			System.out.println("------Cargando Correos------");

			// Enviamos los datos recogidos de la vista a la clase encargada de los correos.
			EnviarMail em = new EnviarMail();
			em.EnviarMail(destinatario, getUsuario(), getUsuario(), getContraseña(), asunto, contenido);
		}
	}

	/**
	 * Clase encargada de comprobar de que los datos del nuevo uduario se guarde en
	 * la base de dato y que no sean datos erróneos.
	 */
	public static class getDatosRegistro {

		/**
		 * Constructor parametrizad.
		 * 
		 * @param <Browser>b: Variable que hace referencia al contexto de la vista.
		 * @param <Conexion>c: Variable que hace permite la conexión con la base de
		 *        datos.
		 */
		public getDatosRegistro(Browser b, Conexion c) {
			browser = b;
			conexion = c;
		}

		/**
		 * Método que permite obtener los datos introducidos pos el usuario desde
		 * javaScript.
		 * 
		 * @param <String> correo: Variable que contiene el correo introducido por el
		 *        usuario.
		 * @param <String> contraseña: Variable que contiene la contraseña introducida
		 *        por el usuario.
		 * @param <String> contraseña2: Variable que contiene la contraseña introducida
		 *        por el usuario.
		 */
		public void save(String correo, String contraseña, String contraseña2) {
			System.out.println("------Comprobamos las credenciales------");

			if (correo.equals("") || contraseña.equals("") || contraseña2.equals("")) {

				JOptionPane.showMessageDialog(null, "No puedes dejar campos vacios", "Error",
						JOptionPane.ERROR_MESSAGE);

			} else if (!contraseña.equals(contraseña2)) {

				JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
				File file = new File(ControladorLogin.class.getResource("Disenio/Html/registrar.html").getFile());
				browser.loadURL(file.toString());

			} else {

				boolean correoValido = false;

				if (correo.endsWith("@hotmail.es") || correo.endsWith("@hotmail.com")) {

					AutentificarCorreo ac = new AutentificarCorreo();

					if (ac.AutentificarCorreo("pop3.live.com", correo, contraseña)) {

						correoValido = true;

					} else {

						JOptionPane.showMessageDialog(null, "Correo no válido", "Error", JOptionPane.ERROR_MESSAGE);

					}

				} else if (correo.endsWith("@gmail.es") || correo.endsWith("@gmail.com")) {

					AutentificarCorreo ac = new AutentificarCorreo();

					if (ac.AutentificarCorreo("pop.gmail.com", correo, contraseña)) {

						correoValido = true;

					} else {

						JOptionPane.showMessageDialog(null, "Correo no válido", "Error", JOptionPane.ERROR_MESSAGE);
					}

				}

				if (correoValido == true) {

					conexion.InsertNewUsuario(correo, contraseña);
					System.out.println("Usuario creado con exito");

				} else {

					File file = new File(ControladorLogin.class.getResource("Disenio/Html/registrar.html").getFile());
					browser.loadURL(file.toString());

				}

			}
		}

	}

}
