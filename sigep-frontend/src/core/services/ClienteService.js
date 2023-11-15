import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class ClienteService extends BaseService {
 
     static listarPaginado(parametros) {
         return this.POST_SEC(UrlServices.CLIENTE_LISTAR_PAGINADO, parametros);
     }
 
     static listarTodos() {
         return this.POST_SEC(UrlServices.CLIENTE_LISTAR_TODOS);
     }
 
     static registrar(parametros) {
         return this.POST_SEC(UrlServices.CLIENTE_REGISTRAR, parametros);
     }
 
     static obtener(parametros) {
         return this.POST_SEC(UrlServices.CLIENTE_OBTENER, parametros);
     }
 
     static actualizar(parametros) {
         return this.PUT_SEC(UrlServices.CLIENTE_ACTUALIZAR, parametros);
     }

     static actualizarEstado(parametros) {
        return this.POST_SEC(UrlServices.CLIENTE_ACTUALIZAR_ESTADO, parametros);
    }
 
 
 
 }