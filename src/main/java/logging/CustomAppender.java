package logging;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by smoldyrev on 14.02.17.
 */
public class CustomAppender extends AppenderSkeleton {

    private ArrayList<LoggingEvent> eventsList = new ArrayList();
    private String filePath = "src/main/resources/myLog.log";
    private CustomLayout cl = new CustomLayout();

    public ArrayList<LoggingEvent> getEventsList() {
        return eventsList;
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {

        eventsList.add(loggingEvent);
        String logText = cl.format(loggingEvent);

        try {
            Files.write(Paths.get(filePath), logText.getBytes(), StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.out.println(e);
        }
        sendEmail(logText);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    private void sendEmail(String text) {
        final String username = "e.smoldyrev.stc@innopolis.ru";
        final String password = "";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "mail.innopolis.ru");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("e.smoldyrev.stc@innopolis.ru"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("e.smoldyrev.stc@innopolis.ru"));
            message.setSubject("Logging CarShop");
            message.setText(text);
;
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
