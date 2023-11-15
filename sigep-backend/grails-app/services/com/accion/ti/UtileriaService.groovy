package com.accion.ti


import com.accion.ti.dto.pagination.PaginacionDTO
import com.accion.ti.enums.EstadoGeneralEnum
import com.accion.ti.enums.TipoEstadoEnum
import grails.plugin.springsecurity.SpringSecurityService
import org.hibernate.transform.Transformers
import org.joda.time.DateTime
import org.joda.time.LocalDateTime

import java.time.LocalDate
import java.time.ZoneId
import java.util.regex.Pattern
import javax.sql.DataSource;
import groovy.sql.Sql;

// import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

class UtileriaService {

    SpringSecurityService springSecurityService

    PaginacionDTO getPaginacionDTO(def params) {
        PaginacionDTO paginacionDTO = new PaginacionDTO(paginaActual: params?.paginaActual ?: 1,
                maximoResultados: params?.maximoResultados?.toLong() ?: 10, busqueda: params?.busqueda ?: "");
        paginacionDTO.cantidadOmitir = (paginacionDTO.paginaActual * paginacionDTO.maximoResultados - paginacionDTO.maximoResultados);
        return paginacionDTO;
    }

    Empresa obtenerEmpresaSesion() {
        Empresa empresa = Empresa.createCriteria().get() {
            resultTransformer(Transformers.aliasToBean(Empresa.class))
            empleados {
                usuario {
                    eq("username", springSecurityService?.principal?.username ?: "")
                }
            }
            projections {
                property 'id', 'id'
            }
        }
        return empresa
    }

    Empleado obtenerEmpleadoSesion() {
        Empleado empleado = Empleado.createCriteria().get() {
            resultTransformer(Transformers.aliasToBean(Empleado.class))
            usuario {
                eq("username", springSecurityService?.principal?.username ?: "")
            }
            projections {
                property 'id', 'id'
            }
        }
        return empleado
    }

    Estado obtenerEstadoActivo() {
        return Estado.findByNombreAndTipoEstado(EstadoGeneralEnum.ACTIVO.getValor(), obtenerTipoEstadoGeneral());
    }

    Estado obtenerEstadoInactivo() {
        return Estado.findByNombreAndTipoEstado(EstadoGeneralEnum.INACTIVO.getValor(), obtenerTipoEstadoGeneral());
    }

    TipoEstado obtenerTipoEstadoGeneral() {
        return TipoEstado.findByNombreAndEmpresa(TipoEstadoEnum.GENERAL.getValor(), obtenerEmpresaSesion())
    }


    def parsearDato(texto, boolean typeResponse = true) {
        if(texto == null){
            return null;
        }
        if(texto instanceof Date){
            return texto;
        }
        def map = null
        switch (texto.toString()) {
            case ~/^\d{4,4}\-\d{2,2}\-\d{2,2}$/:
                map = typeResponse ? new Date().parse("yyyy-MM-dd", texto) : 1
                break
            case ~/^\d{2,2}\-\d{2,2}\-\d{4,4}$/:
                map = typeResponse ? new Date().parse("dd-MM-yyyy", texto) : 2
                break
            case ~/^\d{4,4}\/\d{2,2}\/\d{2,2}$/:
                map = typeResponse ? new Date().parse("yyyy/MM/dd", texto) : 3
                break
            case ~/^\d{2,2}\/\d{2,2}\/\d{4,4}$/:
                map = typeResponse ? new Date().parse("dd/MM/yyyy", texto) : 4
                break
            case ~/^\d{2,2}\:\d{2,2}$/:
                map = typeResponse ? new Date().parse("HH:mm", texto) : 5
                break
            case ~/^[a-z0-9]+[_a-z0-9\.-]*[a-z0-9]+@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,6})$/:
                map = typeResponse ? texto : 6
                break
            case { it.length() < 12 && it =~ /^\d+$/ && it.isInteger() }:
                map = typeResponse ? (texto as long) : 7
                break
            case { it.contains(".") && it.isDouble() }:
                map = typeResponse ? (texto as double) : 8
                break
            case ~/^\d{4,4}\-\d{2,2}\-\d{2,2}T\d{2,2}\:\d{2,2}\:\d{2,2}\.\d{3,3}Z$/:
                map = typeResponse ? new Date().parse("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", texto) : 9
                break
            case ~/^\d{4,4}\-\d{2,2}\-\d{2,2}T\d{2,2}\:\d{2,2}$/:
                map = typeResponse ? new Date().parse("yyyy-MM-dd'T'HH:mm", texto) : 10
                break
            case { it.length() >= 12 && it =~ /^\d+$/ && it.isLong() }:
                map = typeResponse ? new Date(texto as long) : 11
                break
            case { ~/^[\s\S]+$/ && !it.isNumber() && it.length() > 0 }:
                map = typeResponse ? texto : 12
                break
            default:
                map = 0
                break
        }
        return map
    }

    String formatoFecha(Date date) {
        if (date) {
            return date.format("yyyy-MM-dd")
        }
        return null
    }
    String formatoFechaExcel(Date date) {
        if (date) {
            return date.format("dd/MM/yyyy")
        }
        return null
    }

    String formatoFechaHora(Date date, int formato = 1) {
        if (date) {
            switch (formato){
                case 1:
                    return date.format("yyyy-MM-dd'T'HH:mm")
                    break;
                case 2:
                    return date.format("yyyy-MM-dd HH:mm:ss")
                break;
                default:
                    return date.format("yyyy-MM-dd'T'HH:mm")

            }
        }
        return null
    }

    String formatoHora(Date date) {
        if (date) {
            return date.format("HH:mm")
        }
        return null
    }

    DateTime endOfDay(DateTime d) {
        return d.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMinuteOfHour(999);
    }
    DateTime startDay(DateTime d) {
        return d.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMinuteOfHour(0);
    }
    Date stringToDate(String fecha) {
        return DateTime.parse(fecha).toLocalDateTime().toDate()
    }

     Date getDateFromLocalDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
    def getCurrentUser() {
//        def currentUser = Usuario.createCriteria().get(){
//            resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
//            eq("username", springSecurityService?.principal?.username?:"")
//            projections {
//                property 'idUser', 'idUser'
//            }
//        }
//        return currentUser?:[:]
    }

