import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class LoginService extends BaseService {
 
     static iniciarSesion(parametros) {
         return this.POST_UN(UrlServices.INICIO_SESION_LOGIN, parametros);
     } 
     
     static obtenerInformacionLogin(parametros) {
         return this.POST_SEC(UrlServices.OBTENER_INFORMACION_LOGIN, parametros);
     } 
 
 }