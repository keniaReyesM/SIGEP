package com.accion.ti

import grails.converters.JSON;
import com.accion.ti.dto.exception.ServiceException;

public class PermisoController{

    PermisoService permisoService;

    def listarTodos(){
        render(permisoService.listarTodos() as JSON);
    }


}