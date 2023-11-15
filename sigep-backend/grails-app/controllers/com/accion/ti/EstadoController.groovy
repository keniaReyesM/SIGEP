package com.accion.ti

import com.accion.ti.enums.TipoEstadoEnum
import grails.converters.JSON

public class EstadoController {

    EstadoService estadoService;

    def listarTipoTarea(){
        String nombreEstado = TipoEstadoEnum.TAREA.getValor();
        render(estadoService.listarPorTipo(nombreEstado) as JSON);
    }
    def listarTipoGeneral(){
        String nombreEstado = TipoEstadoEnum.GENERAL.getValor();
        render(estadoService.listarPorTipo(nombreEstado) as JSON);
    }


}