package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.responsableCompra.ResponsableCompraDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class ResponsableCompraService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

   RespuestaListaDTO<ResponsableCompraDTO> listarPaginado(PaginacionDTO paginacionDTO) throws ServiceException {

       try {

           String busqueda = "%${paginacionDTO.busqueda}%";
           
           def criteria = ResponsableCompra.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
               resultTransformer(Transformers.aliasToBean(ResponsableCompraDTO.class))
               order("id", "desc")
               or {
                   ilike("nombre", busqueda)
               }
               projections {
                   property 'id', 'idResponsableCompra'
                   property 'nombre', 'nombre'
               }
           }

           return new  RespuestaListaDTO<ResponsableCompraDTO> (elementos: criteria, total: criteria.totalCount);

       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

   List<ResponsableCompraDTO> listarTodos() throws ServiceException {
       try {

           def criteria = ResponsableCompra.createCriteria().list() {
               resultTransformer(Transformers.aliasToBean(ResponsableCompraDTO.class))
               order("id", "desc")
               projections {
                   property 'id', 'idResponsableCompra'
                   property 'nombre', 'nombre'
               }
           }
           return criteria;
       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

    void registrar(ResponsableCompraDTO responsableCompraDTO) throws ServiceException{
        ResponsableCompra.withTransaction{ statusTransaccion-> 
            try{
                ResponsableCompra responsableCompra = ResponsableCompra.findByNombre(responsableCompraDTO.getNombre())
                if(responsableCompra != null){
                    throw new ServiceException(messageService.getErrorUnico(responsableCompra.getNombre()))
                }
                responsableCompra = new ResponsableCompra(nombre: responsableCompraDTO.getNombre());
                responsableCompra.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(ResponsableCompraDTO responsableCompraDTO)  throws ServiceException{
         ResponsableCompra.withTransaction{ statusTransaccion-> 
            try{
                ResponsableCompra responsableCompra = ResponsableCompra.findByNombreAndIdNotEqual(responsableCompraDTO.getNombre(), responsableCompraDTO.getIdResponsableCompra());
                if(responsableCompra != null){
                    throw new ServiceException(messageService.getErrorUnico(responsableCompra.getNombre()))
                }
                responsableCompra = ResponsableCompra.findById(responsableCompraDTO.getIdResponsableCompra());
                if (responsableCompra == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Responsable compra", responsableCompraDTO.getIdResponsableCompra()))
                }
                responsableCompra.setNombre(responsableCompraDTO.getNombre());
                responsableCompra.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    ResponsableCompraDTO obtener(Long idResponsableCompra)  throws ServiceException{
        try {
            ResponsableCompra responsableCompra = ResponsableCompra.findById(idResponsableCompra);
            if (responsableCompra == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Responsable compra", idResponsableCompra));
            }
            return new ResponsableCompraDTO(idResponsableCompra: responsableCompra.getId(), nombre: responsableCompra.getNombre());
        }catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    void eliminar(Long idResponsableCompra) throws ServiceException{
        ResponsableCompra.withTransaction{ statusTransaccion-> 
            try{
                ResponsableCompra responsableCompra = ResponsableCompra.findById(idResponsableCompra);
                if (responsableCompra == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Responsable compra", idResponsableCompra));
                }

                if(utileriaService.nonEmptyList(responsableCompra.plataformasDigitales)){
                    throw new ServiceException(messageService.getErrorEliminacion("Responsable compra", "plataformas digitales"))
                }

                responsableCompra.delete(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }


}
