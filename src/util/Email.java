package util;

import dao.PersonDao;
import model.Person;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {

    private static Person sender = new PersonDao().findByEmail("ctests.service@gmail.com");

    private static Session createSessionMail() {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.port", 465);

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender.getEmail(), sender.getPassword());
                    }
                });

        return session;
    }

    public static void sendEmail(String subject, String message, String recipient) throws MessagingException {
        Message msg = new MimeMessage(createSessionMail());
        msg.setFrom(new InternetAddress(sender.getEmail()));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        msg.setSubject(subject);
        msg.setContent(message, "text/html; charset=utf-8");
        // enviar a mensagem
        Transport.send(msg);
    }

}
