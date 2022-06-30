<template>
  <div style="width: 100%; height: 100%">
    <div class="AccountForm">
      <el-form :model="form" class="demo-form-inline" label-width="80px">
        <el-form-item label="流水号">
          <el-input v-model="form.accountNum" style="width: 200px;float: left"></el-input>
        </el-form-item>
        <el-form-item label="订单号">
          <el-input v-model="form.orderNum" style="width: 200px;float: left"></el-input>
        </el-form-item>
        <el-form-item label="客户名">
          <el-input v-model="form.cusName" style="width: 200px;float: left"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" style="width: 200px;float: left"></el-input>
        </el-form-item>
        <el-form-item label="支付类型">
          <el-select v-model="form.payType" placeholder="-----" style="width: 200px;float: left">
            <el-option label="本站支付" value="0"></el-option>
            <el-option label="网银支付" value="1"></el-option>
            <el-option label="现金支付" value="2"></el-option>
            <el-option label="退款" value="3"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="时间">
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
        </el-form-item>
        <el-form-item style="margin-left: 100px">
          <el-button type="primary" @click="onSubmit">查询</el-button>
          <el-button @click="clearSubmit">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="AccountList">
<!--      流水详细dialog-->
      <el-dialog :visible.sync="dialogInfo">
        <el-descriptions direction="vertical" :column="4" border>
          <el-descriptions-item label="流水号">{{infoData.accountNum}}</el-descriptions-item>
          <el-descriptions-item label="订单号">{{infoData.orderNum}}</el-descriptions-item>
          <el-descriptions-item label="客户名">{{infoData.cusName}}</el-descriptions-item>
          <el-descriptions-item label="手机号">{{infoData.phone}}</el-descriptions-item>
          <el-descriptions-item label="流水备注">{{infoData.accountRemark}}</el-descriptions-item>
          <el-descriptions-item label="订单备注">{{infoData.orderRemark}}</el-descriptions-item>
          <el-descriptions-item label="交易金额">{{infoData.accountAmount}}</el-descriptions-item>
          <el-descriptions-item label="支付类型">
            <el-tag size="small" v-if="infoData.payType === '0'">本站支付</el-tag>
            <el-tag size="small" v-else-if="infoData.payType === '1'" type="warning">网银支付</el-tag>
            <el-tag size="small" v-else-if="infoData.payType === '2'" type="warning">现金支付</el-tag>
            <el-tag size="small" v-else-if="infoData.payType === '3'" type="danger">退款</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="书名" :span="2">{{infoData.bookNames}}</el-descriptions-item>
          <el-descriptions-item label="数量">{{infoData.bookCount}}</el-descriptions-item>
          <el-descriptions-item label="订单状态">
            <el-tag size="small" v-if="infoData.orderState === '0'">完成支付</el-tag>
            <el-tag size="small" v-else-if="infoData.orderState === '1'" type="danger">欠款</el-tag>
            <el-tag size="small" v-else-if="infoData.orderState === '2'" type="info">退款完成</el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </el-dialog>
<!--      编辑流水-->
      <el-dialog title="流水编辑" :visible.sync="dialogEdit">
        <el-form :model="infoData">
          <el-form-item label="流水备注" :label-width="'120px'">
            <el-input v-model="infoData.accountRemark" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="支付类型" :label-width="'120px'">
<!--            <el-input v-model="infoData.payType" autocomplete="off"></el-input>-->
            <el-select v-model="infoData.payType" placeholder="----" style="width: 200px;float: left">
            <el-option label="本站支付" value="0"></el-option>
            <el-option label="网银支付" value="1"></el-option>
            <el-option label="现金支付" value="2"></el-option>
            <el-option label="退款" value="3"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogEdit = false">取 消</el-button>
          <el-button type="primary" @click="editCommit">确 定</el-button>
        </div>
      </el-dialog>
