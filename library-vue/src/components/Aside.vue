<template>
  <el-menu default-active="1-4-1" class="el-menu-vertical-demo"  @open="handleOpen" @close="handleClose" :collapse="isCollapse">
    <h3>鱼塘书店</h3>
    <el-menu-item v-show="flag" v-for="item in noChildren" :key="item.label" :index="menu.label" @click="clickMenu(item)">
      <i :class="'el-icon-' + item.icon"></i>
      <span slot="title">{{item.label}}</span>
    </el-menu-item>
    <el-submenu v-for="item in hasChildren" :index="item.label" :key="item.label" index="1">
      <template slot="title">
        <i :class="'el-icon-' + item.icon"></i>
        <span slot="title">{{item.label}}</span>
      </template>
      <el-menu-item-group v-show="!flag" v-for="(subItem, subIndex) in item.children" :key="subIndex.label">
        <el-menu-item :index="subItem.label" @click="clickMenu(subItem)">{{subItem.label}}</el-menu-item>
      </el-menu-item-group>
    </el-submenu>

  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  data() {
    return {
      flag: false,
      menu: [
        {
          label: '客户',
          icon: 'location',
          children: [
            {
              path: '/customer/bookList',
              name: 'CusBookList',
              label: '可购图书',
              url: ''
            },
            {
              path: '/customer/personCenter',
              name: 'PersonalCenter',
              label: '个人中心',
              url: ''
            }
          ]
        },
        {
          path: '/accountRecord',
          name: 'AccountRecord',
          label: '流水记录',
          icon: 'toilet-paper',
          url: ''
        },
        {
          path: '/bookRecord',
          name: 'BookRecord',
          label: '售书记录',
          icon: 'sunny',
          url: ''
        },
        {
          path: '/customerList',
          name: 'CustomerList',
          label: '客户列表',
          icon: 'position',
          url: 'user-solid'
        },
        {
          path: '/manBookList',
          name: 'ManBookList',
          label: '图书列表',
          icon: 'notebook-1',
          url: 'notebook-1'
        },
      ]
    };
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    getRole() {
      this.$http.get("/getAuthorization").then(
          res => {
            console.log(res.data.data)
            if(res.data.data === "manager") {
              this.flag = true
            }
          }
      )
    },
    // 页面路由跳转
    clickMenu(item) {
      this.$router.push({name: item.name})
      this.$store.commit('selectMenu',item)
    }
  },
  computed: {
    noChildren() {
      return this.menu.filter(item => !item.children)
    },
    hasChildren() {
      return this.menu.filter(item => item.children)
    },
    isCollapse() {
      return this.$store.state.tab.isCollapse;
    }
  },
  mounted() {
    this.getRole()
  }
}
</script>

<style scoped>
  /*侧边栏*/
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
  h3 {
    line-height: 48px;
    align-items: center;
  }
</style>
