<template>
<!--图书列表（后台）-->
  <div>
<!--    表单-->
    <div class="bookTable">
      <el-form ref="form" :model="form" label-width="80px">
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
        <el-form-item label="启用">
          <el-select v-model="form.enabled" placeholder="是否启用">
            <el-option label="启用" value=true></el-option>
            <el-option label="未启用" value=false></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="书类">
          <el-select v-model="form.category" placeholder="书类">
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
        </el-form-item>
      </el-form>
    </div>
<!--    添加书本-->
    <div class="bookList">
      <el-button @click="dialogFormVisible = true">新增书本</el-button>
      <el-dialog title="新增书本" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="ISBN" label-width="120px">
            <el-input v-model="createBook.isbn" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="书名" label-width="120px">
            <el-input v-model="createBook.bookName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="作者" label-width="120px">
            <el-input v-model="createBook.author" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="出版社" label-width="120px">
            <el-input v-model="createBook.publication" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="数量" label-width="120px">
            <el-input v-model="createBook.stock" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="单价" label-width="120px">
            <el-input v-model="createBook.price" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="是否启用" label-width="120px">
            <el-select v-model="createBook.enabled" placeholder="----">
              <el-option label="启用" value="shanghai"></el-option>
              <el-option label="未启用" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="书类" label-width="120px">
            <el-select v-model="createBook.category" placeholder="----">
              <el-option label="教科书" value="教科书"></el-option>
              <el-option label="小说" value="小说"></el-option>
              <el-option label="工具书" value="工具书"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addBook">添 加</el-button>
        </div>
      </el-dialog>

<!--      书本编辑-->
      <el-dialog :title="infoBook.bookName" :visible.sync="dialogEdit">
        <el-form :model="infoBook">
          <el-form-item label="单价" :label-width="'120px'">
            <el-input v-model="infoBook.price" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="数量" :label-width="'120px'">
            <el-input v-model="infoBook.stock" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogEdit = false">取 消</el-button>
          <el-button type="primary" @click="editCommit">确 定</el-button>
        </div>
      </el-dialog>

<!--是否启用的dialog-->
<!--      <el-dialog title="不启用原因" :visible.sync="dialogEnabled">-->
<!--        <el-form :model="enabledDTO">-->
<!--          <el-form-item label="活动名称" :label-width="'120px'">-->
<!--            <el-input v-model="enabledDTO.notEnabledReason" autocomplete="off"></el-input>-->
<!--          </el-form-item>-->
<!--        </el-form>-->
<!--        <div slot="footer" class="dialog-footer">-->
<!--          <el-button @click="dialogEnabled = false">取 消</el-button>-->
<!--          <el-button type="primary" @click="commitEnabled">确 定</el-button>-->
<!--        </div>-->
<!--      </el-dialog>-->

<!--      书本详细-->
      <el-dialog :visible.sync="dialogInfo">
        <el-descriptions title="书本详细" direction="vertical" :column="3" border>
          <el-descriptions-item label="书名">{{infoBook.bookName}}</el-descriptions-item>
          <el-descriptions-item label="图片">
            <img :src="infoBook.picUrl" alt min-width="100" height="70"/>
          </el-descriptions-item>
          <el-descriptions-item label="ISBN">{{infoBook.isbn}}</el-descriptions-item>
          <el-descriptions-item label="作者">{{infoBook.author}}</el-descriptions-item>
          <el-descriptions-item label="出版社">{{infoBook.publication}}</el-descriptions-item>
          <el-descriptions-item label="库存">{{infoBook.stock}}</el-descriptions-item>
          <el-descriptions-item label="价格">{{infoBook.price}}</el-descriptions-item>
          <el-descriptions-item label="书类" :span="2">{{infoBook.category}}</el-descriptions-item>
          <el-descriptions-item label="启用情况">
            <el-tag size="small" v-if="infoBook.enabled !== false">启用</el-tag>
            <el-tag size="small" v-else type="danger">未启用</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="不启用原因" :span="2">{{infoBook.notEnabledReason}}</el-descriptions-item>
          <el-descriptions-item label="简介" :span="3">{{infoBook.brief}}</el-descriptions-item>
        </el-descriptions>
      </el-dialog>


