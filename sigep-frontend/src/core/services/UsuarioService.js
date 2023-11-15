import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class UsuarioService extends BaseService {
 
     static obtenerInformacionLogin(parametros) {
         return this.POST_SEC(UrlServices.OBTENER_INFORMACION_LOGIN, parametros);
     }

 
 }