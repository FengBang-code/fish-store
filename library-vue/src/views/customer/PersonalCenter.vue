<template>

  <el-row>
    <el-col :span="24">
      <div class="grid-content bg-purple-dark">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item label="原密码">
            <el-input v-model="formInline.originalPass" placeholder="原密码" show-password class="inputed-butt"></el-input>
          </el-form-item>
          <el-form-item label="新密码" style="margin-left: 20px">
            <el-input v-model="formInline.password" placeholder="新密码" show-password class="inputed-butt"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit" class="inputed-butt">提交</el-button>
          </el-form-item>
        </el-form>
        <div>
          <div class="block">

              <el-date-picker
                  v-model="timeVal"
                  type="daterange"
                  align="right"
                  unlink-panels
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :picker-options="pickerOptions"
                  value-format="yyyy-MM-ddTHH:mm:ss"
              >
              </el-date-picker>

            <el-button type="primary" round style="margin-left: 30px" @click="checkIt">查询</el-button>
          </div>
          <div>
            <el-table
                :data="tableData"
                border
                style="width: 58%;margin-left: 250px">
              <el-table-column
                  fixed
                  prop="accountNum"
                  label="流水号"
                  width="150"
              >
              </el-table-column>
              <el-table-column
                  prop="orderNum"
                  label="订单号"
                  width="120">
              </el-table-column>
              <el-table-column
                  prop="accountAmount"
                  label="交易金额"
                  width="120">
              </el-table-column>
              <el-table-column
                  prop="payType"
                  label="支付类型"
                  width="120">
              </el-table-column>
              <el-table-column
                  prop="accountRemark"
                  label="流水备注"
                  width="300">
              </el-table-column>
              <el-table-column
                  fixed="right"
                  label="操作"
                  width="80">
                <template slot-scope="scope">
                  <el-button @click="handleClick(scope.row)" type="text" size="small">详细</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="block" style="float: left;margin-left: 260px">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="page"
                :page-sizes="[10, 20, 30, 35]"
                :page-size="limit"
                layout="total, sizes, prev, pager, next, jumper"
                :total="tableData.length">
            </el-pagination>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "PersonalCenter",
  data() {
    return {
      formInline: {
        originalPass: '',
        password: ''
      },
      form: {
        begin: null,
        end: null,
        cusName: null
      },
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      timeVal: null,
      // 表格
      tableData: [],
      limit: 10,
      page: 1,
      myName: ""
    }
  },
  methods: {
    // 修改密码
    onSubmit() {
      this.$http.post("/api/customer/revisePassword", this.formInline).then(
          res => {
            this.$message({
              message: '密码更新成功',
              type: 'success',
              duration: 1500
            })
          }
      )
      this.formInline.originalPass = "",
      this.formInline.password = ""
    },
    // 查询个人流水
    checkIt() {
      this.findName()
      if(this.timeVal !== null) {
        this.form.begin = this.timeVal[0]
        this.form.end = this.timeVal[1].slice(0,11) + '23:59:59'
      }
      this.form.cusName = this.myName
      this.$http.post("/api/account/query", this.form).then(
          res => {
            this.tableData = res.data.data
            this.timeVal = null
            this.form.begin = null
            this.form.end = null
          }
      )
    },
    findName() {
      this.$http.get("/api/customer/personal").then(
          res => {
            this.myName = res.data.data.cusName;
          }
      )
    },
    handleClick(row) {
      console.log(row);
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.page = 1
      this.limit = val
      // this.getList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.page = val
      // this.getList()
    }
  },
  mounted() {
    this.checkIt()
  },

}
</script>

<style scoped>

.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  /*background: #99a9bf;*/
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
.inputed-butt {
  margin-top: 60px;
}
</style>
