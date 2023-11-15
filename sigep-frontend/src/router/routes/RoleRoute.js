import RutasConstants from "@/core/constants/RutasConstants";

export default {
    path: RutasConstants.ROL.path,
    name: RutasConstants.ROL.nombre,
    component: () => import('@/views/crud/CrudView.vue'),
    meta: {
      servicio: () => import('@/core/services/RolService'),
      tabla: () => import('@/components/rol/RolTable.vue'),
      formulario: () => import('@/components/rol/RolFormulario.vue'),
      encabezado:{
        titulo: "Roles",
      }
    }
};