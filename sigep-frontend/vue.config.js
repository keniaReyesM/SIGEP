
module.exports = {
    transpileDependencies: [
        'vuetify'
    ],
    runtimeCompiler: true,
    productionSourceMap: false,
    chainWebpack: config => {
        config.module.rules.delete('eslint');
      },
    publicPath: process.env.NODE_ENV === 'production' ? '/' : '/',
    devServer: {
        open: process.platform === 'darwin',
        host: '0.0.0.0',
        port: 8085, // CHANGE YOUR PORT HERE!
        // https: true,
        hotOnly: false
        // hot: "only"
    },
}