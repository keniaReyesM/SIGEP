import Vue from "vue";
import App from "./App";
import router from "./router";
import BootstrapVue from "bootstrap-vue";
import PublicaLayout from "@/views/layout/PublicaLayout.vue";
import PrivadaLayout from "@/views/layout/PrivadaLayout.vue";
import VueSession from 'vue-session';
import GeneralConstants from "@/core/constants/GeneralConstants";
import Validation from "@/core/validation/index";
import loader from "vue-ui-preloader";
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";
import VueCryptojs from 'vue-cryptojs'
import Utileria from "@/core/util/Utileria";
import vuetify from '@/core/plugins/vuetify';
import "@/assets/css/app.css";
import PermisosContants from "@/core/constants/PermisosContants";

const utileriaPlugin = {
  install() {
    Vue.utileria = Utileria;
    Vue.prototype.$utileria = Utileria;
  }
}
Vue.use(utileriaPlugin);

const permisosPlugin = {
  install() {
    Vue.utileria = PermisosContants;
    Vue.prototype.$permisos = PermisosContants;
  }
}
Vue.use(permisosPlugin);

Vue.use(loader);
Vue.use(VueSession, {
  persist: true
});



Vue.config.productionTip = false;

Vue.use(BootstrapVue);

Vue.use(VueCryptojs);

Vue.use(Toast, {
  transition: "Vue-Toastification__bounce",
  maxToasts: 5,
  newestOnTop: true,
  hideProgressBar: true,
});


Vue.component(GeneralConstants.LAYOUT_PRIVADA, PrivadaLayout);
Vue.component(GeneralConstants.LAYOUT_PUBLICA, PublicaLayout);

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount("#app");