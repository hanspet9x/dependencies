package net;

import javax.mail.*;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Mailer{


    private String host = "twentytwo.qservers.net"; //"smtp.gmail.com";
    private int port = 465;
    private boolean hasSSLEnabled = true;
    private boolean hasAuth = true;
    private Properties properties;
    private String username;
    private String password;
    private String from;
    private String to;
    private String [] tos;
    private String subject;
    private String message;
    private boolean isMultiple = false;
    private boolean isHTML = false;


    public Mailer(String from, String to, String subject, String message) {
        isMultiple = false;
        this.to = to;
        this.common(from, subject, message);
    }

    public Mailer(String from, String [] to, String subject, String message) {
        isMultiple = true;
        this.tos = to;
        this.common(from, subject, message);
    }

    private void common(String from, String subject, String message){
        this.from = from;
        this.subject = subject;
        this.message = message;
        properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", String.valueOf(port));
        properties.put("mail.smtp.ssl.enable", String.valueOf(hasSSLEnabled));
        properties.put("mail.smtp.auth", String.valueOf(hasAuth));
    }

    public void testMail(String username, String password) throws MessagingException {
        this.username = username;
        this.password = password;

        Session session = getSession();
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(this.from);
        InternetAddress [] internetAddresses = getAddresses();

//        msg.setRecipients(Message.RecipientType.TO, internetAddresses);
        msg.setSubject("");
        msg.setText("Test");

        Transport transport = session.getTransport();
        transport.addTransportListener(new TransportListener() {
            @Override
            public void messageDelivered(TransportEvent transportEvent) {
                System.out.println("sent");
            }

            @Override
            public void messageNotDelivered(TransportEvent transportEvent) {
                System.out.println("not delivered.");
            }

            @Override
            public void messagePartiallyDelivered(TransportEvent transportEvent) {
                System.out.println("patially delivered.");
            }
        });
        transport.connect();
        transport.sendMessage(msg, internetAddresses);

    }
    public void sendMessage(String username, String password) throws MessagingException {
        this.username = username;
        this.password = password;
        Transport.send(getMimeMessage(getSession()));
    }

    public void sendAttachment(String username, String password, File file) throws MessagingException, IOException {
        this.username = username;
        this.password = password;
        Transport.send(getMultipart(getSession(), file));
    }

    private Session getSession(){
        return Session.getInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private MimeMessage getMimeMessage(Session session) throws MessagingException {

        return getConditionals(createMimeMessage(session));
    }

    private MimeMessage getMultipart(Session session, File file) throws MessagingException, IOException {

        Multipart multipart = new MimeMultipart();

        MimeBodyPart msgPart = new MimeBodyPart();
        MimeBodyPart filePart = new MimeBodyPart();

        filePart.attachFile(file);
        if(isHTML){
            msgPart.setContent(message, "text/html");
        }else{
            msgPart.setText(message);
        }

        multipart.addBodyPart(msgPart);
        multipart.addBodyPart(filePart);
        MimeMessage mimeMessage = createMimeMessage(session);
        if (!isMultiple) {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        } else {
            mimeMessage.setRecipients(Message.RecipientType.TO, getAddresses());
        }
        mimeMessage.setContent(multipart);
        return mimeMessage;
    }

    private MimeMessage createMimeMessage(Session session) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(from);
        mimeMessage.setSubject(subject);
        return mimeMessage;
    }

    private MimeMessage getConditionals(MimeMessage mimeMessage) throws MessagingException {
        if (!isMultiple) {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        } else {
            mimeMessage.setRecipients(Message.RecipientType.TO, getAddresses());
        }
        if(isHTML){
            mimeMessage.setContent(message, "text/html");
        }else{
            mimeMessage.setText(message);
        }
        return mimeMessage;
    }

    private InternetAddress [] getAddresses (){
       return Arrays.stream(tos).map(to -> {
            try {
                return new InternetAddress(to);
            } catch (AddressException e) {
                e.printStackTrace();
                return null;
            }
        }).toArray(InternetAddress[]::new);

    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isHasSSLEnabled() {
        return hasSSLEnabled;
    }

    public void setHasSSLEnabled(boolean hasSSLEnabled) {
        this.hasSSLEnabled = hasSSLEnabled;
    }

    public boolean isHasAuth() {
        return hasAuth;
    }

    public void setHasAuth(boolean hasAuth) {
        this.hasAuth = hasAuth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String[] getTos() {
        return tos;
    }

    public void setTos(String[] tos) {
        this.tos = tos;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }

    public boolean isHTML() {
        return isHTML;
    }

    public void setHTML(boolean HTML) {
        isHTML = HTML;
    }
}
