package kr.ac.univ.util;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class EmailUtil {
    public static void gmailSend(String sender, String password, List<String> receivers, String subject, String contents) {
        // SMTP 서버 정보 설정
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // 메일 계정 로그인 사용자 정보
        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(sender));
            message.setHeader("content-Type", "text/html");
            //메일 제목
            message.setSubject(subject);
            // 메일 내용
            message.setContent(contents, "text/html; charset=UTF-8");

            InternetAddress[] internetAddress = new InternetAddress[receivers.size()];
            // 수신 메일 주소
            for (int i = 0; i < receivers.size(); i++) {
                internetAddress[i] = new InternetAddress(receivers.get(i));
            }
            message.addRecipients(Message.RecipientType.TO, internetAddress);

            // 메일 전송
            Transport.send(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
