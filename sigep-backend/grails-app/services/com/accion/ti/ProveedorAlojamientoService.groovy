package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.proveedorAlojamiento.ProveedorAlojamientoDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class ProveedorAlojamientoService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

   RespuestaListaDTO<ProveedorAlojamientoDTO> listarPaginado(PaginacionDTO paginacionDTO) throws ServiceException {

       try {

           String busqueda = "%${paginacionDTO.busqueda}%";
           
           def criteria = ProveedorAlojamiento.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
               resultTransformer(Transformers.aliasToBean(ProveedorAlojamientoDTO.class))
               order("id", "desc")
               or {
                   ilike("nombre", busqueda)
               }
               projections {
                   property 'id', 'idProveedorAlojamiento'
                   property 'nombre', 'nombre'
               }
           }

           return new  RespuestaListaDTO<ProveedorAlojamientoDTO> (elementos: criteria, total: criteria.totalCount);

       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

   List<ProveedorAlojamientoDTO> listarTodos() throws ServiceException {
       try {

           def criteria = ProveedorAlojamiento.createCriteria().list() {
               resultTransformer(Transformers.aliasToBean(ProveedorAlojamientoDTO.class))
               order("id", "desc")
               projections {
                   property 'id', 'idProveedorAlojamiento'
                   property 'nombre', 'nombre'
               }
           }
           return criteria;
       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

    void registrar(ProveedorAlojamientoDTO proveedorAlojamientoDTO) throws ServiceException{
        ProveedorAlojamiento.withTransaction{ statusTransaccion-> 
            try{
                ProveedorAlojamiento proveedorAlojamiento = ProveedorAlojamiento.findByNombre(proveedorAlojamientoDTO.getNombre())
                if(proveedorAlojamiento != null){
                    throw new ServiceException(messageService.getErrorUnico(proveedorAlojamiento.getNombre()))
                }
                proveedorAlojamiento = new ProveedorAlojamiento(nombre: proveedorAlojamientoDTO.getNombre());
                proveedorAlojamiento.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(ProveedorAlojamientoDTO proveedorAlojamientoDTO)  throws ServiceException{
         ProveedorAlojamiento.withTransaction{ statusTransaccion-> 
            try{
                ProveedorAlojamiento proveedorAlojamiento = ProveedorAlojamiento.findByNombreAndIdNotEqual(proveedorAlojamientoDTO.getNombre(), proveedorAlojamientoDTO.getIdProveedorAlojamiento());
                if(proveedorAlojamiento != null){
                    throw new ServiceException(messageService.getErrorUnico(proveedorAlojamiento.getNombre()))
                }
                proveedorAlojamiento = ProveedorAlojamiento.findById(proveedorAlojamientoDTO.getIdProveedorAlojamiento());
                if (proveedorAlojamiento == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Proveedor alojamiento", proveedorAlojamientoDTO.getIdProveedorAlojamiento()))
                }
                proveedorAlojamiento.setNombre(proveedorAlojamientoDTO.getNombre());
                proveedorAlojamiento.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    ProveedorAlojamientoDTO obtener(Long idProveedorAlojamiento)  throws ServiceException{
        try {
            ProveedorAlojamiento proveedorAlojamiento = ProveedorAlojamiento.findById(idProveedorAlojamiento);
            if (proveedorAlojamiento == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Proveedor alojamiento", idProveedorAlojamiento));
            }
            return new ProveedorAlojamientoDTO(idProveedorAlojamiento: proveedorAlojamiento.getId(), nombre: proveedorAlojamiento.getNombre());
        }catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    void eliminar(Long idProveedorAlojamiento) throws ServiceException{
        ProveedorAlojamiento.withTransaction{ statusTransaccion-> 
            try{
                ProveedorAlojamiento proveedorAlojamiento = ProveedorAlojamiento.findById(idProveedorAlojamiento);
                if (proveedorAlojamiento == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Proveedor alojamiento", idProveedorAlojamiento));
                }

                if(utileriaService.nonEmptyList(proveedorAlojamiento.plataformasDigitales)){
                    throw new ServiceException(messageService.getErrorEliminacion("Proveedor alojamiento", "Plataformas digitales"))
                }

                proveedorAlojamiento.delete(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }


}
