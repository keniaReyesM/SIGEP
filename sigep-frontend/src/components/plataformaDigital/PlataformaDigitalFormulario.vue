<template>
  <div>
    <div class="container">
      <v-banner color="primary" class="mb-6 white--text" shaped single-line>
        Información general
      </v-banner>
      <v-row align="center" justify="center">
        <v-col md="5" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Cliente" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="clientes" item-text="razonSocial" item-value="idCliente"
              v-model.trim="model.idCliente" label="Cliente*" clear-icon="mdi-eraser"
              no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid? " mdi-check": "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
        <v-col md="5" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Nombre" rules="required|max:100">
            <v-text-field :counter="100" maxlength="100" v-model.trim="model.nombre" label="Nombre*" outlined clearable
              required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid? " mdi-check": "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>

      </v-row>
      <v-row align="center" justify="center">
        <v-col md="5" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="URL" rules="required|max:500">
            <v-text-field :counter="500" maxlength="500" v-model.trim="model.url" label="URL*" outlined clearable
              required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid? " mdi-check": "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="5" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="IP" rules="required|max:100">
            <v-text-field :counter="100" maxlength="100" v-model.trim="model.ip" label="IP*" outlined clearable required
              autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid? " mdi-check": "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
      </v-row>
      <v-banner color="primary" class="mb-6 white--text" shaped single-line>
        Información digital
      </v-banner>
      <v-row>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Tipo de plataforma digital" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="tiposPlataformasDigitales" item-text="nombre"
              item-value="idTipoPlataformaDigital" v-model.trim="model.idTipoPlataformaDigital"
              label="Tipo de plataforma digital*" clear-icon="mdi-eraser" no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid? " mdi-check": "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Proveedor de alojamiento" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="proveedoresAlojamiento" item-text="nombre"
              item-value="idProveedorAlojamiento" v-model.trim="model.idProveedorAlojamiento"
              label="Proveedor de alojamiento*" clear-icon="mdi-eraser" no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid? " mdi-check": "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Responsable compra" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="responsablesCompra" item-text="nombre" item-value="idResponsableCompra"
              v-model.trim="model.idResponsableCompra" label="Responsable compra*" clear-icon="mdi-eraser"
              no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid? " mdi-check": "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
      </v-row>


      <v-row align="center" justify="center">
        <v-col md="5" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Gestor de contenido" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="gestoresContenido" return-object item-text="nombre"
              item-value="idGestorContenido" multiple small-chips v-model.trim="model.gestoresContenido"
              label="Gestor de contenido*" clear-icon="mdi-eraser" no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid? " mdi-check": "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
        <v-col md="5" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Tecnologías en código" rules="required">
            <v-autocomplete dense outlined clearable required auto-select-first autocomplete="off"
              :error-messages="errors" :items="tecnologiasDesarrollo" return-object item-text="nombre"
              item-value="idTecnologiaDesarrollo" multiple small-chips v-model.trim="model.tecnologiasDesarrollo"
              label="Tecnologías en código*" clear-icon="mdi-eraser" no-data-text="Sin resultados disponibles.">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid? " mdi-check": "" }}
              </v-icon>
            </v-autocomplete>
          </validation-provider>
        </v-col>
      </v-row>


    </div>
  </div>
</template>

<script>
import ClienteService from '@/core/services/ClienteService';
import GestorContenidoService from '@/core/services/GestorContenidoService';
import TecnologiaDesarrolloService from '@/core/services/TecnologiaDesarrolloService';
import ResponsableCompraService from '@/core/services/ResponsableCompraService';
import ProveedorAlojamientoService from '@/core/services/ProveedorAlojamientoService';
import TipoPlataformaDigitalService from '@/core/services/TipoPlataformaDigitalService';



export default {
  name: "PlataformaDigitalFormulario",
  props: {
    model: Object,
    accion: Number
  },
  data() {
    return {
      clientes: [],
      gestoresContenido: [],
      tecnologiasDesarrollo: [],
      responsablesCompra: [],
      proveedoresAlojamiento: [],
      tiposPlataformasDigitales: [],












      mostrarContrasena: true,
      areas: [],
      dias: [],
      roles: [],
      puestos: [],
      tiposTelefono: [],
      correo: { correo: null, principal: false },
      telefono: { telefono: null, idTipoTelefono: null, principal: false },
      diaLaboral: { idDia: null, horaInicio: null, horaFin: null },
      representante: {
        nombre: null, primerApellido: null,
        segundoApellido: null, fechaNacimiento: null
      }
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

      ClienteService.listarTodos().then((respuesta) => {
        this.clientes = respuesta.data;
      });

      GestorContenidoService.listarTodos().then((respuesta) => {
        this.gestoresContenido = respuesta.data;
      });

      TecnologiaDesarrolloService.listarTodos().then((respuesta) => {
        this.tecnologiasDesarrollo = respuesta.data;
      });

      ResponsableCompraService.listarTodos().then((respuesta) => {
        this.responsablesCompra = respuesta.data;
      });

      ProveedorAlojamientoService.listarTodos().then((respuesta) => {
        this.proveedoresAlojamiento = respuesta.data;
      });

      TipoPlataformaDigitalService.listarTodos().then((respuesta) => {
        this.tiposPlataformasDigitales = respuesta.data;
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
    agregarRepresentante() {
      this.model.representantes.push(Object.assign(this.representante, {}));
    },
    eliminarRepresentante(indice) {
      this.model.representantes.splice(indice, 1);
    },
  }
}
</script>
