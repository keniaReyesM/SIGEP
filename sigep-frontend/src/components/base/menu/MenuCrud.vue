<template>

    <div>
        <v-tooltip top :index="indice" v-if="eliminar && $utileria.authorized($route?.meta?.permisoEliminar)">
            <template v-slot:activator="{ on, attrxs }" >
                <v-menu offset-x bottom  left :close-on-content-click="true" rounded="lg" ref="menuMain">
                    <template v-slot:activator="{ on: menuDelete, attrs }">
                        <span v-bind="attrs" v-on="{ ...menuDelete }" >
                            <v-icon color="red"  left dense small v-on="on"  v-bind="attrxs"> mdi-delete </v-icon>
                        </span>
                    </template>
                    <v-card>
                        <v-list color="grey lighten-3">
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item-title>
                                        <span> ¿Desea <strong>eliminar</strong> el registro actual? </span>
                                    </v-list-item-title>
                                </v-list-item-content>
                            </v-list-item>
                        </v-list>
                        <v-divider></v-divider>
                        <v-card-actions class="justify-center">
                            <v-btn color="red" type="button"  text @click="$refs.menuMain.isActive = false;">
                                Cancelar
                            </v-btn>
                            <v-divider vertical></v-divider>
                            <v-btn color="primary" type="button"  text
                                @click="eliminar(parametrosEliminar, indice); $refs.menuMain.isActive = false;">
                                Sí, eliminar
                            </v-btn>
                        </v-card-actions>
                    </v-card>
                </v-menu>
            </template>
            <span>Eliminar</span>
        </v-tooltip>
        <v-tooltip top :index="indice" v-if="obtener && $utileria.authorized($route?.meta?.permisoEditar)">
            <template v-slot:activator="{ on, attrs }">
                <v-btn icon @click="obtener(parametrosObtener, indice)">
                    <v-icon color="orange" v-bind="attrs" v-on="on" left dense small> mdi-pencil </v-icon>
                </v-btn>
            </template>
            <span>Editar</span>
        </v-tooltip>
        <v-tooltip top :index="indice" v-if="actualizarEstado && $utileria.authorized($route?.meta?.permisoActualizarEstado)">
            <template v-slot:activator="{ on, attrs }">
                <v-btn icon @click="actualizarEstado(parametrosActualizarEstado, indice)">
                    <v-icon color="green" v-bind="attrs" v-on="on" left dense small> mdi-refresh </v-icon>
                </v-btn>
            </template>
            <span>Actualizar estado</span>
        </v-tooltip>

    </div>
   
</template>

<script>

import Utileria from '@/core/util/Utileria';

export default {
    name: "MenuCrud",
    props: {
        obtener: Function,
        eliminar: Function,
        actualizarEstado: Function,
        parametrosObtener: Object,
        parametrosEliminar: Object,
        parametrosActualizarEstado: Object,
        indice: Number
    },
    data() {
        return {
        }
    },
};
</script>
