package com.avanzada.unilocal.Unilocal.serviceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Juanes Cardona
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Method to send an email
     *
     * @param destinatario User Email
     * @param asunto Email subject
     * @param mensaje Email body
     */
    public void sendmail(String destinatario, String asunto, String mensaje) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(destinatario);
        mail.setSubject(asunto);
        mail.setText(mensaje);

        javaMailSender.send(mail);
    }
}
