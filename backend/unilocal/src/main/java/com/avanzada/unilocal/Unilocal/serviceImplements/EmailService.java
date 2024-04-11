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
        helper.setText(emailDTO.body(), true);
        helper.setTo(emailDTO.destinatario());
        helper.setFrom("no_reply@dominio.com");

        javaMailSender.send(message);
    }
}
