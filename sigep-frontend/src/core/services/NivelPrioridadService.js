import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class NivelPrioridadService extends BaseService {
 
     static listarTodos() {
         return this.POST_SEC(UrlServices.NIVEL_PRIORIDAD_LISTAR_TODOS);
     }
 
 
 }