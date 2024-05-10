package com.avanzada.unilocal.Unilocal.serviceImplements;

import com.avanzada.unilocal.Unilocal.dto.EmailDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author Juanes Cardona
 */
@Service
public class EmailService implements com.avanzada.unilocal.Unilocal.interfaces.EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(EmailDTO emailDTO) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject(emailDTO.asunto());
        // Construir el cuerpo del correo electrónico con HTML
        String htmlBody = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; }" +
                ".container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                ".header { background-color: #007bff; color: #fff; padding: 20px; text-align: center; }" +
                ".content { padding: 20px; }" +
                ".footer { background-color: #f8f9fa; color: #000; padding: 20px; text-align: center; }" +
                ".button { background-color: #007bff; color: #fff; padding: 10px 20px; text-decoration: none; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1>¡Hola!</h1>" +
                "</div>" +
                "<div class='content'>" +
                "<p> "+ emailDTO.body() + "</p>" +
                "<p>Atentamente,<br/>El equipo de UniLocal</p>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>Este es un correo electrónico automatizado. Por favor, no responda a este mensaje.</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
        helper.setText(htmlBody, true);
        helper.setTo(emailDTO.destinatario());
        helper.setFrom("no_reply@dominio.com");

        javaMailSender.send(message);
    }
}
