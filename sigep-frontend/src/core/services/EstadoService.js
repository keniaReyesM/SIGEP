import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class EstadoService extends BaseService {
 
     static listarTipoTarea() {
         return this.POST_SEC(UrlServices.ESTADO_LISTAR_TIPO_TAREA);
     }
 
     static listarTipoGeneral() {
         return this.POST_SEC(UrlServices.ESTADO_LISTAR_TIPO_GENERAL);
     }
 
 
 }