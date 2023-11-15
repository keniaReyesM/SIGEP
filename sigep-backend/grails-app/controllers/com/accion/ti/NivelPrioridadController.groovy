package com.accion.ti

import grails.converters.JSON

public class NivelPrioridadController {

    NivelPrioridadService nivelPrioridadService;

    def listarTodos(){
        render(nivelPrioridadService.listarTodos() as JSON);
    }


}