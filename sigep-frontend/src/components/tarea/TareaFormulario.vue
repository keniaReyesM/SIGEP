<template>
  <div>
    <div class="container">
      <div v-show="accion === 2">
        <v-row>
          <v-col md="12" cols="12">
            <label>¿Desea reutilizar tareas?</label>
            <v-autocomplete dense outlined clearable auto-select-first autocomplete="off" :items="tareasReutilizar"
              item-text="nombreCompleto" item-value="idTarea" v-model.trim="idTareaReutilizar"
              label="Tareas previamente registradas" clear-icon="mdi-eraser" no-data-text="Sin resultados disponibles." />
          </v-col>
        </v-row>
        <hr />
        <br />
      </div>
      <v-row>
        <v-col md="9" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Proyecto" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="proyectos" item-text="nombreCompleto" item-value="idProyecto"
              v-model.trim="model.idProyecto" label="Proyecto*" clear-icon="mdi-eraser"
              no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
        <v-col md="3" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Tipo tarea" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="tiposTarea" item-text="nombre" item-value="idTipoTarea"
              v-model.trim="model.idTipoTarea" label="Tipo tarea*" clear-icon="mdi-eraser" @change="validarTipoTarea()"
              no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
      </v-row>
      <v-row>
        <v-col md="12" cols="12">
          <transition name="fade" mode="out-in">
            <validation-provider v-slot="{ errors, valid }" name="Tarea" rules="required"
              v-if="(esSubtarea && model.idProyecto)">
              <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
                :error-messages="errors" :items="tareas" item-text="nombre" item-value="idTarea"
                v-model.trim="model.idTareaOrigen" label="Tarea*" clear-icon="mdi-eraser"
                no-data-text="Sin resultados disponibles.">
                <v-icon slot="append" :color="valid ? 'success' : ''">
                  {{ valid ? " mdi-check" : "" }}
                </v-icon>
              </v-autocomplete>
            </validation-provider>
          </transition>

        </v-col>
      </v-row>
      <v-row>
        <v-col md="12" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Empleados">
            <v-autocomplete dense outlined clearable :disabled="model.idProyecto ? false : true" auto-select-first
              autocomplete="off" :items="empleados" item-text="empleado" return-object v-model="model.empleados"
              label="Empleados (opcional)" clear-icon="mdi-eraser" multiple small-chips :error-messages="errors"
              no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid && $utileria.nonEmptyList(model.empleados) ? 'success' : ''">
                {{ valid && $utileria.nonEmptyList(model.empleados) ? " mdi-check" : "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>

        </v-col>
      </v-row>
      <v-row>
        <v-col md="6" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Fecha hora de inicio" rules="required">
            <v-text-field v-model.trim="model.fechaHoraInicio" label="Fecha hora de inicio*" outlined clearable
              :min="fechaInicioProyecto" type="datetime-local" required autocomplete="off" :error-messages="errors" dense
              clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="6" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Fecha hora de fin">
            <v-text-field v-model.trim="model.fechaHoraFin" label="Fecha hora de fin*" outlined clearable
              :min="model.fechaHoraInicio" :disabled="!model.fechaHoraInicio" type="datetime-local"
              :max="fechaFinProyecto" autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid && model.fechaHoraFin ? 'success' : ''">
                {{ valid && model.fechaHoraFin ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
      </v-row>
      <v-row>
        <v-col md="3" cols="12" v-if="accion === 3">
          <validation-provider v-slot="{ errors, valid }" name="Clave" rules="required|max:45">
            <v-text-field :counter="45" maxlength="45" v-model.trim="model.clave" label="Clave*" outlined clearable
              required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>

        </v-col>
        <v-col :md="accion === 3 ? '3' : '6'" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Nombre" rules="required|max:45">
            <v-text-field :counter="45" maxlength="45" v-model.trim="model.nombre" label="Nombre*" outlined clearable
              required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="3" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Nivel de prioridad" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="nivelesPrioridad" item-text="nombre" item-value="idNivelPrioridad"
              v-model.trim="model.idNivelPrioridad" label="Nivel de prioridad*" clear-icon="mdi-eraser"
              no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
        <v-col md="3" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Estatus" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="estados" item-text="nombre" item-value="idEstado"
              v-model.trim="model.idEstado" label="Estatus*" clear-icon="mdi-eraser"
              no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
      </v-row>
      <v-row>

        <v-col md="12" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Descripción" rules="required">
            <v-textarea counter rows="3" v-model.trim="model.descripcion" label="Descripción*" outlined clearable required
              autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-textarea>
          </validation-provider>
        </v-col>
      </v-row>
    </div>
  </div>
</template>
    
<script>

import ProyectoService from '@/core/services/ProyectoService';
import TipoTareaService from '@/core/services/TipoTareaService';
import NivelPrioridadService from '@/core/services/NivelPrioridadService';
import TareaService from '@/core/services/TareaService';
import EstadoService from '@/core/services/EstadoService.js';
import Utileria from '@/core/util/Utileria';
import GeneralConstants from '@/core/constants/GeneralConstants';

export default {
  name: "TareaFormulario",
  props: {
    model: Object,
    accion: Number
  },
  data: () => ({
    proyectos: [],
    tiposTarea: [],
    tareas: [],
    tareasReutilizar: [],
    estados: [],
    nivelesPrioridad: [],
    empleados: [],
    datetime: null,
    esSubtarea: false,
    fechaInicioProyecto: null,
    fechaFinProyecto: null,
    idTareaReutilizar: null,
  }),
  watch: {
    "model.idProyecto"() {
      this.listarTareasEmpleadosPorProyecto();
      this.fechaInicioProyecto = null;
      this.fechaFinProyecto = null;
      if (Utileria.nonEmpty(this.model.idProyecto)) {
        let proyectoSeleccionado = this.proyectos.filter(p => p.idProyecto == this.model.idProyecto);
        if (Utileria.nonEmptyList(proyectoSeleccionado)) {
          this.fechaInicioProyecto = proyectoSeleccionado[0].fechaInicio;
          this.fechaFinProyecto = proyectoSeleccionado[0].fechaFin;
        }
      }
    },
    "idTareaReutilizar"() {
      this.obtenerTareaReutilizar();
    },
  },
  mounted() {
    this.listarCatalogos();
  },
  methods: {
    listarCatalogos() {
      ProyectoService.listarTodos().then((respuesta) => {
        this.proyectos = respuesta.data.map((proyecto) => {
          let proyectoNombre = `${Utileria.formatoFecha(proyecto.fechaInicio)} al ${Utileria.formatoFecha(proyecto.fechaFin)}`;
          return {
            idProyecto: proyecto.idProyecto,
            nombreCompleto: `${proyecto.clave} - ${proyecto.nombre} (${proyectoNombre}) - ${proyecto.categoria}`,
          };
        });
      });

      TipoTareaService.listarTodos().then((respuesta) => {
        this.tiposTarea = respuesta.data;
      });

      EstadoService.listarTipoTarea().then((respuesta) => {
        this.estados = respuesta.data;
      });

      NivelPrioridadService.listarTodos().then((respuesta) => {
        this.nivelesPrioridad = respuesta.data;
      });

      this.listarTareasEmpleadosPorProyecto();
      this.listarTareasReutilizar();

    },
    validarTipoTarea() {
      this.esSubtarea = false;
      if (Utileria.nonEmpty(this.model.idTipoTarea)) {
        let tipoTareaActual = this.tiposTarea.filter((tipo) => tipo.idTipoTarea == this.model.idTipoTarea);
        this.esSubtarea = (
          Utileria.nonEmptyList(tipoTareaActual) &&
          tipoTareaActual[0].nombre === GeneralConstants.TIPOS_TAREA.SUB_TAREA
        );
      }
      if (this.esSubtarea) {
        this.model.idTareaOrigen = null;
      }
    },
    listarTareasEmpleadosPorProyecto() {

      this.tareas = [];
      this.empleados = [];

      if (Utileria.nonEmpty(this.model.idProyecto)) {
        TareaService.listarTareasPorProyecto(this.model.idProyecto, this.model.idTarea).then((respuesta) => {
          this.tareas = respuesta.data;
        });

        ProyectoService.listarEmpleadosPorProyecto(this.model.idProyecto).then((respuesta) => {
          this.empleados = respuesta.data;
        });

      }
    },
    listarTareasReutilizar() {
      this.tareasReutilizar = [];
      TareaService.listarTareasReutilizar().then((respuesta) => {
        this.tareasReutilizar = respuesta.data.map((tarea) => {
          return {
            idTarea: tarea.idTarea,
            nombreCompleto: `${tarea.clave} | ${tarea.nombre} (${tarea.claveProyecto}  | ${tarea.nombreProyecto})`
          }
        });
      });
    },
    obtenerTareaReutilizar() {

      if (Utileria.nonEmpty(this.idTareaReutilizar)) {

        TareaService.obtener({ idTarea: this.idTareaReutilizar }).then((respuesta) => {

          let data = respuesta.data;
          data.idTarea = undefined;
          this.$emit('setModel', Object.assign(data));

        });
      }

    }
  }
};
</script>
    