<template>
  <div>

    <v-data-table :headers="headers" :items="lista" :items-per-page="elementosPorPagina"
      no-data-text="Sin resultados disponibles" class="mx-5" dense fixed-header height="auto" hide-default-footer>
      <template v-slot:[`item.indice`]="{ index }">
        <columna-indice :indice="index" :paginaActual="paginaActual" :elementosPorPagina="elementosPorPagina" />
      </template>
      <template v-slot:[`item.acciones`]="{ item, index }">
        <menu-crud :indice="index" :obtener="obtener" :parametrosObtener="item" :eliminar="eliminar"
          :parametrosEliminar="item" />
      </template>
    </v-data-table>

  </div>
</template>
  
<script>
import MenuCrud from "@/components/base/menu/MenuCrud.vue";
import ColumnaIndice from "@/components/common/indice/ColumnaIndice.vue";

export default {
  name: "ProveedorAlojamientoTabla",
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
        { text: "Nombre", value: "nombre", align: "center", sortable: false, width: "80%", },
        { text: "Acciones", value: "acciones", sortable: false, align: "center", width: "15%" },
      ]
    }
  },
};
</script>
  