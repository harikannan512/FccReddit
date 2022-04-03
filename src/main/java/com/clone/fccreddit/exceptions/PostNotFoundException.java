package com.clone.fccreddit.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String s) {
        super(s);
    }
    
    public PostNotFoundException(String s, Throwable cause) {
        super(s, cause);
    }
    
    public PostNotFoundExeption() {
        super();
    }
}
