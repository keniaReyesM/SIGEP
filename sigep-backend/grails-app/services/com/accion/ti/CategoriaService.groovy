package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.categoria.CategoriaDTO;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class CategoriaService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

   RespuestaListaDTO<CategoriaDTO> listarPaginado(PaginacionDTO paginacionDTO) throws ServiceException {

       try {

           String busqueda = "%${paginacionDTO.busqueda}%";
           
           def criteria = Categoria.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
               resultTransformer(Transformers.aliasToBean(CategoriaDTO.class))
               order("id", "desc")
               or {
                   ilike("nombre", busqueda)
                   ilike("descripcion", busqueda)

               }
               eq("empresa", utileriaService.obtenerEmpresaSesion())
               projections {
                   property 'id', 'idCategoria'
                   property 'nombre', 'nombre'
                   property 'descripcion', 'descripcion'
               }
           }

           return new  RespuestaListaDTO<CategoriaDTO> (elementos: criteria, total: criteria.totalCount);

       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }

   
   List<Map> listarTodos() throws ServiceException {
    try{
        Empresa empresaSesion =  utileriaService.obtenerEmpresaSesion();
        def lista = Categoria.createCriteria().list() {
            resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
            order("nombre", "asc")
            eq("empresa", empresaSesion)
            projections {
                property 'id', 'idCategoria'
                property 'nombre', 'nombre'
            }
        }
        return lista
    }catch (Exception e) {
        LOGGER.error(messageService.getMessageErrors(e))
        throw new ServiceException(messageService.getMessageErrors(e))
    }
    
   }

    void registrar(CategoriaDTO categoriaDTO) throws ServiceException{
        Categoria.withTransaction{ statusTransaccion-> 
            try{
                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Categoria categoria = Categoria.findByNombreAndEmpresa(categoriaDTO.getNombre(), empresa)
                if(categoria != null){
                    throw new ServiceException(messageService.getErrorUnico(categoria.getNombre()))
                }
                categoria = new Categoria(nombre: categoriaDTO.getNombre(), 
                                          descripcion: categoriaDTO.getDescripcion(), 
                                          empresa: empresa);
                categoria.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(CategoriaDTO categoriaDTO)  throws ServiceException{
         Categoria.withTransaction{ statusTransaccion-> 
            try{
                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Categoria categoria = Categoria.findByNombreAndEmpresaAndIdNotEqual(categoriaDTO.getNombre(),empresa,
                                                                 categoriaDTO.getIdCategoria());
                if(categoria != null){
                    throw new ServiceException(messageService.getErrorUnico(categoria.getNombre()))
                }
                categoria = Categoria.findById(categoriaDTO.getIdCategoria());
                if (categoria == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Categoria", categoriaDTO.getIdCategoria()))
                }
                categoria.setNombre(categoriaDTO.getNombre());
                categoria.setDescripcion(categoriaDTO.getDescripcion());
                categoria.save(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    CategoriaDTO obtener(Long idCategoria)  throws ServiceException{
        try {
            Categoria categoria = Categoria.findById(idCategoria);
            if (categoria == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Categoria", idCategoria));
            }
            return new CategoriaDTO(idCategoria: categoria.getId(), nombre: categoria.getNombre(), descripcion: categoria.getDescripcion());
        }catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    void eliminar(Long idCategoria) throws ServiceException{
        Categoria.withTransaction{ statusTransaccion-> 
            try{
                Categoria categoria = Categoria.findById(idCategoria);
                if (categoria == null) {
                   throw new ServiceException(messageService.getErrorNoEncontrado("Categoria", idCategoria));
                }

                if(utileriaService.nonEmptyList(categoria.proyectos)){
                    throw new ServiceException(messageService.getErrorEliminacion("Categoria", "Proyectos"))
                }

                categoria.delete(flush: true, failOnError: true);
            }catch(Exception e){
               statusTransaccion.setRollbackOnly()
               LOGGER.error(messageService.getMessageErrors(e))
               throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }


}
