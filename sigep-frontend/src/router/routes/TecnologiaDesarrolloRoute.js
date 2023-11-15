import PermisosContants from "@/core/constants/PermisosContants";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
    path: RutasConstants.TECNOLOGIA_DESARROLLO.path,
    name: RutasConstants.TECNOLOGIA_DESARROLLO.nombre,
    component: () => import('@/views/crud/CrudView.vue'),
    meta: {
      servicio: () => import('@/core/services/TecnologiaDesarrolloService'),
      tabla: () => import('@/components/tecnologiaDesarrollo/TecnologiaDesarrolloTabla.vue'),
      formulario: () => import('@/components/tecnologiaDesarrollo/TecnologiaDesarrolloFormulario.vue'),
      permisoModulo: PermisosContants.TECNOLOGIA_DESARROLLO_MODULO,
      permisoRegistrar: PermisosContants.TECNOLOGIA_DESARROLLO_REGISTRAR,
      permisoEditar: PermisosContants.TECNOLOGIA_DESARROLLO_EDITAR,
      permisoEliminar: PermisosContants.TECNOLOGIA_DESARROLLO_ELIMINAR,
      encabezado:{
        titulo: "Tecnologías de desarrollo",
      }
    }
};