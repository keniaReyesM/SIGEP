<template>
    <div>
        <v-row v-show="mostrarAccion(1)">
            <v-col >
                <v-data-table :headers="headers" :items="lista" :items-per-page="elementosPorPagina"
                    no-data-text="Sin resultados disponibles" class="mx-5" dense fixed-header height="auto"
                    hide-default-footer>
                    <template v-slot:[`item.indice`]="{ index }">
                        <columna-indice :indice="index" :paginaActual="paginaActual"
                            :elementosPorPagina="elementosPorPagina" />
                    </template>
                    <template v-slot:[`item.acciones`]="{ item, index }">
                        <menu-crud :indice="index" :obtener="obtener" :parametrosObtener="item" :eliminar="eliminar"
                            :parametrosEliminar="item" />
                    </template>
                </v-data-table>
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <validation-observer ref="observer" v-slot="{ invalid }">

                    <v-form @submit.prevent="undefined">
                        <!-- <v-card>
        <v-card-title>
            <small> Tiempo invertido </small>
        </v-card-title>
        <v-card-text> -->
                        <v-row align="center" justify="center">
                            <v-col md="12" cols="12">
                                <validation-provider v-slot="{ errors, valid }" name="Tiempo invertido"
                                    rules="required|max:100">
                                    <v-text-field type="number" v-model.trim="tiempoInvertido"
                                        label="Tiempo invertido (minutos)*" outlined clearable required autocomplete="off"
                                        :error-messages="errors" dense clear-icon="mdi-eraser">
                                        <v-icon slot="append" :color="valid ? 'success' : ''">
                                            {{ valid ? " mdi-check" : "" }}
                                        </v-icon>
                                    </v-text-field>
                                </validation-provider>
                            </v-col>
                        </v-row>
                        <v-row align="center" justify="center">

                            <v-btn type="button" class="mb-4 app-crud-theme" :disabled="invalid || enviando"
                                @click="registrarTiempoInvertido()" color="#0d628c" depressed rounded>
                                Guardar <v-icon right> mdi-timer</v-icon>
                            </v-btn>
                        </v-row>
                        <!-- </v-card-text>
    </v-card> -->
                    </v-form>

                </validation-observer>
            </v-col>
        </v-row>




    </div>
</template>

<script>
import TareaService from '@/core/services/TareaService';
import Utileria from '@/core/util/Utileria';

export default {
    name: "TipoPlataformaDigitalFormulario",
    props: {
        idTarea: String
    },
    data: () => ({
        tiempoInvertido: null,
        enviando: false,
        lista: [],
        headers: [
            { text: "#", value: "indice", align: "center", sortable: false, width: "5%", },
            { text: "Fecha de avance", value: "nombre", align: "center", sortable: false, width: "30%", },
            { text: "Responsable", value: "nombre", align: "center", sortable: false, width: "30%", },
            { text: "Fecha de registro", value: "nombre", align: "center", sortable: false, width: "30%", },
            { text: "Acciones", value: "acciones", sortable: false, align: "center", width: "15%" },
        ],
        accion: 1
    }),
    methods: {
        mostrarAccion(accion) {
            return this.accion == accion;
        },
        obtenerTiempoInvertido() {
            // if (!this.enviando) {

            //     this.enviando = true;

            //     let parametros = { idTarea: this.idTarea };

            //     TareaService.obtenerTiempoInvertido(parametros).then((res) => {
            //         this.tiempoInvertido = res.data.tiempoInvertido;
            //     }).catch((e) => {
            //         this.$utileria.errorhttp(e);
            //     }).finally(() => {
            //         this.enviando = false;
            //     });
            // }
        },
        registrarTiempoInvertido() {
            // if (!this.enviando) {

            //     this.enviando = true;

            //     let parametros = { idTarea: this.idTarea, tiempoInvertido: this.tiempoInvertido };

            //     TareaService.registrarTiempoInvertido(parametros).then((response) => {
            //         this.enviando = false;
            //         let { data, status } = response;
            //         if (status == 200) {
            //             this.$utileria.notificacion("Se ha registrado correctamente.", "SUCCESS");
            //         } else {
            //             data = data || "Ocurrió un error durante el registro.";
            //             this.$utileria.notificacion(data, "ERROR");
            //         }
            //     }).catch((e) => {
            //         this.$utileria.errorhttp(e);
            //     }).finally(() => {
            //         this.enviando = false;
            //     });
            // }
        }
    },
    mounted() {
        this.obtenerTiempoInvertido();
    }
};
</script>
