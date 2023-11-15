package com.accion.ti

import org.apache.commons.logging.LogFactory
import org.hibernate.transform.Transformers
import com.accion.ti.dto.exception.ServiceException
import com.accion.ti.enums.EstadoGeneralEnum;
import com.accion.ti.dto.response.RespuestaListaDTO;
import com.accion.ti.dto.pagination.PaginacionDTO;

class PlataformaDigitalService {

    private static final LOGGER = LogFactory.getLog(this)
    static transactional = false
    MessageService messageService
    UtileriaService utileriaService

    RespuestaListaDTO<Map> listarPaginado(PaginacionDTO paginacionDTO) throws ServiceException {

        try {

            String busqueda = "%${paginacionDTO.busqueda}%";


            def criteria = PlataformaDigital.createCriteria().list(max: paginacionDTO.maximoResultados, offset: paginacionDTO.cantidadOmitir) {
                resultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                order("id", "desc")
                createAlias 'cliente', 'cliente'
                createAlias 'cliente.empresa', 'empresa'
                createAlias 'tipoPlataformaDigital', 'tipoPlataformaDigital'
                or {
                    ilike("nombre", busqueda)
                    ilike("url", busqueda)
                    ilike("cliente.razonSocial", busqueda)
                    ilike("tipoPlataformaDigital.nombre", busqueda)
                }
                eq("cliente.empresa", utileriaService.obtenerEmpresaSesion())
                projections {
                    property 'id', 'idPlataformaDigital'
                    property 'nombre', 'nombre'
                    property 'url', 'url'
                    property 'nombre', 'nombre'
                    property 'cliente.razonSocial', 'cliente'
                    property 'tipoPlataformaDigital.nombre', 'tipoPlataformaDigital'
                }
            }

            return new RespuestaListaDTO<Map>(elementos: criteria, total: criteria.totalCount);

        } catch (Exception e) {
            LOGGER.error(e.getMessage())
            throw new ServiceException(messageService.getMessageException(e.getMessage()))
        }

    }