//    HumanResource getCurrentHumanResource(){
//        HumanResource currentHumanResource = HumanResource.createCriteria().get(){
//            person{
//                user{
//                    eq("username", springSecurityService?.principal?.username?:"")
//                }
//            }
//        }
//        return currentHumanResource
//    }


    boolean isNull(Object object) {
        return object == null;
    }

    boolean nonNull(Object object) {
        return !isNull(object);
    }

    boolean nonEmpty(String value) {
        return nonNull(value) && !value.isEmpty() && !"null".equals(value);
    }

    boolean isEmpty(String value) {
        return !nonEmpty(value);
    }


    boolean nonEmptyList(def list) {
        return nonNull(list) && !list.isEmpty();
    }

    boolean isEmptyList(def list) {
        return !nonEmptyList(list);
    }

    boolean isAlphabetic(String cadena) {
        Pattern pat = Pattern.compile("[A-Za-z]");
        return pat.matcher(cadena).find();
    }

    boolean isAlphaNumeric(String cadena) {
        Pattern pat = Pattern.compile("[A-Za-z0-9]");
        return pat.matcher(cadena).find();
    }

    Integer getYearsOlg(Date date) {
        return new Date().getYear() - date.getYear();
    }

    org.joda.time.DateTime parsearJodaDateTime(fecha){
        if(fecha == null){
            return null
        }
        if(fecha instanceof java.util.Date){
            return new DateTime(fecha)
        }
        println parsearDato(fecha)
        return  new org.joda.time.DateTime(parsearDato(fecha));
    }

    java.util.Date parsearLocalDateTime(java.time.LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    void cerrarConexiones(Sql sql){
        if(sql != null){
            sql.close();
        }
    }

    boolean esVocal(letra) {
            return 'aeiouAEIOU'.indexOf(letra) != -1
    }

    String obtenerPrefijo(String cadena) {
        // Dividir la cadena en palabras
        List<String> palabras = cadena.split(' ')

        // Inicializar el prefijo
        String prefijo = ''

        // Lista de palabras conectores a omitir (puedes ampliar esta lista según tus necesidades)
        List<String> conectores = ["el", "la", "de", "en", "y", "o"]

        // Función para verificar si un carácter es una vocal
        

        // Recorrer cada palabra y tomar letras si no es un conector y es vocal o consonante
        for (palabra in palabras) {
            if (!conectores.contains(palabra.toLowerCase())) {

                for(letra in palabra){
                    if (letra && letra =~ /^[a-zA-Z]+$/) {
                        // Verificar si la letra es una vocal o consonante
                        prefijo += letra.toUpperCase() // Agregar la letra al prefijo (en mayúscula)
                        println "prefijo-> ${prefijo} ${prefijo.length()}"
                        if (prefijo.length() == 4) {
                            return prefijo // Si ya tenemos 4 letras, retornamos el prefijo
                        }
                        
                    }
                }

            }
        }


        // Si el prefijo tiene menos de 4 letras, llenamos con "X"
        return prefijo.padRight(4, 'X').toUpperCase()
    }



}