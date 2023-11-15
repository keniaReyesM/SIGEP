import axios from "axios";
import Vue from "vue";
import GeneralConstants from "@/core/constants/GeneralConstants";

export default class BaseService {

  static getHeaders() {
    return { headers: { Authorization: this.getToken() } };
  }
  static getToken() {
    return "Bearer ".concat(new Vue().$session.get(GeneralConstants.TOKEN_SESION));
  }

  static POST_SEC(ruta, parametros) {
    return axios.post(ruta, parametros, this.getHeaders());
  }

  static GET_SEC(ruta) {
    return axios.get(ruta, this.getHeaders());
  }

  static PUT_SEC(ruta, parametros) {
    return axios.put(ruta, parametros, this.getHeaders());
  }

  static DELETE_SEC(ruta, parametros) {
    return axios.delete(ruta, parametros, this.getHeaders());
  }


  static POST_UN(ruta, parametros) {
    return axios.post(ruta, parametros);
  }

  static POST_FILE_SEC(ruta, parametros) {
    let cabeceras = this.getHeaders();
    cabeceras.responseType = "blob";
    return axios.post(ruta, parametros, cabeceras);
  }
  
}
