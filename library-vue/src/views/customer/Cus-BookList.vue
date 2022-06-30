<template>
  <div>
<!--    表单-->
    <div class="bookForm">
      <el-form ref="form" :model="form" label-width="54px">
        <el-form-item label="ISBN">
          <el-input v-model="form.isbn" style="width: 200px;float: left"></el-input>
        </el-form-item>
        <el-form-item label="书名">
          <el-input v-model="form.bookName" style="width: 200px;float: left"></el-input>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="form.author" style="width: 200px;float: left"></el-input>
        </el-form-item>
        <el-form-item label="出版社">
          <el-input v-model="form.publication" style="width: 200px;float: left"></el-input>
        </el-form-item>
        <el-form-item label="书类">
          <el-select v-model="form.category" placeholder="------" style="float: left">
            <el-option label="教科书" value="教科书"></el-option>
            <el-option label="小说" value="小说"></el-option>
            <el-option label="工具书" value="工具书"></el-option>
            <el-option label="世界名著" value="世界名著"></el-option>
            <el-option label="随笔" value="随笔"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
          <el-button @click="clearAction">重置</el-button>
          <el-tooltip content="下单" placement="top">
            <el-button type="success" icon="el-icon-goods" @click="countAction"></el-button>
          </el-tooltip>
        </el-form-item>
      </el-form>
    </div>

    <!--    下单dialog-->
    <el-dialog title="下单" :visible.sync="dialogFormVisible">
      <el-form :model="purchaseDTO">
        <el-form-item label="应付金额" :label-width="'120px'">
          <el-input v-model="purchaseDTO.orderAmount" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="实付金额" :label-width="'120px'">
          <el-input v-model="purchaseDTO.realityAmount" autocomplete="off" :disabled="this.purchaseDTO.payType === '0'"></el-input>
        </el-form-item>
        <el-form-item label="订单备注" :label-width="'120px'">
          <el-input v-model="purchaseDTO.orderRemark" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="支付类型" :label-width="'120px'">
          <el-select v-model="purchaseDTO.payType" placeholder="-------" @change="selectChange">
            <el-option label="本站支付" value="0"></el-option>
            <el-option label="网银支付" value="1"></el-option>
            <el-option label="现金支付" value="2"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="purchaseAction">确 定</el-button>
      </div>
    </el-dialog>

<!--    表格-->
    <div class="bookList">
      <el-table
          :data="tableData.slice((page-1)*limit,page*limit)"
          border
          ref="multipleTable"
          style="width: 1100px"
          :header-row-style="{height:'30px'}"
          :row-style="{height: '80px'}"
          @selection-change="handleSelection"
      >
        <el-table-column
            prop="id"
            type="selection"
        ></el-table-column>
        <el-table-column
            fixed
            prop="isbn"
            label="ISBN"
            width="150">
        </el-table-column>
        <el-table-column
            prop="picUrl"
            label="图片"
            width="120">
          <template slot-scope="scope">
            <img :src="scope.row.picUrl" alt min-width="100" height="70"/>
          </template>
        </el-table-column>
        <el-table-column
            prop="bookName"
            label="书名"
            width="120">
        </el-table-column>
        <el-table-column
            prop="author"
            label="作者"
            width="120">
        </el-table-column>
        <el-table-column
            prop="publication"
            label="出版社"
            width="120">
        </el-table-column>
        <el-table-column
            prop="stock"
            label="库存"
            width="120">
        </el-table-column>
        <el-table-column
            prop="price"
            label="价格"
            width="120">
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="210">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small">详细</el-button>
            <el-divider direction="vertical"></el-divider>
            数量<el-input-number v-model="scope.row.num" controls-position="right" @change="handleChange" :min="0" :max="200"></el-input-number>
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
  name: "Cus-BookList",
  data() {
    return {
      form: {
        isbn: null,
        bookName: null,
        author: null,
        publication: null,
        category: null,
        pageSize: 9999,
        current: 1
      },
      purchaseDTO: {
        count: [],
        // 书本的id
        ids: [],
        cusId: null,
        orderAmount: null,
        orderRemark: null,
        payType: null,
        realityAmount: null
      },
      flag: false, // 查看是否存在没有选择书本数量
      dialogFormVisible: null,
      tableData: [],
      multipleSelection: [], // 多选框中的数据
      limit: 10,
      page: 1,
      // 数字选择器
      num: 0
    }
  },
  methods: {
    // 提交
    onSubmit() {
      this.$http.post('/api/customer/getBooks', this.form).then(
          res => {
            this.tableData = res.data.data
            this.clearAction()
          }
      )
    },
    // 清除表单
    clearAction() {
      this.form.isbn= null
      this.form.bookName= null
      this.form.author= null
      this.form.publication= null
      this.form.category= null
    },
    // 书本详细
    handleClick(val) {

    },
    selectChange() {
      if(this.purchaseDTO.payType === '0') {
        this.purchaseDTO.realityAmount = parseInt(this.purchaseDTO.orderAmount) * 0.8
        this.purchaseDTO.realityAmount = this.purchaseDTO.realityAmount.toFixed(2)
      }
      else {
        this.purchaseDTO.realityAmount = this.purchaseDTO.orderAmount
      }
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.page = 1
      this.limit = val
    },
    // 或者当前的用户的信息
    getPersonal() {
      this.$http.get("/api/customer/personal").then(
          res => {
            this.purchaseDTO.cusId = res.data.data.id
          }
      )
    },
    purchaseAction() {
      console.log(this.multipleSelection)
      console.log('后面')
      console.log(this.purchaseDTO)
      this.$http.post('/api/customer/purchase', this.purchaseDTO).then(
          res => {
            this.$message({
              message: '购买书本成功',
              type: 'success',
              duration: 1500,
              // 回调函数
              onClose: () => {
                this.onSubmit()
                this.purchaseDTO.ids = []
                this.purchaseDTO.cusId= null
                this.purchaseDTO.orderAmount = null
                this.purchaseDTO.orderRemark = null
                this.purchaseDTO.payType = null
                this.purchaseDTO.realityAmount = null
                this.dialogFormVisible = false
              }
            })
          }
      )
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.page = val
    },
    // 数字选择器
    handleChange(value) {
      // console.log(value);
    },
    // 左侧边栏【选中的才能下单】
    handleSelection(val) {
      this.multipleSelection = val
    },
    // 计算过程
    countAction() {
      // 应付金额
      this.getPersonal()
      let Amount = 0
      for(let i = 0; i < this.multipleSelection.length; i++) {
        this.purchaseDTO.ids.push(this.multipleSelection[i].id)
        if(isNaN(parseInt(this.multipleSelection[i].num))) {
          Amount = Amount + parseInt(this.multipleSelection[i].price) * parseInt("0")
          this.purchaseDTO.count.push(0)
        }else {
          Amount = Amount + parseInt(this.multipleSelection[i].price) * parseInt(this.multipleSelection[i].num)
          this.purchaseDTO.count.push(this.multipleSelection[i].num)
        }
      }
      console.log(Amount)
      this.purchaseDTO.orderAmount = Amount
      this.dialogFormVisible = true
      console.log(this.purchaseDTO)
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
.bookForm {
  height: 130px;
  width: 100%;
  /*float: left;*/
}
</style>
