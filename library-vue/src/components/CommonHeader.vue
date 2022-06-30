<template>
  <header>
    <div class="l-content">
      <el-button plain icon="el-icon-menu" size="mini" @click="handleMenu()"></el-button>
<!--      面包屑-->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item v-for="item in tags" :key="item.path" :to="{ path: item.path }">{{item.label}}</el-breadcrumb-item>

      </el-breadcrumb>
    </div>
    <div class="r-content">
      <el-dropdown trigger="click" size="mini">
          <img :src="userImg" class="user"/>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="logout()">退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </header>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: "CommonHeader",
  data() {
    return {
      userImg: require('@/assets/logo.png')
    }
  },
  methods: {
    handleMenu() {
      this.$store.commit('collapseMenu')
    },
    logout() {
      console.log("asdasd")
      this.$store.commit('DEL_TOKEN')
      this.$router.push('/login')
      this.$message({
        message:'退出成功',
        type: 'success',
        duration: 1000,
      })
    }
  },
  computed: {
    ...mapState({
      tags: state => state.tab.tabsList
    })
  }
}
</script>

<style lang="less" scoped>
  header {
    display: flex;
    height: 100%;
    justify-content: space-between;
    align-items: center;
  }
  .l-content {
    display: flex;
    align-items: center;
    .el-button {
      margin-right: 20px;
    }
  }
  .r-content {
    .user {
      width: 40px;
      height: 40px;
      border-radius: 50%;
      margin-top: 28px;
    }
  }

</style>
