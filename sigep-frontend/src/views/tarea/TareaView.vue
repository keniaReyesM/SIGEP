<template >
    <div >
        <titulo-pagina :titulo=titulo :subtitulo=subtitulo />
        <div class="content">
            <v-tabs fixed-tabs background-color="#1C4464" flat dark icons-and-text centered v-model="tab"
            class="border-table-one">
                <v-tab>
                    <v-icon class="mr-2">mdi-calendar</v-icon> Calendario
                </v-tab>
                <v-tab>
                    <v-icon class="mr-2">mdi-table</v-icon> Tablero
                </v-tab>
                <!-- <v-tab>
                    <v-icon class="mr-2">mdi-list-box</v-icon> Árbol
                </v-tab> -->
                <v-tab-item class=" mb-10">
                    <calendario-tarea v-if="tab == 0" />
                </v-tab-item>
                <v-tab-item class=" mb-10">
                    <tablero-tarea v-if="tab == 1"/>
                </v-tab-item>
                <!-- <v-tab-item class="mt-10 mb-10">
                    <arbol-tarea/>
                </v-tab-item> -->
            </v-tabs>
        </div>
    </div>
</template>
  
<script>

import TituloPagina from "@/components/base/header/TituloPagina.vue";
import CalendarioTarea from '@/components/calendario/CalendarioTarea.vue';
import TableroTarea from '@/views/crud/CrudView.vue';
import ArbolTarea from '@/components/arbol/ArbolTarea.vue';

export default {
    name: "TareaView",
    components: {
        TituloPagina,
        CalendarioTarea,
        TableroTarea,
        ArbolTarea
    },
    data() {
        return {
            titulo: "Tareas",
            subtitulo: "",
            tab: null
        }
    },
    mounted() {
        this.construirVista();
    },
    methods: {
        construirVista() {
            this.subtitulo = this.$route?.meta?.encabezado?.subtitulo || "";
        }
    }

}
</script>
