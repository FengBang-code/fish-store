<template>
  <el-container style="height: 100%">
    <el-aside width="auto">
      <Aside></Aside>
    </el-aside>
    <el-container>
      <el-header>
        <CommonHeader/>
      </el-header>
      <el-main>
        <router-view/>
<!--        <home></home>-->
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Aside from '../components/Aside';
import CommonHeader from "@/components/CommonHeader";
import home from '@/views/home/index'
export default {
  name: "Main",

  components: {
    Aside,CommonHeader,home
  },
  mounted() {
    this.$http.post('/api/employee/bookInfo',{
      id: 7
    }).then(
        function(res) {
          // console.log(res)
        }
    ).catch(function(error) {
      console.log("error")
    })
  }
}
</script>

<style scoped>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  /*background-color: #D3DCE6;*/
  /*color: #D3DCE6;*/
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

</style>
