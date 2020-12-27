package com.clone.fccreddit.exceptions;

import org.springframework.mail.MailException;

public class SpringRedditException extends RuntimeException {
    public SpringRedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringRedditException(String exMessage, MailException e) {
        super(exMessage);
    }

    public SpringRedditException(String exMessage) {
        super(exMessage);
    }
}
