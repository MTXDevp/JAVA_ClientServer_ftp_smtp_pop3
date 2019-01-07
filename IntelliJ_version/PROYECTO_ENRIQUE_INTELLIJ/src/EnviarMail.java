import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EnviarMail {


    public void EnviarMail(String toAddress, String fromAddress, String userName, String userPassword,String smtpHost, String emailSubject,String emailBody) {

        System.out.println("Constructor enviar mail");
        String to = toAddress;
        String from = fromAddress;
        final String username = userName;
        final String password = userPassword;
        String host = smtpHost;
        Properties props = new Properties();

        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.starttls.enable",true);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        SimpleMailAuthenticator authenticator = new SimpleMailAuthenticator(username, password);
        Session session = Session.getInstance(props, authenticator);
        session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(emailSubject);
            message.setText(emailBody);
            Transport.send(message);
            System.out.println("Mensaje enviado con exito....");
        } catch (MessagingException e) {
            System.out.println("Se ha producido un error mandando el correo");
            throw new RuntimeException(e);
        }
    }

    static class SimpleMailAuthenticator extends Authenticator {


        String userName;
        String password;
        PasswordAuthentication authentication;

        public SimpleMailAuthenticator(String userName,String password) {
            super();
            this.userName = userName;
            this.password = password;
            authentication = new PasswordAuthentication(userName, password);
        }

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return authentication;
        }
    }
}
