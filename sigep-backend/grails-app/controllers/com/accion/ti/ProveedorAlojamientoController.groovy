package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.proveedorAlojamiento.ProveedorAlojamientoDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

public class ProveedorAlojamientoController{

    UtileriaService utileriaService;
    ProveedorAlojamientoService proveedorAlojamientoService;

    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        RespuestaListaDTO<ProveedorAlojamientoDTO> respuesta = proveedorAlojamientoService.listarPaginado(paginacionDTO);
        render(respuesta as JSON);
    }

    def listarTodos(){
        render(proveedorAlojamientoService.listarTodos() as JSON);
    }

    def registrar(ProveedorAlojamientoDTO proveedorAlojamiento){
        proveedorAlojamientoService.registrar(proveedorAlojamiento);
        render(status: HttpStatus.OK);
    }

    def actualizar(ProveedorAlojamientoDTO proveedorAlojamiento){
        proveedorAlojamientoService.actualizar(proveedorAlojamiento);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idProveedorAlojamiento = request.JSON?.idProveedorAlojamiento?.toLong()?:0;
        ProveedorAlojamientoDTO proveedorAlojamiento = proveedorAlojamientoService.obtener(idProveedorAlojamiento);
        render(proveedorAlojamiento as JSON);
    }

    def eliminar(){
        Long idProveedorAlojamiento = request.JSON?.idProveedorAlojamiento?.toLong()?:0;
        proveedorAlojamientoService.eliminar(idProveedorAlojamiento);
        render(status: HttpStatus.OK);
    }

}