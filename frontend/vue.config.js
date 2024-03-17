const { defineConfig } = require('@vue/cli-service')
const Components = require('unplugin-vue-components/webpack')
const NutUIResolver = require('@nutui/auto-import-resolver')

const BaseUrl = "http://localhost:8080"
const imageURL = "https://food-bank.obs.cn-east-3.myhuaweicloud.com/"

module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
    plugins: [
      // 开启 unplugin 插件，自动引入 NutUI 组件
      Components({
        resolvers: [NutUIResolver()],
      })
    ],
    externals: {
      'BaiduMap': "BMapGL"
    }
  },
  devServer: {
    proxy: {
      '/api': {
        target: BaseUrl,
        changeOrigin: true,
      }
    }
  }
})