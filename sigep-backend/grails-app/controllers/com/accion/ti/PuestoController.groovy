package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.puesto.PuestoDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

public class PuestoController{

    UtileriaService utileriaService;
    PuestoService puestoService;

    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        RespuestaListaDTO<PuestoDTO> respuesta = puestoService.listarPaginado(paginacionDTO);
        render(respuesta as JSON);
    }
    def listarTodos(){
        render(puestoService.listarTodos() as JSON);
    }

    def registrar(PuestoDTO puesto){
        puestoService.registrar(puesto);
        render(status: HttpStatus.OK);
    }

    def actualizar(PuestoDTO puesto){
        puestoService.actualizar(puesto);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idPuesto = request.JSON?.idPuesto?.toLong()?:0;
        PuestoDTO puesto = puestoService.obtener(idPuesto);
        render(puesto as JSON);
    }

    def eliminar(){
        Long idPuesto = request.JSON?.idPuesto?.toLong()?:0;
        puestoService.eliminar(idPuesto);
        render(status: HttpStatus.OK);
    }

}