package com.accion.ti


import com.accion.ti.dto.pagination.PaginacionDTO
import grails.plugin.springsecurity.SpringSecurityService
import org.hibernate.transform.Transformers
import org.joda.time.DateTime

import java.util.regex.Pattern

// import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

class UtilService {

    SpringSecurityService springSecurityService

    PaginacionDTO getPaginacionDTO(def params ){
        return new PaginacionDTO( paginaActual: params?.paginaActual?:1, 
        maximoResultados: params?.maximoResultados?.toLong()?:10, busqueda: params?.busqueda?:"" );
    }


    def validateTypeData = {texto, boolean typeResponse = true ->
        def map
        switch (texto.toString()){
            case ~/^\d{4,4}\-\d{2,2}\-\d{2,2}$/:
                map = typeResponse ? new Date().parse("yyyy-MM-dd",texto) : 1
                break
            case ~/^\d{2,2}\-\d{2,2}\-\d{4,4}$/:
                map = typeResponse ? new Date().parse("dd-MM-yyyy",texto) : 2
                break
            case ~/^\d{4,4}\/\d{2,2}\/\d{2,2}$/:
                map = typeResponse ? new Date().parse("yyyy/MM/dd",texto) : 3
                break
            case ~/^\d{2,2}\/\d{2,2}\/\d{4,4}$/:
                map = typeResponse ? new Date().parse("dd/MM/yyyy",texto) : 4
                break
            case ~/^[a-z0-9]+[_a-z0-9\.-]*[a-z0-9]+@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,6})$/:
                map = typeResponse ? texto : 5
                break
            case { it.length() < 12 && it =~/^\d+$/ && it.isInteger() }:
                map = typeResponse ? (texto as long) : 6
                break
            case {it.contains(".") && it.isDouble()}:
                map = typeResponse ? (texto as double) : 7
                break
            case ~/^\d{4,4}\-\d{2,2}\-\d{2,2}T\d{2,2}\:\d{2,2}\:\d{2,2}\.\d{3,3}Z$/:
                map = typeResponse ? new Date().parse("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", texto) : 8
                break
            case {it.length() >= 12 && it =~/^\d+$/ && it.isLong() }:
                map = typeResponse ? new Date(texto as long) : 9
                break
            case {~/^[\s\S]+$/ && !it.isNumber() && it.length()>0 }:
                map = typeResponse ? texto : 10
                break
            default:
                map = 0
                break
        }
        return map
    }

    String formatDate(Date date){
        if(date){
            return date.format("yyy-MM-dd")
        }
        return null
    }

    Date stringToDate(String fecha) {
        return DateTime.parse(fecha).toLocalDateTime().toDate()
    }

    def getCurrentUser(){
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


      boolean nonEmptyList(List<?> list) {
        return nonNull(list) && !list.isEmpty();
    }

      boolean isEmptyList(List<?> list) {
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


}