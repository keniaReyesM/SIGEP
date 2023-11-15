<template>
  <div>
    <div class="container">
      <v-row align="center" justify="center">
        <v-col md="6" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Nombre" rules="required|max:100">
            <v-text-field :counter="100" maxlength="100" v-model.trim="model.nombre" label="Nombre*" outlined clearable
              required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
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
  name: "GestorContenidoFormulario",
  props: {
    model: Object,
  },
};
</script>
  