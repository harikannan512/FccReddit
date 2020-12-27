package com.clone.fccreddit.service;

import com.clone.fccreddit.exceptions.SpringRedditException;
import com.clone.fccreddit.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    void sendMail(NotificationEmail notificationEmail){
        MimeMessagePreparator messagePrep = mimeMessage -> {
           MimeMessageHelper messageHelper = new MimeMessageHelper((mimeMessage));
           messageHelper.setFrom("springReddit@email.com");
           messageHelper.setTo(notificationEmail.getRecipient());
           messageHelper.setSubject(notificationEmail.getSubject());
           messageHelper.setText(notificationEmail.getBody());
        };
        try{
            mailSender.send(messagePrep);
            log.info("Activation Email Sent !!");
        } catch (MailException e){
            e.printStackTrace();
            throw new SpringRedditException("Exception occurred when sending mail to recipient : " + notificationEmail.getRecipient(), e);
        }
    }
}
