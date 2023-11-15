import com.accion.ti.*
import com.accion.ti.enums.CategoriaEnum
import com.accion.ti.enums.DiaEnum
import com.accion.ti.enums.EstadoGeneralEnum
import com.accion.ti.enums.NivelPrioridadEnum
import com.accion.ti.enums.RolEnum
import com.accion.ti.enums.TipoEstadoEnum
import com.accion.ti.enums.TipoPrivacidadEnum
import com.accion.ti.enums.TipoTareaEnum
import com.accion.ti.constants.PermisosConstants;
import org.codehaus.groovy.grails.commons.GrailsApplication

class BootStrap {

    GrailsApplication grailsApplication


    
    def construirInformacion() {

        def existeUrl = null;

        //PERMISOS: PUBLICOS
        for (String url in ['/', '/index', '/index.gsp', '/error500', '/error500.gsp', '/error404', '/error404.gsp',
                            '/**/static/**', '/**/favicon.ico', '/**/ext/**', '/**/app/**', '/**/fonts/**', '/**/css/**', '/**/images/**',
                            '/**/json/**', '/api/user/validateUserLogin',
                            '/login', '/error', '/login.*', '/login/*', '/logout', '/logout.*', '/logout/*', '/oauth/access_token', '/register/forgotPassword']) {
            existeUrl = Recurso.findByUrl(url)
            if (existeUrl.is(null)) {
                new Recurso(url: url, configAttribute: 'permitAll').save()
            } else {
                existeUrl.configAttribute = 'permitAll'
                existeUrl.save()
            }
        }

        grailsApplication.controllerClasses.findAll { it.getFullName() =~ /^com\.accion\.ti\.\w+Controller$/ }.collect { con ->
            [controlador: con.getName()[0].toLowerCase() + con.getName()[1..-1],
             acciones   : con.getClazz().declaredMethods.findAll {
                 it.getDeclaredAnnotations().findAll { it.toString() =~ /grails.web.Action/ }.size()
             }.collect {
                 [nombreMinuscula: '/api/' + con.getName()[0].toLowerCase() + con.getName()[1..-1] + '/' + it.getName(), nombreReal: '/api/' + con.getName()[0].toLowerCase() + con.getName()[1..-1] + '/' + it.getName()]
             }
            ]
        }.each {
            it.acciones.add([nombreMinuscula: '/' + it.controlador + '/', nombreReal: '/' + it.controlador[0].toUpperCase() + it.controlador[1..-1] + '/'])
            it.acciones.findAll {//
                (it.nombreMinuscula.split("/").size() ?: 0) > 2//
            }.findAll {//
                !it.nombreMinuscula.endsWith("index")//
            }.findAll {//
                !it.nombreMinuscula.endsWith("beforeInterceptor")//
            }.each {
                existeUrl = Recurso.findByUrl(it.nombreMinuscula)
                if (existeUrl.is(null)) {
                    new Recurso(url: it.nombreMinuscula.contains('index') ? it.nombreMinuscula.replaceAll(~/api\//, '') : it.nombreMinuscula, configAttribute: 'IS_AUTHENTICATED_FULLY').save()
                }
            }
        }

        List<String> tiposTelefono = ["Casa", "Personal", "Trabajo"];
        for (String nombre : tiposTelefono) {
            if (TipoTelefono.findByNombre(nombre) == null) {
                new TipoTelefono(nombre: nombre).save()
            }
        }

        String nombreEmpresa = "ACCION TI";
        Empresa empresa = null;

        Empresa.withTransaction { statusTransaction ->
            try {
                def empresas = [[nombre       : nombreEmpresa, razonSocial: nombreEmpresa, fechaRegistro: new Date(),
                                 fechaLicencia: "16/02/1998",
                                 correo       : "accionti@gmail.com",
                                 telefono     : "7771234567",
                                 tipoTelefono : "Trabajo",
                                 direccion    : [numeroInterior: "1", numeroExterior: "N/D", calle: "4ta Privada Adolfo López",
                                                 colonia       : "Latveria", municipio: "Cuernavaca", estado: "Morelos"]
                                ]];

                for (def elemento : empresas) {
                    empresa = Empresa.findByNombre(elemento.nombre);
                    if (empresa == null) {

                        Correo correo = new Correo(correo: elemento.correo, principal: true);
                        correo.save(flush: true, failOnError: true);

                        Telefono telefono = new Telefono(telefono: elemento.telefono, principal: true,
                                tipoTelefono: TipoTelefono.findByNombre(elemento.tipoTelefono));
                        telefono.save(flush: true, failOnError: true);

                        Direccion direccion = new Direccion(numeroInterior: elemento.direccion.numeroInterior, numeroExterior: elemento.direccion.numeroExterior,
                                calle: elemento.direccion.calle, colonia: elemento.direccion.colonia, municipio: elemento.direccion.municipio,
                                estado: elemento.direccion.estado);
                        direccion.save(flush: true, failOnError: true);

                        empresa = new Empresa(nombre: elemento.nombre, razonSocial: elemento.razonSocial,
                                fechaRegistro: elemento.fechaRegistro,
                                fechaLicencia: new Date().parse('dd/MM/yyyy', elemento.fechaLicencia),
                                correo: correo, telefono: telefono, direccion: direccion
                        );
                        empresa.save(flush: true, failOnError: true);

                    }
                }
            } catch (Exception e) {
                statusTransaction.setRollbackOnly()
                println "error ${e.getMessage()}"
               //e.printStackTrace();
                throw new Exception(e)
            }
        }

        List<String> categorias = ["Redes sociales", "Marketing Digital", CategoriaEnum.NO_DEFINIDO.getValor()];
        for (String nombre : categorias) {
            if (Categoria.findByNombreAndEmpresa(nombre, empresa) == null) {
                new Categoria(nombre: nombre, descripcion: nombre, empresa: empresa).save()
            }
        }

        List<String> areas = ["Desarrollo", "SEO"];
        for (String nombre : areas) {
            if (Area.findByNombreAndEmpresa(nombre, empresa) == null) {
                new Area(nombre: nombre, empresa: empresa).save()
            }
        }


        List<String> tiposTarea = TipoTareaEnum.values()*.getValor();
        for (String nombre : tiposTarea) {
            if (TipoTarea.findByNombre(nombre) == null) {
                new TipoTarea(nombre: nombre).save()
            }
        }

        List<String> puestos = ["Soporte", "Desarrollador"];
        for (String nombre : puestos) {
            if (Puesto.findByNombreAndEmpresa(nombre, empresa) == null) {
                new Puesto(nombre: nombre, empresa: empresa).save()
            }
        }

        List<String> gestoresContenidos = ["WordPress", "Magento", "Drupal", "Joomla", "Silverstripe", "Prestashop"];
        for (String nombre : gestoresContenidos) {
            if (GestorContenido.findByNombre(nombre) == null) {
                new GestorContenido(nombre: nombre).save()
            }
        }

        List<String> proveedoresAlojamiento = ["Hostinger", "GoDaddy", "AWS", "000webhost", "Google Cloud", "Wix"];
        for (String nombre : proveedoresAlojamiento) {
            if (ProveedorAlojamiento.findByNombre(nombre) == null) {
                new ProveedorAlojamiento(nombre: nombre).save()
            }
        }

        
        List<String> responsablesCompra = ["Cliente", "Empresa", "No definido"];
        for (String nombre : responsablesCompra) {
            if (ResponsableCompra.findByNombre(nombre) == null) {
                new ResponsableCompra(nombre: nombre).save()
            }
        }

        List<String> tecnologiasDesarrollo = ["Java", "MySQL", "PHP", "noSQL"];
        for (String nombre : tecnologiasDesarrollo) {
            if (TecnologiaDesarrollo.findByNombre(nombre) == null) {
                new TecnologiaDesarrollo(nombre: nombre).save()
            }
        }

        List<String> tiposPlataformaDigital = ["Sitio web", "Multi sitio", "Blog", "Plataforma educativa", "Landing page"];
        for (String nombre : tiposPlataformaDigital) {
            if (TipoPlataformaDigital.findByNombre(nombre) == null) {
                new TipoPlataformaDigital(nombre: nombre).save()
            }
        }


        List<String> dias = DiaEnum.values()*.getValor();
        for (String nombre : dias) {
            if (Dia.findByNombre(nombre) == null) {
                new Dia(nombre: nombre).save()
            }
        }

        List<String> tiposPrivacidad = TipoPrivacidadEnum.values()*.getValor();
        for (String nombre : tiposPrivacidad) {
            if (TipoPrivacidad.findByNombre(nombre) == null) {
                new TipoPrivacidad(nombre: nombre).save()
            }
        }


        def estadosGenerales = EstadoGeneralEnum.values().collect { return [nombre: it.getValor()] };
        def tiposEstado = [
                [nombre: TipoEstadoEnum.GENERAL.getValor(), estados: estadosGenerales],
                [
                        nombre: TipoEstadoEnum.TAREA.getValor(), estados: [
                        [color: "#B6D7A8", nombre: "INICIALIZADO", porcentaje: "0"],
                        [color: "#F5F3B3", nombre: "PENDIENTE", porcentaje: "10"],
                        [color: "#E38989", nombre: "SUSPENDIDO", porcentaje: "0"],
                        [color: "#82D4D0", nombre: "EN PROCESO", porcentaje: "50"],
                        [color: "#B3E5FC", nombre: "TERMINADO", porcentaje: "100"]
                ]
                ]
        ];
        // List<Empresa> empresas = Empresa.findAll();
        // println empresas
        // for(Empresa empresaEstado : empresas){
        for (def objetoTipoEstado : tiposEstado) {
            TipoEstado tipoEstado = TipoEstado.findByNombreAndEmpresa(objetoTipoEstado.nombre, empresa);
            if (tipoEstado == null) {
                tipoEstado = new TipoEstado(nombre: objetoTipoEstado.nombre, empresa: empresa)
                tipoEstado.save(flush: true, failOnError: true)
            }
            for (def estadoJSON : objetoTipoEstado.estados) {
                if (Estado.findByNombreAndTipoEstado(estadoJSON.nombre, tipoEstado) == null) {
                    new Estado(tipoEstado: tipoEstado, nombre: estadoJSON.nombre, color: estadoJSON.color, porcentaje: estadoJSON.porcentaje).save()
                }
            }

        }
        // }


        def roles = RolEnum.values().collect { [authority: it.getRole(), descripcion: it.getDescripcion()] }
        for (def rol : roles) {
            if (Rol.findByAuthority(rol.authority) == null) {
                new Rol(descripcion: rol.descripcion, authority: rol.authority).save(flush: true, failOnError: true);
            }
        }

        construirPermisos(PermisosConstants.permisos);

        def nivelesPrioridad = NivelPrioridadEnum.values().collect {
            return [nombre: it.getNombre(), prioridad: it.getPrioridad()]
        }
        for (def nivelePrioridad : nivelesPrioridad) {
            if (NivelPrioridad.findByNombre(nivelePrioridad.nombre) == null) {
                new NivelPrioridad(nombre: nivelePrioridad.nombre, prioridad: nivelePrioridad.prioridad).save()
            }
        }

       

        Usuario.withTransaction { statusTransaction ->
            try {
                def usuarios = [
                        [nombre         : "Marlen", primerApellido: "Quirós", segundoApellido: null,
                         fechaNacimiento: "16/02/1998",
                         telefonos      : [[telefono: "7771234567", principal: true, tipoTelefono: "Personal"]],
                         correos        : [[correo: "admin@gmail.com", principal: true]],
                         direccion      : [numeroInterior: "1", numeroExterior: "N/D", calle: "4ta Privada Adolfo López",
                                           colonia       : "Latveria", municipio: "Cuernavaca", estado: "Morelos"],
                         usuario        : [usuario: "admin@gmail.com", contrasena: "admin", rol: "Administrador"],
                         areas          : areas, puesto: "Desarrollador", horariosLaborales: [[dia: "Lunes", horaInicio: "08:00:00", horaFin: "14:00:00"]]
                        ]
                ];

                TipoEstado tipoEstado = TipoEstado.findByNombre("General");
                Estado estado = Estado.findByNombreAndTipoEstado("Activo", tipoEstado);

                for (def elemento : usuarios) {
                    if (Usuario.findByUsername(elemento.usuario.usuario) == null) {

                        String correoPrincipal = elemento.correos.find { c -> c.principal }.correo;
                        Usuario usuario = new Usuario(username: elemento.usuario.usuario, password: elemento.usuario.contrasena, email: correoPrincipal);
                        usuario.save(flush: true, failOnError: true);

                        Rol rol = Rol.findByDescripcion(elemento.usuario.rol)
                        new UsuarioRol(usuario: usuario, rol: rol).save(flush: true, failOnError: true);

                        Persona persona = new Persona(nombre: elemento.nombre, primerApellido: elemento.primerApellido,
                                segundoApellido: elemento.segundoApellido,
                                fechaNacimiento: new Date().parse('dd/MM/yyyy', elemento.fechaNacimiento));
                        persona.save(flush: true, failOnError: true);


                        Direccion direccion = new Direccion(numeroInterior: elemento.direccion.numeroInterior, numeroExterior: elemento.direccion.numeroExterior,
                                calle: elemento.direccion.calle, colonia: elemento.direccion.colonia, municipio: elemento.direccion.municipio,
                                estado: elemento.direccion.estado, persona: persona);
                        direccion.save(flush: true, failOnError: true);

                        for (def elementoTelefono : elemento.telefonos) {
                            Telefono telefono = new Telefono(telefono: elementoTelefono.telefono,
                                    principal: elementoTelefono.principal,
                                    persona: persona,
                                    tipoTelefono: TipoTelefono.findByNombre(elementoTelefono.tipoTelefono))
                            telefono.save(flush: true, failOnError: true);
                        }

                        for (def elementoCorreo : elemento.correos) {
                            new Correo(correo: elementoCorreo.correo, principal: elementoCorreo.principal, persona: persona).save(flush: true, failOnError: true);
                        }

                        Empleado empleado = new Empleado(fechaRegistro: new Date(),
                                usuario: usuario, estado: estado, empresa: empresa,
                                persona: persona, puesto: Puesto.findByNombre(elemento.puesto));
                        empleado.save(flush: true, failOnError: true);


                        for (def horario : elemento.horariosLaborales) {
                            HorarioLaboral horariosLaboral = new HorarioLaboral(dia: Dia.findByNombre(horario.dia), empleado: empleado,
                                    horaInicio: new Date().parse('HH:mm', horario.horaInicio),
                                    horaFin: new Date().parse('HH:mm', horario.horaFin));
                            horariosLaboral.save(flush: true, failOnError: true);
                        }

                        for (def nombreArea : elemento.areas) {
                            AreaEmpleado areaEmpleado = new AreaEmpleado(fechaRegistro: new Date(),
                                    area: Area.findByNombreAndEmpresa(nombreArea, empresa),
                                    empleado: empleado, empleadoAsignacion: empleado, estado: estado);
                            areaEmpleado.save(flush: true, failOnError: true);
                        }


                    }
                }
            } catch (Exception e) {
                statusTransaction.setRollbackOnly()
                println "error ${e.getMessage()}"
               //e.printStackTrace();
                throw new Exception(e)
            }
        }

        // DEfinir todos los permisos para el rol ADMINISTRADOR
        Rol rol = Rol.findByAuthority(RolEnum.ADMINISTRADOR.getRole());
        def permisos = Permiso.findAll();
        for(Permiso permiso : permisos){
            if(PermisoRol.findByPermisoAndRol(permiso, rol) == null){
                new PermisoRol(permiso: permiso, rol: rol).save(flush: true, failOnError: true);
            }
        }

    }
    
    def construirPermisos(def permisos, Permiso permisoPadre = null){
        if(permisos){
            for (def elemento : permisos) {
                Permiso permiso = Permiso.findByClave(elemento.clave);
                if( permiso == null){    
                    permiso = new Permiso(nombre: elemento.nombre, clave: elemento.clave, permiso: permisoPadre);
                }
                permiso.clave = elemento.clave
                permiso.nombre = permisoPadre ? permisoPadre.nombre + " / " + elemento.nombre : elemento.nombre
                permiso.save(flush: true, failOnError: true);

                def permisosRol = PermisoRol.findAllByPermiso(permiso);
                for (permisoRol in permisosRol) {
                    permisoRol.delete(flush: true, failOnError: true);
                } 

                for(def nombreRol : elemento.roles){
                    Rol rol = Rol.findByAuthority(nombreRol);
                    if(PermisoRol.findByPermisoAndRol(permiso, rol) == null){
                        new PermisoRol(permiso: permiso, rol: rol).save(flush: true, failOnError: true);
                    }
                }
                
                construirPermisos(elemento.permisos, permiso);

            }
        }
    }
    def init = { servletContext ->
        construirInformacion();
    }

    def destroy = {
    }

}
