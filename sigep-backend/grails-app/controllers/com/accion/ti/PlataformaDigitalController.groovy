package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

public class PlataformaDigitalController{

    UtileriaService utileriaService;
    PlataformaDigitalService plataformaDigitalService;

    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        render(plataformaDigitalService.listarPaginado(paginacionDTO) as JSON);
    }

    def registrar(){
        plataformaDigitalService.registrar(request.JSON);
        render(status: HttpStatus.OK);
    }

    def actualizar(){
        plataformaDigitalService.actualizar(request.JSON);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idPlataformaDigital = request.JSON?.idPlataformaDigital?.toLong()?:0;
        render(plataformaDigitalService.obtener(idPlataformaDigital) as JSON);
    }

    def eliminar(){
        Long idPlataformaDigital = request.JSON?.idPlataformaDigital?.toLong()?:0;
        plataformaDigitalService.eliminar(idPlataformaDigital);
        render(status: HttpStatus.OK);
    }

}