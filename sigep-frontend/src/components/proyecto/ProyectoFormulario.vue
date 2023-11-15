<template>
  <div>
    <div class="container">
      <v-banner color="primary" class="mb-6 white--text" shaped single-line>
        Información de proyecto
      </v-banner>
      <v-row>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Clave" rules="required|max:45">
            <v-text-field :counter="45" maxlength="45" v-model.trim="model.clave" label="Clave (única)*" outlined
              clearable required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="8" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Nombre" rules="required|max:50">
            <v-text-field :counter="50" maxlength="50" v-model.trim="model.nombre" label="Nombre*" outlined clearable
              required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>

      </v-row>
      <v-row>
        <v-col md="12" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Descripción" rules="required|max:200">
            <v-textarea rows="2" :counter="200" maxlength="200" v-model.trim="model.descripcion" label="Descripción*"
              outlined clearable required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-textarea>
          </validation-provider>
        </v-col>
      </v-row>
      <v-row>
        <v-col md="3" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Fecha de inicio" rules="required">
            <v-text-field v-model.trim="model.fechaInicio" label="Fecha de inicio*" outlined type="date" clearable
              required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="3" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Fecha de fin" rules="required">
            <v-text-field v-model.trim="model.fechaFin" :min="model.fechaInicio" label="Fecha de fin*" outlined
              :disabled="!model.fechaInicio" type="date" clearable required autocomplete="off" :error-messages="errors"
              dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>

        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Categoría" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="categorias" item-text="nombre" item-value="idCategoria"
              v-model.trim="model.idCategoria" label="Categoría*" clear-icon="mdi-eraser"
              no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
        <v-col md="2" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Color" rules="required">
            <v-text-field v-model.trim="model.color" label="Color*" outlined type="color" clearable required
              autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
      </v-row>
      
      <v-banner color="primary" class="mb-6 white--text" shaped single-line>
        Información clientes
      </v-banner>
      <v-row>
        <v-col md="12" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Clientes" rules="required">
            <v-autocomplete outlined clearable required auto-select-first autocomplete="off" :error-messages="errors"
              :items="clientes" return-object item-text="razonSocial" multiple small-chips v-model.trim="model.clientes"
              label="Clientes*" clear-icon="mdi-eraser" no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
      </v-row>
      <v-banner color="primary" class="mb-6 white--text" shaped single-line>
        Información de empleados
      </v-banner>
      <v-row>
        <v-col md="9" cols="12">
          <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off" :items="empleados"
            item-text="empleado" return-object v-model.trim="empleado" label="Empleado*" clear-icon="mdi-eraser"
            no-data-text="Sin resultados disponibles." />
        </v-col>
        <v-col md="3" cols="12">
          <div class="text-center mb-2">
            <v-tooltip top>
              <template v-slot:activator="{ on, attrs }">
                <v-btn icon @click="agregarEmpleado()" :disabled="!empleado">
                  <v-icon v-bind="attrs" v-on="on" color="primary" left dense>
                    mdi-plus-circle-outline
                  </v-icon>
                </v-btn>
              </template>
              <small>Agregar a proyecto</small>
            </v-tooltip>
          </div>
        </v-col>
      </v-row>
      <v-row>
        <v-col md="12" cols="12">
          <v-text-field v-model="busqueda" label="Buscar empleado..."
            :append-outer-icon="busqueda ? 'mdi-eraser' : 'mdi-magnify'" @click:append-outer="busqueda = ''">
          </v-text-field>
          <v-data-table :headers="headers" :items="model.empleados" :search="busqueda" :items-per-page="10"
            no-results-text="Sin resultados coincidentes con la búsqueda" :footer-props="{
              'items-per-page-text': 'Elementos por página',
              'items-per-page-all-text': 'Todo',
            }" no-data-text="Sin resultados disponibles" class="mx-5" dense fixed-header height="auto">
            <template v-slot:[`item.indice`]="{ index }">
              {{ index + 1 }}
            </template>
            <template v-slot:[`item.acciones`]="{ item }">
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon @click="model.empleados.splice(model.empleados.indexOf(item), 1);">
                    <v-icon v-bind="attrs" v-on="on" left dense small> mdi-delete </v-icon>
                  </v-btn>
                </template>
                <span>Eliminar</span>
              </v-tooltip>
            </template>
          </v-data-table>
        </v-col>
      </v-row>

    </div>
  </div>
</template>
  
<script>

import EmpleadoService from '@/core/services/EmpleadoService';
import ClienteService from '@/core/services/ClienteService';
import CategoriaService from '@/core/services/CategoriaService';
import ColumnaIndice from "@/components/common/indice/ColumnaIndice.vue";


export default {
  name: "ProyectoFormulario",
  components: {
    ColumnaIndice
  },
  props: {
    model: Object,
    accion: Number
  },
  data() {
    return {
      empleados: [],
      categorias: [],
      clientes: [],
      empleadosSeleccionados: [],
      empleado: null,
      busqueda: null,
      headers: [
        { text: "#", value: "indice", align: "center", sortable: false, width: "5%", },
        { text: "Empleado", value: "empleado.empleado", align: "center", sortable: false, width: "40%", },
        { text: "Acciones", value: "acciones", align: "center", sortable: false, width: "15%", },
      ]
    }
  },
  mounted() {
    this.listarCatalogos();
  },
  methods: {
    listarCatalogos() {

      EmpleadoService.listarTodosActivos().then((respuesta) => {
        this.empleados = respuesta.data;
      });

      ClienteService.listarTodos().then((respuesta) => {
        this.clientes = respuesta.data;
      });

      CategoriaService.listarTodos().then((respuesta) => {
        this.categorias = respuesta.data;
      });



    },
    agregarEmpleado() {
      this.model.empleados = this.model.empleados ? this.model.empleados : [];
      if (this.model.empleados.filter((obj) => { return this.empleado.idEmpleado === obj.empleado.idEmpleado }).length > 0) {
        this.$utileria.notificacion("El empleado ya se encuentra agregado", "ERROR");
      } else {
        this.model.empleados.push(Object.assign({
          empleado: this.empleado
        }));
        this.empleado = null;
      }

    }
  }
}
</script>
  