<!--      表格-->
      <el-table
          :data="tableData.slice((page-1)*limit,page*limit)"
          border
          style="width: 1100px"
          :header-row-style="{height:'30px'}"
          :row-style="{height: '80px'}"
      >
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
            prop="enabled"
            label="未启用/启用"
            width="120">
          <template slot-scope="scope">
            <el-switch
                v-model="tableData[scope.$index].enabled"
                active-color="#13ce66"
                inactive-color="#ff4949"
                @change="enabledIt(scope.row)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            width="150">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button type="text" size="small" @click="editBook(scope.row)">编辑</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button type="text" size="small" @click="removeBook(scope.row)">删除</el-button>
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
  name: "Man-BookList",
  data() {
    return {
      form: {
        author:null ,
        bookName:null,
        category:null,
        isbn:null,
        publication: null,
        enabled: null,
        pageSize: 9999,
        current: 1
      },
      // 书本详细dialog
      dialogInfo: null,
      dialogEnabled: null,
      dialogEdit: null,
      // 启用dto
      enabledDTO: {
        id: null,
        enabled: null,
        notEnabledReason: null
      },
      dialogFormVisible: false,
      // 添加书本的字段
      createBook: {
        author: null,
        bookName: null,
        brief: null,
        category: null,
        isbn: null,
        picUrl: null,
        price: null,
        publication: null,
        stock: null
      },
      // 书本详细的字段
      infoBook: {
        id: null,
        isbn: null,
        picUrl: null,
        bookName: null,
        author: null,
        publication: null,
        category: null,
        enabled: null,
        enabledTime: null,
        price: null,
        stock: null,
        brief: null ,
        notEnabledReason: null,
      },
      tableData: [],
      currentPage: 1,
      limit: 10,
      page: 1,
      bookList:[]
    }
  },
  methods: {
    // 书本列表查询
    onSubmit() {
      this.$http.post("/api/employee/getBooks", this.form).then(
          res => {
            this.tableData = res.data.data
            // this.bookList = this.tableData
          }
      )
      this.clearAction()
    },
    // 数据过滤
    getList() {
      this.bookList = this.bookList.filter((item, index) =>
          index < this.page * this.limit && index >= this.limit * (this.page - 1)
      )
      console.log(this.bookList)
    },
    // 重置表单
    clearAction() {
      this.form.author=null
      this.form.bookName=null
      this.form.category=null
      this.form.isbn=null
      this.form.publication=null
      this.form.enabled=null
    },
    // 添加书本
    addBook() {
      this.dialogFormVisible = false
      this.$http.post("/api/employee/bookAdd",this.createBook).then(
          res => {
            this.$message({
              message:'添加书本成功',
              type: 'success',
              duration: 1000
            })
          }
      )
    },
    // 删除图书
    removeBook(val) {
      this.$http.post("/api/employee/bookRemove", {
        id: val.id
      }).then(
          res => {
            this.$message({
              message:'删除书本成功',
              type: 'success',
              duration: 1000,
              onClose: () => {
                this.onSubmit()
              }
            })
          }
      )
    },
    commitEnabled() {
      this.dialogEnabled = false
      this.$http.post("/api/employee/isEnabled",this.enabledDTO).then(
          res => {
            this.$message({
              message:'设置成功',
              type: 'success',
              duration: 1000,
              onClose: () => {
                this.onSubmit()
              }
            })
          }
      )
    },
    // 编辑书本信息
    editBook(val) {
      this.dialogEdit = true
      // 赋值
      this.infoBook = JSON.parse(JSON.stringify(val, ['id','isbn','picUrl','bookName','author','publication','category',
        'enabled','enabledTime','price','stock','brief','notEnabledReason']))
    },
    // 提交书本详情（修改）
    editCommit() {
      this.$http.post("/api/employee/bookUpdate", {
        id: this.infoBook.id,
        price: this.infoBook.price,
        stock: this.infoBook.stock
      }).then(
          res => {
            this.$message({
              message:'修改书本'+this.infoBook.bookName+'成功',
              type: 'success',
              duration: 1500
            })
            this.onSubmit()
          }
      )
      this.dialogEdit = false

    },
    // 书本详细
    handleClick(val) {
      this.dialogInfo = true
      // 赋值
      this.infoBook = JSON.parse(JSON.stringify(val, ['isbn','picUrl','bookName','author','publication','category',
        'enabled','enabledTime','price','stock','brief','notEnabledReason']))
    },
    // 书本启用
    enabledIt(e) {
      console.log(e)
      this.$http.post("/api/employee/isEnabled",{id: e.id, enabled: e.enabled}).then(
          res => {
            this.$message({
              message:'操作成功',
              type: 'success',
              duration: 1000
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
  watch: {
    tableData: {
      // 这里深度监听变化，直接触发下面方法
      handler(curVal, oldVal) {
        console.log(curVal)
      },
      deep: true,
      immediate: true // 是否第一次触发
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
  .bookTable {
    height: 100px;
    width: 100%;
    float: left;
  }
  .bookList {
    height: 300px;
    width: 100%;
    float: left;
  }
  .upload-demo {
    height: 200px;
  }
</style>
