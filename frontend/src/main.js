import { createApp } from 'vue'
import App from './App.vue'
import '@nutui/touch-emulator'
import router from '../router/index.js'; // 导入路由实例
createApp(App).use(router).mount('#app')
