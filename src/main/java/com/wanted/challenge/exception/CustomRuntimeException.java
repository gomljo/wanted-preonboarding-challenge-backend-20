package com.wanted.challenge.exception;

public abstract class CustomRuntimeException extends RuntimeException{

    public abstract String getDescription();
    public abstract String getErrorCode();
}
