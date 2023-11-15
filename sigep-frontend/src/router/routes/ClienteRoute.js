import PermisosContants from "@/core/constants/PermisosContants";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
  path: RutasConstants.CLIENTE.path,
  name: RutasConstants.CLIENTE.nombre,
  component: () => import('@/views/crud/CrudView.vue'),
  meta: {
    servicio: () => import('@/core/services/ClienteService'),
    tabla: () => import('@/components/cliente/ClienteTabla.vue'),
    formulario: () => import('@/components/cliente/ClienteFormulario.vue'),
    permisoModulo: PermisosContants.CLIENTE_MODULO,
    permisoRegistrar: PermisosContants.CLIENTE_REGISTRAR,
    permisoEditar: PermisosContants.CLIENTE_EDITAR,
    permisoEliminar: PermisosContants.CLIENTE_ELIMINAR,
    permisoActualizarEstado: PermisosContants.CLIENTE_ACTUALIZAR_ESTADO,
    encabezado: {
      icono: "mdi-cube",
      titulo: "Clientes",
    },
    modeloInicial: () => {
      return Object.assign({
        telefonos: [{ telefono: null, idTipoTelefono: null, principal: false }],
        correos: [{ correo: null, principal: false }],
        representantes: [{
          nombre: null, primerApellido: null,
          segundoApellido: null, fechaNacimiento: null
        }]
      }, {});
    }
  }
};