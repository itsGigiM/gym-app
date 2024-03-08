package com.example.taskspring.utils;

import javax.naming.AuthenticationException;

public interface Authenticator {
    public void authenticate(String username, String password) throws AuthenticationException;
}
