import PermisosContants from "@/core/constants/PermisosContants";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
    path: RutasConstants.PROYECTO.path,
    name: RutasConstants.PROYECTO.nombre,
    component: () => import('@/views/crud/CrudView.vue'),
    meta: {
      servicio: () => import('@/core/services/ProyectoService'),
      tabla: () => import('@/components/proyecto/ProyectoTabla.vue'),
      formulario: () => import('@/components/proyecto/ProyectoFormulario.vue'),
      permisoModulo: PermisosContants.PROYECTO_MODULO,
      permisoRegistrar: PermisosContants.PROYECTO_REGISTRAR,
      permisoEditar: PermisosContants.PROYECTO_EDITAR,
      permisoEliminar: PermisosContants.PROYECTO_ELIMINAR,
      permisoActualizarEstado: PermisosContants.PROYECTO_ACTUALIZAR_ESTADO,
      encabezado:{
        icono: "mdi-cube",
        titulo: "Proyectos",
      },
      modeloInicial: () => {
        return Object.assign({clientes: [], empleados: []}, {});
      }
    }
};