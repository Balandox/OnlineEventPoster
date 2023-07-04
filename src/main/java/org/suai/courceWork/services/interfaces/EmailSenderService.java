package org.suai.courceWork.services.interfaces;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {

    void sendEmail(SimpleMailMessage simpleMailMessage);

}
