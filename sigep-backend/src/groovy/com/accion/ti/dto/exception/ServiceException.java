package com.accion.ti.dto.exception;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 1125442070391271558L;
    private final String message;

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }

    public ServiceException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

}
