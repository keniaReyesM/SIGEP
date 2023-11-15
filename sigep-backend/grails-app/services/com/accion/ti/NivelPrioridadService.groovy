package com.accion.ti

import com.accion.ti.dto.exception.ServiceException
import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers

class NivelPrioridadService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService

   List<Map> listarTodos() throws ServiceException {
       try {
           def criteria = NivelPrioridad.createCriteria().list() {
               resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
               order("id", "asc")
                projections {
                   property 'id', 'idNivelPrioridad'
                   property 'nombre', 'nombre'
                   property 'prioridad', 'prioridad'
               }
           }
           return criteria;
       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }


}
