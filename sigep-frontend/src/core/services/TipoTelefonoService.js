import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class TipoTelefonoService extends BaseService {
 
     static listarTodos() {
         return this.POST_SEC(UrlServices.TIPO_TELEFONO_LISTAR_TODOS);
     }
 
 
 }