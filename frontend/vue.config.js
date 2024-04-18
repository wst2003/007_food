const { defineConfig } = require('@vue/cli-service')
const Components = require('unplugin-vue-components/webpack')
const NutUIResolver = require('@nutui/auto-import-resolver')
const { plugins } = require("eslint-plugin-vue/lib/configs/base");

// const BaseUrl = "http://localhost:8000"
const BaseUrl = "http://100.80.74.33:8000"

// const BaseUrl = "http://127.0.0.1:4523/m1/4090306-0-default/api"
const imageURL = "https://food-bank.obs.cn-east-3.myhuaweicloud.com/"

module.exports = defineConfig({
  transpileDependencies: true,
  // configureWebpack:{
  //   plugins: [
  //     // 开启 unplugin 插件，自动引入 NutUI 组件
  //     Components({
  //       resolvers: [NutUIResolver()],
  //     })
  //   ],
  //   externals: {
  //     'BaiduMap': "BMapGL"
  //   }
  // },
  configureWebpack: config => {
    config.module.rules.push({
      test: /\.worker.js$/,
      use: {
        loader: 'worker-loader',
        options: { inline: true, name: 'workerName.[hash].js' }
      }
    })
    const plugin = [
      // 开启 unplugin 插件，自动引入 NutUI 组件
      Components({
        resolvers: [NutUIResolver()],
      })
    ];
    config.plugins.push(...plugin);
    config["externals"] = {
      'BaiduMap': "BMapGL"
    }
  },
  devServer: {
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

    }
  }
})