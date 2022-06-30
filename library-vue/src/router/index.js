import Vue from "vue";
import axios from "../axios";
import Login from "@/views/login/Login";
import Main from "@/views/Main";
import CusBookList from '@/views/customer/Cus-BookList'
import PersonalCenter from "@/views/customer/PersonalCenter";
import AccountRecord from "@/views/manager/AccountRecord";
import BookRecord from "@/views/manager/BookRecord";
import CustomerList from "@/views/manager/CustomerList";
import ManBookList from "@/views/manager/Man-BookList"

import VueRouter from 'vue-router'
Vue.use(VueRouter)
const routes = [
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/',
        component: Main,
        name: 'Main',
        children: [
            {
                path: 'accountRecord',
                name: 'AccountRecord',
                component: AccountRecord
            },
            {
                path: 'bookRecord',
                name: 'BookRecord',
                component: BookRecord
            },
            {
                path: 'customerList',
                name: 'CustomerList',
                component: CustomerList
            },
            {
                path: 'manBookList',
                name: 'ManBookList',
                component: ManBookList
            },
            {
                path: 'customer/bookList',
                name: 'CusBookList',
                component: CusBookList
            },
            {
                path: 'customer/personCenter',
                name: 'PersonalCenter',
                component: PersonalCenter
            }
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})
// 解决重复点击路由报错的BUG
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch((err) => err)
}

router.beforeEach((to,from,next) => {
    let token = localStorage.getItem('token')
    if(to.path === '/login'){
        next()
    }
    else if(token === null) {
        alert("您无权限，请登录重试！")
        next({path: '/login'})
    }
    else {
        next()
    }
})

export default router
