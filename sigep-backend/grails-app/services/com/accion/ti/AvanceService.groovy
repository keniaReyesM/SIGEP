package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class AvanceService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService


    RespuestaListaDTO<Map> listarPaginado(PaginacionDTO parametros,  Long idTarea) throws ServiceException {

        try {

            String busqueda = "%${parametros.busqueda ?: ""}%";


            println "idTarea ${idTarea}"

            def criteria = Avance.createCriteria().list(max: parametros.maximoResultados, offset: parametros.cantidadOmitir) {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("fechaAvance", "desc")

                createAlias 'asignacionTarea', 'asignacionTarea'
                createAlias 'asignacionTarea.empleado', 'empleado'
                createAlias 'asignacionTarea.tarea', 'tarea'
                createAlias 'empleado.persona', 'persona'

                eq("tarea.id", idTarea)

                or {
                    ilike("persona.nombreCompleto", busqueda)
                }

                projections {
                    property 'id', 'idAvance'
                    property 'fechaRegistro', 'fechaRegistro'
                    property 'fechaAvance', 'fechaAvance'
                    property 'horaInicio', 'horaInicio'
                    property 'horaFin', 'horaFin'
                    property 'persona.nombreCompleto', 'empleado'
                }
            }


            return new RespuestaListaDTO<Map>(elementos: criteria, total: criteria.totalCount);

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }

    }
    
    

    void registrar(def avanceJSON) throws ServiceException {
        Avance.withTransaction { statusTransaccion ->
            try {


                Empleado empleado = utileriaService.obtenerEmpleadoSesion();
                Tarea tarea = Tarea.findById(avanceJSON?.idTarea?.toLong()?:0L);
               
                if (tarea == null) {
                    throw new ServiceException(messageService.getErrorNoEncontrado(avanceJSON.idArea))
                }


                AsignacionTarea  asignacionTarea = AsignacionTarea.findByEmpleadoAndTarea(empleado, tarea);
                if(asignacionTarea == null){
                    throw new ServiceException("Empleado no asignado a la tarea.");
                }

                Avance avance = new Avance();
                avance.fechaRegistro = new Date();
                avance.fechaAvance = utileriaService.parsearDato(avanceJSON.fechaAvance);
                avance.horaInicio = utileriaService.parsearDato(avanceJSON.horaInicio);
                avance.horaFin = utileriaService.parsearDato(avanceJSON.horaFin);
                avance.empleadoRegistro = empleado;
                avance.asignacionTarea = asignacionTarea;
                avance.save(flush: true, failOnError: true);

            } catch (Exception e) {
                statusTransaccion.setRollbackOnly()
                LOGGER.error(messageService.getMessageErrors(e))
                e.printStackTrace();
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void eliminar(def avanceJSON) throws ServiceException {
        Avance.withTransaction { statusTransaccion ->
            try {

                Empleado empleado = utileriaService.obtenerEmpleadoSesion();
                Avance avance = Avance.findById(avanceJSON?.idAvance?.toLong()?:0L);

                if (avance == null) {
                    throw new ServiceException(messageService.getErrorNoEncontrado(avanceJSON.idAvance))
                }

                if(avance.asignacionTarea.empleado.id != empleado.id){
                    throw new ServiceException("Empleado no asignado a la tarea.");
                }

                avance.delete(flush: true, failOnError: true);

            } catch (Exception e) {
                statusTransaccion.setRollbackOnly()
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

   

}
