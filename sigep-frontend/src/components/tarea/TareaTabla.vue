<template>
  <div>

    <v-data-table :headers="headers" :items="lista" :items-per-page="elementosPorPagina"
      no-data-text="Sin resultados disponibles" class="mx-5" dense fixed-header height="auto" hide-default-footer>
      <template v-slot:item="i">
        <tr>
          <td class="text-center">
            <columna-indice :indice="i.index" :paginaActual="paginaActual" :elementosPorPagina="elementosPorPagina" />
          </td>
          <td class="text-center">
            {{ $utileria.formatoFechaHora(i.item.fechaHoraInicio) || '- - -' }}
          </td>
          <td class="text-center">
            {{ $utileria.formatoFechaHora(i.item.fechaHoraFin) || '- - -' }}
          </td>
          <td class="text-center" :style="`background-color:${i.item.color}`">
            <span>
              {{ concatenarNombre(i.item) }}
              <br />
              ({{ i.item.estado }})
            </span>
          </td>
          <td class="text-center">
            <menu-crud :indice="i.index" :obtener="obtener" :parametrosObtener="{ idTarea: i.item.idTarea }"
              :parametrosActualizarEstado="{ idTarea: i.item.idTarea }" />

         <v-tooltip top :index="i.index" v-if="$utileria.authorized($permisos.AVANCE_MODULO)">
              <template v-slot:activator="{ on, attrs }">
                <v-btn icon @click="visualizarTarea(i.item)">
                  <v-icon color="green" v-bind="attrs" v-on="on" left dense > mdi-timer </v-icon>
                </v-btn>
              </template>
              <span>Avances</span>
            </v-tooltip> 
          </td>
        </tr>
      </template>

    </v-data-table>

  

  </div>
</template>

<script>
import MenuCrud from "@/components/base/menu/MenuCrud.vue";
import ColumnaIndice from "@/components/common/indice/ColumnaIndice.vue";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
  name: "TareaTabla",
  props: {
    lista: Array,
    obtener: Function,
    eliminar: Function,
    actualizarEstado: Function,
    elementosPorPagina: Number,
    paginaActual: Number
  },
  components: {
    MenuCrud,
    ColumnaIndice
  },
  data() {
    return {
      headers: [
        { text: "#", value: "indice", align: "center", sortable: false, width: "5%" },
        { text: "Inicio", value: "fechaHoraInicio", align: "center", sortable: false, width: "15%" },
        { text: "Fin", value: "fechaHoraFin", align: "center", sortable: false, width: "15%" },
        { text: "Tareas realizadas en el día", value: "nombre", align: "center", sortable: false, width: "40%" },
        { text: "", value: "acciones", sortable: false, align: "center", width: "15%" },
      ],
      idTarea: null
    }
  },
  methods: {
    concatenarNombre(tarea) {
      return `${tarea.nombreProyecto}/${tarea.claveProyecto} - ${tarea.nombre}/${tarea.clave}`;
    },
    visualizarTarea(tarea) {
      this.idTarea = tarea.idTarea;
      this.$router.push({ name: RutasConstants.AVANCE.nombre, params: { idTarea: this.idTarea } });
    },
  }
};
</script>
