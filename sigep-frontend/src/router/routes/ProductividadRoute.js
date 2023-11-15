import PermisosContants from "@/core/constants/PermisosContants";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
  path: RutasConstants.PRODUCTIVIDAD.path,
  name: RutasConstants.PRODUCTIVIDAD.nombre,
  component: () => import('@/views/productividad/ProductividadView.vue'),
  meta: {
    permisoModulo: PermisosContants.TAREA_MODULO,
    encabezado: {
      icono: "pe-7s-graph2",
      titulo: "Productividad",
    }
  }
};