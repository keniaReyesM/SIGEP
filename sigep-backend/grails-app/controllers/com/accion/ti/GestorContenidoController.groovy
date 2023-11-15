package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.gestorContenido.GestorContenidoDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

public class GestorContenidoController{

    UtileriaService utileriaService;
    GestorContenidoService gestorContenidoService;

    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        RespuestaListaDTO<GestorContenidoDTO> respuesta = gestorContenidoService.listarPaginado(paginacionDTO);
        render(respuesta as JSON);
    }

    def listarTodos(){
        render(gestorContenidoService.listarTodos() as JSON);
    }

    def registrar(GestorContenidoDTO gestorContenido){
        gestorContenidoService.registrar(gestorContenido);
        render(status: HttpStatus.OK);
    }

    def actualizar(GestorContenidoDTO gestorContenido){
        gestorContenidoService.actualizar(gestorContenido);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idGestorContenido = request.JSON?.idGestorContenido?.toLong()?:0;
        GestorContenidoDTO gestorContenido = gestorContenidoService.obtener(idGestorContenido);
        render(gestorContenido as JSON);
    }

    def eliminar(){
        Long idGestorContenido = request.JSON?.idGestorContenido?.toLong()?:0;
        gestorContenidoService.eliminar(idGestorContenido);
        render(status: HttpStatus.OK);
    }

}