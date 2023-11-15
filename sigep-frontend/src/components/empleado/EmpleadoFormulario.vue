<template>
  <div>
    <div class="container">
      <v-banner color="primary" class="mb-6 white--text" shaped single-line>
        Información de acceso
      </v-banner>
      <v-row>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Usuario" rules="required|max:100|email">
            <v-text-field :counter="100" maxlength="100" v-model.trim="model.usuario" label="Usuario (correo)*" outlined
              clearable required autocomplete="off" type="email" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Rol" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="roles" item-text="descripcion" item-value="idRol"
              v-model.trim="model.idRol" label="Rol*" clear-icon="mdi-eraser"
              no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
        <v-col md="4" cols="12" v-if="!mostrarAccion(3)">
          <validation-provider v-slot="{ errors }" name="Contraseña" rules="required|max:100">
            <v-text-field :counter="100" maxlength="100" v-model.trim="model.contrasena" label="Contraseña*" outlined
              clearable required autocomplete="off"  :error-messages="errors" dense
              :append-icon="mostrarContrasena ? 'mdi-eye-outline' : 'mdi-eye-off-outline'"
              @click:append="mostrarContrasena = !mostrarContrasena" :type="mostrarContrasena ? 'text' : 'password'"
              clear-icon="mdi-eraser">
            </v-text-field>
          </validation-provider>
        </v-col>
      </v-row>
      <v-banner color="primary" class="mb-6 white--text" shaped single-line>
        Información personal
      </v-banner>
      <v-row>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Nombre" rules="required|max:100|'alpha'">
            <v-text-field :counter="100" maxlength="100" v-model.trim="model.nombre" label="Nombre*" outlined clearable
              required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Primer apellido" rules="required|max:100|'alpha'">
            <v-text-field :counter="100" maxlength="100" v-model.trim="model.primerApellido" label="Primer apellido*"
              outlined clearable required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? "mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Segundo apellido" rules="max:100|'alpha'">
            <v-text-field :counter="100" maxlength="100" v-model.trim="model.segundoApellido"
              label="Segundo apellido (opcional)" outlined clearable required autocomplete="off"
              :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid && model.segundoApellido ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
      </v-row>
      <v-row>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Fecha de nacimiento" rules="required">
            <v-text-field type="date" v-model.trim="model.fechaNacimiento" :max="new Date()" label="Fecha de nacimiento"
              outlined clearable required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
      </v-row>
      <v-banner color="primary" class="mb-6 white--text" shaped single-line>
        Información de dirección
      </v-banner>
      <v-row>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Calle" rules="required|max:100">
            <v-text-field :counter="100" maxlength="100" v-model.trim="model.calle" label="Calle*" outlined clearable
              required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? "mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Número exterior" rules="required|max:5">
            <v-text-field :counter="5" maxlength="5" v-model.trim="model.numeroExterior" label="Número exterior*"
              outlined clearable required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? "mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Número interior" rules="max:5">
            <v-text-field :counter="5" maxlength="5" v-model.trim="model.numeroInterior"
              label="Número interior (opcional)" outlined clearable required autocomplete="off" :error-messages="errors"
              dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid && model.numeroInterior ? "mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
      </v-row>
      <v-row>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Colonia" rules="required|max:100|'alpha'">
            <v-text-field :counter="100" maxlength="100" v-model.trim="model.colonia" label="Colonia*" outlined
              clearable required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? "mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Municipio" rules="required|max:100|'alpha'">
            <v-text-field :counter="100" maxlength="100" v-model.trim="model.municipio" label="Municipio*" outlined
              clearable required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? "mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Entidad federativa" rules="required|max:100|'alpha'">
            <v-text-field :counter="100" maxlength="100" v-model.trim="model.estado" label="Entidad federativa*"
              outlined clearable required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? "mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
      </v-row>
      <v-banner color="primary" class="mb-6 white--text" shaped single-line>
        Información laboral
      </v-banner>
      <v-row>
        <v-col md="6" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Puesto" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="puestos" item-text="nombre" item-value="idPuesto"
              v-model.trim="model.idPuesto" label="Puesto*" clear-icon="mdi-eraser"
              no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
        <v-col md="6" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Áreas" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="areas" return-object item-text="nombre" item-value="idArea" multiple small-chips
              v-model.trim="model.areas" label="Áreas*" clear-icon="mdi-eraser"
              no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
      </v-row>
      <v-divider></v-divider>
      <div class="text-center mb-2">
        <v-tooltip top>
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon @click="agregarHorario()">
              <v-icon v-bind="attrs" v-on="on" color="primary" left dense>
                mdi-plus-circle-outline
              </v-icon>
            </v-btn>
          </template>
          <small>Agregar horario laboral</small>
        </v-tooltip>
      </div>
      <v-row v-for="(horarioLaboral, indice) in model.horariosLaborales" :key="`${indice}horario`">
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Día" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="dias"  item-text="nombre" item-value="idDia"
              v-model.trim="horarioLaboral.idDia" label="Día*" clear-icon="mdi-eraser"
              no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
        <v-col md="3" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Hora inicio" rules="required">
            <v-text-field v-model.trim="horarioLaboral.horaInicio" label="Hora inicio*" type="time" outlined clearable
              required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="3" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Hora fin" rules="required">
            <v-text-field v-model.trim="horarioLaboral.horaFin" label="Hora fin*" type="time" outlined clearable
              required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="2" cols="12">
          <div class="text-center">
            <v-tooltip top v-if="indice != 0">
              <template v-slot:activator="{ on, attrs }">
                <v-btn icon @click="eliminarHorario(indice)">
                  <v-icon v-bind="attrs" v-on="on" small left dense>
                    mdi-delete
                  </v-icon>
                </v-btn>
              </template>
              <small>Eliminar</small>
            </v-tooltip>
          </div>
        </v-col>
      </v-row>
      <v-banner color="primary" class="mb-6 white--text" shaped single-line>
        Información de contacto
      </v-banner>
      <div class="text-center">
        <v-tooltip top>
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon>
              <v-icon v-bind="attrs" v-on="on" @click="agregarTelefono()" color="primary" left dense>
                mdi-plus-circle-outline
              </v-icon>
            </v-btn>
          </template>
          <small>Agregar teléfono</small>
        </v-tooltip>
      </div>
      <v-row v-for="(telefono, indice) in model.telefonos" :key="`${indice}telefono`">
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Tipo de teléfono" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="tiposTelefono" item-text="nombre" item-value="idTipoTelefono"
              v-model.trim="telefono.idTipoTelefono" label="Tipo de teléfono*" clear-icon="mdi-eraser"
              no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Teléfono" rules="required|max:10">
            <v-text-field :counter="10" maxlength="10" v-model.trim="telefono.telefono" label="Teléfono*" outlined
              clearable required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? "mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col  md="2" cols="12">
          <div class="text-right">

            <validation-provider v-slot="{ errors }" name="Principal" rules="required">
              <v-switch v-model="telefono.principal" inset label="Principal" required autocomplete="off"
                :error-messages="errors" dense />
            </validation-provider>
          </div>
        </v-col>
        <v-col md="2" cols="12">
          <div class="text-center"  v-if="indice != 0">
            <v-tooltip top>
              <template v-slot:activator="{ on, attrs }">
                <v-btn icon @click="eliminarTelefono(indice)">
                  <v-icon v-bind="attrs" v-on="on" left small dense>
                    mdi-delete
                  </v-icon>
                </v-btn>
              </template>
              <small>Eliminar</small>
            </v-tooltip>
          </div>
        </v-col>
      </v-row>
      <v-divider></v-divider>
      <div class="text-center mb-2">
        <v-tooltip top>
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon>
              <v-icon v-bind="attrs" v-on="on" color="primary" left dense @click="agregarCorreo()">
                mdi-plus-circle-outline
              </v-icon>
            </v-btn>
          </template>
          <small>Agregar correo</small>
        </v-tooltip>
      </div>
      <v-row v-for="(correo, indice) in model.correos" :key="`${indice}correo`">
        <v-col md="5" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Correo electrónico" rules="required|max:100|email">
            <v-text-field :counter="100" maxlength="100" v-model.trim="correo.correo" label="Correo electrónico*"
              outlined clearable required autocomplete="off" type="email" :error-messages="errors" dense
              clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col offset="3" md="2" cols="12">
          <validation-provider v-slot="{ errors }" name="Principal" rules="required">
            <v-switch v-model="correo.principal" inset label="Principal" required autocomplete="off" :error-messages="errors"
              dense />
          </validation-provider>
        </v-col>
        <v-col md="2" cols="12">
          <div class="text-center"  v-if="indice != 0">
            <v-tooltip top>
              <template v-slot:activator="{ on, attrs }">
                <v-btn icon @click="eliminarCorreo(indice)">
                  <v-icon v-bind="attrs" v-on="on" small left dense>
                    mdi-delete
                  </v-icon>
                </v-btn>
              </template>
              <small>Eliminar</small>
            </v-tooltip>
          </div>
        </v-col>
      </v-row>


    </div>
  </div>
