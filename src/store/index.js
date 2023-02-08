import Vue from 'vue'
import Vuex from 'vuex'
import common from './modules/common.js'
import menu from './modules/menu.js'
import createPersistedState from 'vuex-persistedstate'
import user from './modules/user.js'


Vue.use(Vuex)
export default new Vuex.Store({
  plugins: [
    createPersistedState({
      storage: window.localStorage
    })
  ],
  state: {},
  mutations: {
  },
  getters: {},
  actions: {
  },
  modules: {
    menu,
    common,
    user
  }
})
