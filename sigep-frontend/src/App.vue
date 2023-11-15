<template>
  <v-app>
    <v-main>

      <div id="app">
        <component :is="layout">
          <transition name="fade" mode="out-in">

            <router-view :key="$route.fullPath"></router-view>
          </transition>
        </component>
      </div>
    </v-main>
  </v-app>

</template>

<script>
import GeneralConstants from "@/core/constants/GeneralConstants";

export default {
  computed: {
    layout() {

      const rutaPrivada = (this.$route.meta.privado == true  || this.$session.exists());
      return rutaPrivada ? GeneralConstants.LAYOUT_PRIVADA : GeneralConstants.LAYOUT_PUBLICA;
    }
  },
}
</script>

<style lang="scss">
@import "assets/base.scss";
</style>
