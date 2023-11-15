import PermisosContants from "@/core/constants/PermisosContants";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
    path: RutasConstants.AREA.path,
    name: RutasConstants.AREA.nombre,
    component: () => import('@/views/crud/CrudView.vue'),
    meta: {
      servicio: () => import('@/core/services/AreaService'),
      tabla: () => import('@/components/area/AreaTabla.vue'),
      formulario: () => import('@/components/area/AreaFormulario.vue'),
      permisoModulo: PermisosContants.AREA_MODULO,
      permisoRegistrar: PermisosContants.AREA_REGISTRAR,
      permisoEditar: PermisosContants.AREA_EDITAR,
      permisoEliminar: PermisosContants.AREA_ELIMINAR,
      encabezado:{
        titulo: "Áreas",
      }
    }
};