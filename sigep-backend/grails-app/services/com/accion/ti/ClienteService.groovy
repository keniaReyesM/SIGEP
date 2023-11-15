package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.enums.EstadoGeneralEnum;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class ClienteService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

    RespuestaListaDTO<Map> listarPaginado(PaginacionDTO paginacionDTO) throws ServiceException {

        try {

            String busqueda = "%${paginacionDTO.busqueda}%";


            def criteria = Cliente.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("id", "desc")
                createAlias 'estado', 'estado'
                or {
                    ilike("razonSocial", busqueda)
                    ilike("estado.nombre", busqueda)
                }
                eq("empresa", utileriaService.obtenerEmpresaSesion())
                projections {
                    property 'id', 'idCliente'
                    property 'razonSocial', 'razonSocial'
                    property 'estado.nombre', 'estado'
                }
            }

            return new RespuestaListaDTO<Map>(elementos: criteria, total: criteria.totalCount);

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }

    }

    List<Map> listarTodos() throws ServiceException {
        try {
            Empresa empresaSesion = utileriaService.obtenerEmpresaSesion();
            def lista = Cliente.createCriteria().list() {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("razonSocial", "asc")
                eq("empresa", empresaSesion)
                projections {
                    property 'id', 'idCliente'
                    property 'razonSocial', 'razonSocial'
                }
            }
            return lista
        } catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    void registrar(def clienteJSON) throws ServiceException {
        Cliente.withTransaction { statusTransaccion ->
            try {


                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Cliente cliente = Cliente.findByRazonSocial(clienteJSON.razonSocial)

                if (cliente != null) {
                    throw new ServiceException(messageService.getErrorUnico(clienteJSON.razonSocial))
                }

                clienteJSON.correos = clienteJSON.correos ?: [];
                clienteJSON.telefonos = clienteJSON.telefonos ?: [];
                clienteJSON.representantes = clienteJSON.representantes ?: [];

                def telefonosPrincipales = clienteJSON.telefonos.findAll { t -> t?.principal == true }
                if (telefonosPrincipales.size() > 1) {
                    throw new ServiceException("No es permitido ingresar más de un teléfono principal.")
                }

                if (telefonosPrincipales.size() == 0) {
                    throw new ServiceException("Se debe definir un teléfono como principal.")
                }

                def correosPrincipales = clienteJSON.correos.findAll { t -> t?.principal == true }
                if (correosPrincipales.size() > 1) {
                    throw new ServiceException("No es permitido ingresar más de un correo principal.")
                }

                if (correosPrincipales.size() == 0) {
                    throw new ServiceException("Se debe definir un correo como principal.")
                }

                String correoPrincipal = clienteJSON?.correos?.find { c -> c.principal == true }?.correo ?: clienteJSON.usuario;
                if (clienteJSON?.correos?.size() == 1) {
                    correoPrincipal = clienteJSON?.correos[0]?.correo;
                }

                Estado estado = utileriaService.obtenerEstadoActivo();

                cliente = new Cliente(razonSocial: clienteJSON.razonSocial, empresa: empresa, estado: estado);
                cliente.save(flush: true, failOnError: true);


                for (def representanteJSON : clienteJSON.representantes) {
                    Persona persona = new Persona(nombre: representanteJSON.nombre, primerApellido: representanteJSON.primerApellido,
                            segundoApellido: representanteJSON.segundoApellido,
                            fechaNacimiento: utileriaService.parsearDato(representanteJSON.fechaNacimiento));
                    persona.save(flush: true, failOnError: true);

                    Representante representante = new Representante(fechaRegistro: new Date());
                    representante.cliente = cliente;
                    representante.persona = persona;
                    representante.save(flush: true, failOnError: true);
                }


                for (def correoJSON : clienteJSON.correos) {
                    new Correo(correo: correoJSON.correo, principal: correoJSON.principal, cliente: cliente).save(flush: true, failOnError: true);
                }

                for (def plataformaDigitalJSON : clienteJSON.plataformasDigitales) {
                    new PlataformaDigital(nombre: plataformaDigitalJSON.nombre, 
                                          url: plataformaDigitalJSON.url, 
                                          cliente: cliente).save(flush: true, failOnError: true);
                }

                for (def telefonoJSON : clienteJSON.telefonos) {
                    Telefono telefono = new Telefono(telefono: telefonoJSON.telefono,
                            principal: telefonoJSON.principal,
                            cliente: cliente,
                            tipoTelefono: TipoTelefono.findById(telefonoJSON.idTipoTelefono))
                    telefono.save(flush: true, failOnError: true);
                }


                Direccion direccion = new Direccion(numeroInterior: clienteJSON.numeroInterior,
                        numeroExterior: clienteJSON.numeroExterior,
                        calle: clienteJSON.calle,
                        colonia: clienteJSON.colonia,
                        municipio: clienteJSON.municipio,
                        estado: clienteJSON.estado,
                        cliente: cliente);
                direccion.save(flush: true, failOnError: true);

            } catch (Exception e) {
                statusTransaccion.setRollbackOnly()
                // e.printStackTrace();
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(def clienteJSON) throws ServiceException {
        Cliente.withTransaction { statusTransaccion ->
            try {

                Empresa empresa = utileriaService.obtenerEmpresaSesion();
                Cliente cliente = Cliente.findById(clienteJSON.idCliente?.toLong());
                if (cliente == null) {
                    throw new ServiceException(messageService.getErrorNoEncontrado("Cliente", clienteJSON.idCliente))
                }

                Cliente clienteBuscado = Cliente.findByRazonSocialAndIdNotEqual(clienteJSON.razonSocial, clienteJSON.idCliente.toLong());

                if (clienteBuscado != null) {
                    throw new ServiceException(messageService.getErrorUnico(clienteJSON.razonSocial))
                }


                clienteJSON.correos = clienteJSON.correos ?: [];
                clienteJSON.telefonos = clienteJSON.telefonos ?: [];
                clienteJSON.representantes = clienteJSON.representantes ?: [];

                def telefonosPrincipales = clienteJSON.telefonos.findAll { t -> t?.principal == true }
                if (telefonosPrincipales.size() > 1) {
                    throw new ServiceException("No es permitido ingresar más de un teléfono principal.")
                }
                if (telefonosPrincipales.size() == 0) {
                    throw new ServiceException("Se debe definir un teléfono como principal.")
                }

                def correosPrincipales = clienteJSON.correos.findAll { t -> t?.principal == true }
                if (correosPrincipales.size() > 1) {
                    throw new ServiceException("No es permitido ingresar más de un correo principal.")
                }
                if (correosPrincipales.size() == 0) {
                    throw new ServiceException("Se debe definir un correo como principal.")
                }

                cliente.razonSocial = clienteJSON.razonSocial
                cliente.save(flush: true, failOnError: true);


                Direccion direccion = Direccion.findByCliente(cliente) ?: new Direccion(cliente: cliente);
                direccion.numeroInterior = clienteJSON.numeroInterior;
                direccion.numeroExterior = clienteJSON.numeroExterior;
                direccion.calle = clienteJSON.calle;
                direccion.colonia = clienteJSON.colonia;
                direccion.municipio = clienteJSON.municipio;
                direccion.estado = clienteJSON.estado;
                direccion.save(flush: true, failOnError: true);


                List<Long> idsCorreos = clienteJSON.correos.collect { t -> t?.idCorreo?.toLong() ?: 0 }
                List<Correo> correosEliminar = Correo.findAllByClienteAndIdNotInList(cliente, idsCorreos);
                for (Correo correo : correosEliminar) {
                    correo.delete(flush: true, failOnError: true);
                }
                for (def correoJSON : clienteJSON.correos) {
                    Long idCorreo = correoJSON?.idCorreo?.toLong() ?: null;
                    Correo correo = idCorreo ? Correo.findById(idCorreo) : new Correo(cliente: cliente);
                    correo.correo = correoJSON.correo;
                    correo.principal = correoJSON.principal;
                    correo.save(flush: true, failOnError: true);
                }


                List<Long> idsPlataformasDigitales = clienteJSON.plataformasDigitales.collect { t -> t?.idPlataformaDigital?.toLong() ?: 0 }
                idsPlataformasDigitales = idsPlataformasDigitales?:[0]

                List<PlataformaDigital> plataformasEliminar = PlataformaDigital.findAllByClienteAndIdNotInList(cliente, idsPlataformasDigitales);

                for (PlataformaDigital plataformaDigital : plataformasEliminar) {
                    plataformaDigital.delete(flush: true, failOnError: true);
                }

                for (def plataformaDigitalJSON : clienteJSON.plataformasDigitales) {
                    Long idPlataformaDigital = plataformaDigitalJSON?.idPlataformaDigital?.toLong() ?: null;
                    PlataformaDigital plataformaDigital = idPlataformaDigital ? PlataformaDigital.findById(idPlataformaDigital) : new PlataformaDigital(cliente: cliente);
                    plataformaDigital.nombre = plataformaDigitalJSON.nombre;
                    plataformaDigital.url = plataformaDigitalJSON.url;
                    plataformaDigital.save(flush: true, failOnError: true);
                }


                List<Long> idsTelefonos = clienteJSON.telefonos.collect { t -> t?.idTelefono?.toLong() ?: 0 }
                List<Telefono> telefonosEliminar = Telefono.findAllByClienteAndIdNotInList(cliente, idsTelefonos);
                for (Telefono telefono : telefonosEliminar) {
                    telefono.delete(flush: true, failOnError: true);
                }
                for (def telefonoJSON : clienteJSON.telefonos) {
                    Long idTelefono = telefonoJSON?.idTelefono?.toLong() ?: null;
                    Telefono telefono = idTelefono ? Telefono.findById(idTelefono) : new Telefono(cliente: cliente);
                    telefono.telefono = telefonoJSON.telefono;
                    telefono.principal = telefonoJSON.principal;
                    telefono.tipoTelefono = TipoTelefono.findById(telefonoJSON.idTipoTelefono);
                    telefono.save(flush: true, failOnError: true);
                }

                List<Long> idsRepresentantes = clienteJSON.representantes.collect { t -> t?.idRepresentante?.toLong() ?: 0 }
                List<Representante> representantesEliminar = Representante.findAllByClienteAndIdNotInList(cliente, idsRepresentantes);
                for (Representante representante : representantesEliminar) {
                    Persona persona = representante.persona;
                    representante.delete(flush: true, failOnError: true);
                    persona.delete(flush: true, failOnError: true);
                }

                for (def representanteJSON : clienteJSON.representantes) {
                    Long idRepresentante = representanteJSON?.idRepresentante?.toLong() ?: null;
                    if (idRepresentante) {
                        Representante representante = Representante.findById(idRepresentante)
                        Persona persona = representante.persona;
                        persona.nombre = representanteJSON.nombre;
                        persona.primerApellido = representanteJSON.primerApellido;
                        persona.segundoApellido = representanteJSON.segundoApellido;
                        persona.fechaNacimiento = utileriaService.parsearDato(representanteJSON.fechaNacimiento);
                        persona.save(flush: true, failOnError: true);
                    } else {
                        Persona persona = new Persona(nombre: representanteJSON.nombre, primerApellido: representanteJSON.primerApellido,
                                segundoApellido: representanteJSON.segundoApellido,
                                fechaNacimiento: utileriaService.parsearDato(representanteJSON.fechaNacimiento));
                        persona.save(flush: true, failOnError: true);

                        Representante representante = new Representante(fechaRegistro: new Date());
                        representante.cliente = cliente;
                        representante.persona = persona;
                        representante.save(flush: true, failOnError: true);
                    }
                }


            } catch (Exception e) {
                // e.printStackTrace();
                statusTransaccion.setRollbackOnly()
                println e.getMessage()
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    def obtener(Long idCliente) throws ServiceException {
        try {

            Cliente cliente = Cliente.findById(idCliente);
            if (cliente == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Cliente", idCliente));
            }
            def clienteJSON = [:]

            clienteJSON.idCliente = cliente.id;
            clienteJSON.razonSocial = cliente.razonSocial;

            clienteJSON.correos = clienteJSON.correos ?: [];
            clienteJSON.telefonos = clienteJSON.telefonos ?: [];
            clienteJSON.representantes = clienteJSON.representantes ?: [];

            List<Representante> representantes = Representante.findAllByCliente(cliente);

            for (Representante representante : representantes) {
                clienteJSON.representantes.push([
                        idRepresentante: representante.id,
                        fechaRegistro  : representante.fechaRegistro,
                        nombre         : representante.persona.nombre,
                        primerApellido : representante.persona.primerApellido,
                        segundoApellido: representante.persona.segundoApellido,
                        fechaNacimiento: utileriaService.formatoFecha(representante.persona.fechaNacimiento)
                ]);
            }

            List<Correo> correos = Correo.findAllByCliente(cliente);

            for (Correo correo : correos) {
                clienteJSON.correos.push([
                        idCorreo : correo.id,
                        correo   : correo.correo,
                        principal: correo.principal
                ]);
            }

            List<Telefono> telefonos = Telefono.findAllByCliente(cliente);

            for (Telefono telefono : telefonos) {
                clienteJSON.telefonos.push([
                        idTelefono    : telefono.id,
                        telefono      : telefono.telefono,
                        principal     : telefono.principal,
                        idTipoTelefono: telefono.tipoTelefono.id
                ]);
            }

            Direccion direccion = Direccion.findByCliente(cliente);

            if (direccion) {
                clienteJSON.numeroInterior = direccion.numeroInterior;
                clienteJSON.numeroExterior = direccion.numeroExterior;
                clienteJSON.calle = direccion.calle;
                clienteJSON.colonia = direccion.colonia;
                clienteJSON.municipio = direccion.municipio;
                clienteJSON.estado = direccion.estado;
            }

            return clienteJSON
        } catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }
    void actualizarEstado(Long idCliente) throws ServiceException {
        Cliente.withTransaction { statusTransaccion ->
            try {
                Cliente cliente = Cliente.findById(idCliente);
                if (cliente == null) {
                    throw new ServiceException(messageService.getErrorNoEncontrado("Cliente", idEmpleado));
                }
                Estado nuevoEstado = cliente.estado.nombre == EstadoGeneralEnum.ACTIVO.getValor() ? utileriaService.obtenerEstadoInactivo() : utileriaService.obtenerEstadoActivo();
                cliente.estado = nuevoEstado;
                cliente.save(flush: true, failOnError: true);
            } catch (Exception e) {
                statusTransaccion.setRollbackOnly()
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }




}
