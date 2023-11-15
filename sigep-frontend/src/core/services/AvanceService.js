import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class AvanceService extends BaseService {
 
     static listarPaginado(parametros) {
         return this.POST_SEC(UrlServices.AVANCE_LISTAR_PAGINADO, parametros);
     }
     
     static registrar(parametros) {
         return this.POST_SEC(UrlServices.AVANCE_REGISTRAR, parametros);
     }
 
     static eliminar(parametros) {
         return this.PUT_SEC(UrlServices.AVANCE_ELIMINAR, parametros);
     }
 
 }