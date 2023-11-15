package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.pagination.PaginacionDTO

public class AvanceController{

    UtileriaService utileriaService;
    AvanceService avanceService;


    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        render(avanceService.listarPaginado(paginacionDTO, request.JSON.idTarea) as JSON);
    }

    def registrar(){
        avanceService.registrar(request.JSON);
        render(status: HttpStatus.OK);
    }

    def eliminar(){
        avanceService.eliminar(request.JSON);
        render(status: HttpStatus.OK);
    }

}