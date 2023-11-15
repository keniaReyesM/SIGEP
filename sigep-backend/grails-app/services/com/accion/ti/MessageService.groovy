package com.accion.ti

import com.accion.ti.dto.exception.ServiceException
import org.springframework.context.MessageSource
import org.springframework.validation.ObjectError

class MessageService {

    MessageSource messageSource

    String getMessageErrors(def exception ){
        String message = "";
        if( exception instanceof grails.validation.ValidationException ){
            message = exception.errors.allErrors.collect { ObjectError error ->
                return messageSource.getMessage(error, Locale.default)
            }.join("\n")
        }else if(exception instanceof ServiceException){
            message = exception.getMessage();
        } else{
            message = getMessageException( exception.getMessage() )
        }
        return message
    }

    String getMessageException(String messageError){
        return getMessage("default.error.exception", messageError, Locale.default)
    }

    String getMessage(String code, Object... args){
        return messageSource.getMessage(code, args, Locale.default)
    }

    String getErrorUnico(Object... args){
        return getMessage("default.not.unique.message", args);
    }

    String getErrorNoEncontrado(Object... args){
        return getMessage("default.error.not.found", args);
    }

    String getErrorEliminacion(Object... args){
        return getMessage("default.not.deleted.message", args);
    }

    String getErrorDocumentoVacio(Object... args){
        return getMessage("default.error.document.empty", args);
    }
    String getErrorColumnaValorInvalido(Object... args){
        return getMessage("default.error.column.invalid", args);
    }


}