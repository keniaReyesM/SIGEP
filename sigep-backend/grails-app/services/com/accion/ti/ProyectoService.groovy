package com.accion.ti

import com.accion.ti.enums.EstadoGeneralEnum;
import com.accion.ti.enums.TipoEstadoEnum;
import com.accion.ti.enums.RolEnum
import com.accion.ti.enums.TipoPrivacidadEnum
import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO
import org.springframework.web.multipart.MultipartFile;
import javax.sql.DataSource;
import groovy.sql.Sql

class ProyectoService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService
    ExcelService excelService
    DataSource dataSource;
    TareaService tareaService

    RespuestaListaDTO<Map> listarPaginado(PaginacionDTO paginacionDTO) throws ServiceException {
        try {

            Empleado empleadoSesion = Empleado.findById(utileriaService.obtenerEmpleadoSesion().id);
            Rol rolSesion = UsuarioRol.findByUsuario(empleadoSesion.usuario).rol;

            String busqueda = "%${paginacionDTO.busqueda}%";

            def criteria = Proyecto.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("fechaInicio", "asc")
                createAlias 'categoria', 'categoria'
                createAlias 'tipoPrivacidad', 'tipoPrivacidad'
                createAlias 'estado', 'estado'
                eq("empresa", utileriaService.obtenerEmpresaSesion())
                switch (rolSesion.authority) {
                    case RolEnum.ADMINISTRADOR.getRole():
                        // Se listan todos los proyectos del usuario sin importar su estado.
                        break;
                    default:
                        asignaciones {
                            empleado{
                                idEq(empleadoSesion.id)
                            }
                        }
                        eq("estado.nombre", EstadoGeneralEnum.ACTIVO.getValor())
                        break;
                }
                or {
                    ilike("clave", busqueda)
                    ilike("nombre", busqueda)
                    ilike("descripcion", busqueda)
                    ilike("categoria.nombre", busqueda)
                }
                projections {
                    property 'id', 'idProyecto'
                    property 'clave', 'clave'
                    property 'nombre', 'nombre'
                    property 'descripcion', 'descripcion'
                    property 'color', 'color'
                    property 'fechaInicio', 'fechaInicio'
                    property 'fechaFin', 'fechaFin'
                    property 'fechaRegistro', 'fechaRegistro'
                    property 'categoria.nombre', 'categoria'
                    property 'estado.nombre', 'estado'
                }
            }

            Long total = criteria.totalCount;
            criteria = criteria.collect { elemento ->


                elemento.cantidadEmpleados = AsignacionProyecto.createCriteria().get() {
                    estado {
                        eq("nombre", EstadoGeneralEnum.ACTIVO.getValor())
                    }
                    proyecto {
                        idEq(elemento.idProyecto)
                    }
                    projections {
                        count("id")
                    }
                }
                elemento.cantidadClientes = ClienteProyecto.createCriteria().get() {
                    proyecto {
                        idEq(elemento.idProyecto)
                    }
                    projections {
                        count("id")
                    }
                }
                elemento.cantidadTareas = Tarea.createCriteria().get() {
                    proyecto {
                        idEq(elemento.idProyecto)
                    }
                    projections {
                        count("id")
                    }
                }
                return elemento;
            }

            return new RespuestaListaDTO<Map>(elementos: criteria, total: total);

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            e.printStackTrace();
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }

    }
    
     RespuestaListaDTO<Map> listarPaginadoProductividadGeneral(PaginacionDTO paginacionDTO) throws ServiceException {
          Sql sql = null;
        try {



            String busqueda = "%${paginacionDTO.busqueda}%";

            def criteria = Proyecto.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
                
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
               
                order("fechaInicio", "asc")
               
                createAlias 'categoria', 'categoria'
                createAlias 'tipoPrivacidad', 'tipoPrivacidad'
                createAlias 'estado', 'estado'
                
                eq("empresa", utileriaService.obtenerEmpresaSesion())
              
                or {
                    ilike("clave", busqueda)
                    ilike("nombre", busqueda)
                    ilike("descripcion", busqueda)
                    ilike("categoria.nombre", busqueda)
                }
                

                
                projections {
                    property 'id', 'idProyecto'
                    property 'clave', 'clave'
                    property 'nombre', 'nombre'
                    property 'descripcion', 'descripcion'
                    property 'color', 'color'
                    property 'fechaInicio', 'fechaInicio'
                    property 'fechaFin', 'fechaFin'
                    property 'fechaRegistro', 'fechaRegistro'
                    property 'categoria.nombre', 'categoria'
                    property 'estado.nombre', 'estado'
                }
            }


            sql = new Sql(dataSource);
            String querySQL = '''
            
                SELECT
                TIME_FORMAT( SUM(TIMEDIFF(avance.hora_fin, avance.hora_inicio)), '%H:%i') AS horasTrabajadas
                FROM avance
                INNER JOIN asignacion_tarea ON avance.asignacion_tarea_id = asignacion_tarea.id 
                INNER JOIN tarea ON tarea.id = asignacion_tarea.tarea_id AND  tarea.proyecto_id = :idProyecto
            ''';

            Long total = criteria.totalCount;
            criteria = criteria.collect { elemento ->

                def avance = sql.firstRow(querySQL, [idProyecto: elemento.idProyecto]);

                elemento.horasTrabajadas = avance?.horasTrabajadas?:"00:00"

                elemento.cantidadTareas = Tarea.createCriteria().get() {
                        createAlias 'proyecto', 'proyecto'
                        eq("proyecto.id", elemento.idProyecto)
                        projections {
                            count("id")
                        }
                }
                
                return elemento;
            }

            return new RespuestaListaDTO<Map>(elementos: criteria, total: total);

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            e.printStackTrace();
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        } finally {
            utileriaService.cerrarConexiones(sql);
        }

    }
    RespuestaListaDTO<Map> listarPaginadoProductividadEmpleado(PaginacionDTO paginacionDTO, Long idEmpleado) throws ServiceException {
          Sql sql = null;
        try {


            sql = new Sql(dataSource);

            String querySQL = '''
    
                    SELECT
                        DISTINCT tarea.proyecto_id AS idProyecto
                    FROM avance
                    INNER JOIN asignacion_tarea ON asignacion_tarea.id = avance.asignacion_tarea_id
                    INNER JOIN tarea ON tarea.id = asignacion_tarea.tarea_id
                    WHERE asignacion_tarea.empleado_id = :idEmpleado
    
            ''';

            def proyectos = sql.rows(querySQL, [idEmpleado: idEmpleado]);
            def idsProyectos = proyectos.collect{it-> it.idProyecto}

            if(utileriaService.isEmptyList(proyectos)){
                return new RespuestaListaDTO<Map>(elementos: [], total: 0);
            }


            def criteria = Proyecto.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
                
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
               
                order("fechaInicio", "asc")
               
                createAlias 'categoria', 'categoria'
                createAlias 'tipoPrivacidad', 'tipoPrivacidad'
                createAlias 'estado', 'estado'
                
                eq("empresa", utileriaService.obtenerEmpresaSesion())
              
                inList('id', idsProyectos)
                

                
                projections {
                    groupProperty('id') // Devolverá valores únicos del campo especificado
                    property 'id', 'idProyecto'
                    property 'clave', 'clave'
                    property 'nombre', 'nombre'
                    property 'descripcion', 'descripcion'
                    property 'color', 'color'
                    property 'fechaInicio', 'fechaInicio'
                    property 'fechaFin', 'fechaFin'
                    property 'fechaRegistro', 'fechaRegistro'
                    property 'categoria.nombre', 'categoria'
                    property 'estado.nombre', 'estado'
                }
            }


            querySQL = '''
            
                SELECT
                TIME_FORMAT( SUM(TIMEDIFF(avance.hora_fin, avance.hora_inicio)), '%H:%i') AS horasTrabajadas
                FROM avance
                INNER JOIN asignacion_tarea ON avance.asignacion_tarea_id = asignacion_tarea.id 
                                            AND asignacion_tarea.empleado_id = :idEmpleado
                INNER JOIN tarea ON tarea.id = asignacion_tarea.tarea_id
                                AND  tarea.proyecto_id = :idProyecto
            
            ''';

            Long total = criteria.totalCount;
            criteria = criteria.collect { elemento ->


                def parametrosSQL = [
                        idEmpleado : idEmpleado,
                        idProyecto: elemento.idProyecto
                ];

                def avance = sql.firstRow(querySQL, parametrosSQL);

                elemento.horasTrabajadas = avance?.horasTrabajadas?:"00:00"
                
                return elemento;
            }

            return new RespuestaListaDTO<Map>(elementos: criteria, total: total);

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            e.printStackTrace();
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        } finally {
            utileriaService.cerrarConexiones(sql);
        }

    }

    RespuestaListaDTO<Map> listarPaginadoProductividadProyecto(PaginacionDTO paginacionDTO, Long idProyecto) throws ServiceException {
          Sql sql = null;
        try {


            sql = new Sql(dataSource);

            String querySQL = '''
    
                    SELECT
                        DISTINCT asignacion_tarea.empleado_id AS idEmpleado
                    FROM avance
                    INNER JOIN asignacion_tarea ON asignacion_tarea.id = avance.asignacion_tarea_id
                    INNER JOIN tarea ON tarea.id = asignacion_tarea.tarea_id
                    WHERE tarea.proyecto_id = :idProyecto
    
            ''';

            def empleados = sql.rows(querySQL, [idProyecto: idProyecto]);
            def idsEmpleados = empleados.collect{it-> it.idEmpleado}


            if(utileriaService.isEmptyList(empleados)){
                return new RespuestaListaDTO<Map>(elementos: [], total: 0);
            }


            def criteria = Empleado.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
                
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
               
                order("persona.nombre", "asc")
               
                createAlias 'persona', 'persona'
                    
                inList('id', idsEmpleados)
                       
                projections {
                    property 'id', 'idEmpleado'
                    property 'persona.nombreCompleto', 'nombreCompleto'
                }
            }


            querySQL = '''
            
                SELECT
                TIME_FORMAT( SUM(TIMEDIFF(avance.hora_fin, avance.hora_inicio)), '%H:%i') AS horasTrabajadas
                FROM avance
                INNER JOIN asignacion_tarea ON avance.asignacion_tarea_id = asignacion_tarea.id 
                                            AND asignacion_tarea.empleado_id = :idEmpleado
                INNER JOIN tarea ON tarea.id = asignacion_tarea.tarea_id
                                AND  tarea.proyecto_id = :idProyecto
            
            ''';

            Long total = criteria.totalCount;
            criteria = criteria.collect { elemento ->


                def parametrosSQL = [
                        idProyecto: idProyecto,
                        idEmpleado: elemento.idEmpleado
                ];

                def avance = sql.firstRow(querySQL, parametrosSQL);

                elemento.horasTrabajadas = avance?.horasTrabajadas?:"00:00"
                
                return elemento;
            }

            return new RespuestaListaDTO<Map>(elementos: criteria, total: total);

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            e.printStackTrace();
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        } finally {
            utileriaService.cerrarConexiones(sql);
        }

    }


    def conteoHorasTrabajadas(Long idEmpleado) throws ServiceException {
          Sql sql = null;
        try {


            sql = new Sql(dataSource);

            String querySQL = '''
            
                SELECT
                TIME_FORMAT( SUM(TIMEDIFF(avance.hora_fin, avance.hora_inicio)), '%H:%i') AS horasTrabajadas
                FROM avance
                INNER JOIN asignacion_tarea ON avance.asignacion_tarea_id = asignacion_tarea.id 
                                            AND asignacion_tarea.empleado_id = :idEmpleado
                INNER JOIN tarea ON tarea.id = asignacion_tarea.tarea_id            
            ''';

            def parametrosSQL = [idEmpleado : idEmpleado];

            def avance = sql.firstRow(querySQL, parametrosSQL);

            return avance?.horasTrabajadas?:"00:00"


        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            e.printStackTrace();
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        } finally {
            utileriaService.cerrarConexiones(sql);
        }

    }


    List<Map> listarTodos() throws ServiceException {

        try {


            Empleado empleadoSesion = Empleado.findById(utileriaService.obtenerEmpleadoSesion().id);
            Rol rolSesion = UsuarioRol.findByUsuario(empleadoSesion.usuario).rol;

            def criteria = Proyecto.createCriteria().list() {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("fechaInicio", "asc")
                createAlias 'categoria', 'categoria'
                createAlias 'tipoPrivacidad', 'tipoPrivacidad'
                createAlias 'estado', 'estado'
                eq("empresa", utileriaService.obtenerEmpresaSesion())
                switch (rolSesion.authority) {
                    case RolEnum.ADMINISTRADOR.getRole():
                        // Se listan todos los proyectos del usuario sin importar su estado.
                        break;
                    default:
                        asignaciones {
                            eq("empleado", empleadoSesion)
                        }
                        eq("estado.nombre", EstadoGeneralEnum.ACTIVO.getValor())
                        break;
                }

                projections {
                    property 'id', 'idProyecto'
                    property 'clave', 'clave'
                    property 'nombre', 'nombre'
                    property 'color', 'color'
                    property 'descripcion', 'descripcion'
                    property 'fechaInicio', 'fechaInicio'
                    property 'fechaFin', 'fechaFin'
                    property 'fechaRegistro', 'fechaRegistro'
                    property 'categoria.nombre', 'categoria'
                    property 'estado.nombre', 'estado'
                }
            }

            return criteria;

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }

    }

    void registrar(def proyectoJSON) throws ServiceException {
        Proyecto.withTransaction { statusTransaccion ->
            try {


                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Empleado empleado = utileriaService.obtenerEmpleadoSesion();

                Proyecto proyecto = Proyecto.findByClaveAndEmpresa(proyectoJSON.clave, empresa);

                if (proyecto != null) {
                    throw new ServiceException(messageService.getErrorUnico(proyectoJSON.clave))
                }

                TipoPrivacidad tipoPrivacidad = TipoPrivacidad.findByNombre(TipoPrivacidadEnum.PRIVADO.getValor());
                Estado estado = utileriaService.obtenerEstadoActivo();

                proyecto = new Proyecto(clave: proyectoJSON.clave, nombre: proyectoJSON.nombre, descripcion: proyectoJSON.descripcion,
                        fechaInicio: utileriaService.parsearDato(proyectoJSON.fechaInicio),
                        fechaFin: utileriaService.parsearDato(proyectoJSON.fechaFin),
                        fechaRegistro: new Date(),
                        categoria: Categoria.findById(proyectoJSON.idCategoria.toLong()),
                        empleado: empleado, estado: estado, empresa: empresa, color: proyectoJSON.color,
                        tipoPrivacidad: tipoPrivacidad
                );
                proyecto.save(flush: true, failOnError: true);


                proyectoJSON.clientes = proyectoJSON.clientes ?: [];
                for (def clienteJSON : proyectoJSON.clientes) {
                    Cliente cliente = Cliente.findById(clienteJSON.idCliente.toLong());
                    new ClienteProyecto(cliente: cliente, proyecto: proyecto).save(flush: true, failOnError: true);
                }

                proyectoJSON.empleados = proyectoJSON.empleados ?: [];
                for (def empleadoJSON : proyectoJSON.empleados) {
                    AsignacionProyecto asignacionProyecto = new AsignacionProyecto();
                    asignacionProyecto.fechaRegistro = new Date();
                    asignacionProyecto.empleadoAsignacion = empleado;
                    asignacionProyecto.empleado = Empleado.findById(empleadoJSON.empleado.idEmpleado.toLong())
                    asignacionProyecto.estado = estado
                    asignacionProyecto.proyecto = proyecto
                    asignacionProyecto.save(flush: true, failOnError: true);
                }


            } catch (Exception e) {
                statusTransaccion.setRollbackOnly()
                e.printStackTrace();
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(def proyectoJSON) throws ServiceException {
        Proyecto.withTransaction { statusTransaccion ->
            try {


                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Empleado empleado = utileriaService.obtenerEmpleadoSesion();

                Proyecto proyecto = Proyecto.findByClaveAndEmpresaAndIdNotEqual(proyectoJSON.clave, empresa, proyectoJSON.idProyecto.toLong());

                if (proyecto != null) {
                    throw new ServiceException(messageService.getErrorUnico(proyectoJSON.clave))
                }

                Estado estado = utileriaService.obtenerEstadoActivo();

                proyecto = Proyecto.findById(proyectoJSON.idProyecto.toLong());
                proyecto.clave = proyectoJSON.clave;
                proyecto.nombre = proyectoJSON.nombre;
                proyecto.descripcion = proyectoJSON.descripcion;
                proyecto.fechaInicio = utileriaService.parsearDato(proyectoJSON.fechaInicio);
                proyecto.fechaFin = utileriaService.parsearDato(proyectoJSON.fechaFin);
                proyecto.categoria = Categoria.findById(proyectoJSON.idCategoria.toLong());
                proyecto.color = proyectoJSON.color;
                proyecto.save(flush: true, failOnError: true);


                proyectoJSON.clientes = proyectoJSON.clientes ?: [];

                List<Long> idsCliente = proyectoJSON.clientes.collect { t -> t?.idCliente?.toLong() ?: 0 }
                List<Cliente> clientes = Cliente.findAllByIdInList(idsCliente);
                List<ClienteProyecto> clientesEliminar = ClienteProyecto.findAllByClienteNotInListAndProyecto(clientes, proyecto);
                for (ClienteProyecto clienteProyecto : clientesEliminar) {
                    clienteProyecto.delete(flush: true, failOnError: true);
                }

                for (def clienteJSON : proyectoJSON.clientes) {
                    Cliente cliente = Cliente.findById(clienteJSON.idCliente.toLong());
                    if (ClienteProyecto.findByClienteAndProyecto(cliente, proyecto) == null) {
                        new ClienteProyecto(cliente: cliente, proyecto: proyecto).save(flush: true, failOnError: true);
                    }
                }

                proyectoJSON.empleados = proyectoJSON.empleados ?: [];
                if (proyectoJSON.empleados.size() == 0) {
                    List<AsignacionProyecto> empleadosProyecto = AsignacionProyecto.findAllByProyecto(proyecto)
                    for (AsignacionProyecto asignacionProyecto : empleadosProyecto) {
                        asignacionProyecto.delete(flush: true, failOnError: true);
                    }
                } else {
                    List<Long> idsEmpleados = proyectoJSON.empleados.collect { t -> t?.empleado.idEmpleado?.toLong() ?: 0 }
                    List<Empleado> empleados = Empleado.findAllByIdInList(idsEmpleados);
                    List<AsignacionProyecto> empleadosProyecto = AsignacionProyecto.findAllByEmpleadoNotInListAndProyecto(empleados, proyecto);
                    for (AsignacionProyecto asignacionProyecto : empleadosProyecto) {
                        asignacionProyecto.delete(flush: true, failOnError: true);
                    }
                }


                for (def empleadoJSON : proyectoJSON.empleados) {
                    Empleado empleadoAsignar = Empleado.findById(empleadoJSON.empleado.idEmpleado.toLong());
                    AsignacionProyecto asignacionProyecto = AsignacionProyecto.findByEmpleadoAndProyecto(empleadoAsignar, proyecto);
                    if (asignacionProyecto == null) {
                        asignacionProyecto = new AsignacionProyecto(empleado: empleadoAsignar, proyecto: proyecto);
                        asignacionProyecto.fechaRegistro = new Date();
                        asignacionProyecto.empleadoAsignacion = empleado;
                        asignacionProyecto.empleado = empleadoAsignar;
                        asignacionProyecto.estado = estado
                        asignacionProyecto.proyecto = proyecto
                    }
                    asignacionProyecto.save(flush: true, failOnError: true);
                }


            } catch (Exception e) {
                statusTransaccion.setRollbackOnly()
                e.printStackTrace();
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    def obtener(Long idProyecto) throws ServiceException {
        try {
            Proyecto proyecto = Proyecto.findById(idProyecto);
            if (proyecto == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Proyecto", idProyecto));
            }
            def proyectoJSON = [:]

            proyectoJSON.idProyecto = proyecto.id;
            proyectoJSON.clave = proyecto.clave;
            proyectoJSON.nombre = proyecto.nombre;
            proyectoJSON.color = proyecto.color;
            proyectoJSON.descripcion = proyecto.descripcion;
            proyectoJSON.fechaInicio = utileriaService.formatoFecha(proyecto.fechaInicio);
            proyectoJSON.fechaFin = utileriaService.formatoFecha(proyecto.fechaFin);
            proyectoJSON.idCategoria = proyecto.categoria.id;

            proyectoJSON.clientes = proyecto.clientes.collect { c -> return [idCliente: c.cliente.id, razonSocial: c.cliente.razonSocial] };
            proyectoJSON.empleados = proyecto.asignaciones.collect { a ->
                return [
                        empleado       : [idEmpleado: a.empleado.id, empleado: a.empleado.persona.nombreCompleto]
                ]
            };
            return proyectoJSON
        } catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    void actualizarEstado(Long idProyecto) throws ServiceException {
        Proyecto.withTransaction { statusTransaccion ->
            try {
                Proyecto proyecto = Proyecto.findById(idProyecto);
                if (proyecto == null) {
                    throw new ServiceException(messageService.getErrorNoEncontrado("Proyecto", idProyecto));
                }

                Estado nuevoEstado = proyecto.estado.nombre == EstadoGeneralEnum.ACTIVO.getValor() ? utileriaService.obtenerEstadoInactivo() : utileriaService.obtenerEstadoActivo();
                proyecto.estado = nuevoEstado;
                proyecto.save(flush: true, failOnError: true);
            } catch (Exception e) {
                statusTransaccion.setRollbackOnly()
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    List<Map> listarEmpleadosPorProyecto(Long idProyecto) throws ServiceException{
        try {

            Empresa empresaSesion = utileriaService.obtenerEmpresaSesion();

            def lista = Empleado.createCriteria().list() {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("persona.nombre", "asc")
                createAlias 'persona', 'persona'
                eq("empresa", empresaSesion)
                estado {
                    eq("nombre", EstadoGeneralEnum.ACTIVO.getValor())
                }
                asignacionesProyecto {
                    proyecto {
                        idEq(idProyecto)
                    }
                    estado {
                        eq("nombre", EstadoGeneralEnum.ACTIVO.getValor())
                    }
                }
                projections {
                    property 'id', 'idEmpleado'
                    property 'persona.nombreCompleto', 'empleado'
                }
            }
            return lista
        } catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    def obtenerInformacionGraficaCircular(Long idProyecto) throws ServiceException{
        try{
            
            TipoEstado tipoEstado = TipoEstado.findByNombre(TipoEstadoEnum.TAREA.getValor());
            List<Estado> estados = Estado.findAllByTipoEstado(tipoEstado);

            def estadosGrafica = [];
            for (Estado estado in estados) {
                Long idEstado = estado.id;

                List<Tarea> tareas = Tarea.createCriteria().list() {
                    createAlias 'estado', 'estado'
                    proyecto{
                        idEq(idProyecto)
                    }
                    eq("estado.id", idEstado)
                }
                estadosGrafica.push([ estado: [nombre: estado.nombre, color: estado.color],cantidad: tareas.size() ]);
            }
            return estadosGrafica;
        }catch(Exception e){
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    def obtenerInformacionGeneral(Long idProyecto) throws ServiceException{
        Sql sql = null;
        try{
            
            Proyecto proyecto = Proyecto.findById(idProyecto);
            if (proyecto == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Proyecto", idProyecto));
            }
            def proyectoJSON = [:]

            proyectoJSON.idProyecto = proyecto.id;
            proyectoJSON.clave = proyecto.clave;
            proyectoJSON.nombre = proyecto.nombre;
            proyectoJSON.color = proyecto.color;
            proyectoJSON.descripcion = proyecto.descripcion;
            proyectoJSON.fechaInicio = utileriaService.formatoFecha(proyecto.fechaInicio);
            proyectoJSON.fechaFin = utileriaService.formatoFecha(proyecto.fechaFin);
            proyectoJSON.nombreCategoria = proyecto.categoria.nombre;
            proyectoJSON.clientes = proyecto.clientes.collect { c -> c.cliente.razonSocial };
            proyectoJSON.empleados = proyecto.asignaciones.collect { a -> a.empleado.persona.nombreCompleto };
            proyectoJSON.cantidadTareas = Tarea.createCriteria().get() {
                    createAlias 'proyecto', 'proyecto'
                    eq("proyecto.id", idProyecto)
                    projections {
                        count("id")
                    }
            }
            proyectoJSON.tareas = tareaService.listarTareasPorProyecto(idProyecto, null)

            sql = new Sql(dataSource);

            String querySQL = '''
            
                SELECT
                TIME_FORMAT( SUM(TIMEDIFF(avance.hora_fin, avance.hora_inicio)), '%H:%i') AS horasTrabajadas
                FROM avance
                INNER JOIN asignacion_tarea ON avance.asignacion_tarea_id = asignacion_tarea.id 
                INNER JOIN tarea ON tarea.id = asignacion_tarea.tarea_id 
                                AND tarea.proyecto_id = :idProyecto        
            ''';

            def parametrosSQL = [idProyecto : idProyecto];

            def avance = sql.firstRow(querySQL, parametrosSQL);

            proyectoJSON.horasTrabajadas = avance?.horasTrabajadas?:"00:00"


            return proyectoJSON
        }catch(Exception e){
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        } finally {
            utileriaService.cerrarConexiones(sql);
        }
    }

}
