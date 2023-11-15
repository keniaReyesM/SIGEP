import RutasConstants from "@/core/constants/RutasConstants";
import PermisosContants from "@/core/constants/PermisosContants";

export default {
    path: RutasConstants.RESPONSABLE_COMPRA.path,
    name: RutasConstants.RESPONSABLE_COMPRA.nombre,
    component: () => import('@/views/crud/CrudView.vue'),
    meta: {
      servicio: () => import('@/core/services/ResponsableCompraService'),
      tabla: () => import('@/components/responsableCompra/ResponsableCompraTabla.vue'),
      formulario: () => import('@/components/responsableCompra/ResponsableCompraFormulario.vue'),
      permisoModulo: PermisosContants.RESPONSABLE_COMPRA_MODULO,
      permisoRegistrar: PermisosContants.RESPONSABLE_COMPRA_REGISTRAR,
      permisoEditar: PermisosContants.RESPONSABLE_COMPRA_EDITAR,
      permisoEliminar: PermisosContants.RESPONSABLE_COMPRA_ELIMINAR,
      encabezado:{
        titulo: "Responsables de compra",
      }
    }
};