    void registrar(def plataformaDigitalJSON) throws ServiceException {
        PlataformaDigital.withTransaction { statusTransaccion ->
            try {

                Cliente cliente = Cliente.findById(plataformaDigitalJSON.idCliente.toLong());
                ProveedorAlojamiento proveedorAlojamiento = ProveedorAlojamiento.findById(plataformaDigitalJSON.idProveedorAlojamiento.toLong());
                ResponsableCompra responsableCompra = ResponsableCompra.findById(plataformaDigitalJSON.idResponsableCompra.toLong());
                TipoPlataformaDigital tipoPlataformaDigital = TipoPlataformaDigital.findById(plataformaDigitalJSON.idTipoPlataformaDigital.toLong());

                PlataformaDigital plataformaDigital = new PlataformaDigital(
                     nombre: plataformaDigitalJSON.nombre,
                     url: plataformaDigitalJSON.url,
                     ip: plataformaDigitalJSON.ip,
                     cliente: cliente,
                     tipoPlataformaDigital: tipoPlataformaDigital,
                     responsableCompra: responsableCompra,
                     proveedorAlojamiento: proveedorAlojamiento
                );
                plataformaDigital.save(flush: true, failOnError: true);

                plataformaDigitalJSON.tecnologiasDesarrollo = plataformaDigitalJSON.tecnologiasDesarrollo ?: [];
                for (def tecnologiaDesarrolloJSON : plataformaDigitalJSON.tecnologiasDesarrollo) {
                    new TecnologiaDesarrolloPlataformaDigital(
                        tecnologiaDesarrollo: TecnologiaDesarrollo.findById(tecnologiaDesarrolloJSON.idTecnologiaDesarrollo.toLong()),
                        plataformaDigital: plataformaDigital).save(flush: true, failOnError: true);
                }

                plataformaDigitalJSON.gestoresContenido = plataformaDigitalJSON.gestoresContenido ?: [];
                for (def gestorContenidoJSON : plataformaDigitalJSON.gestoresContenido) {
                    new GestorContenidoPlataformaDigital(
                        gestorContenido: GestorContenido.findById(gestorContenidoJSON.idGestorContenido.toLong()),
                        plataformaDigital: plataformaDigital).save(flush: true, failOnError: true);
                }

            


            } catch (Exception e) {
                statusTransaccion.setRollbackOnly()
                // e.printStackTrace();
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }

    void actualizar(def plataformaDigitalJSON) throws ServiceException {
        PlataformaDigital.withTransaction { statusTransaccion ->
            try {



                Cliente cliente = Cliente.findById(plataformaDigitalJSON.idCliente.toLong());
                ProveedorAlojamiento proveedorAlojamiento = ProveedorAlojamiento.findById(plataformaDigitalJSON.idProveedorAlojamiento.toLong());
                ResponsableCompra responsableCompra = ResponsableCompra.findById(plataformaDigitalJSON.idResponsableCompra.toLong());
                TipoPlataformaDigital tipoPlataformaDigital = TipoPlataformaDigital.findById(plataformaDigitalJSON.idTipoPlataformaDigital.toLong());

                PlataformaDigital plataformaDigital = PlataformaDigital.findById(plataformaDigitalJSON.idPlataformaDigital?.toLong());
                if (plataformaDigital == null) {
                    throw new ServiceException(messageService.getErrorNoEncontrado("Plataforma digital", plataformaDigitalJSON.idPlataformaDigital))
                }
                
                plataformaDigital.nombre = plataformaDigitalJSON.nombre;
                plataformaDigital.url = plataformaDigitalJSON.url;
                plataformaDigital.ip = plataformaDigitalJSON.ip;
                plataformaDigital.cliente = cliente;
                plataformaDigital.tipoPlataformaDigital = tipoPlataformaDigital;
                plataformaDigital.responsableCompra = responsableCompra;
                plataformaDigital.proveedorAlojamiento = proveedorAlojamiento;
                plataformaDigital.save(flush: true, failOnError: true);
                
                plataformaDigitalJSON.tecnologiasDesarrollo = plataformaDigitalJSON.tecnologiasDesarrollo ?: [];
                
                List<Long> idsTecnologiasDesarrollo = plataformaDigitalJSON.tecnologiasDesarrollo.collect { t -> t?.idTecnologiaDesarrollo?.toLong() ?: 0 } ?: [0]
            
                List<TecnologiaDesarrolloPlataformaDigital> tecnologiasEliminar = TecnologiaDesarrolloPlataformaDigital.createCriteria().list(){
                    tecnologiaDesarrollo{
                        not{
                            'in'("id",idsTecnologiasDesarrollo)
                        }
                    }
                }

                for (TecnologiaDesarrolloPlataformaDigital tecnologia : tecnologiasEliminar) {
                    tecnologia.delete(flush: true, failOnError: true);
                }

                for (def tecnologiaDesarrolloJSON : plataformaDigitalJSON.tecnologiasDesarrollo) {
                    TecnologiaDesarrollo tecnologiaDesarrollo = TecnologiaDesarrollo.findById(tecnologiaDesarrolloJSON.idTecnologiaDesarrollo.toLong())
                    TecnologiaDesarrolloPlataformaDigital tecnologia = TecnologiaDesarrolloPlataformaDigital.findByTecnologiaDesarrolloAndPlataformaDigital(tecnologiaDesarrollo, plataformaDigital);
                    if(tecnologia == null){
                        new TecnologiaDesarrolloPlataformaDigital(
                            tecnologiaDesarrollo: tecnologiaDesarrollo,
                            plataformaDigital: plataformaDigital
                        ).save(flush: true, failOnError: true);
                    }
                }
                



                plataformaDigitalJSON.gestoresContenido = plataformaDigitalJSON.gestoresContenido ?: [];

                List<Long> idsGestoresContenido = plataformaDigitalJSON.gestoresContenido.collect { t -> t?.idGestorContenido?.toLong() ?: 0 } ?: [0]

                List<GestorContenidoPlataformaDigital> gestoresEliminar = GestorContenidoPlataformaDigital.createCriteria().list(){
                    gestorContenido{
                        not{
                            'in'("id",idsGestoresContenido)
                        }
                    }
                }
                
                for (GestorContenidoPlataformaDigital gestor : gestoresEliminar) {
                    gestor.delete(flush: true, failOnError: true);
                }

                for (def gestorContenidoJSON : plataformaDigitalJSON.gestoresContenido) {
                    GestorContenido gestorContenido = GestorContenido.findById(gestorContenidoJSON.idGestorContenido.toLong())
                    GestorContenidoPlataformaDigital gestor = GestorContenidoPlataformaDigital.findByGestorContenidoAndPlataformaDigital(gestorContenido, plataformaDigital);
                    if(gestor == null){
                        new GestorContenidoPlataformaDigital(
                            gestorContenido: gestorContenido,
                            plataformaDigital: plataformaDigital
                        ).save(flush: true, failOnError: true);
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

    def obtener(Long idPlataformaDigital) throws ServiceException {
        try {

            PlataformaDigital plataformaDigital = PlataformaDigital.findById(idPlataformaDigital);
            if (plataformaDigital == null) {
                throw new ServiceException(messageService.getErrorNoEncontrado("Plataforma Digital", idPlataformaDigital));
            }
            def plataformaDigitalJSON = [:]


            plataformaDigitalJSON.idPlataformaDigital = plataformaDigital.id;
            plataformaDigitalJSON.nombre = plataformaDigital.nombre;
            plataformaDigitalJSON.url = plataformaDigital.url;
            plataformaDigitalJSON.ip = plataformaDigital.ip;
            plataformaDigitalJSON.idCliente = plataformaDigital?.cliente?.id?:null;
            plataformaDigitalJSON.idTipoPlataformaDigital = plataformaDigital?.tipoPlataformaDigital?.id?:null;
            plataformaDigitalJSON.idResponsableCompra = plataformaDigital?.responsableCompra?.id?:null;
            plataformaDigitalJSON.idProveedorAlojamiento = plataformaDigital?.proveedorAlojamiento?.id?:null;

            plataformaDigitalJSON.gestoresContenido =  [];
            List<GestorContenidoPlataformaDigital> gestores = GestorContenidoPlataformaDigital.findAllByPlataformaDigital(plataformaDigital);
            for (GestorContenidoPlataformaDigital gestor : gestores) {
                plataformaDigitalJSON.gestoresContenido.push([idGestorContenido: gestor.gestorContenido.id, nombre: gestor.gestorContenido.nombre]);
            }
            
            plataformaDigitalJSON.tecnologiasDesarrollo =  [];
            List<TecnologiaDesarrolloPlataformaDigital> tecnologias = TecnologiaDesarrolloPlataformaDigital.findAllByPlataformaDigital(plataformaDigital);
            for (TecnologiaDesarrolloPlataformaDigital tecnologia : tecnologias) {
                plataformaDigitalJSON.tecnologiasDesarrollo.push([idTecnologiaDesarrollo: tecnologia.tecnologiaDesarrollo.id, nombre: tecnologia.tecnologiaDesarrollo.nombre]);
            }
            
            return plataformaDigitalJSON
        } catch (Exception e) {
            LOGGER.error(messageService.getMessageErrors(e))
            throw new ServiceException(messageService.getMessageErrors(e))
        }
    }

    void eliminar(Long idPlataformaDigital) throws ServiceException {
         PlataformaDigital.withTransaction { statusTransaccion ->
            try {



              
                PlataformaDigital plataformaDigital = PlataformaDigital.findById(idPlataformaDigital);
                if (plataformaDigital == null) {
                    throw new ServiceException(messageService.getErrorNoEncontrado("Plataforma digital", idPlataformaDigital))
                }
                
                List<TecnologiaDesarrolloPlataformaDigital> tecnologiasEliminar = TecnologiaDesarrolloPlataformaDigital.findAllByPlataformaDigital(plataformaDigital);
                List<GestorContenidoPlataformaDigital> gestoresEliminar = GestorContenidoPlataformaDigital.findAllByPlataformaDigital(plataformaDigital);

                tecnologiasEliminar*.delete(flush: true, failOnError: true);
                gestoresEliminar*.delete(flush: true, failOnError: true);
                
                plataformaDigital.delete(flush: true, failOnError: true);
                


            } catch (Exception e) {
                // e.printStackTrace();
                statusTransaccion.setRollbackOnly()
                LOGGER.error(messageService.getMessageErrors(e))
                throw new ServiceException(messageService.getMessageErrors(e))
            }
        }
    }
 




}
