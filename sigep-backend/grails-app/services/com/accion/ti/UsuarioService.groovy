package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.categoria.CategoriaDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class UsuarioService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

    def obtenerInformacionLogin(String nombreUsuario)  throws ServiceException{
        try {
           
            // Usuario usuario = Usuario.findByUsername(nombreUsuario);
            // Empleado empleado = Empleado.findByUsuario(usuario);
            // Persona persona = empleado.getPersona();
            // Puesto puesto = empleado.getPuesto();

            // def informacionLogin = [:]
            // informacionLogin.idEmpleado = empleado.id;
            // informacionLogin.nombreCompleto = persona.nombreCompleto;
            // informacionLogin.nombrePuesto = puesto.nombre;
            
            def informacionLogin = Empleado.createCriteria().get(){
               resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
               
               createAlias 'persona', 'persona'
               createAlias 'puesto', 'puesto'
               createAlias 'usuario', 'usuario'

               eq("usuario.username", nombreUsuario)

               projections {
                   property 'id', 'idEmpleado'
                   property 'persona.nombreCompleto', 'nombreCompleto'
                   property 'persona.nombre', 'nombre'
                   property 'persona.primerApellido', 'primerApellido'
                   property 'persona.segundoApellido', 'segundoApellido'
                   property 'puesto.nombre', 'nombrePuesto'
               }
            }
            
            def permisos = [];
            informacionLogin.rol = [:]
            if(informacionLogin){
                Usuario usuario = Usuario.findByUsername(nombreUsuario);
                UsuarioRol usuarioRol = UsuarioRol.findByUsuario(usuario);
                if( usuarioRol ){
                    permisos = PermisoRol.findAllByRol(usuarioRol.rol)*.permiso?.clave;
                }
                informacionLogin.rol = [
                    idRol: usuarioRol.rol.id,
                    authority: usuarioRol.rol.authority,
	                descripcion: usuarioRol.rol.descripcion
                ];
            }
            informacionLogin.permisos = permisos?:[];

            return informacionLogin;
        }catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

   

}
