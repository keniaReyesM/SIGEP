package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.tipoPlataformaDigital.TipoPlataformaDigitalDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

public class TipoPlataformaDigitalController{

    UtileriaService utileriaService;
    TipoPlataformaDigitalService tipoPlataformaDigitalService;

    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        RespuestaListaDTO<TipoPlataformaDigitalDTO> respuesta = tipoPlataformaDigitalService.listarPaginado(paginacionDTO);
        render(respuesta as JSON);
    }

    def listarTodos(){
        render(tipoPlataformaDigitalService.listarTodos() as JSON);
    }

    def registrar(TipoPlataformaDigitalDTO tipoPlataformaDigital){
        tipoPlataformaDigitalService.registrar(tipoPlataformaDigital);
        render(status: HttpStatus.OK);
    }

    def actualizar(TipoPlataformaDigitalDTO tipoPlataformaDigital){
        tipoPlataformaDigitalService.actualizar(tipoPlataformaDigital);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idTipoPlataformaDigital = request.JSON?.idTipoPlataformaDigital?.toLong()?:0;
        TipoPlataformaDigitalDTO tipoPlataformaDigital = tipoPlataformaDigitalService.obtener(idTipoPlataformaDigital);
        render(tipoPlataformaDigital as JSON);
    }

    def eliminar(){
        Long idTipoPlataformaDigital = request.JSON?.idTipoPlataformaDigital?.toLong()?:0;
        tipoPlataformaDigitalService.eliminar(idTipoPlataformaDigital);
        render(status: HttpStatus.OK);
    }

}