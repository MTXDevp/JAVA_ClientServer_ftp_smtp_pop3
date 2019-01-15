package Peval4.ServidorFtpStmpPop3Maven;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Clase encargada de enviar los correos.
 * 
 * @author Rafael Valls.
 * @version 1.0.
 */
public class EnviarMail {

	/**
	 * Constructor que permite el envio de correos.
	 * 
	 * @param <String> toAddress: Variable que indica para quien es el correo.
	 * @param <String> fromAddress: Variable que indica quien manda el correo.
	 * @param <String> userName: Variable que contiene el usuario.
	 * @param <String> userPassword: Variable que contiene la contraseña.
	 * @param <String> emailSubject: Variable que indica el asunto del correo.
	 * @param <String> emailBody: Variable que indica el cuerpo del correo.
	 */
	public void EnviarMail(String toAddress, String fromAddress, String userName, String userPassword,
			String emailSubject, String emailBody) {
		String to = toAddress;
		String from = fromAddress;
		final String username = userName;
		final String password = userPassword;

		Properties props = new Properties();

		// Dependiendo del host del correo.
		if (from.endsWith("@gmail.com") || from.endsWith("gmail.es")) {
			props.put("mail.smtp.host", "smtp.gmail.com");
		} else if (from.endsWith("@hotmail.com") || from.endsWith("@hotmail.es")) {
			props.put("mail.smtp.host", "smtp.live.com");
		}

		// Configuramos las propiedades del correo.
		props.put("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtps.ssl.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", 587);
		SimpleMailAuthenticator authenticator = new SimpleMailAuthenticator(username, password);
		Session session = Session.getInstance(props, authenticator);

		try {
			// Modificamos y enviamos el correo
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(emailSubject);
			message.setText(emailBody);
			Transport.send(message);
			System.out.println("Mensaje enviado con exito....");

		} catch (MessagingException e) {
			System.out.println("Se ha producido un error mandando el correo");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Clase que verifica si un correo es no esta falsificado, por tema de seguridad
	 * para evitar fishing.
	 */
	static class SimpleMailAuthenticator extends Authenticator {

		/**
		 * <String> usuario: Variable que hace referencia al usuario del cliente.
		 */
		String usuario;
		/**
		 * <String> contraseña: Variable que hace referencia a la contraseña del
		 * cliente.
		 */
		String contraseña;
		/**
		 * <PasswordAuthentication> autentificacion: Variable que comprueba la
		 * uatentificacion del correo.
		 */
		PasswordAuthentication autentificacion;

		/**
		 * Método que verifica el correo.
		 * 
		 * @param usuario
		 * @param contraseña
		 */
		public SimpleMailAuthenticator(String usuario, String contraseña) {
			super();
			this.usuario = usuario;
			this.contraseña = contraseña;
			autentificacion = new PasswordAuthentication(usuario, contraseña);
		}

		@Override
		/**
		 * Método para obtener la autentificacion del correo.
		 */
		public PasswordAuthentication getPasswordAuthentication() {
			return autentificacion;
		}
	}
}
