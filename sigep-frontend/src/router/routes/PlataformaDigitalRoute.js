import PermisosContants from "@/core/constants/PermisosContants";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
  path: RutasConstants.PLATAFORMA_DIGITAL.path,
  name: RutasConstants.PLATAFORMA_DIGITAL.nombre,
  component: () => import('@/views/crud/CrudView.vue'),
  meta: {
    servicio: () => import('@/core/services/PlataformaDigitalService'),
    tabla: () => import('@/components/plataformaDigital/PlataformaDigitalTabla.vue'),
    formulario: () => import('@/components/plataformaDigital/PlataformaDigitalFormulario.vue'),
    permisoModulo: PermisosContants.PLATAFORMA_DIGITAL_MODULO,
    permisoRegistrar: PermisosContants.PLATAFORMA_DIGITAL_REGISTRAR,
    permisoEditar: PermisosContants.PLATAFORMA_DIGITAL_EDITAR,
    permisoEliminar: PermisosContants.PLATAFORMA_DIGITAL_ELIMINAR,
    encabezado: {
      icono: "mdi-cube",
      titulo: "Plataformas digitales",
    },
    modeloInicial: () => {
      return Object.assign({
        // telefonos: [{ telefono: null, idTipoTelefono: null, principal: false }],
        // correos: [{ correo: null, principal: false }],
        // representantes: [{
        //   nombre: null, primerApellido: null,
        //   segundoApellido: null, fechaNacimiento: null
        // }]
      }, {});
    }
  }
};