<template>
  <el-row class="home" :gutter="20">
    <el-row :span="8" style="margin-top: 20px">
      <el-card shadow="hover">
        <div class="user">
          <img :src="userImg"/>
          <div class="userinfo">
            <p class="name">Admin</p>
            <p class="access">超级管理员</p>
          </div>
          <div class="login-info">
            <p>上次登录的时间：<span>2022-04-27</span></p>
            <p>上次登录的地点：<span>中山</span></p>
          </div>
        </div>
      </el-card>
    </el-row>
  </el-row>
</template>

<script>
export default {
  name: "home",
  data() {
    return {
      userImg: require("@/assets/logo.png")
    }
  }
}
</script>

<style scoped>

</style>
