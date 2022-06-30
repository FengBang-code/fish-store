<template>
<!--登录-->
  <div class="loginPage">
    <el-switch
        v-model="ruleForm.state"
        active-color="#409EFF"
        inactive-color="#409EFF"
        active-value = 0
        inactive-value = 1
        active-text="用户登录"
        inactive-text="员工登录">
    </el-switch>

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="用户名" prop="userNum">
        <el-input v-model="ruleForm.userNum"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="ruleForm.password" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Login",
  data() {
    return {
      ruleForm: {
        userNum: null,
        password: null,
        state: null
      },
      rules: {
        userNum: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$http.post("/login", this.ruleForm).then(
              res => {
                this.$message({
                  message:'登录成功',
                  type: 'success',
                  duration: 1000,
                })
                const jwt = res.headers['authorization']
                this.$store.commit('SET_TOKEN', jwt)
                this.$router.push('/')
              },

          )
          console.log(this.ruleForm)
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }


}
</script>

<style scoped>
  .loginPage {
    display: flex;
    width: 100%;
    height: 100%;
    justify-content: center;
    align-items: center;
  }
  .demo-ruleForm {
    width: 500px;

  }
</style>
