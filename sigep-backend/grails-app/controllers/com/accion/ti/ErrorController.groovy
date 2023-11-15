package com.accion.ti


import org.apache.commons.logging.LogFactory
import org.springframework.http.HttpStatus

public class ErrorController{

    private static final LOGGER = LogFactory.getLog(this)

    UtileriaService utileriaService;

    def index(){
        String message = request?.exception?.message?:"Error interno";
        render(status: HttpStatus.BAD_REQUEST, text: message);
    }
}