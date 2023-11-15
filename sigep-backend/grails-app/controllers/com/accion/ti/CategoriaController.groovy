package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.categoria.CategoriaDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

public class CategoriaController{

    UtileriaService utileriaService;
    CategoriaService categoriaService;

    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        RespuestaListaDTO<CategoriaDTO> respuesta = categoriaService.listarPaginado(paginacionDTO);
        render(respuesta as JSON);
    }
    
    def listarTodos(){
        render(categoriaService.listarTodos() as JSON);
    }

    def registrar(CategoriaDTO categoria){
        categoriaService.registrar(categoria);
        render(status: HttpStatus.OK);
    }

    def actualizar(CategoriaDTO categoria){
        categoriaService.actualizar(categoria);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idCategoria = request.JSON?.idCategoria?.toLong()?:0;
        CategoriaDTO categoria = categoriaService.obtener(idCategoria);
        render(categoria as JSON);
    }

    def eliminar(){
        Long idCategoria = request.JSON?.idCategoria?.toLong()?:0;
        categoriaService.eliminar(idCategoria);
        render(status: HttpStatus.OK);
    }

}