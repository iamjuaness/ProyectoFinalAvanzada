package com.avanzada.unilocal.Unilocal.interfaces;

import com.avanzada.unilocal.Unilocal.dto.EmailDTO;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmail(EmailDTO emailDTO) throws MessagingException;
}
