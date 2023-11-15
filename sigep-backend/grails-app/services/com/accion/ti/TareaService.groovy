package com.accion.ti

import com.accion.ti.enums.RolEnum
import com.accion.ti.enums.TipoTareaEnum
import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class TareaService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService


    RespuestaListaDTO<Map> listarPaginado(PaginacionDTO parametros) throws ServiceException {

        try {

            Date fechaInicio = utileriaService.parsearDato(parametros.fechaInicio);
            Date fechaFin = utileriaService.parsearDato(parametros.fechaFin);
            String busqueda = "%${parametros.busqueda ?: ""}%";


            Empleado empleadoSesion = Empleado.findById(utileriaService.obtenerEmpleadoSesion().id);
            Rol rolSesion = UsuarioRol.findByUsuario(empleadoSesion.usuario).rol;


            def criteria = Tarea.createCriteria().list(max: parametros.maximoResultados, offset: parametros.cantidadOmitir) {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("fechaHoraInicio", "asc")

                createAlias 'estado', 'estado'
                createAlias 'empleado', 'empleado'
                createAlias 'empleado.persona', 'persona'
                createAlias 'proyecto', 'proyecto'
                createAlias 'nivelPrioridad', 'nivelPrioridad'

                eq("proyecto.empresa", utileriaService.obtenerEmpresaSesion())
                if (fechaInicio && fechaFin) {
                    or {
                        between("fechaHoraInicio", fechaInicio, fechaFin)
                        between("fechaHoraFin", fechaInicio, fechaFin)
                    }
                }
                switch (rolSesion.authority) {
                    case RolEnum.ADMINISTRADOR.getRole():
                        // Se listan todos los tareas del usuario sin importar a quien pertenecen.
                        break;
                    default:
                        asignacionTareas {
                            eq("empleado", empleadoSesion)
                        }
                        break;
                }
                or {
                    ilike("clave", busqueda)
                    ilike("nombre", busqueda)
                    ilike("descripcion", busqueda)
                    ilike("proyecto.clave", busqueda)
                    ilike("proyecto.nombre", busqueda)
                    ilike("persona.nombreCompleto", busqueda)
                }
                projections {
                    property 'id', 'idTarea'
                    property 'clave', 'clave'
                    property 'nombre', 'nombre'
                    property 'descripcion', 'descripcion'
                    property 'fechaHoraInicio', 'fechaHoraInicio'
                    property 'fechaHoraFin', 'fechaHoraFin'
                    property 'fechaRegistro', 'fechaRegistro'
                    property 'estado.color', 'color'
                    property 'estado.nombre', 'estado'
                    property 'estado.porcentaje', 'porcentaje'
                    property 'proyecto.clave', 'claveProyecto'
                    property 'proyecto.nombre', 'nombreProyecto'
                    property 'persona.nombreCompleto', 'empleado'
                }
            }


            return new RespuestaListaDTO<Map>(elementos: criteria, total: criteria.totalCount);

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }

    }
    
    RespuestaListaDTO<Map> listarTareasArbol(PaginacionDTO parametros) throws ServiceException {

        try {

            Date fechaInicio = utileriaService.parsearDato(parametros.fechaInicio);
            Date fechaFin = utileriaService.parsearDato(parametros.fechaFin);
            String busqueda = "%${parametros.busqueda ?: ""}%";


            Empleado empleadoSesion = Empleado.findById(utileriaService.obtenerEmpleadoSesion().id);
            Rol rolSesion = UsuarioRol.findByUsuario(empleadoSesion.usuario).rol;
            Empresa empresa = utileriaService.obtenerEmpresaSesion();


            def criteria = Tarea.createCriteria().list(max: parametros.maximoResultados, offset: parametros.cantidadOmitir) {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("fechaHoraInicio", "asc")

                createAlias 'estado', 'estado'
                createAlias 'empleado', 'empleado'
                createAlias 'empleado.persona', 'persona'
                createAlias 'proyecto', 'proyecto'
                createAlias 'nivelPrioridad', 'nivelPrioridad'

                eq("proyecto.empresa", empresa)
                isNull("tarea")

                if (fechaInicio && fechaFin) {
                    or {
                        between("fechaHoraInicio", fechaInicio, fechaFin)
                        between("fechaHoraFin", fechaInicio, fechaFin)
                    }
                }
                switch (rolSesion.authority) {
                    case RolEnum.ADMINISTRADOR.getRole():
                        // Se listan todos los tareas del usuario sin importar a quien pertenecen.
                        break;
                    default:
                        asignacionTareas {
                            eq("empleado", empleadoSesion)
                        }
                        break;
                }
                or {
                    ilike("clave", busqueda)
                    ilike("nombre", busqueda)
                    ilike("descripcion", busqueda)
                    ilike("proyecto.clave", busqueda)
                    ilike("proyecto.nombre", busqueda)
                    ilike("persona.nombreCompleto", busqueda)
                }
                projections {
                    property 'id', 'idTarea'
                    property 'clave', 'clave'
                    property 'nombre', 'nombre'
                    property 'descripcion', 'descripcion'
                    property 'fechaHoraInicio', 'fechaHoraInicio'
                    property 'fechaHoraFin', 'fechaHoraFin'
                    property 'fechaRegistro', 'fechaRegistro'
                    property 'estado.color', 'color'
                    property 'estado.nombre', 'estado'
                    property 'estado.porcentaje', 'porcentaje'
                    property 'proyecto.clave', 'claveProyecto'
                    property 'proyecto.nombre', 'nombreProyecto'
                    property 'persona.nombreCompleto', 'empleado'
                }
            }

            Long total = criteria.totalCount;
            List<Map> tareas = criteria.collect{ tarea->
                tarea.subtareas = obtenerTareasPorTarea(tarea.idTarea, fechaInicio, fechaFin, busqueda, empleadoSesion, rolSesion, empresa);
                return tarea;
            }


            return new RespuestaListaDTO<Map>(elementos: tareas, total: total);

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }

    }

    List<Map> obtenerTareasPorTarea(Long idTarea, Date fechaInicio, Date fechaFin, String busqueda, Empleado empleadoSesion, Rol rolSesion, Empresa empresa) throws ServiceException{
        try{
            List<Map> tareas = Tarea.createCriteria().list() {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("fechaHoraInicio", "asc")

                createAlias 'estado', 'estado'
                createAlias 'empleado', 'empleado'
                createAlias 'empleado.persona', 'persona'
                createAlias 'proyecto', 'proyecto'
                createAlias 'nivelPrioridad', 'nivelPrioridad'

                eq("proyecto.empresa", empresa)
               tarea{
                    idEq(idTarea)
               }

                if (fechaInicio && fechaFin) {
                    or {
                        between("fechaHoraInicio", fechaInicio, fechaFin)
                        between("fechaHoraFin", fechaInicio, fechaFin)
                    }
                }
                switch (rolSesion.authority) {
                    case RolEnum.ADMINISTRADOR.getRole():
                        // Se listan todos los tareas del usuario sin importar a quien pertenecen.
                        break;
                    default:
                        asignacionTareas {
                            eq("empleado", empleadoSesion)
                        }
                        break;
                }

                projections {
                    property 'id', 'idTarea'
                    property 'clave', 'clave'
                    property 'nombre', 'nombre'
                    property 'descripcion', 'descripcion'
                    property 'fechaHoraInicio', 'fechaHoraInicio'
                    property 'fechaHoraFin', 'fechaHoraFin'
                    property 'fechaRegistro', 'fechaRegistro'
                    property 'estado.color', 'color'
                    property 'estado.nombre', 'estado'
                    property 'estado.porcentaje', 'porcentaje'
                    property 'proyecto.clave', 'claveProyecto'
                    property 'proyecto.nombre', 'nombreProyecto'
                    property 'persona.nombreCompleto', 'empleado'
                }
            }.collect{ tarea->
                tarea.subtareas = obtenerTareasPorTarea(tarea.idTarea, fechaInicio, fechaFin, busqueda, empleadoSesion, rolSesion, empresa);
                return tarea;
            }
            return tareas;

        }catch(Exception e){
            LOGGER.error(e.getMessage())
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }

    }
    
    RespuestaListaDTO<Map> listarTareasEvento(PaginacionDTO parametros) throws ServiceException {

        try {

            Date fechaInicio = utileriaService.parsearDato(parametros.fechaInicio);
            Date fechaFin = utileriaService.parsearDato(parametros.fechaFin);
            String busqueda = "%${parametros.busqueda ?: ""}%";


            Empleado empleadoSesion = Empleado.findById(utileriaService.obtenerEmpleadoSesion().id);
            Rol rolSesion = UsuarioRol.findByUsuario(empleadoSesion.usuario).rol;


            def criteria = Tarea.createCriteria().list() {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("fechaHoraInicio", "asc")

                createAlias 'estado', 'estado'
                createAlias 'empleado', 'empleado'
                createAlias 'empleado.persona', 'persona'
                createAlias 'proyecto', 'proyecto'
                createAlias 'nivelPrioridad', 'nivelPrioridad'

                eq("proyecto.empresa", utileriaService.obtenerEmpresaSesion())
                if (fechaInicio && fechaFin) {
                    or {
                        between("fechaHoraInicio", fechaInicio, fechaFin)
                        between("fechaHoraFin", fechaInicio, fechaFin)
                    }
                }
                switch (rolSesion.authority) {
                    case RolEnum.ADMINISTRADOR.getRole():
                        // Se listan todos los tareas del usuario sin importar a quien pertenecen.
                        break;
                    default:
                        asignacionTareas {
                            eq("empleado", empleadoSesion)
                        }
                        break;
                }
                or {
                    ilike("clave", busqueda)
                    ilike("nombre", busqueda)
                    ilike("descripcion", busqueda)
                    ilike("proyecto.clave", busqueda)
                    ilike("proyecto.nombre", busqueda)
                    ilike("estado.nombre", busqueda)
                    switch (rolSesion.authority) {
                        case RolEnum.ADMINISTRADOR.getRole():
                            ilike("persona.nombreCompleto", busqueda)
                            break;
                    }
                }
                projections {
                    property 'id', 'idTarea'
                    property 'clave', 'clave'
                    property 'nombre', 'nombre'
                    property 'descripcion', 'descripcion'
                    property 'fechaHoraInicio', 'fechaHoraInicio'
                    property 'fechaHoraFin', 'fechaHoraFin'
                    property 'fechaRegistro', 'fechaRegistro'
                    property 'estado.color', 'color'
                    property 'estado.nombre', 'estado'
                    property 'estado.porcentaje', 'porcentaje'
                    property 'proyecto.clave', 'claveProyecto'
                    property 'proyecto.nombre', 'nombreProyecto'
                    property 'persona.nombreCompleto', 'empleado'
                }
            }


            return new RespuestaListaDTO<Map>(elementos: criteria, total: criteria.size());

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }

    }


    List<Map> listarTareasPorProyecto(Long idProyecto, Long idTareaOrigen) throws ServiceException {

        try {

            Proyecto proyecto = Proyecto.findById(idProyecto);
            if (proyecto == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Proyecto", idProyecto));
            }


            def criteria = Tarea.createCriteria().list() {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("fechaHoraInicio", "asc")

                createAlias 'estado', 'estado'
                createAlias 'nivelPrioridad', 'nivelPrioridad'

                eq("proyecto", proyecto)
                if(idTareaOrigen){
                    not{
                        idEq(idTareaOrigen)
                    }
                }
                projections {
                    property 'id', 'idTarea'
                    property 'clave', 'clave'
                    property 'nombre', 'nombre'
                    property 'descripcion', 'descripcion'
                    property 'fechaHoraInicio', 'fechaHoraInicio'
                    property 'fechaHoraFin', 'fechaHoraFin'
                    property 'fechaRegistro', 'fechaRegistro'
                    property 'estado.color', 'color'
                    property 'estado.nombre', 'estado'
                    property 'estado.porcentaje', 'estado.porcentaje'
                }
            }


            return criteria;

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }

    }

    List<Map> listarTareasReutilizar() throws ServiceException {

        try {


            def criteria = Tarea.createCriteria().list() {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("clave", "asc")

                createAlias 'proyecto', 'proyecto'

                projections {
                    property 'id', 'idTarea'
                    property 'clave', 'clave'
                    property 'nombre', 'nombre'
                    property 'proyecto.clave', 'claveProyecto'
                    property 'proyecto.nombre', 'nombreProyecto'
                }
            }


            return criteria;

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }

    }


    void registrar(def tareaJSON) throws ServiceException {
        Tarea.withTransaction { statusTransaccion ->
            try {

                Empleado empleado = utileriaService.obtenerEmpleadoSesion();
                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Proyecto proyecto = Proyecto.findById(tareaJSON.idProyecto.toLong());

                def criteria = Tarea.createCriteria().list(max: 1) {
                    order("id", "desc")
                }

                Integer numero = (criteria[0]?.id?:0)+1;

                String consecutivo = "${numero}".length() == 1 ? "-0${numero}": "-${numero}";
                String clave = "${utileriaService.obtenerPrefijo(proyecto.nombre)}${consecutivo}"




                Tarea tarea = Tarea.findByClaveAndProyecto(clave, proyecto);
                

                if (tarea != null) {
                    throw new ServiceException(messageService.getErrorUnico(clave))
                }


                tarea = new Tarea();
                tarea.clave = clave;
                tarea.descripcion = tareaJSON.descripcion;
                tarea.empleado = empleado;
                tarea.estado = Estado.findById(tareaJSON.idEstado.toLong());
                tarea.fechaHoraInicio = utileriaService.parsearDato(tareaJSON.fechaHoraInicio);
                tarea.fechaHoraFin = utileriaService.parsearDato(tareaJSON.fechaHoraFin);
                tarea.fechaRegistro = new Date()
                tarea.nivelPrioridad = NivelPrioridad.findById(tareaJSON.idNivelPrioridad.toLong());
                tarea.nombre = tareaJSON.nombre
                tarea.proyecto = proyecto
                tarea.tipoTarea = TipoTarea.findById(tareaJSON.idTipoTarea.toLong());
                tarea.tarea = tarea.tipoTarea.nombre == TipoTareaEnum.TAREA.getValor() ? null : Tarea.findById(tareaJSON.idTareaOrigen.toLong());
                tarea.save(flush: true, failOnError: true);


                Estado estadoActivo = utileriaService.obtenerEstadoActivo();

                tareaJSON.empleados = tareaJSON.empleados ?: [];
                for (def empleadoJSON : tareaJSON.empleados) {
                    AsignacionTarea asignacionTarea = new AsignacionTarea();
                    asignacionTarea.fechaRegistro = new Date();
                    asignacionTarea.empleadoAsignacion = empleado;
                    asignacionTarea.empleado = Empleado.findById(empleadoJSON.idEmpleado.toLong())
                    asignacionTarea.estado = estadoActivo
                    asignacionTarea.tarea = tarea
                    asignacionTarea.save(flush: true, failOnError: true);
                }

            } catch (Exception e) {
                statusTransaccion.setRollbackOnly()
                LOGGER.error(messageService.getMessageErrors(e))
                e.printStackTrace();
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(def tareaJSON) throws ServiceException {
        Tarea.withTransaction { statusTransaccion ->
            try {

                Empleado empleado = utileriaService.obtenerEmpleadoSesion();
                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Proyecto proyecto = Proyecto.findById(tareaJSON.idProyecto.toLong());
                Tarea tarea = Tarea.findByClaveAndProyectoAndIdNotEqual(tareaJSON.clave, proyecto, tareaJSON.idTarea.toLong());
                
                Estado estadoInactivo = utileriaService.obtenerEstadoInactivo();
                Estado estadoActivo = utileriaService.obtenerEstadoActivo();

                if (tarea != null) {
                    throw new ServiceException(messageService.getErrorUnico(tareaJSON.clave))
                }


                tarea = Tarea.findById(tareaJSON.idTarea.toLong());
                tarea.clave = tareaJSON.clave;
                tarea.descripcion = tareaJSON.descripcion;
                tarea.fechaHoraInicio = utileriaService.parsearDato(tareaJSON.fechaHoraInicio);
                tarea.fechaHoraFin = utileriaService.parsearDato(tareaJSON.fechaHoraFin);
                tarea.nivelPrioridad = NivelPrioridad.findById(tareaJSON.idNivelPrioridad.toLong());
                tarea.nombre = tareaJSON.nombre
                tarea.proyecto = proyecto
                tarea.tipoTarea = TipoTarea.findById(tareaJSON.idTipoTarea.toLong());
                tarea.tarea = tarea.tipoTarea.nombre == TipoTareaEnum.TAREA.getValor() ? null : Tarea.findById(tareaJSON.idTareaOrigen.toLong());
                tarea.save(flush: true, failOnError: true);




                tareaJSON.empleados = tareaJSON.empleados ?: [];
                if (tareaJSON.empleados.size() == 0) {
                    List<AsignacionTarea> empleadosTarea = AsignacionTarea.findAllByTarea(tarea)
                    for (AsignacionTarea asignacionTarea : empleadosTarea) {
                        asignacionTarea.estado = estadoInactivo;
                        asignacionTarea.save(flush: true, failOnError: true);
                    }
                } else {
                    List<Long> idsEmpleados = tareaJSON.empleados.collect { t -> t?.idEmpleado?.toLong() ?: 0 }
                    List<Empleado> empleados = Empleado.findAllByIdInList(idsEmpleados);
                    List<AsignacionTarea> empleadosTarea = AsignacionTarea.findAllByEmpleadoNotInListAndTarea(empleados, tarea);
                    for (AsignacionTarea asignacionTarea : empleadosTarea) {
                        asignacionTarea.estado = estadoInactivo;
                        asignacionTarea.save(flush: true, failOnError: true);
                    }
                }


                for (def empleadoJSON : tareaJSON.empleados) {
                    Empleado empleadoAsignar = Empleado.findById(empleadoJSON.idEmpleado.toLong());
                    AsignacionTarea asignacionTarea = AsignacionTarea.findByEmpleadoAndTarea(empleadoAsignar, tarea);
                    if (asignacionTarea == null) {
                        asignacionTarea = new AsignacionTarea(empleado: empleadoAsignar, tarea: tarea);
                        asignacionTarea.fechaRegistro = new Date();
                        asignacionTarea.empleadoAsignacion = empleado;
                        asignacionTarea.empleado = empleadoAsignar;
                        asignacionTarea.estado = estadoActivo
                        asignacionTarea.tarea = tarea
                        asignacionTarea.save(flush: true, failOnError: true);
                    }else{
                        asignacionTarea.estado = estadoActivo
                        asignacionTarea.save(flush: true, failOnError: true);
                    }
                }

                Estado estado = Estado.findById(tareaJSON.idEstado.toLong());

                if (tarea.estado.nombre != estado.nombre) {
                    new CambioEstado(
                            fechaRegistro: new Date(),
                            empleado: empleado,
                            estadoAnterior: tarea.estado,
                            estadoNuevo: estado,
                            tarea: tarea
                    ).save(flush: true, failOnError: true);
                    tarea.estado = estado;
                    tarea.save(flush: true, failOnError: true);

                    if (estado.porcentaje == "100") {
                        List<Tarea> subtareas = Tarea.findAllByTarea(tarea);
                        for (Tarea subtarea : subtareas) {
                            tareaJSON.idTarea = subtarea.id
                            actualizarEstado(tareaJSON);
                        }
                    }

                }

            } catch (Exception e) {
                statusTransaccion.setRollbackOnly()
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    def obtener(Long idTarea) throws ServiceException {
        try {
            Tarea tarea = Tarea.findById(idTarea);
            if (tarea == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Tarea", idTarea));
            }
            def tareaJSON = [:]

            tareaJSON.idTarea = tarea.id;
            tareaJSON.clave = tarea.clave;
            tareaJSON.nombre = tarea.nombre;
            tareaJSON.descripcion = tarea.descripcion;
            tareaJSON.idEstado = tarea.estado.id;
            tareaJSON.idNivelPrioridad = tarea.nivelPrioridad.id;
            tareaJSON.idProyecto = tarea.proyecto.id;
            tareaJSON.idTareaOrigen = tarea.tarea ? tarea.tarea.id : null;
            tareaJSON.idTipoTarea = tarea.tipoTarea.id;
            tareaJSON.fechaHoraInicio = utileriaService.formatoFechaHora(tarea.fechaHoraInicio);
            tareaJSON.fechaHoraFin = utileriaService.formatoFechaHora(tarea.fechaHoraFin);


            Estado estadoActivo = utileriaService.obtenerEstadoActivo();
            def asignacionesTarea = AsignacionTarea.findAllByTareaAndEstado(tarea, estadoActivo);
            tareaJSON.empleados = asignacionesTarea.collect { a ->
                return [idEmpleado: a.empleado.id, empleado: a.empleado.persona.nombreCompleto]
                
            };
            return tareaJSON
        } catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }
   

    void actualizarEstado(def tareaJSON) throws ServiceException {
        Tarea.withTransaction { statusTransaccion ->
            try {
                Tarea tarea = Tarea.findById(tareaJSON.idTarea.toLong());
                if (tarea == null) {
                    throw new ServiceException(messageService.getErrorNoEncontrado("Tarea", tareaJSON.idTarea));
                }
                Estado estado = Estado.findById(tareaJSON.idEstado.toLong());

                if (tarea.estado.nombre != estado.nombre) {
                    Empleado empleado = utileriaService.obtenerEmpleadoSesion();
                    new CambioEstado(
                            fechaRegistro: new Date(),
                            empleado: empleado,
                            estadoAnterior: tarea.estado,
                            estadoNuevo: estado,
                            tarea: tarea
                    ).save(flush: true, failOnError: true);
                    tarea.estado = estado;
                    tarea.save(flush: true, failOnError: true);

                    if (estado.porcentaje == "100") {
                        List<Tarea> subtareas = Tarea.findAllByTarea(tarea);
                        for (Tarea subtarea : subtareas) {
                            tareaJSON.idTarea = subtarea.id
                            actualizarEstado(tareaJSON);
                        }
                    }

                }

            } catch (Exception e) {
                statusTransaccion.setRollbackOnly()
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }


    def obtenerTareaCalendario(Long idTarea) throws ServiceException {
        try {
            Tarea tarea = Tarea.findById(idTarea);
            if (tarea == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Tarea", idTarea));
            }
            def tareaJSON = [:]

            tareaJSON.idTarea = tarea.id;
            tareaJSON.clave = tarea.clave;
            tareaJSON.nombre = tarea.nombre;
            tareaJSON.descripcion = tarea.descripcion;
            tareaJSON.estado = [idEstado: tarea.estado.id, nombre: tarea.estado.nombre, color: tarea.estado.color];
            tareaJSON.nivelPrioridad = [idNivelPrioridad: tarea.nivelPrioridad.id,nombre: tarea.nivelPrioridad.nombre];

            Proyecto proyecto = tarea.proyecto;

            def proyectoJSON = [:]
            proyectoJSON.idProyecto = proyecto.id;
            proyectoJSON.clave = proyecto.clave;
            proyectoJSON.nombre = proyecto.nombre;
            proyectoJSON.color = proyecto.color;
            proyectoJSON.descripcion = proyecto.descripcion;
            proyectoJSON.fechaInicio = utileriaService.formatoFecha(proyecto.fechaInicio);
            proyectoJSON.fechaFin = utileriaService.formatoFecha(proyecto.fechaFin);
            proyectoJSON.categoria = [idCategoria: proyecto.categoria.id, nombre: proyecto.categoria.nombre];
            proyectoJSON.clientes = proyecto.clientes.collect { c -> return [idCliente: c.cliente.id, razonSocial: c.cliente.razonSocial] };

            tareaJSON.proyecto = proyectoJSON;

            tareaJSON.fechaHoraInicio = utileriaService.formatoFechaHora(tarea.fechaHoraInicio);
            tareaJSON.fechaHoraFin = utileriaService.formatoFechaHora(tarea.fechaHoraFin);
            tareaJSON.subtareas = Tarea.findAllByTarea(tarea).collect{ subtarea-> obtenerSubTareaCalendario(subtarea)};

            return tareaJSON
        } catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    def obtenerSubTareaCalendario(Tarea tarea) throws ServiceException{
        try {
            
            def tareaJSON = [:]

            tareaJSON.idTarea = tarea.id;
            tareaJSON.clave = tarea.clave;
            tareaJSON.nombre = tarea.nombre;
            tareaJSON.descripcion = tarea.descripcion;
            tareaJSON.estado = [idEstado: tarea.estado.id, nombre: tarea.estado.nombre, color: tarea.estado.color];
            tareaJSON.nivelPrioridad = [idNivelPrioridad: tarea.nivelPrioridad.id,nombre: tarea.nivelPrioridad.nombre];
            tareaJSON.fechaHoraInicio = utileriaService.formatoFechaHora(tarea.fechaHoraInicio);
            tareaJSON.fechaHoraFin = utileriaService.formatoFechaHora(tarea.fechaHoraFin);
            tareaJSON.subtareas = Tarea.findAllByTarea(tarea).collect{ subtarea-> obtenerSubTareaCalendario(subtarea)};

            return tareaJSON
        } catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

}
