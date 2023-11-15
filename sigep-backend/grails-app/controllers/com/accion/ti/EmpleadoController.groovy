package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.pagination.PaginacionDTO;

public class EmpleadoController{

    UtileriaService utileriaService;
    EmpleadoService empleadoService;

    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        render(empleadoService.listarPaginado(paginacionDTO) as JSON);
    }

    def listarTodosActivos(){
        render(empleadoService.listarTodosActivos() as JSON);
    }

     def listarTodos(){
        render(empleadoService.listarTodos() as JSON);
    }

    def registrar(){
        empleadoService.registrar(request.JSON);
        render(status: HttpStatus.OK);
    }

    def actualizar(){
        empleadoService.actualizar(request.JSON);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idEmpleado = request.JSON?.idEmpleado?.toLong()?:0;
        render(empleadoService.obtener(idEmpleado) as JSON);
    }

    def actualizarEstado(){
        Long idEmpleado = request.JSON?.idEmpleado?.toLong()?:0;
        empleadoService.actualizarEstado(idEmpleado);
        render(status: HttpStatus.OK);
    }

}