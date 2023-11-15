<template>
  <div>
    <div class="container">
      <v-row>
        <v-col md="6" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Nombre" rules="required|max:45">
            <v-text-field :counter="45" maxlength="45" v-model.trim="model.authority" label="Nombre*" outlined clearable
              required autocomplete="off" :error-messages="errors" dense clear-icon="mdi-eraser">
              <v-icon slot="append" :color="valid ? 'success' : ''">
                {{ valid ? " mdi-check" : "" }}
              </v-icon>
            </v-text-field>
          </validation-provider>
        </v-col>
        <v-col md="6" cols="12">
          <validation-provider v-slot="{ errors, valid }" name="Descripción" rules="required|max:100">
            <v-text-field :counter="100" maxlength="100" v-model.trim="model.descripcion" label="Descripción*" outlined clearable
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
    name: "RolFormulario",
    props: {
      model: Object,
    },
  };
  </script>
    