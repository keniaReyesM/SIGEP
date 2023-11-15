package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.area.AreaDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

public class AreaController{

    UtileriaService utileriaService;
    AreaService areaService;

    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        RespuestaListaDTO<AreaDTO> respuesta = areaService.listarPaginado(paginacionDTO);
        render(respuesta as JSON);
    }

    def listarTodos(){
        render(areaService.listarTodos() as JSON);
    }

    def registrar(AreaDTO area){
        areaService.registrar(area);
        render(status: HttpStatus.OK);
    }

    def actualizar(AreaDTO area){
        areaService.actualizar(area);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idArea = request.JSON?.idArea?.toLong()?:0;
        AreaDTO area = areaService.obtener(idArea);
        render(area as JSON);
    }

    def eliminar(){
        Long idArea = request.JSON?.idArea?.toLong()?:0;
        areaService.eliminar(idArea);
        render(status: HttpStatus.OK);
    }

}