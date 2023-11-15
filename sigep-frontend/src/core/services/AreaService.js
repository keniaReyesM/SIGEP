import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class AreaService extends BaseService {
 
     static listarPaginado(parametros) {
         return this.POST_SEC(UrlServices.AREA_LISTAR_PAGINADO, parametros);
     }
 
     static listarTodos() {
         return this.POST_SEC(UrlServices.AREA_LISTAR_TODOS);
     }
 
     static registrar(parametros) {
         return this.POST_SEC(UrlServices.AREA_REGISTRAR, parametros);
     }
 
     static obtener(parametros) {
         return this.POST_SEC(UrlServices.AREA_OBTENER, parametros);
     }
 
     static actualizar(parametros) {
         return this.PUT_SEC(UrlServices.AREA_ACTUALIZAR, parametros);
     }
 
     static eliminar(parametros) {
         return this.POST_SEC(UrlServices.AREA_ELIMINAR, parametros);
     }
 
 }