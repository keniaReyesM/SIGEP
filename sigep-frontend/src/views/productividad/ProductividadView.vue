<template >
    <div >
        <titulo-pagina :titulo=titulo :subtitulo=subtitulo />
        <div class="content">
            <v-tabs fixed-tabs background-color="#1C4464" flat dark 
            icons-and-text centered v-model="tab"
            class="border-table-one" dense>
                <v-tab>
                    <v-icon class="mr-2">pe-7s-graph2</v-icon> General
                </v-tab>
                <v-tab>
                    <v-icon class="mr-2">pe-7s-rocket</v-icon> Proyectos
                </v-tab>
                <v-tab>
                    <v-icon class="mr-2">pe-7s-users</v-icon> Empleados
                </v-tab>
                <v-tab-item class="mt-3 mb-10">
                    <general-productividad v-if="tab == 0"/>
                </v-tab-item>
                <v-tab-item class="mt-3 mb-10">
                    <proyecto-productividad v-if="tab == 1"/>
                </v-tab-item>
                <v-tab-item class="mt-3 mb-10">
                    <empleado-productividad v-if="tab == 2"/>
                </v-tab-item>
            </v-tabs>
        </div>
    </div>
</template>
  
<script>

import TituloPagina from "@/components/base/header/TituloPagina.vue";
import EmpleadoProductividad from '@/components/productividad/EmpleadoProductividad.vue';
import ProyectoProductividad from '@/components/productividad/ProyectoProductividad.vue';
import GeneralProductividad from '@/components/productividad/GeneralProductividad.vue';


export default {
    name: "ProductividadView",
    components: {
        TituloPagina,
        EmpleadoProductividad,
        ProyectoProductividad,
        GeneralProductividad
    },
    data() {
        return {
            titulo: "Productividad",
            subtitulo: "",
            tab: null
        }
    },
    mounted() {
        this.construirVista();
    },
    methods: {
        construirVista() {
        }
    }

}
</script>
