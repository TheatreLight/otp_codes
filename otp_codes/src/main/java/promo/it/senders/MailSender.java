package promo.it.senders;

import promo.it.settings.SettingsManager;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender extends  BaseSender {
    private Session session;

    public MailSender(SettingsManager sm) {
        super(sm);
        session = Session.getInstance(settingsManager.getEMailProps(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(settingsManager.getEMailUserName(),
                        settingsManager.getEMailPassword());
            }
        });
    }

    @Override
    public void send(String code) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(settingsManager.getEMailAddressFrom()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(settingsManager.getEMailAddressTo()));
            message.setSubject("Your OTP Code");
            message.setText("Your verification code is: " + code);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
