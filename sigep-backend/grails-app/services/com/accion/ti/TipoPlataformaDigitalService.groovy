package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.tipoPlataformaDigital.TipoPlataformaDigitalDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class TipoPlataformaDigitalService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

   RespuestaListaDTO<TipoPlataformaDigitalDTO> listarPaginado(PaginacionDTO paginacionDTO) throws ServiceException {

       try {

           String busqueda = "%${paginacionDTO.busqueda}%";
           
           def criteria = TipoPlataformaDigital.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
               resultTransformer(Transformers.aliasToBean(TipoPlataformaDigitalDTO.class))
               order("id", "desc")
               or {
                   ilike("nombre", busqueda)
               }
               projections {
                   property 'id', 'idTipoPlataformaDigital'
                   property 'nombre', 'nombre'
               }
           }

           return new  RespuestaListaDTO<TipoPlataformaDigitalDTO> (elementos: criteria, total: criteria.totalCount);

       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

   List<TipoPlataformaDigitalDTO> listarTodos() throws ServiceException {
       try {

           def criteria = TipoPlataformaDigital.createCriteria().list() {
               resultTransformer(Transformers.aliasToBean(TipoPlataformaDigitalDTO.class))
               order("id", "desc")
               projections {
                   property 'id', 'idTipoPlataformaDigital'
                   property 'nombre', 'nombre'
               }
           }
           return criteria;
       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

    void registrar(TipoPlataformaDigitalDTO tipoPlataformaDigitalDTO) throws ServiceException{
        TipoPlataformaDigital.withTransaction{ statusTransaccion-> 
            try{
                TipoPlataformaDigital tipoPlataformaDigital = TipoPlataformaDigital.findByNombre(tipoPlataformaDigitalDTO.getNombre())
                if(tipoPlataformaDigital != null){
                    throw new ServiceException(messageService.getErrorUnico(tipoPlataformaDigital.getNombre()))
                }
                tipoPlataformaDigital = new TipoPlataformaDigital(nombre: tipoPlataformaDigitalDTO.getNombre());
                tipoPlataformaDigital.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(TipoPlataformaDigitalDTO tipoPlataformaDigitalDTO)  throws ServiceException{
         TipoPlataformaDigital.withTransaction{ statusTransaccion-> 
            try{
                TipoPlataformaDigital tipoPlataformaDigital = TipoPlataformaDigital.findByNombreAndIdNotEqual(tipoPlataformaDigitalDTO.getNombre(), tipoPlataformaDigitalDTO.getIdTipoPlataformaDigital());
                if(tipoPlataformaDigital != null){
                    throw new ServiceException(messageService.getErrorUnico(tipoPlataformaDigital.getNombre()))
                }
                tipoPlataformaDigital = TipoPlataformaDigital.findById(tipoPlataformaDigitalDTO.getIdTipoPlataformaDigital());
                if (tipoPlataformaDigital == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("TipoPlataformaDigital", tipoPlataformaDigitalDTO.getIdTipoPlataformaDigital()))
                }
                tipoPlataformaDigital.setNombre(tipoPlataformaDigitalDTO.getNombre());
                tipoPlataformaDigital.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    TipoPlataformaDigitalDTO obtener(Long idTipoPlataformaDigital)  throws ServiceException{
        try {
            TipoPlataformaDigital tipoPlataformaDigital = TipoPlataformaDigital.findById(idTipoPlataformaDigital);
            if (tipoPlataformaDigital == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("TipoPlataformaDigital", idTipoPlataformaDigital));
            }
            return new TipoPlataformaDigitalDTO(idTipoPlataformaDigital: tipoPlataformaDigital.getId(), nombre: tipoPlataformaDigital.getNombre());
        }catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    void eliminar(Long idTipoPlataformaDigital) throws ServiceException{
        TipoPlataformaDigital.withTransaction{ statusTransaccion-> 
            try{
                TipoPlataformaDigital tipoPlataformaDigital = TipoPlataformaDigital.findById(idTipoPlataformaDigital);
                if (tipoPlataformaDigital == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("TipoPlataformaDigital", idTipoPlataformaDigital));
                }

                if(utileriaService.nonEmptyList(tipoPlataformaDigital.plataformasDigitales)){
                    throw new ServiceException(messageService.getErrorEliminacion("TipoPlataformaDigital", "Empleados"))
                }

                tipoPlataformaDigital.delete(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }


}
