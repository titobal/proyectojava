package modelo.bean;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EnviarCorreo {

    public static void enviarCorreo(String destino, String asunto, String mensaje){
        String servidorSMTP = "smtp.gmail.com";
        String puerto = "587";
        String usuario = "cristobal.dz.a@gmail.com";
        String password = "contrasenablablabla";

        Properties props = new Properties();

        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", servidorSMTP);
        props.put("mail.smtp.port", puerto);

        Session session = Session.getInstance(props, null);

        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    destino));
            message.setSubject(asunto);
            message.setSentDate(new Date());
            message.setText(mensaje);

            Transport tr = session.getTransport("smtp");
            tr.connect(servidorSMTP, usuario, password);
            message.saveChanges();
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();

        } catch (MessagingException e) {
            //e.printStackTrace();
        }
    }
}