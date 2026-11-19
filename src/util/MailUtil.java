package util;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
    private static String getSmtpHost() {
        return EnvUtil.get("EMAIL_HOST", "smtp.gmail.com");
    }

    private static int getSmtpPort() {
        return EnvUtil.getInt("EMAIL_PORT", 587);
    }

    private static String getEmailUser() {
        return EnvUtil.get("EMAIL_USER", "ptd.ntn2907@gmail.com");
    }

    private static String getEmailPass() {
        return EnvUtil.get("EMAIL_PASS", "qfdk pykl ypkq cdds");
    }

    public static boolean sendMail(String toEmail, String subject, String htmlContent) {
        if (toEmail == null || toEmail.trim().isEmpty()) {
            System.err.println("[MailUtil] Destination email is null or empty!");
            return false;
        }

        final String user = getEmailUser();
        final String pass = getEmailPass();
        final String host = getSmtpHost();
        final int port = getSmtpPort();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");

        try {
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user, "28Tech Travel"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject, "UTF-8");
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            return true;
        } catch (Exception e) {
            System.err.println("[MailUtil] Loi khi gui email toi " + toEmail + ": " + e.getMessage());
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
