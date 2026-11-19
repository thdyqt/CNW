package com.tour.util;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;
    private static final String DEFAULT_USER = "ptd.ntn2907@gmail.com";
    private static final String DEFAULT_PASS = "qfdk pykl ypkq cdds";

    private static String getEmailUser() {
        String envUser = System.getenv("EMAIL_USER");
        if (envUser != null && !envUser.trim().isEmpty()) {
            return envUser.trim();
        }
        return DEFAULT_USER;
    }

    private static String getEmailPass() {
        String envPass = System.getenv("EMAIL_PASS");
        if (envPass != null && !envPass.trim().isEmpty()) {
            return envPass.trim();
        }
        return DEFAULT_PASS;
    }

    public static boolean sendMail(String toEmail, String subject, String htmlContent) {
        if (toEmail == null || toEmail.trim().isEmpty()) {
            System.err.println("[MailUtil] Destination email is null or empty!");
            return false;
        }

        final String user = getEmailUser();
        final String pass = getEmailPass();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", String.valueOf(SMTP_PORT));
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");

        try {
            Session mailSession = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });

            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(user, "DUT Admin", "UTF-8"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail.trim()));
            message.setSubject(subject, "UTF-8");
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("[MailUtil] Email sent successfully to: " + toEmail);
            return true;
        } catch (Exception e) {
            System.err.println("[MailUtil] Failed to send email to: " + toEmail + " - Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void sendMailAsync(String toEmail, String subject, String htmlContent) {
        new Thread(() -> {
            sendMail(toEmail, subject, htmlContent);
        }).start();
    }
}
