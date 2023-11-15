import RutasConstants from "@/core/constants/RutasConstants";
import PermisosContants from "@/core/constants/PermisosContants";

export default {
    path: RutasConstants.CATEGORIA.path,
    name: RutasConstants.CATEGORIA.nombre,
    component: () => import('@/views/crud/CrudView.vue'),
    meta: {
      servicio: () => import('@/core/services/CategoriaService'),
      tabla: () => import('@/components/categoria/CategoriaTabla.vue'),
      formulario: () => import('@/components/categoria/CategoriaFormulario.vue'),
      permisoModulo: PermisosContants.CATEGORIA_MODULO,
      permisoRegistrar: PermisosContants.CATEGORIA_REGISTRAR,
      permisoEditar: PermisosContants.CATEGORIA_EDITAR,
      permisoEliminar: PermisosContants.CATEGORIA_ELIMINAR,
      encabezado:{
        icono: "mdi-cube",
        titulo: "Categorías",
      }
    }
};