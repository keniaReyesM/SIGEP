import PermisosContants from "@/core/constants/PermisosContants";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
  path: RutasConstants.TAREA.path,
  name: RutasConstants.TAREA.nombre,
  component: () => import('@/views/tarea/TareaView.vue'),
  meta: {
    servicio: () => import('@/core/services/TareaService'),
    tabla: () => import('@/components/tarea/TareaTabla.vue'),
    formulario: () => import('@/components/tarea/TareaFormulario.vue'),
    permisoModulo: PermisosContants.TAREA_MODULO,
    permisoRegistrar: PermisosContants.TAREA_REGISTRAR,
    permisoEditar: PermisosContants.TAREA_EDITAR,
    permisoEliminar: PermisosContants.TAREA_ELIMINAR,
    permisoActualizarEstado: PermisosContants.TAREA_ACTUALIZAR_ESTADO,
    encabezado: {
      icono: "mdi-cube",
      titulo: " ",
    }
  }
};