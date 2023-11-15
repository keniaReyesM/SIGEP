package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.responsableCompra.ResponsableCompraDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

public class ResponsableCompraController{

    UtileriaService utileriaService;
    ResponsableCompraService responsableCompraService;

    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        RespuestaListaDTO<ResponsableCompraDTO> respuesta = responsableCompraService.listarPaginado(paginacionDTO);
        render(respuesta as JSON);
    }

    def listarTodos(){
        render(responsableCompraService.listarTodos() as JSON);
    }

    def registrar(ResponsableCompraDTO responsableCompra){
        responsableCompraService.registrar(responsableCompra);
        render(status: HttpStatus.OK);
    }

    def actualizar(ResponsableCompraDTO responsableCompra){
        responsableCompraService.actualizar(responsableCompra);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idResponsableCompra = request.JSON?.idResponsableCompra?.toLong()?:0;
        ResponsableCompraDTO responsableCompra = responsableCompraService.obtener(idResponsableCompra);
        render(responsableCompra as JSON);
    }

    def eliminar(){
        Long idResponsableCompra = request.JSON?.idResponsableCompra?.toLong()?:0;
        responsableCompraService.eliminar(idResponsableCompra);
        render(status: HttpStatus.OK);
    }

}