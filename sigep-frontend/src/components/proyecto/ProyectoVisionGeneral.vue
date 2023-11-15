<template>
    <div>
        <loader :loader="enviando" />

        <v-row align="center" justify="center">
            <v-col md="4" cols="12">
                <strong class="mr-4"><v-icon left>mdi-key</v-icon>Clave</strong> {{ model.clave }}
            </v-col>
            <v-col md="4" cols="12">
                <strong class="mr-4"><v-icon left>mdi-information-outline</v-icon>Nombre</strong> {{ model.nombre }}
            </v-col>
            <v-col md="4" cols="12">
                <strong class="mr-4"><v-icon left>mdi-border-color</v-icon>Categoría</strong> {{
                    model.nombreCategoria
                }}
            </v-col>
        </v-row>

        <v-row align="center" justify="center">
            <v-col md="4" cols="12">
                <strong class="mr-4"><v-icon left>mdi-calendar-check</v-icon>Inicio</strong> {{ model.fechaInicio }}
            </v-col>
            <v-col md="4" cols="12">
                <strong class="mr-4"><v-icon left>mdi-calendar-remove</v-icon>Fin</strong> {{ model.fechaFin }}
            </v-col>
            <v-col md="4" cols="12">
                <strong class="mr-4"><v-icon left>mdi-details</v-icon>Descripción</strong> {{ model.descripcion }}
            </v-col>
        </v-row>
        <hr class="mt-4 mb-4" />
        <v-row align="center" justify="center">
            <v-col md="4" cols="12">
                <strong class="mr-4"><v-icon left>mdi-human-male-female</v-icon>Empleados</strong>
                {{ model.empleados?.join(", ") || "Sin empleados asignados" }}
            </v-col>
            <v-col md="4" cols="12">
                <strong class="mr-4"><v-icon left>mdi-cube-outline</v-icon>Clientes</strong>
                {{ model.clientes?.join(", ") || "Sin clientes asignados" }}
            </v-col>
            <v-col md="4" cols="12">
                <strong class="mr-4"><v-icon left>mdi-content-paste</v-icon>Tareas</strong> {{ model.cantidadTareas }}
            </v-col>
        </v-row>
        <hr class="mt-4 mb-4" />
        <v-row align="center" justify="center">
            <v-col>
                <v-data-table :headers="headers" :items="model.tareas" :items-per-page="10"
                    no-data-text="Sin resultados disponibles" class="mx-5" dense fixed-header height="auto">
                    <template v-slot:[`item.indice`]="{  index }">
                        {{ index+1 }}
                    </template>

                </v-data-table>
            </v-col>
            <v-col>
                <grafica-pastel :idProyecto="idProyecto" />
            </v-col>
        </v-row>
    </div>
</template>
<script>
import GraficaPastel from "@/components/proyecto/ProyectoGraficaPastel.vue";
import ProyectoService from "@/core/services/ProyectoService";
import Loader from "@/components/base/loader/Loader.vue";

export default {
    name: "ProyectoVisionGeneral",
    props: {
        idProyecto: Number
    },
    components: {
        GraficaPastel,
        Loader
    },
    data() {
        return {
            enviando: false,
            model: {
                color: ""
            },
            headers: [
                { text: "No", value: "indice", align: "center", sortable: false },
                { text: "Clave", value: "clave", align: "center", sortable: false },
                { text: "Nombre", value: "nombre", align: "center", sortable: false },
            ],
        }
    },
    mounted() {
        this.obtenerInformacionGeneral();
    },
    methods: {
        obtenerInformacionGeneral() {
            this.enviando = true;
            ProyectoService.obtenerInformacionGeneral(this.idProyecto).then((response) => {
                this.model = response.data;
            }).catch(this.$utileria.errorhttp).finally(() => this.enviando = false);
        }
    }
};

</script>

