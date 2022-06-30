export default {
    state: {
        isCollapse: false,
        tabsList: [
            {
                path: '/accountRecord',
                name: 'AccountRecord',
                label: '流水记录',
                icon: 'toilet-paper'
            },
            // {
            //     path: '/bookRecord',
            //     name: 'BookRecord',
            //     label: '图书列表',
            //     icon: 'notebook-1'
            // },
            // {
            //     path: '/customerList',
            //     name: 'CustomerList',
            //     label: '客户列表',
            //     icon: 'position'
            // },
            // {
            //     path: '/bookRecord',
            //     name: 'BookRecord',
            //     label: '售书记录',
            //     icon: 'sunny'
            // },
            // {
            //     path: '/customer/personCenter',
            //     name: 'PersonCenter',
            //     label: '个人中心',
            // },
            // {
            //     path: '/customer/bookList',
            //     name: 'CusBookList',
            //     label: '可购图书',
            // },
        ],
        currentMenu: null
    },
    mutations: {
        collapseMenu(state) {
            state.isCollapse = !state.isCollapse;
        },
        selectMenu(state, val) {
            if(val.name !== 'AccountRecord') {
                state.currentMenu = val
                const result = state.tabsList.findIndex(item => item.name === val.name)
                if(result === -1) {
                    state.tabsList.push(val)
                }
            } else {
                state.currentMenu = null;
            }
        }
    }
}
