import RutasConstants from "@/core/constants/RutasConstants";

export default {
  path: RutasConstants.CALENDARIO.path,
  name: RutasConstants.CALENDARIO.nombre,
  component: () => import('@/views/calendario/CalendarioView.vue'),
  meta: {
    encabezado: {
      titulo: "Calendario",
      subtitulo: "Listado de tareas"
    }
  }
};