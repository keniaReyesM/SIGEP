import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class TipoTareaService extends BaseService {
 
     static listarTodos() {
         return this.POST_SEC(UrlServices.TIPO_TAREA_LISTAR_TODOS);
     }
 
 
 }