</template>
  
<script>

import AreaService from '@/core/services/AreaService';
import DiaService from '@/core/services/DiaService';
import RolService from '@/core/services/RolService';
import PuestoService from '@/core/services/PuestoService';
import TipoTelefonoService from '@/core/services/TipoTelefonoService';

export default {
  name: "EmpleadoFormulario",
  props: {
    model: Object,
    accion: Number
  },
  data() {
    return {
      mostrarContrasena: true,
      areas: [],
      dias: [],
      roles: [],
      puestos: [],
      tiposTelefono: [],
      correo: { correo: null, principal: false },
      telefono: { telefono: null, idTipoTelefono: null, principal: false },
      diaLaboral: { idDia: null, horaInicio: null, horaFin: null }
    }
  },
  mounted() {
    this.listarCatalogos();
  },
  methods: {
    mostrarAccion(accion) {
      return this.accion == accion;
    },
    listarCatalogos() {

      RolService.listarTodos().then((respuesta) => {
        this.roles = respuesta.data;
      });

      DiaService.listarTodos().then((respuesta) => {
        this.dias = respuesta.data;
      });

      AreaService.listarTodos().then((respuesta) => {
        this.areas = respuesta.data;
      });

      PuestoService.listarTodos().then((respuesta) => {
        this.puestos = respuesta.data;
      });

      TipoTelefonoService.listarTodos().then((respuesta) => {
        this.tiposTelefono = respuesta.data;
      });
    },
    agregarHorario() {
      this.model.horariosLaborales.push({});
    },
    eliminarHorario(indice) {
      if (this.model.horariosLaborales.length == 1) {
        this.$utileria.notificacion("Debe ser ingresado mínimo 1 horario.", "ERROR");
      } else {
        this.model.horariosLaborales.splice(indice, 1);
      }
    },
    agregarTelefono() {
      this.model.telefonos.push(Object.assign(this.telefono, {}));
    },
    eliminarTelefono(indice) {
      this.model.telefonos.splice(indice, 1);
    },
    agregarCorreo() {
      this.model.correos.push(Object.assign(this.correo, {}));
    },
    eliminarCorreo(indice) {
      this.model.correos.splice(indice, 1);
    },
  }
}
</script>
  