import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class RolService extends BaseService {
 
     static listarPaginado(parametros) {
         return this.POST_SEC(UrlServices.ROL_LISTAR_PAGINADO, parametros);
     }
 
     static listarTodos() {
         return this.POST_SEC(UrlServices.ROL_LISTAR_TODOS);
     }
 
     static registrar(parametros) {
         return this.POST_SEC(UrlServices.ROL_REGISTRAR, parametros);
     }
 
     static obtener(parametros) {
         return this.POST_SEC(UrlServices.ROL_OBTENER, parametros);
     }
 
     static actualizar(parametros) {
         return this.PUT_SEC(UrlServices.ROL_ACTUALIZAR, parametros);
     }
 
     static eliminar(parametros) {
         return this.POST_SEC(UrlServices.ROL_ELIMINAR, parametros);
     }
 
 
 }