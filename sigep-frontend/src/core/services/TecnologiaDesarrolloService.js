import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class TecnologiaDesarrolloService extends BaseService {
 
     static listarPaginado(parametros) {
         return this.POST_SEC(UrlServices.TECNOLOGIA_DESARROLLO_LISTAR_PAGINADO, parametros);
     }
 
     static listarTodos() {
         return this.POST_SEC(UrlServices.TECNOLOGIA_DESARROLLO_LISTAR_TODOS);
     }
 
     static registrar(parametros) {
         return this.POST_SEC(UrlServices.TECNOLOGIA_DESARROLLO_REGISTRAR, parametros);
     }
 
     static obtener(parametros) {
         return this.POST_SEC(UrlServices.TECNOLOGIA_DESARROLLO_OBTENER, parametros);
     }
 
     static actualizar(parametros) {
         return this.PUT_SEC(UrlServices.TECNOLOGIA_DESARROLLO_ACTUALIZAR, parametros);
     }
 
     static eliminar(parametros) {
         return this.POST_SEC(UrlServices.TECNOLOGIA_DESARROLLO_ELIMINAR, parametros);
     }
 
 }