package com.accion.ti

import com.accion.ti.dto.exception.ServiceException
import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers

class EstadoService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService


    List<Map> listarPorTipo(String nombreEstado) {
        try {
            def criteria = Estado.createCriteria().list() {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("id", "asc")
                tipoEstado{
                    eq("nombre", nombreEstado)
                }
                projections {
                    property 'id', 'idEstado'
                    property 'nombre', 'nombre'
                    property 'porcentaje', 'porcentaje'
                    property 'color', 'color'
                }
            }
            return criteria;
        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }
    }
}
