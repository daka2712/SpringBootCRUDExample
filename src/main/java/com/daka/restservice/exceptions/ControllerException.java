package com.daka.restservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ControllerException extends RuntimeException {
    public ControllerException()    {
        super();
    }
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }
    public ControllerException(String message) {
        super(message, null);
    }
    public ControllerException(Throwable cause) {
        super(cause);
    }
}
