package com.clone.fccreddit.exceptions;

public class UsernameNotFoundException extends RuntimeException {

    public UsernameNotFoundException(String s, Exception e) { super(s,e); }

    public UsernameNotFoundException(Exception e) { super(e); }


    public UsernameNotFoundException(String s) { super(s); }
}
