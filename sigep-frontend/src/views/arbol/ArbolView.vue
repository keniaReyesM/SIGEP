<template>
    <div>
        <titulo-pagina :titulo=titulo :subtitulo=subtitulo />
        <div class="content">
            <arbol-tarea/>
        </div>
    </div>
</template>
  
<script>

import TituloPagina from "@/components/base/header/TituloPagina.vue";
import ArbolTarea from '@/components/arbol/ArbolTarea.vue';

export default {
    name: "CalendarioView",
    components: {
        TituloPagina,
        ArbolTarea
    },
    data() {
        return {
            titulo: "Sin titulo",
            subtitulo: "",
        }
    },
    mounted() {
        this.construirVista();
    },
    methods: {
         construirVista() {
            this.titulo = this.$route?.meta?.encabezado?.titulo || "Sin titulo";
            this.subtitulo = this.$route?.meta?.encabezado?.subtitulo || "";
        }
    }

}
</script>
