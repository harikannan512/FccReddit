package com.clone.fccreddit.exceptions;

import org.springframework.mail.MailException;

public class SubredditNotFoundException extends RuntimeException{
    public SubredditNotFoundException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SubredditNotFoundException(Exception exception) {
        super(exception);
    }

    public SubredditNotFoundException(String exMessage) {
        super(exMessage);
    }
}
