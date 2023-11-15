import PermisosContants from "@/core/constants/PermisosContants";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
  path: RutasConstants.AVANCE.path,
  name: RutasConstants.AVANCE.nombre,
  component: () => import('@/views/avance/AvanceView.vue'),
  meta: {
    servicio: () => import('@/core/services/AvanceService'),
    tabla: () => import('@/components/avance/AvanceTabla.vue'),
    formulario: () => import('@/components/avance/AvanceFormulario.vue'),
    permisoModulo: PermisosContants.AVANCE_MODULO,
    permisoRegistrar: PermisosContants.AVANCE_REGISTRAR,
    permisoEliminar: PermisosContants.AVANCE_ELIMINAR,
    encabezado: {
      icono: "mdi-cube",
      titulo: " ",
    }
  }
};