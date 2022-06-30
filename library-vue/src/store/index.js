import Vue from 'vue'
import Vuex from 'vuex'
import tab from './tab'
Vue.use(Vuex)

export default new Vuex.Store({
    modules: {
        tab
    },
    state: {
        token: ''
    },
    mutations: {
        SET_TOKEN: (state, token) => {
            state.token = token
            localStorage.setItem("token", token)
        },
        DEL_TOKEN: (state) => {
            localStorage.clear()
        },
    },
    actions: {},
})
