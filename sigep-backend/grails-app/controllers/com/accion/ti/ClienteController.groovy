package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

public class ClienteController{

    UtileriaService utileriaService;
    ClienteService clienteService;

    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        render(clienteService.listarPaginado(paginacionDTO) as JSON);
    }

    def listarTodos(){
        render(clienteService.listarTodos() as JSON);
    }

    def registrar(){
        clienteService.registrar(request.JSON);
        render(status: HttpStatus.OK);
    }

    def actualizar(){
        clienteService.actualizar(request.JSON);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idCliente = request.JSON?.idCliente?.toLong()?:0;
        render(clienteService.obtener(idCliente) as JSON);
    }

    def actualizarEstado(){
        Long idCliente = request.JSON?.idCliente?.toLong()?:0;
        clienteService.actualizarEstado(idCliente);
        render(status: HttpStatus.OK);
    }

}