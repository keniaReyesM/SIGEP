<template>
    <div>
        <titulo-pagina :titulo=titulo :subtitulo=subtitulo />
        <div class="content">

            <div class="main-card mb-3 card mt-6">


                <v-card class="mx-auto" color="#26c6da" dark max-width="400">
                    <v-card-title>
                        <v-icon large left>
                            mdi-twitter
                        </v-icon>
                        <span class="text-h6 font-weight-light">Twitter</span>
                    </v-card-title>

                    <v-card-text class="text-h5 font-weight-bold">
                        "Turns out semicolon-less style is easier and safer in TS because most gotcha edge cases are
                        type invalid as well."
                    </v-card-text>

                    <v-card-actions>
                        <v-list-item class="grow">
                            <v-list-item-avatar color="grey darken-3">
                                <v-img class="elevation-6" alt=""
                                    src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light">
                                </v-img>
                            </v-list-item-avatar>

                            <v-list-item-content>
                                <v-list-item-title>Evan You</v-list-item-title>
                            </v-list-item-content>

                            <v-row align="center" justify="end">
                                <v-icon class="mr-1">
                                    mdi-heart
                                </v-icon>
                                <span class="subheading mr-2">256</span>
                                <span class="mr-1">·</span>
                                <v-icon class="mr-1">
                                    mdi-share-variant
                                </v-icon>
                                <span class="subheading">45</span>
                            </v-row>
                        </v-list-item>
                    </v-card-actions>
                </v-card>
                <div class="card-header" style="text-align: right;" v-if="mostrarAccion(1)">
                    <v-row align="center" justify="center">

                        <v-col cols="12" sm="12" md="10">
                            <v-text-field v-model.trim="busqueda" append-icon="mdi-magnify" label="Búsqueda..." outlined
                                dense hide-details class="mx-5 mt-3"></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="12" md="2" class="text-center justify-center">
                            <v-btn @click="cambiarAccion(2)" depressed rounded small color="primary" class="mt-3">
                                Agregar <v-icon right> mdi-plus-circle-outline </v-icon>
                            </v-btn>
                        </v-col>
                    </v-row>
                </div>

                <div class="card-body">
                    <div name="tabla" v-if="mostrarAccion(1) && tabla">
                        <component key="tabla" :obtener="obtener" :eliminar="eliminar" :lista="lista" :is="tabla"
                            :actualizarEstado="actualizarEstado" :busqueda="busqueda" :paginaActual="paginaActual"
                            :total="total" :elementosPorPagina="elementosPorPagina" />
                    </div>
                    <div name="formulario" v-if="(mostrarAccion(2) || mostrarAccion(3)) && formulario">
                        <validation-observer ref="observer" v-slot="{ invalid }">

                            <v-form @submit.prevent="undefined">

                                <component :is="formulario" key="formulario" :model="model" :accion="accion" />

                                <div class="text-center justify-center">
                                    <v-col class=" justify-space-around">


                                        <v-menu offset-x bottom left :close-on-content-click="true" rounded="lg">
                                            <template v-slot:activator="{ on: menu, attrs }">
                                                <v-btn type="button" class="mr-12 mb-4" :disabled="enviando" small
                                                    depressed rounded color="blue-grey lighten-3" v-bind="attrs"
                                                    v-on="{ ...menu }">
                                                    <v-icon left> mdi-skip-previous </v-icon> Cancelar
                                                </v-btn>
                                            </template>
                                            <v-card>
                                                <v-list color="grey lighten-3">
                                                    <v-list-item>
                                                        <v-list-item-content>
                                                            <v-list-item-title>
                                                                <span> ¿Desea <strong>cancelar</strong> el registro
                                                                    actual? </span>
                                                            </v-list-item-title>
                                                        </v-list-item-content>
                                                    </v-list-item>
                                                </v-list>
                                                <v-divider></v-divider>
                                                <v-card-actions class="justify-center">
                                                    <v-btn color="primary" type="button" small block text
                                                        @click="cambiarAccion(1)">
                                                        Confirmar
                                                    </v-btn>
                                                </v-card-actions>
                                            </v-card>
                                        </v-menu>
                                        <v-menu offset-x bottom rigth :close-on-content-click="true" rounded="lg">
                                            <template v-slot:activator="{ on: menu, attrs }">
                                                <v-btn type="button" class="mb-4" :disabled="invalid || enviando"
                                                    v-bind="attrs" v-on="{ ...menu }" color="primary" small depressed
                                                    rounded>
                                                    Guardar <v-icon right>mdi-skip-next</v-icon>
                                                </v-btn>
                                            </template>
                                            <v-card>
                                                <v-list color="grey lighten-3">
                                                    <v-list-item>
                                                        <v-list-item-content>
                                                            <v-list-item-title>
                                                                <span> ¿Desea <strong>guardar</strong> el registro
                                                                    actual? </span>
                                                            </v-list-item-title>
                                                        </v-list-item-content>
                                                    </v-list-item>
                                                </v-list>
                                                <v-divider></v-divider>
                                                <v-card-actions class="justify-center">
                                                    <v-btn type="button" @click="hacerAccion()"
                                                        :disabled="invalid || enviando" color="primary" small block
                                                        text>
                                                        Confirmar
                                                    </v-btn>
                                                </v-card-actions>
                                            </v-card>
                                        </v-menu>



                                    </v-col>
                                </div>

                            </v-form>

                        </validation-observer>
                    </div>
                </div>
                <div class="card-footer" v-if="mostrarAccion(1)">
                    <b-pagination align="center" :total-rows="total" v-model="paginaActual"
                        :per-page="elementosPorPagina">
                    </b-pagination>
                    <div class="text-right mb-4">
                        <!-- <v-btn disabled medium text outlined> -->
                        <strong> Total: {{ total }}</strong>
                        <!-- </v-btn> -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>

