package Peval4.ServidorFtpStmpPop3Maven;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EnviarMail {


    public void EnviarMail(String toAddress, String fromAddress, String userName, String userPassword, String emailSubject,String emailBody) {

        System.out.println("Constructor enviar mail");
        String to = toAddress;
        String from = fromAddress;
        final String username = userName;
        final String password = userPassword;
        //String host = smtpHost;


        Properties props = new Properties();

        //DEPENDIENDO DEL DOMINIO DE CORREO LO ENVIAREMO A UN HOST U OTRO

        if(from.endsWith("@gmail.com") || from.endsWith("gmail.es")){

            props.put("mail.smtp.host", "smtp.gmail.com");
            System.out.println("entro en gmail");

        }else if(from.endsWith("@hotmail.com") || from.endsWith("@hotmail.es")){

            props.put("mail.smtp.host", "smtp.live.com");
            System.out.println("entro en hotmail");
        }

        props.put("mail.smtp.starttls.enable",true);
        props.put("mail.smtp.auth",true);
        props.put("mail.smtp.port", 587);

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
            e.printStackTrace();
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
