package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.rol.RolDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class RolService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

   RespuestaListaDTO<RolDTO> listarPaginado(PaginacionDTO paginacionDTO) throws ServiceException {

       try {

           String busqueda = "%${paginacionDTO.busqueda}%";
           
           def criteria = Rol.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
               resultTransformer(Transformers.aliasToBean(RolDTO.class))
               order("id", "desc")
               or {
                   ilike("descripcion", busqueda)
                   ilike("authority", busqueda)
               }
               projections {
                   property 'id', 'idRol'
                   property 'authority', 'authority'
                   property 'descripcion', 'descripcion'
               }
           }

           return new  RespuestaListaDTO<RolDTO> (elementos: criteria, total: criteria.totalCount);

       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

   List<RolDTO> listarTodos() throws ServiceException {
       try {

           def criteria = Rol.createCriteria().list() {
               resultTransformer(Transformers.aliasToBean(RolDTO.class))
               order("id", "desc")
                projections {
                   property 'id', 'idRol'
                   property 'authority', 'authority'
                   property 'descripcion', 'descripcion'
               }
           }
           return criteria;
       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

    void registrar(RolDTO rolDTO) throws ServiceException{
        Rol.withTransaction{ statusTransaccion-> 
            try{
                Rol rol = Rol.findByDescripcion(rolDTO.getDescripcion(), empresa)
                if(rol != null){
                    throw new ServiceException(messageService.getErrorUnico(rol.getDescripcion()))
                }
                rol = new Rol(authority: rolDTO.getAuthority(), 
                                          descripcion: rolDTO.getDescripcion());
                rol.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(RolDTO rolDTO)  throws ServiceException{
         Rol.withTransaction{ statusTransaccion-> 
            try{
                Rol rol = Rol.findByDescripcionAndIdNotEqual(rolDTO.getDescripcion(), rolDTO.getIdRol());
                if(rol != null){
                    throw new ServiceException(messageService.getErrorUnico(rol.getDescripcion()))
                }
                rol = Rol.findById(rolDTO.getIdRol());
                if (rol == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Rol", rolDTO.getIdRol()))
                }
                rol.setAuthority(rolDTO.getAuthority());
                rol.setDescripcion(rolDTO.getDescripcion());
                rol.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    RolDTO obtener(Long idRol)  throws ServiceException{
        try {
            Rol rol = Rol.findById(idRol);
            if (rol == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Rol", idRol));
            }
            return new RolDTO(idRol: rol.getId(), authority: rol.getAuthority(), descripcion: rol.getDescripcion());
        }catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    void eliminar(Long idRol) throws ServiceException{
        Rol.withTransaction{ statusTransaccion-> 
            try{
                Rol rol = Rol.findById(idRol);
                if (rol == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Rol", idRol));
                }

                if(utileriaService.nonEmptyList(UsuarioRol.findAllByRol(rol))){
                    throw new ServiceException(messageService.getErrorEliminacion("Rol", "Usuarios"))
                }

                rol.delete(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }


}
