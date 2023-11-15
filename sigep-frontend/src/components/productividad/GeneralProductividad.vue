<template>
    <div>
        <loader :loader="enviando" />
        <div class="content">

            <div class="mb-3 ">

                <div class="card-header">
                    <v-row align="center" justify="center" class="ml-2 mr-2">

                        <v-col cols="12" sm="12" md="12">
                            <v-text-field v-model.trim="busqueda" append-icon="mdi-magnify" label="Búsqueda..." outlined
                                dense hide-details class="mx-5 mt-3"></v-text-field>
                        </v-col>
                    </v-row>
                </div>

                <div class="card-body">
                    <v-data-table :headers="headers" :items="lista" :items-per-page="elementosPorPagina"
                        no-data-text="Sin resultados disponibles" class="mx-5" dense fixed-header height="auto"
                        hide-default-footer>
                        <template v-slot:[`item.indice`]="{ index }">
                            <columna-indice :indice="index" :paginaActual="paginaActual"
                                :elementosPorPagina="elementosPorPagina" />
                        </template>
                        <template v-slot:[`item.acciones`]="{ item }">
                            <v-icon class="mr-1" @click="verInformacionGeneral(item)">
                                mdi-information-outline
                            </v-icon>
                        </template>
                    </v-data-table>

                </div>
                <div class="card-footer">


                    <b-pagination align="center" :total-rows="total" v-model="paginaActual" :per-page="elementosPorPagina">
                    </b-pagination>



                    <div class="text-right mb-4">
                        <strong> Total: {{ total }}</strong>
                    </div>

                </div>
            </div>
        </div>
        <v-dialog v-model="dialogoInformacion" v-if="dialogoInformacion == true" fullscreen class="mt-10"
            transition="dialog-bottom-transition">

            <v-card>

                <v-toolbar dark color="#1C4464">
                    <v-btn icon dark @click="dialogoInformacion = false">
                        <v-icon>mdi-close</v-icon>
                    </v-btn>
                    <v-toolbar-title>Visión general</v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-toolbar-items>
                        <v-btn dark text @click="dialogoInformacion = false">
                            Salir
                        </v-btn>
                    </v-toolbar-items>
                </v-toolbar>
                <v-list three-line subheader class="ml-10 mr-10 mt-10">
                    <v-list-item>
                        <v-list-item-content>
                            <vision-general :idProyecto="idProyecto" />
                        </v-list-item-content>
                    </v-list-item>
                </v-list>


            </v-card>


        </v-dialog>
    </div>
</template>
  
<script>

import Loader from "@/components/base/loader/Loader.vue";
import ColumnaIndice from "@/components/common/indice/ColumnaIndice.vue";
import VisionGeneral from "@/components/proyecto/ProyectoVisionGeneral.vue";

import EmpleadoService from '@/core/services/EmpleadoService';
import ProyectoService from '@/core/services/ProyectoService';

export default {
    name: "EmpleadoProductividad",
    components: {
        Loader,
        ColumnaIndice,
        VisionGeneral
    },
    data() {
        return {
            titulo: "Sin titulo",
            busqueda: "",
            accion: 1,
            enviando: false,
            indice: -1,
            accion: 1,
            lista: [],
            model: {},
            busqueda: "",
            total: 0,
            paginaActual: 1,
            elementosPorPagina: 10,
            retrasoBusqueda: null,
            dialogoInformacion: false,
            idProyecto: null,
            empleados: [],
            idEmpleado: null,
            totalHoras: "00:00",
            headers: [
                { text: "#", value: "indice", align: "center", sortable: false, width: "5%", },
                { text: "Clave", value: "clave", align: "center", sortable: false },
                { text: "Nombre", value: "nombre", align: "center", sortable: false, width: "40%" },
                { text: "Horas trabajadas", value: "horasTrabajadas", align: "center", sortable: false, width: "20%" },
                { text: "Tareas", value: "cantidadTareas", align: "center", sortable: false, },
                { text: "Acciones", value: "acciones", align: "center", sortable: false, },

            ]
        }
    },
    watch: {
        busqueda(nuevoValor, anteriorValor) {
            this.eliminarRetrasoBusqueda();
            this.retrasoBusqueda = setTimeout(() => {
                this.paginaActual = 1;
                this.listarPaginado();
            }, 400);
        },
        paginaActual() {
            this.listarPaginado();
        }
    },
    async mounted() {
        this.construirVista();
    },
    methods: {
        async construirVista() {
            this.listarPaginado();
        },
        cambiarPagina(page) {
            if (this.paginaActual != page) {
                this.paginaActual = page;
                this.listarPaginado();
            }
        },
        eliminarRetrasoBusqueda() {
            if (this.retrasoBusqueda != null) {
                clearTimeout(this.retrasoBusqueda);
                this.retrasoBusqueda = null;
            }
        },
        listarPaginado() {

            if (!this.enviando) {

                this.enviando = true;

                let parametros = {
                    busqueda: this.busqueda,
                    maximoResultados: this.elementosPorPagina,
                    paginaActual: this.paginaActual,
                    idEmpleado: this.idEmpleado
                };

                ProyectoService.listarPaginadoProductividadGeneral(parametros).then((res) => {

                    let { elementos, total, totalHorasTrabajadas } = res.data;
                    this.lista = elementos;
                    this.total = total;
                    this.totalHoras = totalHorasTrabajadas;

                }).catch((e) => {
                    this.$utileria.errorhttp(e);
                }).finally(() => {
                    this.enviando = false;
                });


            }
        },
        listarEmpleados() {
            EmpleadoService.listarTodos().then((response) => {
                this.empleados = response.data;
            });
        },
        descargarReporte() {
            if (!this.enviando) {

                this.enviando = true;
                let parametros = { idEmpleado: this.idEmpleado };
                let elementoSeleccionado = this.empleados.find((empleado) => { return empleado.idEmpleado === this.idEmpleado });

                ProyectoService.generarReporteProductividadEmpleado(parametros).then((respuesta) => {
                    this.$utileria.descargarExcel(respuesta.data, `${elementoSeleccionado.empleado}.xlsx`);
                }).catch((e) => {
                    this.$utileria.errorhttp(e);
                }).finally(() => {
                    this.enviando = false;
                });
            }
        },
        verInformacionGeneral(item){
            this.idProyecto = item.idProyecto;
            this.dialogoInformacion = true;
        }

    }

}
</script>
