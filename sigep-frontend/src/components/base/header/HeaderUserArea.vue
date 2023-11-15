<template>
    <div class="d-flex">
        <div class="header-btn-lg pr-0">
            <div class="widget-content p-0">
                <div class="widget-content-wrapper">
                    <div class="widget-content-left">
                        <!-- <b-dropdown toggle-class="p-0 mr-2" menu-class="dropdown-menu-lg" variant="link" right> -->
                            <!-- <span slot="button-content">
                                <div class="icon-wrapper icon-wrapper-alt rounded-circle">
                                    <img width="42" class="rounded-circle" src="@/assets/images/avatars/1.jpg" alt="">
                                </div>
                            </span> -->
                            <!-- <button type="button" tabindex="0" class="dropdown-item">Menus</button>
                            <button type="button" tabindex="0" class="dropdown-item">Settings</button>
                            <h6 tabindex="-1" class="dropdown-header">Header</h6>
                            <button type="button" tabindex="0" class="dropdown-item">Actions</button>
                            <div tabindex="-1" class="dropdown-divider"></div>
                            <button type="button" tabindex="0" class="dropdown-item">Dividers</button>
                        </b-dropdown> -->
                    </div>
                    <div class="widget-content-left  ml-3 header-user-info">
                        <div class="widget-heading">{{ nombreCompleto }}</div>
                        <div class="widget-subheading">{{ nombrePuesto }} / {{ nombreRol }}</div>
                    </div>
                    <div class="widget-content-right header-user-info ml-3">
                        <v-btn @click="cerrarSesion()" depressed rounded color="white" class="mt-3" text>
                            Salir <v-icon right> mdi-logout</v-icon>
                        </v-btn>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
// import VuePerfectScrollbar from 'vue-perfect-scrollbar'
import RutasConstants from "@/core/constants/RutasConstants";
import { library } from '@fortawesome/fontawesome-svg-core';
import {
    faAngleDown,
    faCalendarAlt,
    faTrashAlt,
    faCheck,
    faFileAlt,
    faCloudDownloadAlt,
    faFileExcel,
    faFilePdf,
    faFileArchive,
    faEllipsisH,
} from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

library.add(
    faAngleDown,
    faCalendarAlt,
    faTrashAlt,
    faCheck,
    faFileAlt,
    faCloudDownloadAlt,
    faFileExcel,
    faFilePdf,
    faFileArchive,
    faEllipsisH,
);

export default {
    components: {
        'font-awesome-icon': FontAwesomeIcon,
    },
    data: () => ({
        nombreCompleto: "Usuario no definido",
        nombrePuesto: "",
        nombreRol: "Sin rol"
    }),
    mounted() {
        this.obtenerUsuario();
    },
    methods: {
        obtenerUsuario() {
            let usuario = this.$utileria.obtenerUsuario();
            this.nombreCompleto = usuario.nombreCompleto;
            this.nombrePuesto = usuario.nombrePuesto;
            this.nombreRol = usuario?.rol?.descripcion || this.nombreRol;
        },
        cerrarSesion() {
            this.$session.destroy();
            this.$router.push({ name: RutasConstants.LOGIN.nombre });
        }
    }
}


</script>
