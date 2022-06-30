import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import http from './axios'
import router from "@/router";
import store from '@/store/index'

Vue.config.productionTip = false
// 全局引入Element-ui
Vue.use(ElementUI)
Vue.prototype.$http = http
// Vue.prototype.$echarts = echarts
new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')
