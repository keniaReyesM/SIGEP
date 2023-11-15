package com.accion.ti

import com.accion.ti.dto.pagination.PaginacionDTO
import com.accion.ti.dto.response.RespuestaListaDTO
import com.accion.ti.dto.rol.RolDTO
import grails.converters.JSON
import org.springframework.http.HttpStatus

public class RolController{

    UtileriaService utileriaService;
    RolService rolService;

    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        RespuestaListaDTO<RolDTO> respuesta = rolService.listarPaginado(paginacionDTO);
        render(respuesta as JSON);
    }
    def listarTodos(){
        render(rolService.listarTodos() as JSON);
    }

    def registrar(RolDTO rol){
        rolService.registrar(rol);
        render(status: HttpStatus.OK);
    }

    def actualizar(RolDTO rol){
        rolService.actualizar(rol);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idRol = request.JSON?.idRol?.toLong()?:0;
        RolDTO rol = rolService.obtener(idRol);
        render(rol as JSON);
    }

    def eliminar(){
        Long idRol = request.JSON?.idRol?.toLong()?:0;
        rolService.eliminar(idRol);
        render(status: HttpStatus.OK);
    }

}