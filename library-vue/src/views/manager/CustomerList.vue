<template>
<!--客户列表-->
  <div>
    <div class="cusTable">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="客户名">
          <el-input v-model="form.cusName" style="width: 200px;float: left"></el-input>
        </el-form-item>
        <el-form-item label="账号">
          <el-input v-model="form.cusNum" style="width: 200px;float: left"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" style="width: 200px;float: left"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
          <el-divider direction="vertical"></el-divider>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

<!--    添加用户的dialog   -->
    <el-button @click="dialogFormVisible = true">添加用户</el-button>
<!--    初始化密码-->
    <el-button @click="resetPass">初始化密码</el-button>
    <div>
      <el-dialog title="添加用户" :visible.sync="dialogFormVisible">
        <el-form :model="addCustomer">
          <el-form-item label="真实姓名" :label-width="'120px'">
            <el-input v-model="addCustomer.cusName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="账号" :label-width="'120px'">
            <el-input v-model="addCustomer.cusNum" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="手机号" :label-width="'120px'">
            <el-input v-model="addCustomer.phone" autocomplete="off" @blur="checkPhone($event)"></el-input>
          </el-form-item>
          <el-form-item label="身份证号" :label-width="'120px'">
            <el-input v-model="addCustomer.identity" autocomplete="off" @blur="checkIdentity($event)"></el-input>
          </el-form-item>
          <el-form-item label="常用地址" :label-width="'120px'">
            <el-input v-model="addCustomer.address" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="commitAdd()">确 定</el-button>
        </div>
      </el-dialog>
    </div>

<!--查看详细dialog-->
    <el-dialog title="详细信息" :visible.sync="dialogTableVisible">
      <el-table :data="infoData">
        <el-table-column property="cusName" label="客户名" ></el-table-column>
        <el-table-column property="cusNum"  label="账号"></el-table-column>
        <el-table-column property="phone" label="手机号"></el-table-column>
        <el-table-column property="identity" label="身份证号" width="180"></el-table-column>
        <el-table-column property="balance" label="本站余额"></el-table-column>
        <el-table-column property="address" label="常用地址"></el-table-column>
        <el-table-column property="createTime" label="创建时间" width="150"></el-table-column>
      </el-table>
    </el-dialog>
<!--编辑dialog-->
    <el-dialog title="用户信息" :visible.sync="dialogEditVisible">
      <el-form :model="editForm">
        <el-form-item label="真实姓名" :label-width="'120px'">
          <el-input v-model="editForm.cusName" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="账号" :label-width="'120px'">
          <el-input v-model="editForm.cusNum" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="'120px'">
          <el-input v-model="editForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" :label-width="'120px'">
          <el-input v-model="editForm.identity" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="常用地址" :label-width="'120px'">
          <el-input v-model="editForm.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="创建时间" :label-width="'120px'">
          <el-input v-model="editForm.createTime" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="本站余额" :label-width="'120px'">
          <el-input v-model="editForm.balance" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditVisible = false">取 消</el-button>
        <el-button type="primary" @click="commitEditInfo">提 交</el-button>
      </div>
    </el-dialog>

    <div class="cusList">
      <el-table
          :data="tableData"
          stripe
          ref="multipleTable"
          style="width: 800px"
          :header-row-style="{height:'30px'}"
          max-height="600"
          @selection-change="handleSelection"
      >
        <el-table-column
          prop="id"
          type="selection"
        >
        </el-table-column>
        <el-table-column
            fixed
            prop="cusName"
            label="客户名"
            width="120">
        </el-table-column>
        <el-table-column
            prop="cusNum"
            label="账号"
            width="120">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="手机号"
            width="120">
        </el-table-column>
        <el-table-column
            prop="identity"
            label="身份证号"
            width="180">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="180">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="customerInfo(scope.row)">详细</el-button>
              <!--分割线-->
            <el-divider direction="vertical"></el-divider>
            <el-button type="text" size="small" @click="editAction(scope.row)">编辑</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button
                @click.native.prevent="deleteCustomer(scope.row)"
                type="text"
                size="small">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>

</template>