import TituloPagina from "@/components/base/header/TituloPagina.vue";


export default {
    name: "ProyectoView",
    components: {
        TituloPagina
    },
    data() {
        return {
            titulo: "Sin titulo",
            subtitulo: "",
            busqueda: "",
            accion: 1,
            enviando: false,
            dialogo: false,
            indice: -1,
            accion: 1,
            lista: [],
            model: {},
            tabla: null,
            servicio: null,
            formulario: null,
            busqueda: "",
            total: 0,
            paginaActual: 1,
            elementosPorPagina: 10,
            retrasoBusqueda: null,
            conteoPagina: 1,
            ocultarBotoAgregar: false
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
            this.titulo = this.$route?.meta?.encabezado?.titulo || "Sin titulo";
            this.subtitulo = this.$route?.meta?.encabezado?.subtitulo || "";
            this.servicio = (await this.$route.meta.servicio())?.default || null;
            this.tabla = (await this.$route.meta.tabla())?.default || null;
            this.formulario = (await this.$route.meta.formulario())?.default || null;
            this.ocultarBotoAgregar = this.$route.meta.ocultarBotoAgregar || false;
            this.listarTodos();
        },
        mostrarAccion(accion) {
            return this.accion == accion;
        },
        cambiarAccion(accionNueva) {
            if (accionNueva == 1) {
                this.model = {};
                if (this.$route?.meta?.modeloInicial != undefined) {
                    this.model = Object.assign(this.$route?.meta?.modeloInicial, {});
                }
                this.$refs.observer.reset();
                this.accion = accionNueva;
            } else {
                if (accionNueva == 2 && this.$route?.meta?.modeloInicial != undefined) {
                    this.model = this.$route?.meta?.modeloInicial();
                }
                this.accion = accionNueva;
            }
        },
        hacerAccion() {
            switch (this.accion) {
                case 2:
                    this.registrar();
                    break;
                case 3:
                    this.actualizar();
                    break;
                default:
                    break;
            }
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
        listarTodos() {


            this.servicio.listarTodos().then((res) => {
                console.log(res);
                // let { elementos, total } = res.data;
                // this.lista = elementos;
                // this.total = total;
                // this.conteoPagina = Math.ceil(total / this.elementosPorPagina);

            }).catch((e) => {
                this.$utileria.errorhttp(e);
            });

        },
        listarPaginado() {

            let parametros = {
                busqueda: this.busqueda,
                maximoResultados: this.elementosPorPagina,
                paginaActual: this.paginaActual
            };

            this.servicio.listarPaginado(parametros).then((res) => {

                let { elementos, total } = res.data;
                this.lista = elementos;
                this.total = total;
                this.conteoPagina = Math.ceil(total / this.elementosPorPagina);

            }).catch((e) => {
                this.$utileria.errorhttp(e);
            });


        },
        registrar() {

            if (!this.enviando) {
                this.enviando = true;
                this.servicio.registrar(this.model).then((response) => {
                    this.enviando = false;
                    let { data, status } = response;
                    if (status == 200) {
                        this.listarPaginado();
                        this.$utileria.notificacion("Se ha registrado correctamente.", "SUCCESS");
                        this.cambiarAccion(1);
                    } else {
                        data = data || "Ocurrió un error durante el registro.";
                        this.$utileria.notificacion(data, "ERROR");
                    }
                }).catch((e) => {
                    this.enviando = false;
                    this.$utileria.errorhttp(e);
                });
            }

        },
        actualizar() {

            if (!this.enviando) {

                this.enviando = true;

                this.servicio.actualizar(this.model).then((response) => {
                    this.enviando = false;
                    let { data, status } = response;
                    if (status == 200) {

                        this.listarPaginado();
                        this.$utileria.notificacion("Se ha modificado correctamente.", "SUCCESS");
                        this.cambiarAccion(1);
                        this.index = -1;

                    } else {
                        data = data || "Ocurrió un error durante la modificación.";
                        this.$utileria.notificacion(data, "ERROR");
                    }

                }).catch((e) => {
                    this.enviando = false;
                    this.$utileria.errorhttp(e);
                });
            }

        },
        obtener(parametros, i) {

            if (!this.enviando) {

                this.enviando = true;

                this.servicio.obtener(parametros).then((response) => {
                    this.enviando = false;
                    let { data, status } = response;
                    if (status == 200) {
                        this.model = data;
                        this.index = Number(i);
                        this.cambiarAccion(3);
                    } else {
                        data = data || "Ocurrió un error al obtener la información.";
                        this.$utileria.notificacion(data, "ERROR");
                    }
                }).catch((e) => {
                    this.enviando = false;
                    this.$utileria.errorhttp(e);
                });

            }

        },
        eliminar(parametros, i) {
            if (!this.enviando) {

                this.enviando = true;

                this.servicio.eliminar(parametros).then((response) => {
                    this.enviando = false;
                    let { data, status } = response;
                    if (status == 200) {
                        this.listarPaginado();
                        this.$utileria.notificacion("Se ha eliminado correctamente.", "SUCCESS");
                    } else {
                        data = data || "Ocurrió un error al eliminar la información.";
                        this.$utileria.notificacion(data, "ERROR");
                    }
                }).catch((e) => {
                    this.enviando = false;
                    this.$utileria.errorhttp(e);
                });

            }
        },
        actualizarEstado(parametros, i) {
            if (!this.enviando) {

                this.enviando = true;

                this.servicio.actualizarEstado(parametros).then((response) => {
                    this.enviando = false;
                    let { data, status } = response;
                    if (status == 200) {
                        this.listarPaginado();
                        this.$utileria.notificacion("Se ha actualizado correctamente.", "SUCCESS");
                    } else {
                        data = data || "Ocurrió un error al eliminar la información.";
                        this.$utileria.notificacion(data, "ERROR");
                    }
                }).catch((e) => {
                    this.enviando = false;
                    this.$utileria.errorhttp(e);
                });

            }
        },
    }

}
</script>
