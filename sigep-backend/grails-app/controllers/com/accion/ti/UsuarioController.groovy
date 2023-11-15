package com.accion.ti

import grails.converters.JSON
import org.springframework.http.HttpStatus
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

public class UsuarioController{

    UtileriaService utileriaService;
    UsuarioService usuarioService;

    def obtenerInformacionLogin(){
        String usuario = request?.JSON?.username?:"";
        def informacionLogin = usuarioService.obtenerInformacionLogin(usuario);
        render(informacionLogin as JSON);
    }


}