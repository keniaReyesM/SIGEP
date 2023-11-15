<template>
    <div>
        <loader :loader="enviando" />

        <div class="main-card mb-3 card mt-6">

            <div class="card-body">
                <div name="calendario">
                    <full-calendar :options="opciones" ref="calendario" />

                </div>

            </div>
            <div class="card-footer">
                <v-row align="center" justify="center">

                    <v-col cols="12" sm="12" md="2" class="text-center justify-center">
                        <v-btn @click="generarTablero()" :disabled="enviando" text outlined rounded color="success"
                            class="mt-3">
                            Generar tablero <v-icon right> mdi-download </v-icon>
                        </v-btn>
                    </v-col>
                </v-row>
                <div class="text-right mb-4">
                    <strong> Total: {{ total }}</strong>
                </div>
            </div>
        </div>



    </div>
</template>

<script>
import Loader from "@/components/base/loader/Loader.vue";
import FullCalendar from "@fullcalendar/vue";
import DayGridPlugin from "@fullcalendar/daygrid";
import TimeGridPlugin from "@fullcalendar/timegrid";
import InteractionPlugin from "@fullcalendar/interaction";
import TareaService from "@/core/services/TareaService";
import TareaReporteService from "@/core/services/TareaReporteService";
import Utileria from "@/core/util/Utileria";
import RutasConstants from "@/core/constants/RutasConstants";

export default {
    name: "CalendarioTarea",
    props: {
    },
    components: {
        FullCalendar,
        Loader
    },
    data() {
        return {
            calendario: null,
            busqueda: null,
            total: 0,
            enviando: false,
            lista: [],
            opciones: {
                height: 500,
                eventTextColor: "black",
                initialView: "dayGridWeek",
                plugins: [DayGridPlugin, InteractionPlugin, TimeGridPlugin],
                firstDay: 1,
                headerToolbar: {
                    left: "prev,next,today",
                    center: "title",
                    // right: "dayGridMonth,timeGridWeek,timeGridDay",
                    right: "dayGridMonth,dayGridWeek,dayGridDay",
                },
                locale: "es",
                themeSystem: "bootstrap5",
                buttonText: {
                    today: "Hoy",
                    month: "Mes",
                    day: "Día",
                    week: "Semana",
                },
                customButtons: {
                    prev: { // this overrides the prev button
                        text: "Anterior",
                        click: this.clickAnterior
                    },
                    next: { // this overrides the next button
                        text: "Siguiente",
                        click: this.clickSiguiente
                    },
                    today: {
                        text: "Hoy",
                        click: this.clickHoy
                    }
                },
                allDaySlot: true,
                events: [],
                dayMaxEventRows: 10,

                // ! Evento para desplegar ALERT()
                eventClick: (info) => {
                    this.dialog = true;
                    this.idTarea = info.event.id;
                    this.$router.push({ name: RutasConstants.AVANCE.nombre, params: { idTarea: this.idTarea } });
                },
                eventDisplay: (info) => {
                    console.log(info);
                },
                // ! Evento para desplegar ALERT()

                // eventDisplay: "block",
                eventMaxStack: 3,
                slotEventOverlap: true,
                dayMaxEvents: true,
                views: {
                    week: {
                        eventMaxStack: 2,
                    },
                },
            },
            dialog: false,

        }
    },
    mounted() {

        this.calendario = this.$refs.calendario;
        this.listarEventos();
    },
    methods: {
        clickAnterior() {
            this.calendario.getApi().prev();
        },
        clickSiguiente() {
            this.calendario.getApi().next();
            this.listarEventos()
        },
        clickHoy() {
            this.calendario.getApi().today();
            this.listarEventos()
        },
        listarEventos() {
            if (!this.enviando) {

                this.enviando = true;
                let parametros = {
                    busqueda: this.busqueda,
                    ... this.obtenerRangoFechas()
                };

                TareaService.listarTareasEvento(parametros).then((res) => {

                    let { elementos, total } = res.data;
                    this.lista = this.construirEventos(elementos);
                    this.total = total;
                    this.opciones.events = this.lista;

                }).catch((e) => {
                    this.$utileria.errorhttp(e);
                }).finally(() => {
                    this.enviando = false;
                });
            }
        },
        obtenerRangoFechas() {
            let inicio = this.calendario?.getApi()?.currentData?.dateProfile?.activeRange?.start;
            inicio = inicio ? inicio : new Date();
            inicio.setUTCHours(0, 0, 0, 0);

            let fin = this.calendario?.getApi()?.currentData?.dateProfile?.activeRange?.end;
            fin = fin ? fin : new Date();
            fin.setUTCHours(23, 59, 59, 999);

            return { fechaInicio: inicio, fechaFin: fin };
        },
        construirEventos(elementos) {
            return elementos.map((tarea) => {
                return {
                    id: tarea.idTarea,
                    title: this.concatenarNombre(tarea),
                    start: new Date(tarea.fechaHoraInicio),
                    end: Utileria.nonEmpty(tarea.fechaHoraFin) ? new Date(tarea.fechaHoraFin) : null,
                    color: tarea.color,
                    extendedProps: { idTarea: tarea.idTarea }
                };
            });
        },
        concatenarNombre(tarea) {
            return `${tarea.nombreProyecto}/${tarea.claveProyecto} - ${tarea.nombre}/${tarea.clave}`;
        },
        generarTablero() {
            if (!this.enviando) {

                this.enviando = true;
                let parametros = {
                    busqueda: this.busqueda,
                    ... this.obtenerRangoFechas()
                };

                TareaReporteService.generarReporteTableroOperacion(parametros).then((respuesta) => {
                    Utileria.descargarExcel(respuesta.data, "tablero_operacion.xlsx");
                }).catch((e) => {
                    this.$utileria.errorhttp(e);
                }).finally(() => {
                    this.enviando = false;
                });
            }
        },
        cerrarDialog() {
            this.dialog = false;
        }
    }
};
</script>
