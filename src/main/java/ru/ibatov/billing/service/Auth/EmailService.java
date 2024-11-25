package ru.ibatov.billing.service.Auth;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.ibatov.billing.entity.People.Code;
import ru.ibatov.billing.repos.People.CodeRepository;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final CodeRepository codeRepo;

    @Value("${email.password}")
    private String password_mail;

    @Value("${email.from}")
    private String from;

    public void sendCodeToEmail(String to) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.mail.ru");
        properties.put("mail.smtp.post", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(
                properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password_mail);
                    }
                }
        );
        session.setDebug(true);
        try {
            String code = generateCode();
            String text = "Ваш код подтверждения, который действует 5 минут: " + code;
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipients(Message.RecipientType.TO, to);
            message.setSubject("Вход в Альфа-биллинг");
            message.setText(text);
            Transport.send(message);
            codeRepo.save(makeObjectCode(to,code));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String generateCode() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int CODE_LENGTH = 6;
        SecureRandom RANDOM = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }

    private Code makeObjectCode(String email, String code){
        return Code.builder()
                .email(email)
                .code(code)
                .expireTime((Instant.now().plus(Duration.ofMinutes(5))))
                .build();
    }
}