<!--      退款dialog-->
      <el-dialog :visible.sync="dialogRefund">
        <el-form :model="refundRemark">
          <el-form-item label="备注" :label-width="'120px'">
            <el-input v-model="refundRemark.marker" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogRefund = false">取 消</el-button>
          <el-button type="primary" @click="refundCommit">确 定</el-button>
        </div>
      </el-dialog>

      <el-table
          :data="tableData.slice((page-1)*limit,page*limit)"
          stripe
          style="width: 1100px"
          :header-row-style="{height:'30px'}"
          max-height="600"
      >
        <el-table-column
            prop="id"
            type="selection"
        >
        </el-table-column>
        <el-table-column
            fixed
            prop="accountNum"
            label="流水号"
            width="150">
        </el-table-column>
        <el-table-column
            fixed
            prop="orderNum"
            label="订单号"
            width="150">
        </el-table-column>
        <el-table-column
            prop="cusName"
            label="客户名"
            width="120">
        </el-table-column>
        <el-table-column
            prop="accountAmount"
            label="交易金额"
            width="130">
        </el-table-column>
        <el-table-column
            prop="payType"
            label="支付类型"
            width="130">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="手机号"
            width="130">
        </el-table-column>
        <el-table-column
            prop="accountRemark"
            label="流水备注"
            width="180">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="180">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="accountInfo(scope.row)">详细</el-button>
            <!--分割线-->
            <el-divider direction="vertical"></el-divider>
            <el-button type="text" size="small" @click="editAction(scope.row)">编辑</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button type="text" size="small" @click="refund(scope.row)" v-if="scope.row.payType !== '退款'">退款</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="block" style="float: left">
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
</template>

<script>
export default {
  name: "AccountRecord",
  data() {
    return {
      form: {
        accountNum:null,
        begin: null,
        end: null,
        current: 1,
        cusName:null ,
        orderNum: null,
        pageSize: 999,
        payType:null,
        phone:null
      },
      // 表格数据
      tableData:[],
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
      refundDTO:{},
      infoData: {},
      dialogInfo: null,
      dialogEdit: null,
      refundRemark: {},
      dialogRefund: null,
      timeVal: null,
      limit: 10,
      page: 1,
    }
  },
  methods: {
    // 查询
    onSubmit() {
      if(this.timeVal !== null) {
        this.form.begin = this.timeVal[0]
        this.form.end = this.timeVal[1].slice(0,11) + '23:59:59'
      }
      this.$http.post("/api/account/query", this.form).then(
          res => {
            this.tableData = res.data.data
          }
      )
      this.clearSubmit()
    },
    // 清除输入框
    clearSubmit() {
      this.form.accountNum=null
      this.form.begin= null
      this.form.end= null
      this.form.cusName=null
      this.form.orderNum= null
      this.form.payType=null
      this.form.phone=null
      this.timeVal = null
    },
    // 获取详细信息
    accountInfo(val) {
      let id = val.id
      this.$http.post("/api/account/info",{id: id}).then(
          res => {
            this.infoData = res.data.data
          }
      )
      this.dialogInfo = true
    },
    // 编辑流水
    editAction(val) {
      this.infoData = val
      this.dialogEdit = true
    },
    // 退款
    refund(val) {
      console.log(val)
      this.dialogRefund = true
      val.accountRemark = this.refundRemark.marker
      this.refundDTO = val
    },
    refundCommit() {
      this.refundDTO.accountRemark = this.refundRemark.marker
      this.$http.post("/api/employee/refund",this.refundDTO).then(
          res => {
            this.$message({
              message: "退款成功",
              type: 'success',
              duration: 1000,
              onClose: () => {
                this.dialogRefund = false
                this.onSubmit()
              }
            })
          }
      )
    },
    // 提交编辑
    editCommit() {
      this.dialogEdit = false
      this.$http.post("/api/account/update", {
        id: this.infoData.id,
        accountRemark: this.infoData.accountRemark,
        payType: this.infoData.payType
      }).then(
          res => {
            this.$message({
              message:'流水修改成功',
              type: 'success',
              duration: 1000,
              onClose: () => {
                this.onSubmit()
              }
            })
          }
      )
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
    this.onSubmit()
  }
}
</script>

<style scoped>
  .el-select {
    float: left;
  }
  .el-form-item {
    width: 300px;
    float: left;
  }
  .AccountForm {
    height: 200px;
    width: 100%;

  }
  .AccountList {
    width: 100%;

  }
</style>
