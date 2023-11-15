<template>
    <div>
        <loader :loader="enviando" />
        <div class="h-100 dd-header-bg-7 bg-animation">
            <div class="d-flex h-100 justify-content-center align-items-center">
                <b-col md="8" class="mx-auto app-login-box">
                    <!-- <div class="app-logo-inverse mx-auto mb-3" /> -->
                    <validation-observer ref="observer" v-slot="{ invalid }">

                        <b-form lazy-validation @submit.prevent="login">

                            <div class="modal-dialog w-100 mx-auto">
                                <div class="modal-content">
                                    <div class="modal-body">
                                        <div class="h5 modal-title text-center">
                                            <h4 class="mt-2">
                                                <div>Bienvenido</div>
                                                <span>Inicie sesión con sus datos de acceso.</span>
                                            </h4>
                                            <small style="font-style: italic;">
                                                {{ $utileria.VERSION_SISTEMA }}
                                            </small>
                                        </div>

                                        <validation-provider rules="required|email" name="Correo electrónico">
                                            <b-form-group slot-scope="{ valid, errors }" label="Correo electrónico">
                                                <b-form-input autocomplete="off" type="email" v-model.trim="model.usuario"
                                                    placeholder="Ingrese..."
                                                    :state="errors[0] ? false : (valid ? true : null)" />
                                                <b-form-invalid-feedback>{{ errors[0] }} </b-form-invalid-feedback>
                                            </b-form-group>
                                        </validation-provider>

                                        <ValidationProvider rules="required" name="Contrasena" vid="password">
                                            <b-form-group slot-scope="{ valid, errors }" label="Contraseña">
                                                <b-form-input type="password" v-model.trim="model.contrasena"
                                                    placeholder="Ingrese..."
                                                    :state="errors[0] ? false : (valid ? true : null)" />
                                                <b-form-invalid-feedback>{{ errors[0] }} </b-form-invalid-feedback>
                                            </b-form-group>
                                        </ValidationProvider>

                                        <!-- <b-form-group id="exampleInputGroup2" label-for="exampleInput2">
                                            <b-form-input id="exampleInput2" type="password"
                                                v-model.trim="model.contrasena" placeholder="Contraseña..." />
                                        </b-form-group> -->
                                        <!-- <b-form-checkbox name="check" id="exampleCheck">
                                        Keep me logged in
                                    </b-form-checkbox> -->
                                        <div class="divider" />
                                        <!-- <h6 class="mb-0">
                                        ¿No tienes cuenta?
                                        <a href="javascript:void(0);" class="text-primary">Registrarme</a>
                                    </h6> -->
                                    </div>
                                    <div class="modal-footer clearfix">
                                        <!-- <div class="float-left">
                                            <a href="javascript:void(0);" class="btn-lg btn btn-link">
                                                Recuperar contraseña
                                            </a>
                                        </div> -->
                                        <div class="float-right">
                                            <b-button variant="primary" type="submit" size="lg"
                                                :disabled="enviando || invalid">
                                                Iniciar sesión
                                            </b-button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </b-form>
                    </validation-observer>

                    <div class="text-center text-white opacity-8 mt-3">
                        ACCION TI | 2022
                    </div>
                </b-col>
            </div>
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
            usuario: "",
            contrasena: "",
        },
        enviando: false
    }),
    methods: {
        login() {
            this.enviando = true;
            let parametros = { username: this.model.usuario, password: this.model.contrasena };
            LoginService.iniciarSesion(parametros).then((respuestaLogin) => {

                this.$session.destroy();
                this.$session.start();
                this.$session.set(GeneralConstants.TOKEN_SESION, respuestaLogin.data.access_token);

                let usuario = respuestaLogin.data;

                LoginService.obtenerInformacionLogin(parametros).then((respuestaInformacion) => {

                    usuario = JSON.stringify({ ...usuario, ...respuestaInformacion.data });
                    this.$session.set(GeneralConstants.USUARIO_SESION, this.$utileria.encriptar(usuario));
                    this.$router.push({ name: RutasConstants.INICIO.nombre });

                }).catch(this.$utileria.errorhttp);

            }).catch((error) => {
                console.log(error);
                this.$utileria.notificacion("Contraseña incorrecta.", "ERROR");
            }).finally(() => {
                this.enviando = false;
            });
        }
    }
}
</script>
