import RutasConstants from "@/core/constants/RutasConstants";

export default {
  path: RutasConstants.ARBOL.path,
  name: RutasConstants.ARBOL.nombre,
  component: () => import('@/views/arbol/ArbolView.vue'),
  meta: {
    encabezado: {
      titulo: "Árbol",
      subtitulo: "Listado de tareas como árbol"
    }
  }
};