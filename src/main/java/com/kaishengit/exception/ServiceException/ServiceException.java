package com.kaishengit.exception.ServiceException;

public class ServiceException extends RuntimeException {

    public ServiceException(){}
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    public ServiceException(String message, Throwable throwable) {
        super(message,throwable);
    }
}
