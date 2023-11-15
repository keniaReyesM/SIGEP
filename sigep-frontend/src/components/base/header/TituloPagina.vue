<template>
    <!-- <div class="app-page-title"> -->
        <!-- <div class="page-title-wrapper"> -->
            <!-- <div class="page-title-heading"> -->
                <h2 class="mb-6 tittle-color-theme" style="font-weight: bold;"> {{ titulo }}</h2>
            <!-- </div> -->
        <!-- </div> -->
    <!-- </div> -->
</template>

<script>
import { library } from '@fortawesome/fontawesome-svg-core';
import {
    faStar,
    faPlus
} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

library.add(
    faStar,
    faPlus,
);

export default {
    components: {
        'font-awesome-icon': FontAwesomeIcon,
    },
    props: {
        icon: String,
        titulo: String,
        subtitulo: String
    },

}
</script>
