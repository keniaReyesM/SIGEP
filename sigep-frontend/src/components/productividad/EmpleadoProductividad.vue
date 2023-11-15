<template>
    <div>
        <loader :loader="enviando" />
        <div class="contxxxent">

            <div class="mb-3 ">

                <div class="card-header" >
                    <v-row align="center" justify="center" class="ml-2 mr-2">

                        <v-col cols="12" sm="12" md="8"  align="center" justify="center">


                            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
                                :items="empleados" item-text="empleado" item-value="idEmpleado" v-model.trim="idEmpleado"
                                label="Seleccione empleado*" @change="listarPaginado" clear-icon="mdi-eraser"
                                no-data-text="Sin resultados disponibles." />
                        </v-col>
                        <!-- <v-col cols="12" sm="12" md="4"  align="center" justify="center">

                            <v-btn @click="descargarReporte()" :disabled="enviando || lista.length == 0"  outlined
                            
                                rounded color="success" class="mb-4">
                                Generar reporte <v-icon right> mdi-download </v-icon>
                            </v-btn>
                        </v-col> -->
                        
                        <v-col cols="12" sm="12" md="4"  align="center" justify="center">
                            <h5>Total de horas: {{ totalHoras }}</h5>
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
    </div>
</template>
  
<script>

import Loader from "@/components/base/loader/Loader.vue";
import ColumnaIndice from "@/components/common/indice/ColumnaIndice.vue";

import EmpleadoService from '@/core/services/EmpleadoService';
import ProyectoService from '@/core/services/ProyectoService';

export default {
    name: "EmpleadoProductividad",
    components: {
        Loader,
        ColumnaIndice
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

            empleados: [],
            idEmpleado: null,
            totalHoras: "00:00",
            headers: [
                { text: "#", value: "indice", align: "center", sortable: false, width: "5%", },
                { text: "Clave", value: "clave", align: "center", sortable: false },
                { text: "Nombre", value: "nombre", align: "center", sortable: false, width: "40%" },
                { text: "Estado", value: "estado", align: "center", sortable: false, },
                { text: "Horas trabajadas", value: "horasTrabajadas", align: "center", sortable: false, width: "20%", }
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
            this.listarEmpleados();
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

                if (this.$utileria.isNull(this.idEmpleado)) {
                    this.lista = [];
                    this.total = 0;
                    this.totalHoras = "00:00";
                } else {
                    this.enviando = true;

                    let parametros = {
                        busqueda: this.busqueda,
                        maximoResultados: this.elementosPorPagina,
                        paginaActual: this.paginaActual,
                        idEmpleado: this.idEmpleado
                    };

                    ProyectoService.listarPaginadoProductividadEmpleado(parametros).then((res) => {

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
        }

    }

}
</script>
