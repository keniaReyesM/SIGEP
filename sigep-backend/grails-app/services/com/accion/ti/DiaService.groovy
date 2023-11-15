package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException

class DiaService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

   List<Map> listarTodos() throws ServiceException {
       try {
           def criteria = Dia.createCriteria().list() {
               resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
               order("id", "asc")
                projections {
                   property 'id', 'idDia'
                   property 'nombre', 'nombre'
               }
           }
           return criteria;
       } catch (Exception e) {
           LOGGER.error(e.getMessage())
           throw new ServiceException(messageService.getMessageException(e.getMessage()))
       }
       
   }


}
