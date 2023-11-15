import Vue from 'vue';
import moment from "moment";
import GeneralConstants from "@/core/constants/GeneralConstants";
import RutasConstants from "@/core/constants/RutasConstants";

export default class Utileria {

    static FORMATO_FECHA = "DD/MM/YYYY";
    static FORMATO_FECHA_HORA = "DD/MM/YYYY HH:mm";
    static FORMATO_HORA = "HH:mm";
    static PALABRA_CLAVE = "4ct10n-t1";
    static VERSION_SISTEMA = "v. 1.4";

    static encriptar(textoPlano) {
        return (new Vue).$CryptoJS.AES.encrypt(textoPlano, this.PALABRA_CLAVE).toString();
    }

    static desencriptar(textoEncriptado) {
        return (new Vue).$CryptoJS.AES.decrypt(textoEncriptado, this.PALABRA_CLAVE).toString((new Vue).$CryptoJS.enc.Utf8);
    }

    static obtenerUsuario() {
        let usuario = (new Vue).$session.get(GeneralConstants.USUARIO_SESION)
        usuario = (new Vue).$utileria.desencriptar(usuario);
        return JSON.parse(usuario);
    }

    static esAdmin() {
        return this.obtenerUsuario().roles[0] === "ROLE_ADMIN";
    }

    static notificacion(mensaje, type) {
        switch (type) {
            case 'SUCCESS':
                (new Vue).$toast.success(mensaje);
                break;
            case 'ERROR':
                (new Vue).$toast.error(mensaje);
                break;
            case 'INFO':
                (new Vue).$toast.info(mensaje);
                break;
            case 'WARNING':
                (new Vue).$toast.warning(mensaje);
                break;
            default:
                (new Vue).$toast(mensaje);
                break;
        }
    }

    static errorhttp(error) {
        console.log(error);
        let response = error.response;
        let status = 0;
        if (response != undefined && Number(response.status) != undefined) {
            status = response.status;
        }

        switch (status) {
            case 401:
                this.$session.destroy();
                this.notificacion("No estás autorizado para ver el recurso solicitado.", "ERROR");
                this.$router.push({
                    name: RutasConstants.LOGIN.nombre
                });
                break;
            case 403:
                this.notificacion("No tienes permisos para realizar la tarea.", "WARNING");
                this.$session.destroy();
                this.$router.push({
                    name: RutasConstants.LOGIN.nombre
                });
                break;
            case 404:
                this.notificacion("No se encontró el recurso que solicitó.", "WARNING");
                break;
            case 400:
                this.notificacion(response.data || "Problemas en el servidor, notifique al administrador.", "WARNING");
                break;
            default:
                this.notificacion("Problemas en el servidor, notifique al administrador.", "ERROR");
        }
    }

    static formatoRango(date) {
        if (date == "" || this.date == null || date == undefined) {
            return "";
        }
        if (date.start == "" || date.start == null || date.start == undefined) {
            return "";
        }
        if (date.end == "" || date.end == null || date.end == undefined) {
            return "";
        }
        return `${moment(date.start).format("DD/MM/YYYY")} al ${moment(date.end).format("DD/MM/YYYY")}`;
    }

    static finalDia(date) {
        if (this.nonEmpty(date)) {
            return moment(date).endOf('day');
        }
        return null;
    }

    static inicioDia(date) {
        if (this.nonEmpty(date)) {
            return moment(date).startOf('day');
        }
        return null;
    }

    static formatoFecha(date) {
        return moment(new Date(date)).locale("es").format("DD/MM/YYYY") || "---";
    }

    static formatoFechaHora(date) {
        if (this.nonEmpty(date)) {
            return moment(new Date(date)).locale("es").format("DD/MM/YYYY HH:mm")
        }
        return null;
    }

    static formatoHora(date) {
        if (this.nonEmpty(date)) {
            return moment(new Date(date)).format("HH:mm")
        }
        return null;
    }

    static isEmptyList(list) {
        if (list != null && list != undefined && Array.isArray(list)) {
            return list.length == 0;
        }
        return true;
    }

    static nonEmptyList(list) {
        return !this.isEmptyList(list)
    }

    static nonEmpty(data, valueDafault) {
        if (valueDafault != null && valueDafault != undefined && valueDafault != "") {
            if (data != null && data != undefined && data != "") {
                return data;
            } else {
                return valueDafault;
            }
        }
        return data != null && data != undefined && data != "";
    }

    static isEmpty(data) {
        return !this.nonEmpty(data);
    }
    static authorized(permiso) {
        return this.isEmpty(permiso) || this.obtenerUsuario()?.permisos?.includes(permiso);
    }
    static notAuthorized(permiso) {
        return !this.authorized(permiso);
    }

    static isNull(date) {
        return date == null || date == undefined;
    }

    static isNonNull(data) {
        return !this.isNull(data);
    }

    static catchError(e) {
        this.notificacion(e || 'Ocurrió un error interno, intente nuevamente', "ERROR");
    }

    static descargarExcel(data, nombreArchivo) {
        const url = URL.createObjectURL(new Blob([data], {
            type: "application/vnd.ms-excel"
        }));
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", nombreArchivo);
        document.body.appendChild(link);
        link.click();
    }

    static obtenerPrefijo(cadena) {
        const palabras = cadena.split(' '); // Dividir la cadena en palabras

        // Inicializar el prefijo
        let prefijo = '';
        let contador = 0;

        // Lista de palabras conectores a omitir (puedes ampliar esta lista según tus necesidades)
        const conectores = ["el", "la", "de", "en", "y", "o"];

        // Función para verificar si un carácter es una vocal
        function esVocal(char) {
            return 'aeiouAEIOU'.indexOf(char) !== -1;
        }

        // Recorrer cada palabra y tomar letras si no es un conector y es vocal o consonante
        for (const palabra of palabras) {
            // Verificar si la palabra no es un conector
            if (!conectores.includes(palabra.toLowerCase())) {
                for (const letra of palabra) {
                    if (letra && /^[a-zA-Z]+$/.test(letra)) {
                        // Verificar si la letra es una vocal o consonante
                        if (esVocal(letra) || !esVocal(letra)) {
                            prefijo += letra.toUpperCase(); // Agregar la letra al prefijo (en mayúscula)
                            contador++;

                            if (contador === 4) {
                                return prefijo; // Si ya tenemos 4 letras, retornamos el prefijo
                            }
                        }
                    }
                }
            }
        }

        return prefijo; // Si no alcanzamos las 4 letras, retornamos el prefijo tal como está

    }
}