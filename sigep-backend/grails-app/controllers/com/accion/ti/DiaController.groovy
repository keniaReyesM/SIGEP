package com.accion.ti

import grails.converters.JSON;
import org.springframework.http.HttpStatus;
import com.accion.ti.dto.exception.ServiceException;

public class DiaController{

    UtileriaService utileriaService;
    DiaService diaService;

    def listarTodos(){
        render(diaService.listarTodos() as JSON);
    }


}