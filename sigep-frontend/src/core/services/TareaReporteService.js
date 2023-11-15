import UrlServices from "@/core/constants/UrlServices";
import BaseService from "@/core/services/BaseService";
 
 export default class TareaService extends BaseService {
 
     static generarReporteTableroOperacion(parametros) {
         return this.POST_FILE_SEC(UrlServices.TAREA_OBTENER_REPORTE_TABLERO_OPERACION, parametros);
     }
     
 
 
 
 }