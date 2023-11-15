import PermisosContants from "@/core/constants/PermisosContants";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
    path: RutasConstants.PROVEEDOR_ALOJAMIENTO.path,
    name: RutasConstants.PROVEEDOR_ALOJAMIENTO.nombre,
    component: () => import('@/views/crud/CrudView.vue'),
    meta: {
      servicio: () => import('@/core/services/ProveedorAlojamientoService'),
      tabla: () => import('@/components/proveedorAlojamiento/ProveedorAlojamientoTabla.vue'),
      formulario: () => import('@/components/proveedorAlojamiento/ProveedorAlojamientoFormulario.vue'),
      permisoModulo: PermisosContants.PROVEEDOR_ALOJAMIENTO_MODULO,
      permisoRegistrar: PermisosContants.PROVEEDOR_ALOJAMIENTO_REGISTRAR,
      permisoEditar: PermisosContants.PROVEEDOR_ALOJAMIENTO_EDITAR,
      permisoEliminar: PermisosContants.PROVEEDOR_ALOJAMIENTO_ELIMINAR,
      encabezado:{
        titulo: "Proveedores de alojamiento",
      }
    }
};