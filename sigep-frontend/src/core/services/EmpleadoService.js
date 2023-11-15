import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class EmpleadoService extends BaseService {
 
     static listarPaginado(parametros) {
         return this.POST_SEC(UrlServices.EMPLEADO_LISTAR_PAGINADO, parametros);
     }
 
     static listarTodosActivos() {
         return this.POST_SEC(UrlServices.EMPLEADO_LISTAR_TODOS_ACTIVOS);
     }

     static listarTodos() {
         return this.GET_SEC(UrlServices.EMPLEADO_LISTAR_TODOS);
     }
 
     static registrar(parametros) {
         return this.POST_SEC(UrlServices.EMPLEADO_REGISTRAR, parametros);
     }
 
     static obtener(parametros) {
         return this.POST_SEC(UrlServices.EMPLEADO_OBTENER, parametros);
     }
 
     static actualizar(parametros) {
         return this.PUT_SEC(UrlServices.EMPLEADO_ACTUALIZAR, parametros);
     }
 
     static actualizarEstado(parametros) {
         return this.POST_SEC(UrlServices.EMPLEADO_ACTUALIZAR_ESTADO, parametros);
     }
 
 
 }