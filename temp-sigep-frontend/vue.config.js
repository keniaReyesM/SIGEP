const path = require('path');

module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  chainWebpack: config => {
    config.module.rules.delete('eslint');
    config.resolve.alias.set(
      'vue$',
      // If using the runtime only build
      path.resolve(__dirname, 'node_modules/vue/dist/vue.runtime.esm.js')
      // Or if using full build of Vue (runtime + compiler)
      // path.resolve(__dirname, 'node_modules/vue/dist/vue.esm.js')
    );
  },
  publicPath: process.env.NODE_ENV === 'production' ? '/' : '/',
  devServer: {
    open: process.platform === 'darwin',
    host: 'localhost',
    port: 8085, // CHANGE YOUR PORT HERE!
    // https: true,
    // hotOnly: false
    hot: "only"
  },

}