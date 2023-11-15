import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class DiaService extends BaseService {
 
     static listarTodos() {
         return this.POST_SEC(UrlServices.DIA_LISTAR_TODOS);
     }
 
 
 }