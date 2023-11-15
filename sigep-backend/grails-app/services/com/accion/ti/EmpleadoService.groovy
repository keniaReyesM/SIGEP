package com.accion.ti

import com.accion.ti.enums.EstadoGeneralEnum
import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class EmpleadoService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

    RespuestaListaDTO<Map> listarPaginado(PaginacionDTO paginacionDTO) throws ServiceException {

        try {

            String busqueda = "%${paginacionDTO.busqueda}%";

            def criteria = Empleado.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("id", "desc")
                createAlias 'persona', 'persona'
                createAlias 'usuario', 'usuario'
                createAlias 'puesto', 'puesto'
                createAlias 'estado', 'estado'
                or {
                    ilike("persona.nombreCompleto", busqueda)
                    ilike("usuario.username", busqueda)
                    ilike("puesto.nombre", busqueda)
                    ilike("estado.nombre", busqueda)
                }
                eq("empresa", utileriaService.obtenerEmpresaSesion())
                projections {
                    property 'id', 'idEmpleado'
                    property 'persona.nombreCompleto', 'nombreCompleto'
                    property 'usuario.username', 'usuario'
                    property 'puesto.nombre', 'puesto'
                    property 'estado.nombre', 'estado'
                }
            }

            return new RespuestaListaDTO<Map>(elementos: criteria, total: criteria.totalCount);

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }

    }

    List<Map> listarTodosActivos() throws ServiceException {
        try {
            Empresa empresaSesion = utileriaService.obtenerEmpresaSesion();
            def lista = Empleado.createCriteria().list() {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("persona.nombre", "asc")
                createAlias 'persona', 'persona'
                eq("empresa", empresaSesion)
                estado {
                    eq("nombre", "Activo")
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

    List<Map> listarTodos() throws ServiceException {
        try {
            Empresa empresaSesion = utileriaService.obtenerEmpresaSesion();
            def lista = Empleado.createCriteria().list() {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("persona.nombre", "asc")
                createAlias 'persona', 'persona'
                eq("empresa", empresaSesion)
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

    void registrar(def empleadoJSON) throws ServiceException {
        Empleado.withTransaction { statusTransaccion ->
            try {


                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Usuario usuario = Usuario.findByUsername(empleadoJSON.usuario)

                if (usuario != null) {
                    throw new ServiceException(messageService.getErrorUnico(empleadoJSON.usuario))
                }

                empleadoJSON.correos = empleadoJSON.correos ?: [];
                empleadoJSON.telefonos = empleadoJSON.telefonos ?: [];

                def telefonosPrincipales = empleadoJSON.telefonos.findAll { t -> t?.principal == true }
                if (telefonosPrincipales.size() > 1) {
                    throw new ServiceException("No es permitido ingresar más de un teléfono principal.")
                }

                if (telefonosPrincipales.size() == 0) {
                    throw new ServiceException("Se debe definir un teléfono como principal.")
                }

                def correosPrincipales = empleadoJSON.correos.findAll { t -> t?.principal == true }
                if (correosPrincipales.size() > 1) {
                    throw new ServiceException("No es permitido ingresar más de un correo principal.")
                }

                if (correosPrincipales.size() == 0) {
                    throw new ServiceException("Se debe definir un correo como principal.")
                }

                String correoPrincipal = empleadoJSON?.correos?.find { c -> c.principal == true }?.correo ?: empleadoJSON.usuario;
                if (empleadoJSON?.correos?.size() == 1) {
                    correoPrincipal = empleadoJSON?.correos[0]?.correo;
                }

                usuario = new Usuario(username: empleadoJSON.usuario, password: empleadoJSON.contrasena, email: correoPrincipal);
                usuario.save(flush: true, failOnError: true);

                Rol rol = Rol.findById(empleadoJSON.idRol)
                new UsuarioRol(usuario: usuario, rol: rol).save(flush: true, failOnError: true);

                Persona persona = new Persona(nombre: empleadoJSON.nombre, primerApellido: empleadoJSON.primerApellido,
                        segundoApellido: empleadoJSON.segundoApellido,
                        fechaNacimiento: utileriaService.parsearDato(empleadoJSON.fechaNacimiento));
                persona.save(flush: true, failOnError: true);


                Direccion direccion = new Direccion(numeroInterior: empleadoJSON.numeroInterior,
                        numeroExterior: empleadoJSON.numeroExterior,
                        calle: empleadoJSON.calle,
                        colonia: empleadoJSON.colonia,
                        municipio: empleadoJSON.municipio,
                        estado: empleadoJSON.estado,
                        persona: persona);
                direccion.save(flush: true, failOnError: true);

                for (def telefonoJSON : empleadoJSON.telefonos) {
                    Telefono telefono = new Telefono(telefono: telefonoJSON.telefono,
                            principal: telefonoJSON.principal,
                            persona: persona,
                            tipoTelefono: TipoTelefono.findById(telefonoJSON.idTipoTelefono))
                    telefono.save(flush: true, failOnError: true);
                }

                for (def correoJSON : empleadoJSON.correos) {
                    new Correo(correo: correoJSON.correo, principal: correoJSON.principal, persona: persona).save(flush: true, failOnError: true);
                }


                Estado estado = utileriaService.obtenerEstadoActivo();

                Empleado empleado = new Empleado(fechaRegistro: new Date(),
                        usuario: usuario,
                        estado: estado, empresa: empresa,
                        persona: persona,
                        puesto: Puesto.findByIdAndEmpresa(empleadoJSON.idPuesto, empresa));
                empleado.save(flush: true, failOnError: true);


                for (def horarioJSON : empleadoJSON.horariosLaborales) {
                    HorarioLaboral horarioLaboral = new HorarioLaboral(dia: Dia.findById(horarioJSON.idDia), empleado: empleado,
                            horaInicio: utileriaService.parsearDato(horarioJSON.horaInicio),
                            horaFin: utileriaService.parsearDato(horarioJSON.horaFin));
                    horarioLaboral.save(flush: true, failOnError: true);
                }

                Empleado empleadoAsignacion = utileriaService.obtenerEmpleadoSesion();

                for (def areaJSON : empleadoJSON.areas) {
                    AreaEmpleado areaEmpleado = new AreaEmpleado(fechaRegistro: new Date(),
                            area: Area.findByIdAndEmpresa(areaJSON.idArea, empresa),
                            empleado: empleado,
                            empleadoAsignacion: empleadoAsignacion,
                            estado: estado);
                    areaEmpleado.save(flush: true, failOnError: true);
                }

            } catch (Exception e) {
                statusTransaccion.setRollbackOnly()
                // e.printStackTrace();
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(def empleadoJSON) throws ServiceException {
        Empleado.withTransaction { statusTransaccion ->
            try {

                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Empleado empleado = Empleado.findById(empleadoJSON.idEmpleado?.toLong());
                if (empleado == null) {
                    throw new ServiceException(messageService.getErrorNoEncontrado("Empleado", empleadoJSON.idEmpleado))
                }

                Usuario usuario = empleado.usuario;
                Usuario usuarioBuscado = Usuario.findByUsernameAndIdNotEqual(empleadoJSON.usuario, usuario.id);

                if (usuarioBuscado != null) {
                    throw new ServiceException(messageService.getErrorUnico(empleadoJSON.usuario))
                }

                empleadoJSON.correos = empleadoJSON.correos ?: [];
                empleadoJSON.telefonos = empleadoJSON.telefonos ?: [];

                def telefonosPrincipales = empleadoJSON.telefonos.findAll { t -> t?.principal == true }
                if (telefonosPrincipales.size() > 1) {
                    throw new ServiceException("No es permitido ingresar más de un teléfono principal.")
                }
                if (telefonosPrincipales.size() == 0) {
                    throw new ServiceException("Se debe definir un teléfono como principal.")
                }

                def correosPrincipales = empleadoJSON.correos.findAll { t -> t?.principal == true }
                if (correosPrincipales.size() > 1) {
                    throw new ServiceException("No es permitido ingresar más de un correo principal.")
                }
                if (correosPrincipales.size() == 0) {
                    throw new ServiceException("Se debe definir un correo como principal.")
                }

                empleado.puesto = Puesto.findByIdAndEmpresa(empleadoJSON.idPuesto, empresa);
                empleado.save(flush: true, failOnError: true);

                String correoPrincipal = empleadoJSON?.correos?.find { c -> c.principal }?.correo ?: empleadoJSON.usuario;
                if (empleadoJSON?.correos?.size() == 1) {
                    correoPrincipal = empleadoJSON?.correos[0]?.correo;
                }

                usuario.username = empleadoJSON.usuario;
                usuario.email = correoPrincipal;
                usuario.save(flush: true, failOnError: true);

                List<UsuarioRol> usuarioRoles = UsuarioRol.findAllByUsuario(usuario);
                for (UsuarioRol usuarioRol : usuarioRoles) {
                    usuarioRol.delete(flush: true, failOnError: true)
                }

                Rol rol = Rol.findById(empleadoJSON.idRol);
                new UsuarioRol(usuario: usuario, rol: rol).save(flush: true, failOnError: true);


                Persona persona = empleado.persona;

                persona.nombre = empleadoJSON.nombre;
                persona.primerApellido = empleadoJSON.primerApellido;
                persona.segundoApellido = empleadoJSON.segundoApellido;
                persona.fechaNacimiento = utileriaService.parsearDato(empleadoJSON.fechaNacimiento);
                persona.save(flush: true, failOnError: true);

                Direccion direccion = Direccion.findByPersona(persona) ?: new Direccion(persona: persona);
                direccion.numeroInterior = empleadoJSON.numeroInterior;
                direccion.numeroExterior = empleadoJSON.numeroExterior;
                direccion.calle = empleadoJSON.calle;
                direccion.colonia = empleadoJSON.colonia;
                direccion.municipio = empleadoJSON.municipio;
                direccion.estado = empleadoJSON.estado;
                direccion.save(flush: true, failOnError: true);

                List<Long> idsTelefonos = empleadoJSON.telefonos.collect { t -> t?.idTelefono?.toLong() ?: 0 }
                List<Telefono> telefonosEliminar = Telefono.findAllByPersonaAndIdNotInList(persona, idsTelefonos);
                for (Telefono telefono : telefonosEliminar) {
                    telefono.delete(flush: true, failOnError: true);
                }

                for (def telefonoJSON : empleadoJSON.telefonos) {
                    Long idTelefono = telefonoJSON?.idTelefono?.toLong() ?: null;
                    Telefono telefono = idTelefono ? Telefono.findById(idTelefono) : new Telefono(persona: persona);
                    telefono.telefono = telefonoJSON.telefono;
                    telefono.principal = telefonoJSON.principal;
                    telefono.tipoTelefono = TipoTelefono.findById(telefonoJSON.idTipoTelefono);
                    telefono.save(flush: true, failOnError: true);
                }

                List<Long> idsCorreos = empleadoJSON.correos.collect { t -> t?.idCorreo?.toLong() ?: 0 }
                List<Correo> correosEliminar = Correo.findAllByPersonaAndIdNotInList(persona, idsCorreos);
                for (Correo correo : correosEliminar) {
                    correo.delete(flush: true, failOnError: true);
                }

                for (def correoJSON : empleadoJSON.correos) {
                    Long idCorreo = correoJSON?.idCorreo?.toLong() ?: null;
                    Correo correo = idCorreo ? Correo.findById(idCorreo) : new Correo(persona: persona);
                    correo.correo = correoJSON.correo;
                    correo.principal = correoJSON.principal;
                    correo.save(flush: true, failOnError: true);
                }


                empleadoJSON.horariosLaborales = empleadoJSON.horariosLaborales ?: [];

                List<Long> idsHorarios = empleadoJSON.horariosLaborales.collect { t -> t?.idHorarioLaboral?.toLong() ?: 0 }
                List<HorarioLaboral> horariosEliminar = HorarioLaboral.findAllByEmpleadoAndIdNotInList(empleado, idsHorarios);
                for (HorarioLaboral horarioLaboral : horariosEliminar) {
                    horarioLaboral.delete(flush: true, failOnError: true);
                }

                for (def horarioJSON : empleadoJSON.horariosLaborales) {
                    Long idHorarioLaboral = null;
                    if (horarioJSON.idHorarioLaboral) {
                        idHorarioLaboral = horarioJSON?.idHorarioLaboral?.toLong()
                    }
                    HorarioLaboral horarioLaboral = idHorarioLaboral ? HorarioLaboral.findById(idHorarioLaboral) : new HorarioLaboral(empleado: empleado);
                    horarioLaboral.dia = Dia.findById(horarioJSON.idDia);
                    horarioLaboral.horaInicio = utileriaService.parsearDato(horarioJSON.horaInicio);
                    horarioLaboral.horaFin = utileriaService.parsearDato(horarioJSON.horaFin);
                    horarioLaboral.save(flush: true, failOnError: true);
                }

                empleadoJSON.areas = empleadoJSON.areas ?: [];

                Empleado empleadoAsignacion = utileriaService.obtenerEmpleadoSesion();

                List<Long> idsAreaEmpleado = empleadoJSON.areas.collect { t -> t?.idAreaEmpleado?.toLong() ?: 0 }

                List<AreaEmpleado> areasEliminar = AreaEmpleado.findAllByEmpleadoAndIdNotInList(empleado, idsAreaEmpleado);
                for (AreaEmpleado areaEmpleado : areasEliminar) {
                    areaEmpleado.delete(flush: true, failOnError: true);
                }


                Estado estado = utileriaService.obtenerEstadoActivo();

                for (def areaJSON : empleadoJSON.areas) {
                    Area area = Area.findByIdAndEmpresa(areaJSON?.idArea?.toLong(), empresa);
                    AreaEmpleado areaEmpleado = AreaEmpleado.findByAreaAndEmpleado(area, empleado);
                    if (areaEmpleado == null) {
                        areaEmpleado = new AreaEmpleado(fechaRegistro: new Date(),
                                area: area,
                                empleado: empleado,
                                empleadoAsignacion: empleadoAsignacion,
                                estado: estado);
                        areaEmpleado.save(flush: true, failOnError: true);
                    }
                }

            } catch (Exception e) {
                // e.printStackTrace();
                statusTransaccion.setRollbackOnly()
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    def obtener(Long idEmpleado) throws ServiceException {
        try {
            Empleado empleado = Empleado.findById(idEmpleado);
            if (empleado == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Empleado", idEmpleado));
            }
            def empleadoJSON = [:]

            empleadoJSON.idEmpleado = empleado.id;

            Usuario usuario = empleado.usuario;
            UsuarioRol usuarioRol = UsuarioRol.findByUsuario(usuario);
            Rol rol = usuarioRol.rol;

            empleadoJSON.usuario = usuario.username;
            empleadoJSON.idRol = rol.id;

            Persona persona = empleado.persona;

            empleadoJSON.nombre = persona.nombre;
            empleadoJSON.primerApellido = persona.primerApellido;
            empleadoJSON.segundoApellido = persona.segundoApellido;
            empleadoJSON.fechaNacimiento = utileriaService.formatoFecha(persona.fechaNacimiento);

            Direccion direccion = Direccion.findByPersona(persona);

            empleadoJSON.numeroInterior = direccion.numeroInterior;
            empleadoJSON.numeroExterior = direccion.numeroExterior;
            empleadoJSON.calle = direccion.calle;
            empleadoJSON.colonia = direccion.colonia;
            empleadoJSON.municipio = direccion.municipio;
            empleadoJSON.estado = direccion.estado;


            List<Telefono> telefonos = Telefono.findAllByPersona(persona);

            empleadoJSON.telefonos = [];

            for (Telefono telefono : telefonos) {

                def telefonoJSON = [
                        idTelefono    : telefono.id,
                        telefono      : telefono.telefono,
                        principal     : telefono.principal,
                        idTipoTelefono: telefono.tipoTelefono.id
                ];

                empleadoJSON.telefonos.push(telefonoJSON);
            }


            List<Correo> correos = Correo.findAllByPersona(persona);
            empleadoJSON.correos = [];

            for (Correo correo : correos) {
                def correoJSON = [
                        idCorreo : correo.id,
                        correo   : correo.correo,
                        principal: correo.principal
                ];
                empleadoJSON.correos.push(correoJSON);
            }


            Puesto puesto = empleado.puesto;

            empleadoJSON.idPuesto = puesto.id;


            List<HorarioLaboral> horariosLaborales = HorarioLaboral.findAllByEmpleado(empleado);
            empleadoJSON.horariosLaborales = [];

            for (HorarioLaboral horarioLaboral : horariosLaborales) {
                def horarioJSON = [
                        idHorarioLaboral: horarioLaboral.id,
                        idDia           : horarioLaboral.dia.id,
                        horaInicio      : utileriaService.formatoHora(horarioLaboral.horaInicio),
                        horaFin         : utileriaService.formatoHora(horarioLaboral.horaFin)
                ];
                empleadoJSON.horariosLaborales.push(horarioJSON);
            }

            List<AreaEmpleado> areas = AreaEmpleado.findAllByEmpleado(empleado);
            empleadoJSON.areas = [];

            for (AreaEmpleado areaEmpleado : areas) {
                def areaJSON = [
                        idAreaEmpleado: areaEmpleado.id,
                        idArea        : areaEmpleado.area.id
                ];
                empleadoJSON.areas.push(areaJSON);
            }

            return empleadoJSON
        } catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    void actualizarEstado(Long idEmpleado) throws ServiceException {
        Empleado.withTransaction { statusTransaccion ->
            try {
                Empleado empleado = Empleado.findById(idEmpleado);
                if (empleado == null) {
                    throw new ServiceException(messageService.getErrorNoEncontrado("Empleado", idEmpleado));
                }
                Estado nuevoEstado = empleado.estado.nombre == EstadoGeneralEnum.ACTIVO.getValor() ? utileriaService.obtenerEstadoInactivo() : utileriaService.obtenerEstadoActivo();
                empleado.estado = nuevoEstado;
                empleado.save(flush: true, failOnError: true);
            } catch (Exception e) {
                statusTransaccion.setRollbackOnly()
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }


}
