package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.area.AreaDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class AreaService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

   RespuestaListaDTO<AreaDTO> listarPaginado(PaginacionDTO paginacionDTO) throws ServiceException {

       try {

           String busqueda = "%${paginacionDTO.busqueda}%";
           
           def criteria = Area.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
               resultTransformer(Transformers.aliasToBean(AreaDTO.class))
               order("id", "desc")
               or {
                   ilike("nombre", busqueda)
               }
               eq("empresa", utileriaService.obtenerEmpresaSesion())
               projections {
                   property 'id', 'idArea'
                   property 'nombre', 'nombre'
               }
           }

           return new  RespuestaListaDTO<AreaDTO> (elementos: criteria, total: criteria.totalCount);

       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

   List<AreaDTO> listarTodos() throws ServiceException {
       try {

           def criteria = Area.createCriteria().list() {
               resultTransformer(Transformers.aliasToBean(AreaDTO.class))
               order("id", "desc")
               eq("empresa", utileriaService.obtenerEmpresaSesion())
               projections {
                   property 'id', 'idArea'
                   property 'nombre', 'nombre'
               }
           }
           return criteria;
       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

    void registrar(AreaDTO areaDTO) throws ServiceException{
        Area.withTransaction{ statusTransaccion-> 
            try{
                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Area area = Area.findByNombreAndEmpresa(areaDTO.getNombre(), empresa)
                if(area != null){
                    throw new ServiceException(messageService.getErrorUnico(area.getNombre()))
                }
                area = new Area(nombre: areaDTO.getNombre(), empresa: empresa);
                area.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(AreaDTO areaDTO)  throws ServiceException{
         Area.withTransaction{ statusTransaccion-> 
            try{
                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Area area = Area.findByNombreAndEmpresaAndIdNotEqual(areaDTO.getNombre(), empresa, areaDTO.getIdArea());
                if(area != null){
                    throw new ServiceException(messageService.getErrorUnico(area.getNombre()))
                }
                area = Area.findById(areaDTO.getIdArea());
                if (area == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Area", areaDTO.getIdArea()))
                }
                area.setNombre(areaDTO.getNombre());
                area.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    AreaDTO obtener(Long idArea)  throws ServiceException{
        try {
            Area area = Area.findById(idArea);
            if (area == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Area", idArea));
            }
            return new AreaDTO(idArea: area.getId(), nombre: area.getNombre());
        }catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    void eliminar(Long idArea) throws ServiceException{
        Area.withTransaction{ statusTransaccion-> 
            try{
                Area area = Area.findById(idArea);
                if (area == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Area", idArea));
                }

                if(utileriaService.nonEmptyList(area.areaEmpleados)){
                    throw new ServiceException(messageService.getErrorEliminacion("Area", "Empleados"))
                }

                area.delete(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }


}
