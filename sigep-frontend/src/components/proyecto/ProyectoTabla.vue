<template>
  <div>
    <v-row align="center" justify="center">


      <v-col md="4" cols="12" v-for="(proyecto, index) in lista" :key="index" v-if="dialogoInformacion == false">
        <v-card :color="proyecto.color" shaped>
          <v-card-title>
            <v-icon left>
              mdi-book 
            </v-icon>
            <span class="text-h6 font-weight-light">
              {{ proyecto.nombre }}
            </span>
            <v-row align="center" justify="end">
              <v-tooltip top v-if="$utileria.authorized($permisos.PROYECTO_EDITAR)">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon @click="obtener({ idProyecto: proyecto.idProyecto }, index)" color="yellow">
                    <v-icon v-bind="attrs" v-on="on" left dense small> mdi-pencil </v-icon>
                  </v-btn>
                </template>
                <span>Editar</span>
              </v-tooltip>
              <br />
              <v-tooltip top v-if="$utileria.authorized($permisos.PROYECTO_ACTUALIZAR_ESTADO)">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon @click="actualizarEstado({ idProyecto: proyecto.idProyecto }, index)">
                    <v-icon v-bind="attrs" v-on="on" left dense small> mdi-refresh </v-icon>
                  </v-btn>
                </template>
                <span>Actualizar estado</span>
              </v-tooltip>
            </v-row>


          </v-card-title>

          <v-card-text class="font-weight-bold text-dark">
            <small>
              <span style="font-style: italic;">
                {{ proyecto.descripcion }}
              </span>
              <br />
              Clave: {{ proyecto.clave }}
              <br />
              Categoría: {{ proyecto.categoria }}
              <br />
              Duración: {{ $utileria.formatoFecha(proyecto.fechaInicio) }} al {{
                $utileria.formatoFecha(proyecto.fechaFin)
              }}
              <br />
              Estado: <strong :class="{ 'text-danger': proyecto.estado != 'Activo' }">
                {{ proyecto.estado }}
              </strong>
            </small>
          </v-card-text>

          <v-card-actions>
            <v-list-item class="grow">

              <v-row align="center" justify="start">
                <small>
                  <v-icon class="mr-1">
                    mdi-human-male-female
                  </v-icon>
                  <span class="subheading mr-2">
                    Empleados: {{ proyecto.cantidadEmpleados }}
                  </span>
                  <v-icon class="mr-1">
                    mdi-content-paste
                  </v-icon>
                  <span class="subheading mr-2">
                    Tareas: {{ proyecto.cantidadTareas }}
                  </span>
                  <span class="mr-1">·</span>
                  <v-icon class="mr-1">
                    mdi-cube-outline
                  </v-icon>
                  <span class="subheading">
                    Clientes: {{ proyecto.cantidadClientes }}
                  </span>
                  <span class="mr-1">·</span>
                  <v-icon class="mr-1" @click="verInformacionGeneral(proyecto)">
                    mdi-information-outline
                  </v-icon>
                  <span class="subheading">
                    Visión general
                  </span>
                </small>
              </v-row>
            </v-list-item>
          </v-card-actions>
        </v-card>
      </v-col>
      <div v-if="lista.length == 0 && dialogoInformacion == false">
        Sin resultados disponibles.
      </div>
      <v-dialog v-model="dialogoInformacion" v-if="dialogoInformacion == true" 
      
      fullscreen class="mt-10" transition="dialog-bottom-transition">

        <v-card>

          <v-toolbar dark :color="color">
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
    </v-row>
  </div>
</template>

<script>
import ColumnaIndice from "@/components/common/indice/ColumnaIndice.vue";
import VisionGeneral from "@/components/proyecto/ProyectoVisionGeneral.vue";

export default {
  name: "EmpleadoTabla",
  props: {
    lista: Array,
    obtener: Function,
    eliminar: Function,
    actualizarEstado: Function,
    elementosPorPagina: Number,
    paginaActual: Number
  },
  components: {
    ColumnaIndice,
    VisionGeneral
  },
  data() {
    return {
      headers: [
        { text: "#", value: "indice", align: "center", sortable: false, width: "5%" },
        { text: "Empleado", value: "nombreCompleto", align: "center", sortable: false, width: "20%" },
        { text: "Puesto", value: "puesto", align: "center", sortable: false, width: "20%" },
        { text: "Usuario", value: "usuario", align: "center", sortable: false, width: "20%" },
        { text: "Estado", value: "estado", align: "center", sortable: false, width: "20%" },
        { text: "Acciones", value: "acciones", sortable: false, align: "center", width: "15%" },
      ],
      dialogoInformacion: false,
      idProyecto: null,
      color: null
    }
  },
  methods: {
    verInformacionGeneral(proyecto) {
      this.idProyecto = proyecto.idProyecto;
      this.color = proyecto.color;
      this.dialogoInformacion = true;
    }
  }
};
</script>
