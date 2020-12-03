import Vue from 'vue'
import routes from './route/index'
import Vuex from 'vuex'
import store from './store'
import VueRouter from 'vue-router'
import VueI18n from 'vue-i18n'
import lang from './i18n'

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(VueRouter)

const router = new VueRouter({
  routes: routes
})

Vue.use(Vuex)

Vue.config.productionTip = true


Vue.use(VueI18n)

const i18n = new VueI18n({
  locale: 'en', // set locale
  messages: lang
})

router.beforeEach((to, from, next) => {

  if(to.name===from.name){
    console.log("全局导航守卫");
  }else{
    next();
  }

})

new Vue({
  i18n,
  store,
  router:router
}).$mount('#app')
