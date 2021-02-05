
module.exports = {
  productionSourceMap: false,
  runtimeCompiler: true,
  configureWebpack: {
    externals: {
      "L": "L",
      "leaflet":"leaflet"
    },
    plugins: [
      
    ]
  }
}