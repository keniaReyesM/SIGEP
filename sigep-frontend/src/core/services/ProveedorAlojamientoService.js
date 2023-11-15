import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class ProveedorAlojamientoService extends BaseService {
 
     static listarPaginado(parametros) {
         return this.POST_SEC(UrlServices.PROVEEDOR_ALOJAMIENTO_LISTAR_PAGINADO, parametros);
     }
 
     static listarTodos() {
         return this.POST_SEC(UrlServices.PROVEEDOR_ALOJAMIENTO_LISTAR_TODOS);
     }
 
     static registrar(parametros) {
         return this.POST_SEC(UrlServices.PROVEEDOR_ALOJAMIENTO_REGISTRAR, parametros);
     }
 
     static obtener(parametros) {
         return this.POST_SEC(UrlServices.PROVEEDOR_ALOJAMIENTO_OBTENER, parametros);
     }
 
     static actualizar(parametros) {
         return this.PUT_SEC(UrlServices.PROVEEDOR_ALOJAMIENTO_ACTUALIZAR, parametros);
     }
 
     static eliminar(parametros) {
         return this.POST_SEC(UrlServices.PROVEEDOR_ALOJAMIENTO_ELIMINAR, parametros);
     }
 
 }