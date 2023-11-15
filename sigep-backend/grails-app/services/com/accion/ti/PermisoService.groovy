package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException

class PermisoService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

   List<Map> listarTodos() throws ServiceException {
       try {
            // def permisos = construirPermisos(Permiso.findAllByPermiso(null))
            return []
       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
   }





}
