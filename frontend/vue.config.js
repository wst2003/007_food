const { defineConfig } = require('@vue/cli-service')
const Components = require('unplugin-vue-components/webpack')
const NutUIResolver = require('@nutui/auto-import-resolver')
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin')
const { BundleAnalyzerPlugin } = require('webpack-bundle-analyzer');
// import globalData from "../../global.js"
const fs=require('fs')
// const BaseUrl = "http://119.3.153.217:8002"
const BaseUrl = "http://124.70.156.23:8002"
// const BaseUrl="http://localhost:8002"
// const BaseUrl = "http://100.80.74.33:8002"

// const BaseUrl = "http://127.0.0.1:4523/m1/4090306-0-default/api"
const imageURL = "https://food-bank.obs.cn-east-3.myhuaweicloud.com/"

module.exports = defineConfig({
  transpileDependencies: true,

  configureWebpack:
    config => {
      config.mode = 'production';
      config.plugins.push(new BundleAnalyzerPlugin());
      config.optimization = {
        minimizer: [
          new CssMinimizerPlugin(), // 去重压缩css
        ],
      };
      config.module.rules.push(
      )
      const plugin = [
        // 开启 unplugin 插件，自动引入 NutUI 组件
        Components({
          resolvers: [NutUIResolver()],
        }),
      ];
      config.plugins.push(...plugin);
      config["externals"] = {
        'BaiduMap': "BMapGL",
      };
    },
  devServer: {
    port:80,
    // port:443,
    client: { //加上这个就好了
      overlay: false,
    },
    proxy: {
      '/api': {
        target: BaseUrl,
        changeOrigin: true,
      },
      "/gpt": {
        target: "http://119.8.11.44:8081",
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          "^/gpt": "",
        },
      },
      "/alipay": {
        target: "http://124.70.156.23:3000",
        changeOrigin: true,ws: true,
        pathRewrite: {
          "^/alipay": "/",
        },
      },
    },
    // https:{
    //   key:fs.readFileSync('./server.key'),
    //   cert:fs.readFileSync('./server.cert'),
    // }
  },
  parallel: false
})