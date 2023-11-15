<template>
  <div>

    <v-data-table :headers="headers" :items="lista" :items-per-page="elementosPorPagina"
      no-data-text="Sin resultados disponibles" class="mx-5" dense fixed-header height="auto" hide-default-footer>
      <template v-slot:[`item.indice`]="{ index }">
        <columna-indice :indice="index" :paginaActual="paginaActual" :elementosPorPagina="elementosPorPagina" />
      </template>
      <template v-slot:[`item.estado`]="{ item }">
        <strong :class="{ 'text-success': item.estado == 'Activo', 'text-danger': item.estado != 'Activo' }">
            {{item.estado}}
        </strong>
      </template>
      <template v-slot:[`item.acciones`]="{ item, index }">
        <menu-crud :indice="index" :obtener="obtener" :parametrosObtener="{idCliente: item.idCliente}" 
          :parametrosActualizarEstado="{idCliente: item.idCliente}"
          :eliminar="eliminar"
          :actualizarEstado="actualizarEstado"
          :parametrosEliminar="{idCliente: item.idCliente}" />
      </template>
    </v-data-table>

  </div>
</template>
  
<script>
import MenuCrud from "@/components/base/menu/MenuCrud.vue";
import ColumnaIndice from "@/components/common/indice/ColumnaIndice.vue";

export default {
  name: "ClienteTabla",
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
        { text: "#", value: "indice", align: "center", sortable: false, width: "5%"},
        { text: "Razón social", value: "razonSocial", align: "center", sortable: false, width: "20%"},
        { text: "Estado", value: "estado", align: "center", sortable: false, width: "20%"},
        { text: "Acciones", value: "acciones", sortable: false, align: "center", width: "15%" },
      ]
    }
  },
};
</script>
  