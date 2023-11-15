<template>
    <div class="app-header header-shadow">
        <div class="logo-src" style="{
                
                background-size: contain;
                background-repeat: no-repeat;
                background-position: center;
                width: 100px;
                height: 100%;
                
            }"/>
        <div class="app-header__content">
            <div class="app-header-left">
                <!-- <SearchBox /> -->
            </div>
            <div class="app-header-right">
                <UserArea />
            </div>
        </div>
        <div class="app-header__mobile-menu">
            <div>
                <button type="button" color="white" class="hamburger close-sidebar-btn hamburger--elastic"
                    v-bind:class="{ 'is-active': isOpen }" @click="toggleMobile('closed-sidebar-open')">
                    <span class="hamburger-box">
                        <span class="hamburger-inner"></span>
                    </span>
                </button>
            </div>
        </div>
        <div class="app-header__menu">
            <span>
                <!-- <b-button class="btn-icon btn-icon-only" variant="primary" size="sm"
                    v-bind:class="{ 'active': isOpenMobileMenu }" @click="toggleMobile2('header-menu-open')">
                    <div class="btn-icon-wrapper">
                        <font-awesome-icon icon="ellipsis-v" />
                    </div>
                </b-button> -->
                <v-btn @click="cerrarSesion()" depressed rounded color="primary" class="mt-3" text>
                    Salir <v-icon right> mdi-logout</v-icon>
                </v-btn>
                <!-- <b-button class="btn-icon btn-icon-only" variant="primary" size="sm"
                    v-bind:class="{ 'active': isOpenMobileMenu }" @click="toggleMobile2('header-menu-open')">
                    <div class="btn-icon-wrapper">
                        <font-awesome-icon icon="ellipsis-v" />
                    </div>
                </b-button> -->
            </span>
        </div>
    </div>
</template>

<script>

// import SearchBox from '@/components/base/header/SearchBox';
import UserArea from '@/components/base/header/HeaderUserArea';
import RutasConstants from "@/core/constants/RutasConstants";

import { library } from '@fortawesome/fontawesome-svg-core'
import {
    faEllipsisV,
} from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(
    faEllipsisV,
);
export default {
    name: "Header",
    components: {
        UserArea,
        'font-awesome-icon': FontAwesomeIcon,
    },

    data() {
        return {
            isOpen: false,
            isOpenMobileMenu: false,
        }
    },
    props: {


    },
    methods: {
        toggleMobile(className) {
            const el = document.body;
            this.isOpen = !this.isOpen;

            if (this.isOpen) {
                el.classList.add(className);
            } else {
                el.classList.remove(className);
            }
        },

        toggleMobile2(className) {
            const el = document.body;
            this.isOpenMobileMenu = !this.isOpenMobileMenu;

            if (this.isOpenMobileMenu) {
                el.classList.add(className);
            } else {
                el.classList.remove(className);
            }
        },
        cerrarSesion() {
            this.$session.destroy();
            this.$session.clear();
            this.$router.push({ name: RutasConstants.LOGIN.nombre });
        }
    }
};
</script>
