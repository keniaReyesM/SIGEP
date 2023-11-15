package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.tecnologiaDesarrollo.TecnologiaDesarrolloDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

public class TecnologiaDesarrolloController{

    UtileriaService utileriaService;
    TecnologiaDesarrolloService tecnologiaDesarrolloService;

    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        RespuestaListaDTO<TecnologiaDesarrolloDTO> respuesta = tecnologiaDesarrolloService.listarPaginado(paginacionDTO);
        render(respuesta as JSON);
    }

    def listarTodos(){
        render(tecnologiaDesarrolloService.listarTodos() as JSON);
    }

    def registrar(TecnologiaDesarrolloDTO tecnologiaDesarrollo){
        tecnologiaDesarrolloService.registrar(tecnologiaDesarrollo);
        render(status: HttpStatus.OK);
    }

    def actualizar(TecnologiaDesarrolloDTO tecnologiaDesarrollo){
        tecnologiaDesarrolloService.actualizar(tecnologiaDesarrollo);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idTecnologiaDesarrollo = request.JSON?.idTecnologiaDesarrollo?.toLong()?:0;
        TecnologiaDesarrolloDTO tecnologiaDesarrollo = tecnologiaDesarrolloService.obtener(idTecnologiaDesarrollo);
        render(tecnologiaDesarrollo as JSON);
    }

    def eliminar(){
        Long idTecnologiaDesarrollo = request.JSON?.idTecnologiaDesarrollo?.toLong()?:0;
        tecnologiaDesarrolloService.eliminar(idTecnologiaDesarrollo);
        render(status: HttpStatus.OK);
    }

}