<script>
export default {
  name: "CustomerList",
  data() {
    return {
      // 表单传入的数据
      form: {
        cusName: null,
        cusNum: null,
        phone: null
      },
      editForm: {
          cusName: null,
          cusNum: null,
          phone: null,
          identity: null,
          balance: null,
          address: null,
          createTime: null,
          id: null
      },
      addCustomer: {
        cusName: null,
        cusNum: null,
        phone: null,
        identity: null,
        address: null
      },
      // 存储展示表格信息
      tableData: [],
      infoData: [],
      ids: [],
      dialogFormVisible: false,
      dialogTableVisible: false,
      dialogEditVisible: false
    }
  },
  methods: {
    // 查询
    onSubmit() {
      this.$http.post("/api/employee/getCustomers", this.form)
        .then(res => {
          this.tableData = res.data.data
          // this.resetForm(res.data)
        })
    },
    // 表格左侧多选框
    handleSelection(val) {
      const tempIds = []
      for(let i = 0; i < val.length; i++) {
        tempIds.push(val[i].id)
      }
      this.ids = tempIds
    },
    resetPass() {
      // 初始化密码
      this.$http.post("/api/employee/initCustomerPass", {
        ids: this.ids
      }).then(res => {
        this.$message({
          message:'初始化密码成功',
          type: 'success',
          duration: 1000
        })
        this.$refs.multipleTable.clearSelection();
        }
      )
    },
    commitAdd() {
      // 关闭dialog
      this.dialogFormVisible = false
      // 新增用户
      this.$http.post("/api/employee/addCustomer",this.addCustomer)
      .then(res => {
        this.$message({
          message:'添加用户成功',
          type: 'success',
          // 回调函数
          onClose: () => {
            this.onSubmit()
          }
        })
      })
    },
    // 重置
    resetForm() {
      this.form.cusName = null
      this.form.cusNum = null
      this.form.phone = null
    },
    // 详细
    customerInfo(cusInfo) {
      this.infoData = []
      this.infoData.push(cusInfo)
      this.dialogTableVisible = true
    },
    // 删除用户
    deleteCustomer(cusInfo) {
      this.$http.post("/api/employee/removeCustomer",{
        id: cusInfo.id
      }).then(res => {
        this.$message({
          message:'删除用户'+cusInfo.cusName+'成功',
          type: 'success',
          duration: 1000,
          // 回调函数
          onClose: () => {
            this.onSubmit()
          }
        })
      })
    },
    // 编辑用户
    editAction(cusInfo) {
      this.dialogEditVisible = true
      this.editForm = cusInfo
    },
    // 提交编辑后的数据
    commitEditInfo() {
      this.$http.post("/api/employee/updateCustomer", {
        id: this.editForm.id,
        address: this.editForm.address,
        phone: this.editForm.phone
      }).then(res => {
        this.$message({
          message:'修改用户成功',
          type: 'success',
          duration: 1000,
          // 回调函数
          onClose: () => {
            this.onSubmit()
            this.dialogEditVisible = false
          }
        })
      })
    },
    // 校验身份证
    checkIdentity(e) {
      console.log(e.target.value)
      let id = e.target.value
      // 1 "验证通过!", 0 //校验不通过
      var format = /^(([1][1-5])|([2][1-3])|([3][1-7])|([4][1-6])|([5][0-4])|([6][1-5])|([7][1])|([8][1-2]))\d{4}(([1][9]\d{2})|([2]\d{3}))(([0][1-9])|([1][0-2]))(([0][1-9])|([1-2][0-9])|([3][0-1]))\d{3}[0-9xX]$/;
      //号码规则校验
      if (!format.test(id)) {
        alert("身份证校验码不合规")
        return
      }
      //区位码校验
      //出生年月日校验   前正则限制起始年份为1900;
      var year = id.substr(6, 4),//身份证年
          month = id.substr(10, 2),//身份证月
          date = id.substr(12, 2),//身份证日
          time = Date.parse(month + '-' + date + '-' + year),//身份证日期时间戳date
          now_time = Date.parse(new Date()),//当前时间戳
          dates = (new Date(year, month, 0)).getDate();//身份证当月天数
      if (time > now_time || date > dates) {
        alert("出生日期不合规")
        return;
      }
      //校验码判断
      var c = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);   //系数
      var b = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');  //校验码对照表
      var id_array = id.split("");
      var sum = 0;
      for (var k = 0; k < 17; k++) {
        sum += parseInt(id_array[k]) * parseInt(c[k]);
      }
      if (id_array[17].toUpperCase() != b[sum % 11].toUpperCase()) {
        alert("身份证校验码不合规")
        return;
      }
    },
    checkPhone(e) {
      let num = e.target.value;
      var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
      if (!myreg.test(num)) {
        alert("手机号格式错误")
      }
    }
  },
  mounted() {
    this.onSubmit()
  }
}
</script>

<style scoped>
  .el-form-item {
    width: 300px;
    float: left;
  }
  /*.cusList {*/
  /*  height: 300px;*/
  /*}*/
  .cusTable {
    height: 80px;
    width: 100%;
  }
</style>
