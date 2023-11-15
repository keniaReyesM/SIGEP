package com.accion.ti

import grails.converters.JSON;
import org.springframework.http.HttpStatus;
import com.accion.ti.dto.exception.ServiceException;

public class TipoTelefonoController{

    UtileriaService utileriaService;
    TipoTelefonoService tipoTelefonoService;

    def listarTodos(){
        render(tipoTelefonoService.listarTodos() as JSON);
    }


}