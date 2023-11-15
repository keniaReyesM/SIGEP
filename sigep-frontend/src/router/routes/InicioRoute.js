import RutasConstants from "@/core/constants/RutasConstants";

export default {
    path: RutasConstants.INICIO.path,
    name: RutasConstants.INICIO.nombre,
    component: () => import('@/views/inicio/InicioView.vue'),
    meta: {
        encabezado:{
          titulo: "Bienvenido",
        }
      }
};