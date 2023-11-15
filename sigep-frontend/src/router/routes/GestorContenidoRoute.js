import PermisosContants from "@/core/constants/PermisosContants";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
    path: RutasConstants.GESTOR_CONTENIDO.path,
    name: RutasConstants.GESTOR_CONTENIDO.nombre,
    component: () => import('@/views/crud/CrudView.vue'),
    meta: {
      servicio: () => import('@/core/services/GestorContenidoService'),
      tabla: () => import('@/components/gestorContenido/GestorContenidoTabla.vue'),
      formulario: () => import('@/components/gestorContenido/GestorContenidoFormulario.vue'),
      permisoModulo: PermisosContants.GESTOR_CONTENIDO_MODULO,
      permisoRegistrar: PermisosContants.GESTOR_CONTENIDO_REGISTRAR,
      permisoEditar: PermisosContants.GESTOR_CONTENIDO_EDITAR,
      permisoEliminar: PermisosContants.GESTOR_CONTENIDO_ELIMINAR,
      encabezado:{
        titulo: "Gestores de contenido",
      }
    }
};