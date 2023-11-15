package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.puesto.PuestoDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class PuestoService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

   RespuestaListaDTO<PuestoDTO> listarPaginado(PaginacionDTO paginacionDTO) throws ServiceException {

       try {

           String busqueda = "%${paginacionDTO.busqueda}%";
           
           def criteria = Puesto.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
               resultTransformer(Transformers.aliasToBean(PuestoDTO.class))
               order("id", "desc")
               or {
                   ilike("nombre", busqueda)
               }
               eq("empresa", utileriaService.obtenerEmpresaSesion())
               projections {
                   property 'id', 'idPuesto'
                   property 'nombre', 'nombre'
               }
           }

           return new  RespuestaListaDTO<PuestoDTO> (elementos: criteria, total: criteria.totalCount);

       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

    List<PuestoDTO> listarTodos() throws ServiceException {
       try {

           def criteria = Puesto.createCriteria().list() {
               resultTransformer(Transformers.aliasToBean(PuestoDTO.class))
               order("id", "desc")
               eq("empresa", utileriaService.obtenerEmpresaSesion())
                projections {
                   property 'id', 'idPuesto'
                   property 'nombre', 'nombre'
               }
           }
           return criteria;
       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
    }

    void registrar(PuestoDTO puestoDTO) throws ServiceException{
        Puesto.withTransaction{ statusTransaccion-> 
            try{
                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Puesto puesto = Puesto.findByNombreAndEmpresa(puestoDTO.getNombre(), empresa)
                if(puesto != null){
                    throw new ServiceException(messageService.getErrorUnico(puesto.getNombre()))
                }
                puesto = new Puesto(nombre: puestoDTO.getNombre(), empresa: empresa);
                puesto.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(PuestoDTO puestoDTO)  throws ServiceException{
         Puesto.withTransaction{ statusTransaccion-> 
            try{
                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Puesto puesto = Puesto.findByNombreAndEmpresaAndIdNotEqual(puestoDTO.getNombre(), empresa, puestoDTO.getIdPuesto());
                if(puesto != null){
                    throw new ServiceException(messageService.getErrorUnico(puesto.getNombre()))
                }
                puesto = Puesto.findById(puestoDTO.getIdPuesto());
                if (puesto == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Puesto", puestoDTO.getIdPuesto()))
                }
                puesto.setNombre(puestoDTO.getNombre());
                puesto.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    PuestoDTO obtener(Long idPuesto)  throws ServiceException{
        try {
            Puesto puesto = Puesto.findById(idPuesto);
            if (puesto == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Puesto", idPuesto));
            }
            return new PuestoDTO(idPuesto: puesto.getId(), nombre: puesto.getNombre());
        }catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    void eliminar(Long idPuesto) throws ServiceException{
        Puesto.withTransaction{ statusTransaccion-> 
            try{
                Puesto puesto = Puesto.findById(idPuesto);
                if (puesto == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Puesto", idPuesto));
                }

                if(utileriaService.nonEmptyList(puesto.empleados)){
                    throw new ServiceException(messageService.getErrorEliminacion("Puesto", "Empleados"))
                }

                puesto.delete(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }


}
