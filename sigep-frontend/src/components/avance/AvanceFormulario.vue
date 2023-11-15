<template>
  <div>
    <div class="container">
      <v-row>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Fecha de avance" rules="required">
            <v-text-field v-model.trim="model.fechaAvance" label="Fecha de avance*" outlined clearable
              type="date" required autocomplete="off" :error-messages="errors"
              dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Hora de inicio" rules="required">
            <v-text-field v-model.trim="model.horaInicio" label="Hora de inicio*" outlined clearable
              type="time" required autocomplete="off" :error-messages="errors"
              dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="4" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Hora de fin" rules="required">
            <v-text-field v-model.trim="model.horaFin" label="Hora de fin*" outlined clearable
              type="time" required autocomplete="off" :error-messages="errors"
              dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
      </v-row>
    </div>
  </div>
</template>
  
<script>
export default {
  name: "AvanceFormulario",
  props: {
    model: Object,
    tarea: Object
  },
  mounted(){
  }
};
</script>
  