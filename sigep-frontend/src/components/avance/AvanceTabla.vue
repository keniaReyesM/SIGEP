<template>
  <div>
    <v-data-table :headers="headers" :items="lista" :items-per-page="elementosPorPagina"
      no-data-text="Sin resultados disponibles" class="mx-5" dense fixed-header height="auto" hide-default-footer>
      <template v-slot:[`item.indice`]="{ index }">
        <columna-indice :indice="index" :paginaActual="paginaActual" :elementosPorPagina="elementosPorPagina" />
      </template>
      <template v-slot:[`item.fechaAvance`]="{ item, index }">
          {{  $utileria.formatoFecha(item.fechaAvance) }}
      </template>
      <template v-slot:[`item.horaInicio`]="{ item, index }">
          {{  $utileria.formatoHora(item.horaInicio) }}
          a
          {{  $utileria.formatoHora(item.horaFin) }}
      </template>
      <template v-slot:[`item.acciones`]="{ item, index }">
        <menu-crud :indice="index" :eliminar="eliminar"
          :parametrosEliminar="item" />
      </template>
    </v-data-table>

  </div>
</template>
  
<script>
import MenuCrud from "@/components/base/menu/MenuCrud.vue";
import ColumnaIndice from "@/components/common/indice/ColumnaIndice.vue";

export default {
  name: "AreaTabla",
  props: {
    lista: Array,
    obtener: Function,
    eliminar: Function,
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
        { text: "#", value: "indice", align: "center", sortable: false, width: "5%", },
        { text: "Responsable", value: "empleado", align: "center", sortable: false, width: "20%", },
        { text: "Fecha de avance", value: "fechaAvance", align: "center", sortable: false, width: "20%", },
        { text: "Horario", value: "horaInicio", align: "center", sortable: false, width: "40%", },
        { text: "Acciones", value: "acciones", sortable: false, align: "center", width: "15%" },
      ]
    }
  }
};
</script>
  