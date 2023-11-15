package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO
// import org.springframework.web.multipart.MultipartFile;

public class TareaController{

    UtileriaService utileriaService;
    TareaExcelService tareaExcelService;
    TareaService tareaService;


    def listarPaginado(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        render(tareaService.listarPaginado(paginacionDTO) as JSON);
    }
    
    def listarTareasArbol(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        render(tareaService.listarTareasArbol(paginacionDTO) as JSON);
    }

    def listarTareasEvento(){
        PaginacionDTO paginacionDTO = utileriaService.getPaginacionDTO(request.JSON);
        render(tareaService.listarTareasEvento(paginacionDTO) as JSON);
    }

    def listarTareasPorProyecto(){
        Long idProyecto = request.JSON?.idProyecto?.toLong()?:0;
        Long idTareaOrigen = request.JSON?.idTareaOrigen?.toLong()?:0;
        render(tareaService.listarTareasPorProyecto(idProyecto, idTareaOrigen) as JSON);
    }

    def listarTareasReutilizar(){
        render(tareaService.listarTareasReutilizar() as JSON);
    }

    def registrar(){
        tareaService.registrar(request.JSON);
        render(status: HttpStatus.OK);
    }

    def actualizar(){
        tareaService.actualizar(request.JSON);
        render(status: HttpStatus.OK);
    }

    def obtener(){
        Long idTarea = request.JSON?.idTarea?.toLong()?:0;
        render(tareaService.obtener(idTarea) as JSON);
    }

    def actualizarEstado(){
        tareaService.actualizarEstado(request.JSON);
        render(status: HttpStatus.OK);
    }

//    def importarTareas(){
//        MultipartFile archivoExcel = request.getFile('x');
//        excelService.leerArchivo(archivoExcel)
//    }

    // def generarReporteTableroOperacion(){
        
    //     OutputStream out = new BufferedOutputStream(response.outputStream)

    //     try {
    //         String path = servletContext.getRealPath("/");
    //         String nombreArchivo = "tareas.xlsx";
    //         response.setHeader("Content-disposition",  "attachment;filename=\"" + nombreArchivo + "\";");
    //         def parametros = request.JSON;
    //         byte[] reporte = tareaExcelService.generarReporteTableroOperacion(parametros,path);
    //         out.write(reporte)

    //     }catch(Exception e){
    //         render(status: HttpStatus.BAD_REQUEST, text: e.getMessage());
    //     } finally {
    //         out.close()
    //         return false
    //     }
    // }


    def generarReporteTableroOperacion(){
        
        try {
            
            String path = servletContext.getRealPath("/");
            String nombreArchivo = "tareas.xlsx";
            def parametros = request.JSON;

            byte[] reporte = tareaExcelService.generarReporteTableroOperacion(parametros,path);
            
            response.setHeader("Content-disposition", "attachment;filename=\"${nombreArchivo}\";")
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
            response.setCharacterEncoding("UTF-8")
            response.outputStream << reporte

        }catch(Exception e){
            render(status: HttpStatus.BAD_REQUEST, text: e.getMessage());
        }
    }
    
    // private generarRespuesta = { reportDef ->
    //     response.setHeader("Content-disposition", "Attachment; filename=" + (reportDef.parameters._name ?: reportDef.name) + "." + reportDef.fileFormat.extension)
    //     response.contentType = reportDef.fileFormat.mimeTyp
    //     response.characterEncoding = "UTF-8"
    //     response.outputStream << reportDef.contentStream.toByteArray()
    // }

    def obtenerTareaCalendario(){
        Long idTarea = request.JSON?.idTarea?.toLong()?:0;
        render(tareaService.obtenerTareaCalendario(idTarea) as JSON);
    }


}