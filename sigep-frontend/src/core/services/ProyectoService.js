import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class ProyectoService extends BaseService {
 
    static listarPaginado(parametros) {
        return this.POST_SEC(UrlServices.PROYECTO_LISTAR_PAGINADO, parametros);
    }

     static listarTodos(parametros) {
         return this.POST_SEC(UrlServices.PROYECTO_LISTAR_TODOS, parametros);
     }
 
     static registrar(parametros) {
         return this.POST_SEC(UrlServices.PROYECTO_REGISTRAR, parametros);
     }
 
     static obtener(parametros) {
         return this.POST_SEC(UrlServices.PROYECTO_OBTENER, parametros);
     }
 
     static actualizar(parametros) {
         return this.PUT_SEC(UrlServices.PROYECTO_ACTUALIZAR, parametros);
     }
 
     static actualizarEstado(parametros) {
         return this.POST_SEC(UrlServices.PROYECTO_ACTUALIZAR_ESTADO, parametros);
     }
 
    static listarEmpleadosPorProyecto(idProyecto) {
        return this.POST_SEC(UrlServices.PROYECTO_LISTAR_EMPLEADOS_POR_PROYECTO, {idProyecto: idProyecto});
    }
    
    static obtenerInformacionGraficaCircular(idProyecto) {
        return this.POST_SEC(UrlServices.PROYECTO_OBTENER_INFORMACION_GRAFICA_CIRCULAR, {idProyecto: idProyecto});
    }
    
    static obtenerInformacionGeneral(idProyecto) {
        return this.POST_SEC(UrlServices.PROYECTO_OBTENER_INFORMACION_GENERAL, {idProyecto: idProyecto});
    }
 
    static listarPaginadoProductividadEmpleado(parametros) {
        return this.POST_SEC(UrlServices.PROYECTO_LISTAR_PAGINADO_PRODUCTIVIDAD_EMPLEADO,parametros);
    }

    static listarPaginadoProductividadProyecto(parametros) {
        return this.POST_SEC(UrlServices.PROYECTO_LISTAR_PAGINADO_PRODUCTIVIDAD_PROYECTO,parametros);
    }
 
    static listarPaginadoProductividadGeneral(parametros) {
        return this.POST_SEC(UrlServices.PROYECTO_LISTAR_PAGINADO_PRODUCTIVIDAD_GENERAL,parametros);
    }

    static generarReporteProductividadEmpleado(parametros) {
        return this.POST_FILE_SEC(UrlServices.PROYECTO_REPORTE_PRODUCTIVIDAD_EMPLEADO, parametros);
    }

    static generarReporteProductividadProyecto(parametros) {
        return this.POST_FILE_SEC(UrlServices.PROYECTO_REPORTE_PRODUCTIVIDAD_PROYECTO, parametros);
    }
    
 }