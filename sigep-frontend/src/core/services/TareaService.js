import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class TareaService extends BaseService {
 
     static listarPaginado(parametros) {
         return this.POST_SEC(UrlServices.TAREA_LISTAR_PAGINADO, parametros);
     }
     
     static listarTareasPorProyecto(idProyecto, idTareaOrigen) {
         return this.POST_SEC(UrlServices.TAREA_LISTAR_POR_PROYECTO, {idProyecto: idProyecto, idTareaOrigen: idTareaOrigen});
     }
     
     static listarTareasEvento(parametros) {
         return this.POST_SEC(UrlServices.TAREA_LISTAR_CALENDARIO, parametros);
     }

     static listarTareasArbol(parametros) {
         return this.POST_SEC(UrlServices.TAREA_LISTAR_ARBOL, parametros);
     }
 
     static listarTareasReutilizar() {
         return this.GET_SEC(UrlServices.TAREA_LISTAR_REUTILIZAR);
     }
 
     static registrar(parametros) {
         return this.POST_SEC(UrlServices.TAREA_REGISTRAR, parametros);
     }
 
     static obtener(parametros) {
         return this.POST_SEC(UrlServices.TAREA_OBTENER, parametros);
     }
 
     static actualizar(parametros) {
         return this.PUT_SEC(UrlServices.TAREA_ACTUALIZAR, parametros);
     }
 
     /*
        Ejemplo de parametros: {idTarea: 0}
     */
     static obtenerTareaCalendario(parametros) {
         return this.PUT_SEC(UrlServices.TAREA_OBTENER_TAREA_CALENDARIO, parametros);
     } 
 
 }