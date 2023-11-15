package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.gestorContenido.GestorContenidoDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class GestorContenidoService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

   RespuestaListaDTO<GestorContenidoDTO> listarPaginado(PaginacionDTO paginacionDTO) throws ServiceException {

       try {

           String busqueda = "%${paginacionDTO.busqueda}%";
           
           def criteria = GestorContenido.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
               resultTransformer(Transformers.aliasToBean(GestorContenidoDTO.class))
               order("id", "desc")
               or {
                   ilike("nombre", busqueda)
               }
               projections {
                   property 'id', 'idGestorContenido'
                   property 'nombre', 'nombre'
               }
           }

           return new  RespuestaListaDTO<GestorContenidoDTO> (elementos: criteria, total: criteria.totalCount);

       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

   List<GestorContenidoDTO> listarTodos() throws ServiceException {
       try {

           def criteria = GestorContenido.createCriteria().list() {
               resultTransformer(Transformers.aliasToBean(GestorContenidoDTO.class))
               order("id", "desc")
               projections {
                   property 'id', 'idGestorContenido'
                   property 'nombre', 'nombre'
               }
           }
           return criteria;
       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

    void registrar(GestorContenidoDTO gestorContenidoDTO) throws ServiceException{
        GestorContenido.withTransaction{ statusTransaccion-> 
            try{
                GestorContenido gestorContenido = GestorContenido.findByNombre(gestorContenidoDTO.getNombre())
                if(gestorContenido != null){
                    throw new ServiceException(messageService.getErrorUnico(gestorContenido.getNombre()))
                }
                gestorContenido = new GestorContenido(nombre: gestorContenidoDTO.getNombre());
                gestorContenido.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(GestorContenidoDTO gestorContenidoDTO)  throws ServiceException{
         GestorContenido.withTransaction{ statusTransaccion-> 
            try{
                GestorContenido gestorContenido = GestorContenido.findByNombreAndIdNotEqual(gestorContenidoDTO.getNombre(), gestorContenidoDTO.getIdGestorContenido());
                if(gestorContenido != null){
                    throw new ServiceException(messageService.getErrorUnico(gestorContenido.getNombre()))
                }
                gestorContenido = GestorContenido.findById(gestorContenidoDTO.getIdGestorContenido());
                if (gestorContenido == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Proveedor alojamiento", gestorContenidoDTO.getIdGestorContenido()))
                }
                gestorContenido.setNombre(gestorContenidoDTO.getNombre());
                gestorContenido.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    GestorContenidoDTO obtener(Long idGestorContenido)  throws ServiceException{
        try {
            GestorContenido gestorContenido = GestorContenido.findById(idGestorContenido);
            if (gestorContenido == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Proveedor alojamiento", idGestorContenido));
            }
            return new GestorContenidoDTO(idGestorContenido: gestorContenido.getId(), nombre: gestorContenido.getNombre());
        }catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    void eliminar(Long idGestorContenido) throws ServiceException{
        GestorContenido.withTransaction{ statusTransaccion-> 
            try{
                GestorContenido gestorContenido = GestorContenido.findById(idGestorContenido);
                if (gestorContenido == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Proveedor alojamiento", idGestorContenido));
                }

                if(utileriaService.nonEmptyList(gestorContenido.gestorContenidoPlataformasDigitales)){
                    throw new ServiceException(messageService.getErrorEliminacion("Proveedor alojamiento", "Plataformas digitales"))
                }

                gestorContenido.delete(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }


}
