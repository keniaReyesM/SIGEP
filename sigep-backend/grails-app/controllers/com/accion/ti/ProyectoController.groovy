package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

public class ProyectoController{

    UtileriaService utileriaService;
    ProyectoService proyectoService;
    ProyectoExcelService proyectoExcelService;


    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        render(proyectoService.listarPaginado(paginacionDTO) as JSON);
    }

    def listarPaginadoProductividadGeneral(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        render(proyectoService.listarPaginadoProductividadGeneral(paginacionDTO) as JSON);
    }

    def listarTodos(){
        render(proyectoService.listarTodos() as JSON);
    }

    def registrar(){
        proyectoService.registrar(request.JSON);
        render(status: HttpStatus.OK);
    }

    def actualizar(){
        proyectoService.actualizar(request.JSON);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idProyecto = request.JSON?.idProyecto?.toLong()?:0;
        render(proyectoService.obtener(idProyecto) as JSON);
    }

    def actualizarEstado(){
        Long idProyecto = request.JSON?.idProyecto?.toLong()?:0;
        proyectoService.actualizarEstado(idProyecto);
        render(status: HttpStatus.OK);
    }

    def listarEmpleadosPorProyecto(){
        Long idProyecto = request.JSON?.idProyecto?.toLong()?:0;
        render(proyectoService.listarEmpleadosPorProyecto(idProyecto) as JSON);
    }

    def obtenerInformacionGraficaCircular(){
        Long idProyecto = request.JSON?.idProyecto?.toLong()?:0;
        render(proyectoService.obtenerInformacionGraficaCircular(idProyecto) as JSON);
    }
    
    def obtenerInformacionGeneral(){
        Long idProyecto = request.JSON?.idProyecto?.toLong()?:0;
        render(proyectoService.obtenerInformacionGeneral(idProyecto) as JSON);
    }


    def listarPaginadoProductividadEmpleado(){
        
        Long idEmpleado = request.JSON?.idEmpleado?.toLong()?:0;
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);


        def paginacion =  proyectoService.listarPaginadoProductividadEmpleado(paginacionDTO, idEmpleado);
        def totalHorasTrabajadas = proyectoService.conteoHorasTrabajadas(idEmpleado);


        def respuesta = [
            totalHorasTrabajadas: totalHorasTrabajadas,
            total: paginacion.total,
            elementos: paginacion.elementos
        ];
                
        render( respuesta as JSON);
    }

    def listarPaginadoProductividadProyecto(){
        
        Long idProyecto = request.JSON?.idProyecto?.toLong()?:0;
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
    
        render( proyectoService.listarPaginadoProductividadProyecto(paginacionDTO, idProyecto) as JSON);
    }

    def generarReporteProductividadEmpleado(){
        
        String path = servletContext.getRealPath("/");
        Long idEmpleado = request.JSON?.idEmpleado?.toLong()?:0;
        String nombreArchivo = request.JSON.nombreArchivo?:"documento.xlsx";

        byte[] reporte = proyectoExcelService.generarReporteProductividadEmpleado(idEmpleado, path);
        
        response.setHeader("Content-disposition", "attachment;filename=\"${nombreArchivo}\";")
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        response.setCharacterEncoding("UTF-8")
        response.outputStream << reporte

    }

    def generarReporteProductividadProyecto(){
        
        String path = servletContext.getRealPath("/");
        Long idProyecto = request.JSON?.idProyecto?.toLong()?:0;
        String nombreArchivo = request.JSON.nombreArchivo?:"documento.xlsx";

        byte[] reporte = proyectoExcelService.generarReporteProductividadProyecto(idProyecto, path);
        
        response.setHeader("Content-disposition", "attachment;filename=\"${nombreArchivo}\";")
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        response.setCharacterEncoding("UTF-8")
        response.outputStream << reporte

    }
}