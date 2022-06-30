import axios from "axios";
import router from "./router";
import Vue from 'vue'
import Element, {Message} from "element-ui"
axios.defaults.baseURL = "http://localhost:8001"
// axios.defaults.baseURL = "http://fzhuoxing.work:8001"
const request = axios.create({
    timeout: 5000,
    headers: {
        'Content-Type': "application/json; charset=utf-8"
    }
})

request.interceptors.request.use(config => {
    // 每次请求前都要带上token，否则后端会拦截请求
    config.headers['Authorization'] = localStorage.getItem("token")
    return config
})

request.interceptors.response.use(
    response => {

        console.log("response ->" + response)

        let res = response.data

        if (res.code === 200) {
            return response
        } else {
            Element.Message.error(!res.msg ? '系统异常' : res.msg)
            return Promise.reject(response.data.msg)
        }
    },
    error => {

        console.log(error)

        if (error.response.data) {
            error.message = error.response.data.msg
        }

        if (error.response.status === 401) {
            router.push("/login")
        }
        // 弹窗秒数3000
        Element.Message.error(error.massage, {duration: 1500})
        return Promise.reject(error.message)
    }
)

export default request
