import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class TipoPlataformaDigitalService extends BaseService {
 
     static listarPaginado(parametros) {
         return this.POST_SEC(UrlServices.TIPO_PLATAFORMA_DIGITAL_LISTAR_PAGINADO, parametros);
     }
 
     static listarTodos() {
         return this.POST_SEC(UrlServices.TIPO_PLATAFORMA_DIGITAL_LISTAR_TODOS);
     }
 
     static registrar(parametros) {
         return this.POST_SEC(UrlServices.TIPO_PLATAFORMA_DIGITAL_REGISTRAR, parametros);
     }
 
     static obtener(parametros) {
         return this.POST_SEC(UrlServices.TIPO_PLATAFORMA_DIGITAL_OBTENER, parametros);
     }
 
     static actualizar(parametros) {
         return this.PUT_SEC(UrlServices.TIPO_PLATAFORMA_DIGITAL_ACTUALIZAR, parametros);
     }
 
     static eliminar(parametros) {
         return this.POST_SEC(UrlServices.TIPO_PLATAFORMA_DIGITAL_ELIMINAR, parametros);
     }
 
 }