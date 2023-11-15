<template>
    <div>
        <loader :loader="enviando" />


        <center>
        <h2 class="mb-6"> Bienvenido(a) {{ $utileria.obtenerUsuario().nombreCompleto }} </h2>

            <div class=" dd-header-bg-8 bg-animation" style="width: 60%; height: auto">
            </div>

            <div id="app">
                <!-- <ejs-accumulationchart id="container">
                    <e-accumulation-series-collection>
                        <e-accumulation-series :dataSource='seriesData' xName='x' yName='y'> </e-accumulation-series>
                    </e-accumulation-series-collection>
                </ejs-accumulationchart> -->
            </div>
        </center>

        <div width="50px">
            <!-- <canvas id="grafica"></canvas> -->
        </div>
    </div>
</template>

<script>
import { ValidationObserver, ValidationProvider } from "vee-validate";
import Loader from "@/components/base/loader/Loader.vue";
import LoginService from "@/core/services/LoginService";
import RutasConstants from "@/core/constants/RutasConstants";
import GeneralConstants from "@/core/constants/GeneralConstants";

export default {

    name: "LoginView",
    components: {
        ValidationObserver,
        ValidationProvider,
        Loader
    },
    data: () => ({
        model: {
            usuario: "spiderman@gmail.com",
            contrasena: "spiderman",
        },
        enviando: false
    }),
    methods: {

    }
}
</script>
