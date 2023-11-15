import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';
import '@mdi/font/css/materialdesignicons.css';

Vue.use(Vuetify);

export default new Vuetify({
  theme: { dark: false },
  icons: {
    iconFont: 'mdi'
  },
  theme: {
    themes: {
      light: {
        primary: '#1C4464',
        secondary: '#1565C0',
        success: '#098E81'
      },
      dark: {
        primary: '#66BB6A',
        secondary: '#1565C0',
        success: '#098E81'
      },
    },
  },
});
