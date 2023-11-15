package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.tecnologiaDesarrollo.TecnologiaDesarrolloDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class TecnologiaDesarrolloService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

   RespuestaListaDTO<TecnologiaDesarrolloDTO> listarPaginado(PaginacionDTO paginacionDTO) throws ServiceException {

       try {

           String busqueda = "%${paginacionDTO.busqueda}%";
           
           def criteria = TecnologiaDesarrollo.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
               resultTransformer(Transformers.aliasToBean(TecnologiaDesarrolloDTO.class))
               order("id", "desc")
               or {
                   ilike("nombre", busqueda)
               }
               projections {
                   property 'id', 'idTecnologiaDesarrollo'
                   property 'nombre', 'nombre'
               }
           }

           return new  RespuestaListaDTO<TecnologiaDesarrolloDTO> (elementos: criteria, total: criteria.totalCount);

       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

   List<TecnologiaDesarrolloDTO> listarTodos() throws ServiceException {
       try {

           def criteria = TecnologiaDesarrollo.createCriteria().list() {
               resultTransformer(Transformers.aliasToBean(TecnologiaDesarrolloDTO.class))
               order("id", "desc")
               projections {
                   property 'id', 'idTecnologiaDesarrollo'
                   property 'nombre', 'nombre'
               }
           }
           return criteria;
       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

    void registrar(TecnologiaDesarrolloDTO tecnologiaDesarrolloDTO) throws ServiceException{
        TecnologiaDesarrollo.withTransaction{ statusTransaccion-> 
            try{
                TecnologiaDesarrollo tecnologiaDesarrollo = TecnologiaDesarrollo.findByNombre(tecnologiaDesarrolloDTO.getNombre())
                if(tecnologiaDesarrollo != null){
                    throw new ServiceException(messageService.getErrorUnico(tecnologiaDesarrollo.getNombre()))
                }
                tecnologiaDesarrollo = new TecnologiaDesarrollo(nombre: tecnologiaDesarrolloDTO.getNombre());
                tecnologiaDesarrollo.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(TecnologiaDesarrolloDTO tecnologiaDesarrolloDTO)  throws ServiceException{
         TecnologiaDesarrollo.withTransaction{ statusTransaccion-> 
            try{
                TecnologiaDesarrollo tecnologiaDesarrollo = TecnologiaDesarrollo.findByNombreAndIdNotEqual(tecnologiaDesarrolloDTO.getNombre(), tecnologiaDesarrolloDTO.getIdTecnologiaDesarrollo());
                if(tecnologiaDesarrollo != null){
                    throw new ServiceException(messageService.getErrorUnico(tecnologiaDesarrollo.getNombre()))
                }
                tecnologiaDesarrollo = TecnologiaDesarrollo.findById(tecnologiaDesarrolloDTO.getIdTecnologiaDesarrollo());
                if (tecnologiaDesarrollo == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Proveedor alojamiento", tecnologiaDesarrolloDTO.getIdTecnologiaDesarrollo()))
                }
                tecnologiaDesarrollo.setNombre(tecnologiaDesarrolloDTO.getNombre());
                tecnologiaDesarrollo.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    TecnologiaDesarrolloDTO obtener(Long idTecnologiaDesarrollo)  throws ServiceException{
        try {
            TecnologiaDesarrollo tecnologiaDesarrollo = TecnologiaDesarrollo.findById(idTecnologiaDesarrollo);
            if (tecnologiaDesarrollo == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Proveedor alojamiento", idTecnologiaDesarrollo));
            }
            return new TecnologiaDesarrolloDTO(idTecnologiaDesarrollo: tecnologiaDesarrollo.getId(), nombre: tecnologiaDesarrollo.getNombre());
        }catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    void eliminar(Long idTecnologiaDesarrollo) throws ServiceException{
        TecnologiaDesarrollo.withTransaction{ statusTransaccion-> 
            try{
                TecnologiaDesarrollo tecnologiaDesarrollo = TecnologiaDesarrollo.findById(idTecnologiaDesarrollo);
                if (tecnologiaDesarrollo == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Proveedor alojamiento", idTecnologiaDesarrollo));
                }

                if(utileriaService.nonEmptyList(tecnologiaDesarrollo.tecnologiaDesarrolloPlataformasDigitales)){
                    throw new ServiceException(messageService.getErrorEliminacion("Proveedor alojamiento", "Plataformas digitales"))
                }

                tecnologiaDesarrollo.delete(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }


}
