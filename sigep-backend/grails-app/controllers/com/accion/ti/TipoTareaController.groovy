package com.accion.ti

import grails.converters.JSON

public class TipoTareaController {

    TipoTareaService tipoTareaService;

    def listarTodos(){
        render(tipoTareaService.listarTodos() as JSON);
    }


}