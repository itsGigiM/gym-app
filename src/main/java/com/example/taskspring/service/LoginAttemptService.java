package com.example.taskspring.service;

public interface LoginAttemptService {

    public void loginFailed(final String key);
    public boolean isBlocked();

}

