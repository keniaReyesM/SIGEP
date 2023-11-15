import RutasConstants from "@/core/constants/RutasConstants";

export default {
    path: RutasConstants.GRAFICA_PASTEL.path,
    name: RutasConstants.GRAFICA_PASTEL.nombre,
    component: () => import('@/views/graficapastel/GraficaPastel.vue'),
    meta: {
      // servicio: () => import('@/core/services/AreaService'),
      // tabla: () => import('@/components/area/AreaTabla.vue'),
      // formulario: () => import('@/components/area/AreaFormulario.vue'),
      encabezado:{
        titulo: "Gráfica Pastel",
      },
      privado: true
    }
};