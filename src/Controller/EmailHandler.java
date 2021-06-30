package Controller;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import Model.*;

/**
 * The Class EmailHandler is used to send emails to the participants of an event
 * @author ZuHyunLee97
 */

public class EmailHandler {

    /**creating new session object to hold host data */
    Session newSession = null;

    /**creating new message object to hold email content */
    MimeMessage mimeMessage = null;

    /**
     * Sets up email server, creates a email draft and sends the email to all participants
     *
     * @param User 
     */
    public static void sendRegistrationMail(String registrationCode, String mailAddress ) {
        EmailHandler mail = new EmailHandler();
        mail.setupServerProperties();
        mail.draftMail(registrationCode, mailAddress);
        mail.sendMail();
    }

    /**
     * Sets up Google's SMTP server for email session
     */
    void setupServerProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        newSession = Session.getDefaultInstance(properties, null);
    }

    /**
     * Drafts the email depending on event status which decides from different mail layouts
     *
     * @param event event of subject
     * @param status status of event
     */
    private void draftMail(String registrationCode, String mailAddress) {
        mimeMessage = new MimeMessage(newSession);
        try{
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailAddress));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        try {
            mimeMessage.setSubject("MemTool: Please confirm your registration");
            mimeMessage.setText(EmailHandlerHTML.setupText(registrationCode),null, "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    /**
     * Logs into Gmail account and sends email to all participants
     */
    public  void sendMail() {
        String fromUser = "progexmevius2021@gmail.com";
        String fromUserPassword = "progex2021";
        String mailHost = "smtp.gmail.com";
        Transport transport = null;
        try {
            transport = newSession.getTransport("smtp");
            transport.connect(mailHost, fromUser, fromUserPassword);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
            System.out.println("Mail successfully sent");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String testcode ="test";
        String testmail ="hyun.lee2@stud.fra-uas.de";
        sendRegistrationMail(testcode, testmail);
      }
}

